import XCTest
@testable import RBDarts

final class LeagueMatchTests: XCTestCase {
    func testLeagueHandicapAndStandingsRanking() async throws {
        let service = LeagueService(repository: InMemoryRBDartsRepository())
        let league = try await service.createLeague(name: "League", gamesPerMatch: 3, playersPerTeamPerGame: 4, handicapPercent: 80)

        let handicap = service.handicapForGame(
            homeTeamId: "home",
            awayTeamId: "away",
            homeAverageSum: 72,
            awayAverageSum: 63.4,
            league: league
        )

        XCTAssertEqual(handicap.lowerAverageTeamId, "away")
        XCTAssertEqual(handicap.handicap, 7)

        let ranked = service.standings([
            TeamStanding(teamId: "b", leaguePoints: 12, gamesWon: 6, pointDifferential: 12, totalAdjustedScore: 320, totalRawScore: 318),
            TeamStanding(teamId: "a", leaguePoints: 12, gamesWon: 6, pointDifferential: 24, totalAdjustedScore: 310, totalRawScore: 300)
        ])
        XCTAssertEqual(ranked.map(\.teamId), ["a", "b"])
    }
}
