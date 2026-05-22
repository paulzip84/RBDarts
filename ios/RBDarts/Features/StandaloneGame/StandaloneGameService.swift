import Foundation

public actor StandaloneGameService {
    private let repository: RBDartsRepository
    private let sessionStore: ActiveScoreSessionStoring

    public init(
        repository: RBDartsRepository = InMemoryRBDartsRepository(),
        sessionStore: ActiveScoreSessionStoring = FileActiveScoreSessionStore()
    ) {
        self.repository = repository
        self.sessionStore = sessionStore
    }

    public func createGame(
        name: String,
        createdByUserId: String,
        participantNames: [String],
        regulationInnings: Int = ScoringRules.defaultRegulationInnings,
        extraInningsEnabled: Bool = true
    ) async throws -> ActiveScoreSession {
        let now = Date()
        let game = StandaloneGame(
            standaloneGameId: UUID().uuidString,
            createdByUserId: createdByUserId,
            gameName: name.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty ? "Standalone Game" : name,
            gameDate: now,
            participantType: .singles,
            regulationInnings: regulationInnings,
            extraInningsEnabled: extraInningsEnabled,
            status: .scoring,
            createdAt: now,
            completedAt: nil
        )
        let participants = participantNames
            .filter { !$0.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty }
            .enumerated()
            .map { index, name in
                ParticipantScorecard(
                    participantId: "participant-\(index + 1)",
                    displayName: name.trimmingCharacters(in: .whitespacesAndNewlines)
                )
            }
        let safeParticipants = participants.isEmpty ? [
            ParticipantScorecard(participantId: "participant-1", displayName: "Player 1")
        ] : participants

        let session = ActiveScoreSession(
            sessionId: game.standaloneGameId,
            updatedAt: now,
            game: game,
            participants: safeParticipants,
            pendingSyncRequestIds: []
        )
        try sessionStore.save(session)
        try await repository.createStandaloneGame(game, participants: safeParticipants)
        return session
    }

    public func enterScore(
        _ score: Int,
        participantId: String,
        inningNumber: Int,
        in session: ActiveScoreSession
    ) async throws -> ActiveScoreSession {
        var updated = session
        guard let index = updated.participants.firstIndex(where: { $0.participantId == participantId }) else {
            return session
        }
        try ScoringRules.recordScore(
            score,
            inningNumber: inningNumber,
            for: &updated.participants[index]
        )
        updated.updatedAt = Date()
        try sessionStore.save(updated)
        return updated
    }

    public func undoScore(
        participantId: String,
        inningNumber: Int,
        in session: ActiveScoreSession
    ) throws -> ActiveScoreSession {
        var updated = session
        if let index = updated.participants.firstIndex(where: { $0.participantId == participantId }) {
            updated.participants[index].scoresByInning.removeValue(forKey: inningNumber)
            updated.updatedAt = Date()
            try sessionStore.save(updated)
        }
        return updated
    }

    public func complete(_ session: ActiveScoreSession) async throws -> GameSummary {
        var completed = session
        completed.game.status = .complete
        completed.game.completedAt = Date()
        let summary = ScoringRules.summarizeStandalone(
            participants: completed.participants,
            regulationInnings: completed.game.regulationInnings,
            extraInningsEnabled: completed.game.extraInningsEnabled
        )
        try sessionStore.save(completed)
        try await repository.saveStandaloneSummary(summary, for: completed.game.standaloneGameId)
        return summary
    }

    public func recoverMostRecentSession() throws -> ActiveScoreSession? {
        try sessionStore.loadMostRecent()
    }
}
