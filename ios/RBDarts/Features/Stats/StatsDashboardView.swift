import SwiftUI

struct StatsDashboardView: View {
    let projections: [ProjectionResult]

    var body: some View {
        List {
            Section("Projections") {
                ForEach(projections, id: \.projectedFinalScore) { projection in
                    HStack {
                        Text(projection.label)
                        Spacer()
                        Text("\(projection.projectedFinalScore, specifier: "%.1f")")
                    }
                }
            }
        }
        .navigationTitle("Stats")
    }
}
