import SwiftUI

struct StandaloneGameSetupView: View {
    @State private var viewModel = StandaloneGameViewModel()

    var body: some View {
        List {
            Section("Game") {
                TextField("Game name", text: $viewModel.gameName)
                ForEach(viewModel.playerNames.indices, id: \.self) { index in
                    TextField("Player \(index + 1)", text: $viewModel.playerNames[index])
                }
                Button("Add player") {
                    viewModel.playerNames.append("Player \(viewModel.playerNames.count + 1)")
                }
                Button("Start scoring") {
                    viewModel.startGame()
                }
                .disabled(viewModel.isSaving)
            }

            if let session = viewModel.session {
                Section("Scoring") {
                    StandaloneScoreEntryView(viewModel: viewModel, session: session)
                }
            }

            if let summary = viewModel.summary ?? viewModel.liveSummary {
                Section("Scorecard") {
                    StandaloneScorecardView(summary: summary)
                }
            }

            if let summary = viewModel.summary {
                Section("Summary") {
                    StandaloneGameSummaryView(summary: summary)
                }
            }

            if let errorMessage = viewModel.errorMessage {
                Section {
                    Text(errorMessage)
                        .foregroundStyle(.red)
                }
            }
        }
    }
}
