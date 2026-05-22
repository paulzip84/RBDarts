import XCTest

final class StandaloneGameFlowTests: XCTestCase {
    func testStandaloneGameFlowLaunches() throws {
        let app = XCUIApplication()
        app.launch()
        XCTAssertTrue(app.textFields["Game name"].waitForExistence(timeout: 3))
    }
}
