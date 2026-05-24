import SwiftUI

struct PrivacyAndSupportView: View {
    let configuration: ReleaseConfiguration

    var body: some View {
        List {
            Section("Support") {
                Link("Support", destination: configuration.supportURL)
                Link("Privacy Policy", destination: configuration.privacyPolicyURL)
                Link("Account Deletion", destination: configuration.accountDeletionURL)
            }

            Section("Release") {
                LabeledContent("Environment", value: configuration.appEnvironment)
                LabeledContent("Version", value: "\(configuration.appVersion) (\(configuration.buildNumber))")
                LabeledContent("Diagnostics", value: configuration.diagnosticsEnabled ? "Enabled" : "Disabled")
            }
        }
        .navigationTitle("Privacy")
    }
}
