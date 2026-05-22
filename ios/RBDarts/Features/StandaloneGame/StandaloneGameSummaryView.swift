import SwiftUI

struct StandaloneGameSummaryView: View {
    let summary: GameSummary

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Final")
                .font(.headline)
            if summary.winnerIds.isEmpty {
                Text("No winner yet")
            } else {
                Text("Winner: \(summary.winnerIds.joined(separator: ", "))")
            }
            Text("Margin: \(summary.margin)")
            Text("Innings played: \(summary.inningsPlayed)")
        }
    }
}
