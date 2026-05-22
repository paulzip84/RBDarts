import Foundation

public struct SessionMetadata: Codable, Equatable, Sendable {
    public let userId: String
    public let providerIds: [String]
    public let issuedAt: Date
    public let expiresAt: Date?
}

public protocol SecureSessionStoring: Sendable {
    func save(_ session: SessionMetadata) throws
    func load() throws -> SessionMetadata?
    func clear() throws
}

public enum SecureSessionStoreError: Error, Equatable {
    case encodingFailed
    case decodingFailed
    case keychainFailure(Int32)
}

public final class SecureSessionStore: SecureSessionStoring, @unchecked Sendable {
    private let service = "com.rbdarts.session"
    private let account = "session-metadata"
    private let encoder = JSONEncoder()
    private let decoder = JSONDecoder()

    public init() {}

    public func save(_ session: SessionMetadata) throws {
        let data = try encoder.encode(session)
        #if canImport(Security)
        try clear()
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrService as String: service,
            kSecAttrAccount as String: account,
            kSecValueData as String: data,
            kSecAttrAccessible as String: kSecAttrAccessibleAfterFirstUnlockThisDeviceOnly
        ]
        let status = SecItemAdd(query as CFDictionary, nil)
        guard status == errSecSuccess else {
            throw SecureSessionStoreError.keychainFailure(status)
        }
        #else
        UserDefaults.standard.set(data, forKey: "\(service).\(account)")
        #endif
    }

    public func load() throws -> SessionMetadata? {
        #if canImport(Security)
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrService as String: service,
            kSecAttrAccount as String: account,
            kSecReturnData as String: true,
            kSecMatchLimit as String: kSecMatchLimitOne
        ]
        var item: CFTypeRef?
        let status = SecItemCopyMatching(query as CFDictionary, &item)
        if status == errSecItemNotFound {
            return nil
        }
        guard status == errSecSuccess else {
            throw SecureSessionStoreError.keychainFailure(status)
        }
        guard let data = item as? Data else {
            throw SecureSessionStoreError.decodingFailed
        }
        return try decoder.decode(SessionMetadata.self, from: data)
        #else
        guard let data = UserDefaults.standard.data(forKey: "\(service).\(account)") else {
            return nil
        }
        return try decoder.decode(SessionMetadata.self, from: data)
        #endif
    }

    public func clear() throws {
        #if canImport(Security)
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrService as String: service,
            kSecAttrAccount as String: account
        ]
        let status = SecItemDelete(query as CFDictionary)
        guard status == errSecSuccess || status == errSecItemNotFound else {
            throw SecureSessionStoreError.keychainFailure(status)
        }
        #else
        UserDefaults.standard.removeObject(forKey: "\(service).\(account)")
        #endif
    }
}
