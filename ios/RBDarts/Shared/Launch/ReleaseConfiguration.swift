import Foundation

public enum ReleaseConfigurationError: Error, Equatable {
    case missingField(String)
    case invalidURL(String)
    case nonProductionEnvironment(String)
    case insecureURL(String)
}

public struct ReleaseConfiguration: Equatable, Sendable {
    public let appEnvironment: String
    public let appVersion: String
    public let buildNumber: String
    public let supportURL: URL
    public let privacyPolicyURL: URL
    public let accountDeletionURL: URL
    public let diagnosticsEnabled: Bool

    public var isProduction: Bool {
        appEnvironment == "production"
    }

    public init(
        appEnvironment: String,
        appVersion: String,
        buildNumber: String,
        supportURL: URL,
        privacyPolicyURL: URL,
        accountDeletionURL: URL,
        diagnosticsEnabled: Bool
    ) {
        self.appEnvironment = appEnvironment
        self.appVersion = appVersion
        self.buildNumber = buildNumber
        self.supportURL = supportURL
        self.privacyPolicyURL = privacyPolicyURL
        self.accountDeletionURL = accountDeletionURL
        self.diagnosticsEnabled = diagnosticsEnabled
    }

    public static let defaultProduction = ReleaseConfiguration(
        appEnvironment: "production",
        appVersion: "0.1.0",
        buildNumber: "1",
        supportURL: URL(string: "https://rbdarts.app/support")!,
        privacyPolicyURL: URL(string: "https://rbdarts.app/privacy")!,
        accountDeletionURL: URL(string: "https://rbdarts.app/account/delete")!,
        diagnosticsEnabled: true
    )

    public static func from(dictionary: [String: Any]) throws -> ReleaseConfiguration {
        let appEnvironment = try stringValue("AppEnvironment", in: dictionary)
        let appVersion = try stringValue("AppVersion", in: dictionary)
        let buildNumber = try stringValue("BuildNumber", in: dictionary)
        let supportURL = try urlValue("SupportURL", in: dictionary)
        let privacyPolicyURL = try urlValue("PrivacyPolicyURL", in: dictionary)
        let accountDeletionURL = try urlValue("AccountDeletionURL", in: dictionary)
        let diagnosticsEnabled = dictionary["DiagnosticsEnabled"] as? Bool ?? true

        return ReleaseConfiguration(
            appEnvironment: appEnvironment,
            appVersion: appVersion,
            buildNumber: buildNumber,
            supportURL: supportURL,
            privacyPolicyURL: privacyPolicyURL,
            accountDeletionURL: accountDeletionURL,
            diagnosticsEnabled: diagnosticsEnabled
        )
    }

    public static func loadFromBundle(_ bundle: Bundle = .main) -> ReleaseConfiguration {
        guard let url = bundle.url(forResource: "ReleaseConfig", withExtension: "plist"),
              let data = try? Data(contentsOf: url),
              let plist = try? PropertyListSerialization.propertyList(from: data, options: [], format: nil),
              let dictionary = plist as? [String: Any],
              let configuration = try? ReleaseConfiguration.from(dictionary: dictionary) else {
            return .defaultProduction
        }

        return configuration
    }

    public func validateForLaunch() throws {
        guard isProduction else {
            throw ReleaseConfigurationError.nonProductionEnvironment(appEnvironment)
        }

        for url in [supportURL, privacyPolicyURL, accountDeletionURL] where url.scheme != "https" {
            throw ReleaseConfigurationError.insecureURL(url.absoluteString)
        }
    }

    private static func stringValue(_ key: String, in dictionary: [String: Any]) throws -> String {
        guard let value = dictionary[key] as? String, value.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty == false else {
            throw ReleaseConfigurationError.missingField(key)
        }
        return value
    }

    private static func urlValue(_ key: String, in dictionary: [String: Any]) throws -> URL {
        let rawValue = try stringValue(key, in: dictionary)
        guard let url = URL(string: rawValue), url.scheme == "https" else {
            throw ReleaseConfigurationError.invalidURL(key)
        }
        return url
    }
}
