package com.bergsonnobrega.essenciallenil.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bergsonnobrega.essenciallenil.data.model.Task
import com.bergsonnobrega.essenciallenil.ui.theme.EssencialLenilTheme

@Composable
fun TaskItem(
    task: Task,
    onTaskClick: (Task) -> Unit,
    onCheckedChange: (Task, Boolean) -> Unit,
    onDeleteClick: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onTaskClick(task) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = task.isDone,
                    onCheckedChange = { isChecked -> onCheckedChange(task, isChecked) },
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge,
                    textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None
                )
            }
            IconButton(onClick = { onDeleteClick(task) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Deletar Tarefa",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskItemPreview() {
    EssencialLenilTheme {
        TaskItem(
            task = Task(id = 1, title = "Comprar pão", isDone = false),
            onTaskClick = {},
            onCheckedChange = { _, _ -> },
            onDeleteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TaskItemDonePreview() {
    EssencialLenilTheme {
        TaskItem(
            task = Task(id = 2, title = "Lavar a louça", description = "Não esquecer panelas", isDone = true),
            onTaskClick = {},
            onCheckedChange = { _, _ -> },
            onDeleteClick = {}
        )
    }
} 