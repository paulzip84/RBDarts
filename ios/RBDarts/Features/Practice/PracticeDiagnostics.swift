import Foundation

public struct PracticeDiagnostics {
    private let reporter: DiagnosticsReporting

    public init(reporter: DiagnosticsReporting = PrivacySafeDiagnostics()) {
        self.reporter = reporter
    }

    public func recordInvalidPracticeScore(target: Int) {
        reporter.record(DiagnosticEvent(name: "practice_invalid_score", severity: .warning, attributes: ["target": "\(target)"]))
    }

    public func recordSaveFailure(target: Int) {
        reporter.record(DiagnosticEvent(name: "practice_save_failed", severity: .error, attributes: ["target": "\(target)"]))
    }
}
