import XCTest
@testable import RBDarts

final class LaunchDiagnosticsPrivacyTests: XCTestCase {
    func testDiagnosticsRemoveSensitiveLaunchAttributes() {
        let diagnostics = PrivacySafeDiagnostics()

        diagnostics.record(DiagnosticEvent(
            name: "launch.sign_in",
            severity: .info,
            attributes: [
                "provider": "google",
                "email": "tester@example.com",
                "accessToken": "redacted"
            ]
        ))

        XCTAssertEqual(diagnostics.events.first?.attributes["provider"], "google")
        XCTAssertNil(diagnostics.events.first?.attributes["email"])
        XCTAssertNil(diagnostics.events.first?.attributes["accessToken"])
    }
}
