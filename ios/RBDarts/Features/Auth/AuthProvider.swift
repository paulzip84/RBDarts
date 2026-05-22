import Foundation

public enum AuthProviderID: String, Codable, Sendable {
    case google
    case facebook
    case apple
}

public struct AuthSession: Equatable, Sendable {
    public let userId: String
    public let displayName: String
    public let providerId: AuthProviderID
    public let expiresAt: Date?
}

public enum AuthProviderError: Error, Equatable {
    case cancelled
    case providerUnavailable
    case tokenExchangeFailed
}

public protocol AuthProviding: Sendable {
    var providerId: AuthProviderID { get }
    func signIn() async throws -> AuthSession
    func signOut() async throws
}

public struct AuthProviderRegistry: Sendable {
    public let providers: [AuthProviderID: AuthProviding]

    public init(providers: [AuthProviding]) {
        self.providers = Dictionary(uniqueKeysWithValues: providers.map { ($0.providerId, $0) })
    }

    public func provider(_ providerId: AuthProviderID) throws -> AuthProviding {
        guard let provider = providers[providerId] else {
            throw AuthProviderError.providerUnavailable
        }
        return provider
    }
}
