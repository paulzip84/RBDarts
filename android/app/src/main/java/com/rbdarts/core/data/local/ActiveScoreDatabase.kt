package com.rbdarts.core.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity(tableName = "active_score_sessions")
data class ActiveScoreSessionEntity(
    @PrimaryKey val sessionId: String,
    val updatedAtEpochMillis: Long,
    val payloadJson: String
)

@Dao
interface ActiveScoreSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(session: ActiveScoreSessionEntity)

    @Query("SELECT * FROM active_score_sessions WHERE sessionId = :sessionId LIMIT 1")
    suspend fun load(sessionId: String): ActiveScoreSessionEntity?

    @Query("SELECT * FROM active_score_sessions ORDER BY updatedAtEpochMillis DESC LIMIT 1")
    suspend fun loadMostRecent(): ActiveScoreSessionEntity?

    @Query("DELETE FROM active_score_sessions WHERE sessionId = :sessionId")
    suspend fun delete(sessionId: String)
}

@Database(
    entities = [ActiveScoreSessionEntity::class],
    version = 1,
    exportSchema = true
)
abstract class ActiveScoreDatabase : RoomDatabase() {
    abstract fun activeScoreSessionDao(): ActiveScoreSessionDao
}
