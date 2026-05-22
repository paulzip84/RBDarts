import SwiftUI

struct StandaloneScoreEntryView: View {
    @Bindable var viewModel: StandaloneGameViewModel
    let session: ActiveScoreSession

    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            HStack {
                Text("Inning \(viewModel.currentInning)")
                    .font(.headline)
                Spacer()
                Button("Next") {
                    viewModel.advanceInning()
                }
                .buttonStyle(.bordered)
            }

            ForEach(session.participants, id: \.participantId) { participant in
                VStack(alignment: .leading, spacing: 8) {
                    HStack {
                        Text(participant.displayName)
                            .font(.subheadline.weight(.semibold))
                        Spacer()
                        Text("Total \(ScoringRules.participantTotal(participant))")
                            .monospacedDigit()
                    }
                    LazyVGrid(columns: Array(repeating: GridItem(.fixed(RBDartsDesignTokens.compactScoreButtonSize)), count: 5), spacing: 8) {
                        ForEach(0...9, id: \.self) { score in
                            Button("\(score)") {
                                viewModel.enterScore(score, for: participant.participantId)
                            }
                            .frame(width: RBDartsDesignTokens.compactScoreButtonSize, height: RBDartsDesignTokens.compactScoreButtonSize)
                            .buttonStyle(.borderedProminent)
                        }
                    }
                    Button("Undo inning score") {
                        viewModel.undo(participantId: participant.participantId)
                    }
                    .buttonStyle(.bordered)
                }
            }

            Button("Complete game") {
                viewModel.completeGame()
            }
            .buttonStyle(.borderedProminent)
        }
        .padding(.vertical, 8)
    }
}
