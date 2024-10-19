package com.example.noteapp.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.noteapp.MainActivity
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentAddNoteBinding
import com.example.noteapp.viewmodel.NoteViewmodel

class AddNoteFragment : Fragment(R.layout.fragment_add_note), MenuProvider {
    private var addNoteBinding: FragmentAddNoteBinding? = null;
    private lateinit var noteViewmodel: NoteViewmodel;
    private lateinit var addNoteView: View;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addNoteBinding = FragmentAddNoteBinding.inflate(inflater, container, false);
        return addNoteBinding!!.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity();
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED);
        noteViewmodel = (activity as MainActivity).noteViewmodel;
        addNoteView = view;
        super.onViewCreated(view, savedInstanceState)
    }

    private fun saveNote(view:View){
        val noteTitle = addNoteBinding!!.addNoteTitle.text.toString().trim();
        val noteDesc = addNoteBinding!!.addNoteDesc.text.toString().trim();

        if(noteTitle.isNotEmpty()){
            val note = com.example.noteapp.model.Note(0, noteTitle, noteDesc);
            noteViewmodel.addNote(note);
            Toast.makeText(view.context, "Note saved", Toast.LENGTH_SHORT ).show();
            view.findNavController().popBackStack();
        }else {
            Toast.makeText(view.context, "Please enter note title", Toast.LENGTH_SHORT ).show();

        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
      menu.clear();
        menuInflater.inflate(R.menu.menu_add_note, menu);
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.saveMenu -> {
                saveNote(addNoteView);
                true;
            }
            else -> false;
        }
    }

    override fun onDestroy() {
        addNoteBinding = null;
        super.onDestroy()
    }


}