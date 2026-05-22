import SwiftUI

struct PracticeView: View {
    @State private var target = 5
    @State private var scores: [Int] = []

    private var stats: PracticeStats {
        let attempts = scores.enumerated().map { index, score in
            PracticeAttempt(
                practiceAttemptId: "practice-\(index)",
                playerId: "local-player",
                userId: "local-user",
                target: target,
                score: score,
                attemptDate: Date(),
                createdAt: Date()
            )
        }
        return ScoringRules.practiceStats(from: attempts)
    }

    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            Stepper("Target \(target)", value: $target, in: 1...20)
            LazyVGrid(columns: Array(repeating: GridItem(.fixed(RBDartsDesignTokens.compactScoreButtonSize)), count: 5), spacing: 8) {
                ForEach(0...9, id: \.self) { score in
                    Button("\(score)") {
                        scores.append(score)
                    }
                    .buttonStyle(.borderedProminent)
                }
            }
            Text("Attempts \(stats.attempts)")
            Text("Average \(stats.average, specifier: "%.1f")")
            Text("Best \(stats.bestScore)")
        }
        .padding()
        .navigationTitle("Practice")
    }
}
