package com.maadcoding.notesapp.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maadcoding.notesapp.database.Note
import com.maadcoding.notesapp.ui.theme.NotesAppTheme
import com.maadcoding.notesapp.ui.viewmodel.NoteViewModel

class EditNoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val note =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                intent?.getParcelableExtra<Note>("note")!!
            else
                intent?.getParcelableExtra("note")!!

        setContent {
            NotesAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    EditNoteScreen(
                        note,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun EditNoteScreen(
    note: Note,
    modifier: Modifier = Modifier,
    viewModel: NoteViewModel = viewModel()
) {

    val activity = LocalActivity.current
    val context = LocalContext.current
    val noteFieldState = rememberTextFieldState(initialText = note.content)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
    ) {


        OutlinedTextField(
            state = noteFieldState,
            label = { Text("Note details") },
            modifier = Modifier
                .padding(top = 64.dp)
                .fillMaxWidth()
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        ) {
            OutlinedButton(
                onClick = {
                    val n = Note(id = note.id, content = noteFieldState.text.toString())
                    viewModel.upsertNote(n)
                    Toast.makeText(context, "Updated!", Toast.LENGTH_SHORT).show();
                    activity?.finish()
                },
                enabled = noteFieldState.text.toString() != note.content,
                modifier = modifier
                    .weight(1f)
            ) {
                Text(text = "Update")
            }
            OutlinedButton(
                onClick = {
                    viewModel.delete(note)
                    Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT).show();
                    activity?.finish()
                },
                modifier = modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(text = "Delete")
            }
        }
    }
}

@Preview(showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
private fun EditNoteScreenPreview() {
    EditNoteScreen(Note(id = 1, content = ""))
}