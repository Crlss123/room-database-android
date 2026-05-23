package amador.carlos.tasksapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.Volatile

private val TAREAS_INICIALES = listOf(
    TaskEntity(
        title = "Configurar repositorio de Git y flujos de trabajo del equipo",
        completed = true
    ),
    TaskEntity(
        title = "Definir e implementar Clean Architecture y el patrón MVVM",
        completed = true
    ),
    TaskEntity(
        title = "Configurar inyección de dependencias",
        completed = true
    ),
    TaskEntity(
        title = "Integrar Firebase Authentication",
        completed = true
    ),
    TaskEntity(
        title = "Configurar esquemas de base de datos en Firestore y Storage para imágenes",
        completed = true
    ),
    TaskEntity(
        title = "Construir UI del feed colaborativo y visualización de datos con Jetpack Compose",
        completed = true
    ),
    TaskEntity(
        title = "Implementar batch fetching (chunking) para superar el límite de 30 elementos en consultas 'in' de Firestore",
        completed = true
    ),
    TaskEntity(
        title = "Desarrollar e implementar Firebase Cloud Functions",
        completed = true
    ),
    TaskEntity(
        title = "Optimizar la carga de datos al feed.",
        completed = true
    ),
    TaskEntity(
        title = "Implementar busqueda de usuarios para compartir la visualizacion.",
        completed = true
    ),
    TaskEntity(
        title = "Crear algoritmo de parseo para pasar JSONs de la base de datos a objetos serializables para al aplicacion.",
        completed = true
    ),
    TaskEntity(
        title = "Renderizar fotos de perfil de los usuarios.",
        completed = true
    ),
    TaskEntity(
        title = "Cargar las visualizaciones del usuario al feed.",
        completed = true
    ),
    TaskEntity(
        title = "Implementar los roles en la aplicacion.",
        completed = false
    ),
    TaskEntity(
        title = "Actualizar las tarjetas del feed al detectar cambios.",
        completed = false
    )
)

@Database(
    entities = [TaskEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase(){
    abstract fun taskDao(): TaskDAO
    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(
            context: Context
        ): AppDatabase {
            return INSTANCE ?: synchronized(
                this
            ){
                val instance = Room
                    .databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tasks_db"
                    )
                    .addCallback(
                        object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                CoroutineScope(Dispatchers.IO).launch {
                                    val dao = getInstance(context).taskDao()
                                    TAREAS_INICIALES.forEach { tarea ->
                                        dao.insert(tarea)
                                    }
                                }
                            }
                        }
                    )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}