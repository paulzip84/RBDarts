import SwiftUI

struct LeagueScoreEntryView: View {
    let handicap: HandicapResult
    let gameNumber: Int

    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text("Game \(gameNumber)")
                .font(.headline)
            if let lowerAverageTeamId = handicap.lowerAverageTeamId {
                Text("\(lowerAverageTeamId) receives \(handicap.handicap, specifier: "%.1f") handicap")
            } else {
                Text("No handicap")
            }
            Text("Starting the next game locks the prior completed game.")
                .foregroundStyle(RBDartsDesignTokens.warningColor)
        }
        .padding()
    }
}
