import SwiftUI
#if canImport(UIKit)
import UIKit
#elseif canImport(AppKit)
import AppKit
#endif

public enum RBDartsDesignTokens {
    public static let scoreButtonSize: CGFloat = 56
    public static let compactScoreButtonSize: CGFloat = 44
    public static let scorecardCellMinWidth: CGFloat = 42
    public static let cornerRadius: CGFloat = 8
    public static let hitColor = Color.green
    public static let missColor = Color.red
    public static let warningColor = Color.orange
    public static let lockedColor = Color.gray
    #if canImport(UIKit)
    public static let surfaceColor = Color(UIColor.secondarySystemBackground)
    #elseif canImport(AppKit)
    public static let surfaceColor = Color(NSColor.windowBackgroundColor)
    #else
    public static let surfaceColor = Color.gray.opacity(0.12)
    #endif
}
