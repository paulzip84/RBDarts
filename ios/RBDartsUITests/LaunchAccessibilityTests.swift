import XCTest

final class LaunchAccessibilityTests: XCTestCase {
    func testPrimaryLaunchControlsExposeAccessibleLabels() throws {
        let app = XCUIApplication()
        app.launch()

        XCTAssertTrue(app.textFields["Game name"].waitForExistence(timeout: 3))
        XCTAssertTrue(app.buttons["Add player"].exists)
        XCTAssertTrue(app.buttons["Start scoring"].exists)
        XCTAssertTrue(app.buttons["Privacy"].exists)
    }
}
