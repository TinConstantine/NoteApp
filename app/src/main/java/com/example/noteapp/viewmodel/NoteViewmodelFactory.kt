package com.example.noteapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.repository.NoteRepository

class NoteViewmodelFactory(val app: Application, private val noteRepository: NoteRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewmodel(app,noteRepository) as T;
    }


}