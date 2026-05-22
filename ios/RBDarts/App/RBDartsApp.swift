import SwiftUI

@main
struct RBDartsApp: App {
    var body: some Scene {
        WindowGroup {
            RBDartsHomeView()
        }
    }
}

struct RBDartsHomeView: View {
    var body: some View {
        NavigationStack {
            StandaloneGameSetupView()
                .navigationTitle("RBDarts")
        }
    }
}
