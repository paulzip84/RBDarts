import SwiftUI

struct LeagueSettingsView: View {
    @State private var leagueName = "Wednesday Baseball Darts"
    @State private var gamesPerMatch = 3
    @State private var playersPerTeam = 4
    @State private var handicapPercent = 80.0

    var body: some View {
        Form {
            TextField("League name", text: $leagueName)
            Stepper("Games per match: \(gamesPerMatch)", value: $gamesPerMatch, in: 1...12)
            Stepper("Players per team: \(playersPerTeam)", value: $playersPerTeam, in: 1...8)
            Slider(value: $handicapPercent, in: 0...100, step: 1) {
                Text("Handicap")
            }
            Text("Handicap \(Int(handicapPercent))%")
        }
        .navigationTitle("League Settings")
    }
}
