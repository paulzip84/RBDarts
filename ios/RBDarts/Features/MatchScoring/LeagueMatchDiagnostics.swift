import Foundation

public struct LeagueMatchDiagnostics {
    private let reporter: DiagnosticsReporting

    public init(reporter: DiagnosticsReporting = PrivacySafeDiagnostics()) {
        self.reporter = reporter
    }

    public func recordPermissionDenied(action: String) {
        reporter.record(DiagnosticEvent(name: "league_permission_denied", severity: .warning, attributes: ["action": action]))
    }

    public func recordLockTransition(gameId: String) {
        reporter.record(DiagnosticEvent(name: "league_game_locked", severity: .info, attributes: ["gameId": gameId]))
    }

    public func recordFinalizeFailure(matchId: String) {
        reporter.record(DiagnosticEvent(name: "league_finalize_failed", severity: .error, attributes: ["matchId": matchId]))
    }
}
