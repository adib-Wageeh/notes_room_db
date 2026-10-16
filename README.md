# Notes App

**Notes App** is an app that allows you to create notes and update or delete them.


## Features

- Add notes that is stored in mobile database, allowing notes to persist after closing and opening the app.
- Show all your added notes in a list format.
- allow you to edit or delete your added notes.

## Tech stack

- Kotlin
- Android Compose
- Room db
- MVVM
- Parcelable


## Project structure

```text
app/
├── src/main/java/com/maadcoding/notesapp/
│   ├── database/
        ├── Note # note entity class
        ├── NoteDao # interface that contains methods that interact with your table
        ├── RoomDBHelper
    └── ui/activity/
        ├── AddNoteActivity
        ├── EditNoteActivity
        ├── MainActivity
    └── viewmodel/
        ├── NoteViewModel
├── src/androidTest/java/com/maadcoding/notesapp/
│   ├── RoomDatabaseTest


```



passed Tests screenShot:
<img width="860" height="231" alt="image" src="https://github.com/user-attachments/assets/3f4b21c4-ab6c-459c-994a-1443fa58ecbe" />
