import Foundation

public struct ProjectionResult: Equatable, Sendable {
    public let label: String
    public let projectedFinalScore: Double
}

public struct ProjectionService: Sendable {
    public init() {}

    public func baseline(currentScore: Int, inningsPlayed: Int, regulationInnings: Int = 9) -> ProjectionResult {
        ProjectionResult(
            label: "Estimate",
            projectedFinalScore: ScoringRules.projectedFinalScore(
                currentScore: currentScore,
                inningsPlayed: inningsPlayed,
                regulationInnings: regulationInnings
            )
        )
    }
}
