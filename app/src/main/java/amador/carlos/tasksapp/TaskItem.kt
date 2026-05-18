package amador.carlos.tasksapp

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextDecoration
import java.text.SimpleDateFormat
import java.util.Date
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TaskItem(
    task: TaskEntity,
    onToggleCompleted: () -> Unit,
    onDelete: () -> Unit
) {
    // Formato de fecha corto: dia/mes hora:minuto.
    val dateFormat = remember {
        SimpleDateFormat("dd/MM HH:mm")
    }
    val fechaTexto = remember(task.createdAt) {
        dateFormat.format(Date(task.createdAt))
    }

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
            }
            false
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            val color by animateColorAsState(
                targetValue = if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                    MaterialTheme.colorScheme.errorContainer
                } else {
                    Color.Transparent
                },
                label = "colorFondo"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete_action_desc),
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        },
        content = {
            ListItem(
                leadingContent = {
                    Checkbox(
                        checked = task.completed,
                        onCheckedChange = { onToggleCompleted() }
                    )
                },
                headlineContent = {
                    Text(
                        text = task.title,
                        textDecoration = if (task.completed)
                            TextDecoration.LineThrough else null,
                        color = if (task.completed)
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                },
                supportingContent = {
                    Text(text = fechaTexto)
                },
            )
        }
    )
}