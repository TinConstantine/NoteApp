package com.example.noteapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.annotation.Nullable
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.MainActivity
import com.example.noteapp.R
import com.example.noteapp.adapter.NoteAdapter
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.model.Note
import com.example.noteapp.viewmodel.NoteViewmodel

class HomeFragment : Fragment(R.layout.fragment_home),SearchView.OnQueryTextListener, MenuProvider {
    private lateinit var homeBinding: FragmentHomeBinding;
    private lateinit var notesViewmodel: NoteViewmodel;
    private lateinit var noteAdapter: NoteAdapter;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(inflater, container,false);
        return homeBinding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity();
        menuHost.addMenuProvider(this, viewLifecycleOwner,Lifecycle.State.RESUMED );
        notesViewmodel = (activity as MainActivity).noteViewmodel;
        setupHomeRcView();
        homeBinding.addNoteFab.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment);
        }


        super.onViewCreated(view, savedInstanceState)
    }
    private fun updateUi(notes: List<Note>?){
        if(notes!= null){
            if(notes.isNotEmpty()){
                homeBinding.emptyNotesImage.visibility = View.GONE;
                homeBinding.homeRecyclerView.visibility = View.VISIBLE;
            }
            else{
                homeBinding.emptyNotesImage.visibility = View.VISIBLE;
                homeBinding.homeRecyclerView.visibility = View.GONE;
            }
        }
    }

    private fun setupHomeRcView(){
        noteAdapter = NoteAdapter();
        homeBinding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            setHasFixedSize(true);
            adapter = noteAdapter;
        }
        activity.let {
//            notesViewmodel.getAllNote().observe(viewLifecycleOwner){note ->
//                noteAdapter.differ.submitList(note);
//                updateUi(note);
//            }
           searchNote("");
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false;
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null){
            searchNote(newText);
        }
        return true;
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menu_search, menu);
        val menuSearch = menu.findItem(R.id.searchMenu).actionView as SearchView;
        menuSearch.isSubmitButtonEnabled = true;
        menuSearch.setOnQueryTextListener(this);
    }

    override fun onResume() {
        searchNote("");
        super.onResume()
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false;
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun searchNote(query: String?){
        Log.d("Cold", query.toString())
        val searchQuery = "%$query";
        notesViewmodel.searchNote(searchQuery.trim()).observe(viewLifecycleOwner) {
            noteAdapter.differ.submitList(it);
            updateUi(it);
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}