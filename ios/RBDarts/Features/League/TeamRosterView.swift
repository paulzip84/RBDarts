import SwiftUI

struct TeamRosterView: View {
    @State private var teamName = "Home Team"
    @State private var players = ["Player 1", "Player 2", "Player 3", "Player 4"]

    var body: some View {
        List {
            Section("Team") {
                TextField("Team name", text: $teamName)
            }
            Section("Roster") {
                ForEach(players.indices, id: \.self) { index in
                    TextField("Player \(index + 1)", text: $players[index])
                }
                Button("Add roster player") {
                    players.append("Player \(players.count + 1)")
                }
            }
        }
        .navigationTitle("Roster")
    }
}
