import SwiftUI

@main
struct RBDartsApp: App {
    private let launchConfiguration = ReleaseConfiguration.loadFromBundle()

    var body: some Scene {
        WindowGroup {
            RBDartsHomeView(launchConfiguration: launchConfiguration)
        }
    }
}

struct RBDartsHomeView: View {
    let launchConfiguration: ReleaseConfiguration

    var body: some View {
        NavigationStack {
            StandaloneGameSetupView()
                .navigationTitle("RBDarts")
                .toolbar {
                    ToolbarItem {
                        NavigationLink("Privacy") {
                            PrivacyAndSupportView(configuration: launchConfiguration)
                        }
                    }
                }
        }
    }
}
