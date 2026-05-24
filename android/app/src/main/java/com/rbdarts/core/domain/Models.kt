package com.rbdarts.core.domain

import java.time.Instant

enum class AccountStatus { Active, Disabled, Deleted }
enum class ActiveStatus { Active, Inactive }
enum class LeagueRole { LeagueManager, TeamManager, Scorekeeper, Player, Viewer, Admin }
enum class ParticipantType { Singles, Teams, Player, Team }
enum class GameStatus { Setup, Scoring, Complete, Locked, Finalized, Corrected, Voided }
enum class MatchStatus { Scheduled, InProgress, Finalized, Corrected, Voided }
enum class HandicapRoundingRule { Nearest, Floor, Ceiling, Decimal }
enum class AverageUpdateRule { AfterMatch }
enum class ExtraInningsRule { Enabled, Disabled, LeagueTieRule }
enum class TieBonusRule { NoBonus, SplitBonus, TiebreakerGame, RawScoreTiebreak }
enum class CorrectionStatus { Requested, Approved, Rejected, Applied }
enum class Post20TieRule { Repeat20, RestartAt1, BullseyeTiebreaker }
enum class DartRing { Miss, Single, Double, Triple, OuterBull, InnerBull }
enum class DartValidity { Valid, WrongNumber, BounceOut, FellOut, FoulLine, OutOfTurn }
enum class BaseballParticipantKind { Player, Team }
enum class BaseballAnalyticsSubject { Player, Team }

data class BaseballRuleSet(
    val ruleSetId: String = "baseball-standard",
    val name: String = "Standard Baseball Darts",
    val regulationInnings: Int = 9,
    val dartsPerTurn: Int = 3,
    val singleValue: Int = 1,
    val doubleValue: Int = 2,
    val tripleValue: Int = 3,
    val extraInningsEnabled: Boolean = true,
    val post20TieRule: Post20TieRule = Post20TieRule.Repeat20,
    val bullseyeBonusEnabled: Boolean = false,
    val seventhInningStretchEnabled: Boolean = false,
    val mercyRuleEnabled: Boolean = false,
    val handicapEnabled: Boolean = false
)

data class BaseballDartThrow(
    val dartThrowId: String,
    val dartIndex: Int,
    val targetNumber: Int,
    val landedNumber: Int?,
    val ring: DartRing,
    val validity: DartValidity = DartValidity.Valid
)

data class BaseballTurn(
    val turnId: String,
    val gameId: String,
    val inningNumber: Int,
    val targetNumber: Int,
    val participantId: String,
    val teamId: String?,
    val playerId: String,
    val lineupSlot: Int,
    val darts: List<BaseballDartThrow>,
    val enteredByUserId: String,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class BaseballParticipant(
    val participantId: String,
    val displayName: String,
    val kind: BaseballParticipantKind,
    val teamId: String? = null,
    val playerIds: List<String> = emptyList(),
    val lineupOrder: List<String> = emptyList(),
    val startingHandicap: Int = 0
)

data class BaseballParticipantSummary(
    val participantId: String,
    val displayName: String,
    val total: Int,
    val inningTotals: Map<Int, Int>,
    val zeroCount: Int,
    val nineCount: Int
)

data class BaseballGameSummary(
    val participantSummaries: List<BaseballParticipantSummary>,
    val winnerIds: List<String>,
    val margin: Int,
    val inningsPlayed: Int,
    val needsExtraInning: Boolean,
    val currentTarget: Int
)

data class BaseballScoreboardSnapshot(
    val gameId: String,
    val currentInning: Int,
    val targetNumber: Int,
    val currentThrowerId: String,
    val currentThrowerDisplayName: String,
    val playerInningScore: Int,
    val playerTotal: Int,
    val teamInningScore: Int?,
    val teamTotal: Int?,
    val leaderId: String?,
    val leadMargin: Int,
    val remainingInnings: Int,
    val needsExtraInning: Boolean,
    val gameStatus: GameStatus
)

data class BaseballAnalyticsSnapshot(
    val subjectId: String,
    val subjectType: BaseballAnalyticsSubject,
    val averageScorePerInning: Double?,
    val highestInningScore: Int?,
    val shutoutInnings: Int,
    val tripleRate: Double?,
    val winPercentage: Double?,
    val projectedFinalScore: Double?,
    val comebackProbability: Double?,
    val consistency: Double?,
    val bestTargetNumbers: List<Int>,
    val weakestTargetNumbers: List<Int>,
    val isEstimate: Boolean
)

data class BaseballSubstitution(
    val substitutionId: String,
    val gameId: String,
    val teamId: String,
    val outPlayerId: String,
    val inPlayerId: String,
    val effectiveInning: Int,
    val effectiveTurn: Int,
    val authorizedByUserId: String,
    val authorizedByRole: LeagueRole,
    val createdAt: Instant,
    val reason: String
)

data class UserAccount(
    val userId: String,
    val displayName: String,
    val email: String?,
    val linkedProviderIds: List<String>,
    val accountStatus: AccountStatus,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class RoleAssignment(
    val roleAssignmentId: String,
    val userId: String,
    val leagueId: String,
    val teamId: String?,
    val role: LeagueRole,
    val permissions: Set<String>,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class Player(
    val playerId: String,
    val userId: String?,
    val firstName: String?,
    val lastName: String?,
    val displayName: String,
    val activeStatus: ActiveStatus,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class League(
    val leagueId: String,
    val leagueName: String,
    val gamesPerMatch: Int,
    val playersPerTeamPerGame: Int,
    val handicapPercent: Double,
    val handicapRoundingRule: HandicapRoundingRule,
    val pointsPerGameWin: Double,
    val matchBonusPointValue: Double,
    val averageUpdateRule: AverageUpdateRule,
    val extraInningsRule: ExtraInningsRule,
    val tieBonusRule: TieBonusRule,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class Team(
    val teamId: String,
    val leagueId: String,
    val teamName: String,
    val captainPlayerId: String?,
    val activeStatus: ActiveStatus,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class Match(
    val matchId: String,
    val leagueId: String,
    val matchDate: Instant,
    val awayTeamId: String,
    val homeTeamId: String,
    val status: MatchStatus,
    val awayTeamMatchPoints: Double,
    val homeTeamMatchPoints: Double,
    val awayTeamAdjustedTotal: Double,
    val homeTeamAdjustedTotal: Double,
    val matchBonusWinnerTeamId: String?
)

data class Game(
    val gameId: String,
    val matchId: String?,
    val standaloneGameId: String?,
    val gameNumber: Int,
    val status: GameStatus,
    val awayTeamRawScore: Int,
    val homeTeamRawScore: Int,
    val awayTeamHandicap: Double,
    val homeTeamHandicap: Double,
    val winningTeamId: String?,
    val inningsPlayed: Int,
    val lockedAt: Instant?
)

data class StandaloneGame(
    val standaloneGameId: String,
    val createdByUserId: String,
    val gameName: String,
    val gameDate: Instant,
    val participantType: ParticipantType,
    val regulationInnings: Int,
    val extraInningsEnabled: Boolean,
    val status: GameStatus,
    val createdAt: Instant,
    val completedAt: Instant?
)

data class GameLineup(
    val gameLineupId: String,
    val gameId: String,
    val teamId: String?,
    val playerId: String,
    val lineupSlot: Int,
    val playerAverageAtStartOfMatch: Double
)

data class InningScore(
    val inningScoreId: String,
    val gameId: String,
    val playerId: String,
    val teamId: String?,
    val inningNumber: Int,
    val target: Int,
    val score: Int,
    val enteredByUserId: String,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class PracticeAttempt(
    val practiceAttemptId: String,
    val playerId: String,
    val userId: String,
    val target: Int,
    val score: Int,
    val attemptDate: Instant,
    val createdAt: Instant
)

data class CorrectionAuditRecord(
    val correctionId: String,
    val leagueId: String,
    val matchId: String,
    val gameId: String,
    val inningScoreId: String,
    val editedByUserId: String,
    val editedByRole: LeagueRole,
    val reason: String,
    val previousValue: Int,
    val newValue: Int,
    val affectedRecords: List<String>,
    val status: CorrectionStatus,
    val createdAt: Instant,
    val appliedAt: Instant?
)
