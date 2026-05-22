import SwiftUI

struct MatchSetupView: View {
    @State private var homeTeam = "Home Team"
    @State private var awayTeam = "Away Team"

    var body: some View {
        Form {
            TextField("Home team", text: $homeTeam)
            TextField("Away team", text: $awayTeam)
            Text("Lineup averages are snapshotted when the first game starts.")
                .foregroundStyle(.secondary)
        }
        .navigationTitle("Match Setup")
    }
}
