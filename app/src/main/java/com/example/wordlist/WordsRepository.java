package com.example.wordlist;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Update;

import java.util.List;

public class WordsRepository {

    private wordsDao mWordsDao;

    private LiveData<List<Words>> getAllWords;

    public WordsRepository (Application app)
    {
        WordRoomDb db = WordRoomDb.getInstance(app);
        mWordsDao = db.wordsDao();
        getAllWords = mWordsDao.getAllWords();
    }
    //operation
//insert
    public void insert(Words word)
    {
        new InsertAsyncTask(mWordsDao).execute(word);
    }


//delete
    public void delete (Words word){
        new DeleteAsyncTask(mWordsDao).execute(word);

    }
//update
public void update (Words word){
    new UpdateAsyncTask(mWordsDao).execute(word);

}

    //getallwords
    public LiveData<List<Words>> getAllWords()
    {
        return getAllWords;
    }

    //delete all words
    public void deleteAllwords(){
        new DeleteAsyncTask(mWordsDao).execute();
    }


    private static class InsertAsyncTask extends android.os.AsyncTask<Words,Void,Void> {
        private wordsDao mWordsDao;
        public InsertAsyncTask(wordsDao wordsDao){
            mWordsDao=wordsDao;
        }
        @Override
        protected Void doInBackground(Words... words) {
            mWordsDao.insert(words[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Words,Void,Void>{
        private wordsDao mWordsDao;
        public DeleteAsyncTask(wordsDao wordsDao){
            mWordsDao=wordsDao;
        }
        @Override
        protected Void doInBackground(Words... words) {
            mWordsDao.delete(words[0]);
            return null;
        }
    }
    private static class UpdateAsyncTask extends AsyncTask<Words,Void,Void>{
        private wordsDao mWordsDao;
        public UpdateAsyncTask(wordsDao wordsDao){
            mWordsDao=wordsDao;
        }
        @Override
        protected Void doInBackground(Words... words) {
            mWordsDao.update(words[0]);
            return null;
        }
    }
    private static class DeleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void>{

        private wordsDao mWordsDao;

        public DeleteAllWordsAsyncTask(wordsDao wordsDao)
        {
            mWordsDao = wordsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mWordsDao.deleteAllWords();
            return null;
        }
    }


}

