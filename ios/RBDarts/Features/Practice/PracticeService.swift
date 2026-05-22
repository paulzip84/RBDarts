import Foundation

public struct PracticeService: Sendable {
    public init() {}

    public func validate(target: Int, score: Int) throws {
        try ScoringRules.validate(inningNumber: target)
        try ScoringRules.validate(score: score)
    }

    public func stats(for attempts: [PracticeAttempt]) -> PracticeStats {
        ScoringRules.practiceStats(from: attempts)
    }
}
