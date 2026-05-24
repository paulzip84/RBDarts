import XCTest

final class LaunchReadinessSmokeTests: XCTestCase {
    func testLaunchReadinessSurfaceIsReachable() throws {
        let app = XCUIApplication()
        app.launch()

        XCTAssertTrue(app.textFields["Game name"].waitForExistence(timeout: 3))
        XCTAssertTrue(app.buttons["Privacy"].exists)

        app.buttons["Privacy"].tap()
        XCTAssertTrue(app.staticTexts["Privacy Policy"].waitForExistence(timeout: 3))
        XCTAssertTrue(app.staticTexts["Account Deletion"].exists)
    }
}
