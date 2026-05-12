package amador.carlos.tasksapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: TaskDAO

    // @Before se ejecuta antes de CADA test
    @Before
    fun setUp() {
        val context = ApplicationProvider
            .getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        dao = db.taskDao()
    }

    // @After se ejecuta después de CADA test
    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertarTarea() = runTest {

        // Arrange
        val tarea = TaskEntity(
            title = "Lavar los platos."
        )

        // Act
        dao.insert(tarea)

        // Assert
        val tareas = dao
            .getAllTasks()
            .first()

        assertEquals(1, tareas.size)
        assertEquals(
            "Lavar los platos.",
            tareas[0].title
        )
    }

    @Test
    fun actualizarTarea() = runTest {

        dao.insert(
            TaskEntity(title = "Pasear al perro.")
        )

        val original = dao
            .getAllTasks()
            .first()
            .first()

        assertEquals(
            false,
            original.completed
        )

        dao.update(
            original.copy(
                completed = true
            )
        )

        val actualizada = dao
            .getAllTasks()
            .first()
            .first()

        assertTrue(
            actualizada.completed
        )
    }

    @Test
    fun borrarTarea() = runTest {

        dao.insert(
            TaskEntity(title = "Comprar leche.")
        )

        val tarea = dao
            .getAllTasks()
            .first()
            .first()

        dao.delete(tarea)

        val tareas = dao
            .getAllTasks()
            .first()

        assertTrue(tareas.isEmpty())
    }
}