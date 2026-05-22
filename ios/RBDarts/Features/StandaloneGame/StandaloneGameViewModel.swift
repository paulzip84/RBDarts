import Foundation
import Observation

@MainActor
@Observable
public final class StandaloneGameViewModel {
    public var playerNames: [String] = ["Player 1", "Player 2"]
    public var gameName: String = ""
    public var currentInning: Int = 1
    public var session: ActiveScoreSession?
    public var summary: GameSummary?
    public var errorMessage: String?
    public var isSaving: Bool = false

    private let service: StandaloneGameService
    private let diagnostics: StandaloneGameDiagnostics

    public init(
        service: StandaloneGameService = StandaloneGameService(),
        diagnostics: StandaloneGameDiagnostics = StandaloneGameDiagnostics()
    ) {
        self.service = service
        self.diagnostics = diagnostics
        recover()
    }

    public func startGame() {
        Task {
            do {
                isSaving = true
                session = try await service.createGame(
                    name: gameName,
                    createdByUserId: "local-user",
                    participantNames: playerNames
                )
                currentInning = 1
                summary = nil
                errorMessage = nil
            } catch {
                errorMessage = error.localizedDescription
            }
            isSaving = false
        }
    }

    public func enterScore(_ score: Int, for participantId: String) {
        guard let session else { return }
        Task {
            do {
                self.session = try await service.enterScore(
                    score,
                    participantId: participantId,
                    inningNumber: currentInning,
                    in: session
                )
                diagnostics.recordScoreAccepted(inning: currentInning)
                errorMessage = nil
            } catch {
                diagnostics.recordInvalidScore(inning: currentInning)
                errorMessage = error.localizedDescription
            }
        }
    }

    public func undo(participantId: String) {
        guard let session else { return }
        Task {
            do {
                self.session = try await service.undoScore(
                    participantId: participantId,
                    inningNumber: currentInning,
                    in: session
                )
            } catch {
                errorMessage = error.localizedDescription
            }
        }
    }

    public func advanceInning() {
        currentInning += 1
    }

    public func completeGame() {
        guard let session else { return }
        Task {
            do {
                summary = try await service.complete(session)
                diagnostics.recordCompletedGame()
                errorMessage = nil
            } catch {
                errorMessage = error.localizedDescription
            }
        }
    }

    public var liveSummary: GameSummary? {
        guard let session else { return nil }
        return ScoringRules.summarizeStandalone(
            participants: session.participants,
            regulationInnings: session.game.regulationInnings,
            extraInningsEnabled: session.game.extraInningsEnabled
        )
    }

    private func recover() {
        Task {
            do {
                session = try await service.recoverMostRecentSession()
                if session != nil {
                    diagnostics.recordRecovery()
                }
            } catch {
                errorMessage = error.localizedDescription
            }
        }
    }
}
