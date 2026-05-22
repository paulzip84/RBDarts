import Foundation

public struct StatsDiagnostics {
    private let reporter: DiagnosticsReporting

    public init(reporter: DiagnosticsReporting = PrivacySafeDiagnostics()) {
        self.reporter = reporter
    }

    public func recordStatsLoadingFailure(scope: String) {
        reporter.record(DiagnosticEvent(name: "stats_loading_failed", severity: .error, attributes: ["scope": scope]))
    }

    public func recordProjectionFailure(scope: String) {
        reporter.record(DiagnosticEvent(name: "stats_projection_failed", severity: .warning, attributes: ["scope": scope]))
    }

    public func recordStaleOfflineData(scope: String) {
        reporter.record(DiagnosticEvent(name: "stats_stale_offline_data", severity: .info, attributes: ["scope": scope]))
    }
}
