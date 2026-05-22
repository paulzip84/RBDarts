import SwiftUI

struct StandaloneScorecardView: View {
    let summary: GameSummary

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            ForEach(summary.participantSummaries, id: \.participantId) { participant in
                HStack {
                    Text(participant.displayName)
                    Spacer()
                    Text("\(participant.total)")
                        .font(.title3.monospacedDigit().weight(.semibold))
                    Text("0s \(participant.zeroCount)")
                        .foregroundStyle(.secondary)
                    Text("9s \(participant.nineCount)")
                        .foregroundStyle(.secondary)
                }
                .padding(8)
                .background(RBDartsDesignTokens.surfaceColor)
                .clipShape(RoundedRectangle(cornerRadius: RBDartsDesignTokens.cornerRadius))
            }

            if summary.needsExtraInning {
                Text("Tie after regulation. Continue to the next inning.")
                    .foregroundStyle(RBDartsDesignTokens.warningColor)
            }
        }
    }
}
