import Foundation

public struct ActiveScoreSession: Codable, Equatable, Sendable {
    public let sessionId: String
    public var updatedAt: Date
    public var game: StandaloneGame
    public var participants: [ParticipantScorecard]
    public var pendingSyncRequestIds: [String]
}

public protocol ActiveScoreSessionStoring: Sendable {
    func save(_ session: ActiveScoreSession) throws
    func load(sessionId: String) throws -> ActiveScoreSession?
    func loadMostRecent() throws -> ActiveScoreSession?
    func delete(sessionId: String) throws
}

public final class FileActiveScoreSessionStore: ActiveScoreSessionStoring, @unchecked Sendable {
    private let directory: URL
    private let encoder = JSONEncoder()
    private let decoder = JSONDecoder()

    public init(directory: URL = FileManager.default.urls(for: .applicationSupportDirectory, in: .userDomainMask).first!) {
        self.directory = directory.appendingPathComponent("RBDarts/ActiveSessions", isDirectory: true)
    }

    public func save(_ session: ActiveScoreSession) throws {
        try FileManager.default.createDirectory(at: directory, withIntermediateDirectories: true)
        let data = try encoder.encode(session)
        try data.write(to: url(for: session.sessionId), options: [.atomic, .completeFileProtectionUnlessOpen])
    }

    public func load(sessionId: String) throws -> ActiveScoreSession? {
        let fileURL = url(for: sessionId)
        guard FileManager.default.fileExists(atPath: fileURL.path) else { return nil }
        return try decoder.decode(ActiveScoreSession.self, from: Data(contentsOf: fileURL))
    }

    public func loadMostRecent() throws -> ActiveScoreSession? {
        guard FileManager.default.fileExists(atPath: directory.path) else { return nil }
        let files = try FileManager.default.contentsOfDirectory(
            at: directory,
            includingPropertiesForKeys: [.contentModificationDateKey]
        )
        let newest = try files.max { lhs, rhs in
            let left = try lhs.resourceValues(forKeys: [.contentModificationDateKey]).contentModificationDate ?? .distantPast
            let right = try rhs.resourceValues(forKeys: [.contentModificationDateKey]).contentModificationDate ?? .distantPast
            return left < right
        }
        guard let newest else { return nil }
        return try decoder.decode(ActiveScoreSession.self, from: Data(contentsOf: newest))
    }

    public func delete(sessionId: String) throws {
        let fileURL = url(for: sessionId)
        if FileManager.default.fileExists(atPath: fileURL.path) {
            try FileManager.default.removeItem(at: fileURL)
        }
    }

    private func url(for sessionId: String) -> URL {
        directory.appendingPathComponent("\(sessionId).json")
    }
}
