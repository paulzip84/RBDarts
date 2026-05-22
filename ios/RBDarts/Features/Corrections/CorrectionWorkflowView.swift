import SwiftUI

struct CorrectionWorkflowView: View {
    @State private var reason = ""
    @State private var newValue = 0

    var body: some View {
        Form {
            Stepper("New score: \(newValue)", value: $newValue, in: 0...9)
            TextField("Reason", text: $reason, axis: .vertical)
            Button("Submit correction") {}
                .disabled(reason.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty)
        }
        .navigationTitle("Correction")
    }
}
