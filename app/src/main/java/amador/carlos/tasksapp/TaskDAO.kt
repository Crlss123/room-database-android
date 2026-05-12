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
}