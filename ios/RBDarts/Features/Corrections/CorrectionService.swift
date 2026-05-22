import Foundation

public struct CorrectionService: Sendable {
    public init() {}

    public func requestCorrection(
        score: InningScore,
        newValue: Int,
        reason: String,
        requestedByUserId: String,
        requestedByRole: LeagueRole
    ) throws -> CorrectionAuditRecord {
        let (_, audit) = try ScoringRules.applyCorrection(
            score: score,
            newValue: newValue,
            reason: reason,
            editedByUserId: requestedByUserId,
            editedByRole: requestedByRole,
            affectedRecords: [score.gameId, score.playerId, "standings"]
        )
        var requested = audit
        requested.status = .requested
        requested.appliedAt = nil
        return requested
    }
}
