package amador.carlos.tasksapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {
    @Query(
        "SELECT * FROM tasks;"
    )
    fun getAllTasks(): Flow<List<TaskEntity>>
    @Insert
    suspend fun insert(task: TaskEntity)
    @Update
    suspend fun update(task: TaskEntity)
    @Delete
    suspend fun delete(task: TaskEntity)

    @Query("SELECT * FROM tasks WHERE title LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchTasksNewestFirst(query: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE title LIKE '%' || :query || '%' ORDER BY createdAt ASC")
    fun searchTasksOldestFirst(query: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE title LIKE '%' || :query || '%' ORDER BY title ASC")
    fun searchTasksTitleAsc(query: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE title LIKE '%' || :query || '%' ORDER BY title DESC")
    fun searchTasksTitleDesc(query: String): Flow<List<TaskEntity>>
}