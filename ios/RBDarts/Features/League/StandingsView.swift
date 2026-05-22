import SwiftUI

struct StandingsView: View {
    let standings: [TeamStanding]

    var body: some View {
        List(ScoringRules.rankedStandings(standings), id: \.teamId) { standing in
            HStack {
                Text(standing.teamId)
                Spacer()
                Text("\(standing.leaguePoints, specifier: "%.1f") pts")
                Text("W \(standing.gamesWon)")
            }
        }
        .navigationTitle("Standings")
    }
}
