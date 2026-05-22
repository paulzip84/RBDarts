import XCTest
@testable import RBDarts

final class ReleaseConfigurationTests: XCTestCase {
    func testDefaultProductionConfigurationIsLaunchValid() throws {
        let configuration = ReleaseConfiguration.defaultProduction

        XCTAssertTrue(configuration.isProduction)
        XCTAssertNoThrow(try configuration.validateForLaunch())
        XCTAssertEqual(configuration.supportURL.scheme, "https")
        XCTAssertEqual(configuration.privacyPolicyURL.scheme, "https")
        XCTAssertEqual(configuration.accountDeletionURL.scheme, "https")
    }

    func testDictionaryConfigurationRejectsInsecureUrls() {
        XCTAssertThrowsError(try ReleaseConfiguration.from(dictionary: [
            "AppEnvironment": "production",
            "AppVersion": "0.1.0",
            "BuildNumber": "1",
            "SupportURL": "http://rbdarts.app/support",
            "PrivacyPolicyURL": "https://rbdarts.app/privacy",
            "AccountDeletionURL": "https://rbdarts.app/account/delete",
            "DiagnosticsEnabled": true
        ]))
    }

    func testLaunchValidationRejectsNonProductionEnvironment() {
        let configuration = ReleaseConfiguration(
            appEnvironment: "staging",
            appVersion: "0.1.0",
            buildNumber: "1",
            supportURL: URL(string: "https://rbdarts.app/support")!,
            privacyPolicyURL: URL(string: "https://rbdarts.app/privacy")!,
            accountDeletionURL: URL(string: "https://rbdarts.app/account/delete")!,
            diagnosticsEnabled: true
        )

        XCTAssertThrowsError(try configuration.validateForLaunch())
    }
}
