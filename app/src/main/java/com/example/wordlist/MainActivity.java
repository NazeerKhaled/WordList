package com.example.wordlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //View Model
    private WordViewModel mWordViewModel;

    //RecyclerView
    private RecyclerView mRecyclerView;
    private WordAdapter mWordAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //floating button
        FloatingActionButton floatingActionButton=findViewById(R.id.button_add_word);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to the add activity
                Intent i =new Intent(MainActivity.this,AddNewWordActivity.class);
                startActivityForResult(i,1);
            }
        });
        //recycler view
        mRecyclerView=findViewById(R.id.words_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);


        //connect Recyclerview With adapter
        mWordAdapter =new WordAdapter();
        mRecyclerView.setAdapter(mWordAdapter);


        //ViewModel
        mWordViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(WordViewModel.class);
        mWordViewModel.getAllWords().observe(this, new Observer<List<Words>>() {
            @Override
            public void onChanged(List<Words> words) {
                //Update UI
                //RecyclerView
                mWordAdapter.setWords(words);

            }
        });
        mWordAdapter.OnItemClickListener(new WordAdapter.OnItemCliclListener() {
            @Override
            public void onItemClick(Words word) {
                Intent i =new Intent(MainActivity.this,AddNewWordActivity.class);
                i.putExtra(AddNewWordActivity.EXTRA_ID,word.getId());
                i.putExtra(AddNewWordActivity.EXTRA_WORD,word.getWordName());
                i.putExtra(AddNewWordActivity.EXTRA_TYPE,word.getWordType());
                i.putExtra(AddNewWordActivity.EXTRA_MEANING,word.getWordMeaning());
                startActivity(i);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //delete item
                int position = viewHolder.getAdapterPosition();
                mWordViewModel.delete(mWordAdapter.getWordAt(position));
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    }
