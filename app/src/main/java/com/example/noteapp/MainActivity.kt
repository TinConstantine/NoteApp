package com.example.noteapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.database.NoteDatabase
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.model.Note
import com.example.noteapp.repository.NoteRepository
import com.example.noteapp.viewmodel.NoteViewmodel
import com.example.noteapp.viewmodel.NoteViewmodelFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding :ActivityMainBinding;
    lateinit var noteViewmodel: NoteViewmodel;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupViewmodel();
    }

    private fun setupViewmodel(){
        val noteRepository = NoteRepository(NoteDatabase(this))
        val viewModelProviderFactory = NoteViewmodelFactory(application, noteRepository);
        noteViewmodel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewmodel::class.java]
    }
}
