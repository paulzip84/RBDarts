import Foundation

public struct CorrectionDiagnostics {
    private let reporter: DiagnosticsReporting

    public init(reporter: DiagnosticsReporting = PrivacySafeDiagnostics()) {
        self.reporter = reporter
    }

    public func recordRequested(gameId: String) {
        reporter.record(DiagnosticEvent(name: "correction_requested", severity: .info, attributes: ["gameId": gameId]))
    }

    public func recordDenied(gameId: String) {
        reporter.record(DiagnosticEvent(name: "correction_denied", severity: .warning, attributes: ["gameId": gameId]))
    }

    public func recordRecalculationFailure(gameId: String) {
        reporter.record(DiagnosticEvent(name: "correction_recalculation_failed", severity: .error, attributes: ["gameId": gameId]))
    }
}
