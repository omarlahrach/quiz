package com.ailyan.quizz.data.sources.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ailyan.quizz.data.sources.local.dao.AnswerDao;
import com.ailyan.quizz.data.sources.local.dao.CategoryDao;
import com.ailyan.quizz.data.sources.local.dao.QuestionDao;
import com.ailyan.quizz.data.sources.local.entities.AnswerEntity;
import com.ailyan.quizz.data.sources.local.entities.CategoryEntity;
import com.ailyan.quizz.data.sources.local.entities.QuestionEntity;

@Database(entities = {CategoryEntity.class, QuestionEntity.class, AnswerEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract CategoryDao categoryDao();
    public abstract QuestionDao questionDao();
    public abstract AnswerDao answerDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "intrus.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}