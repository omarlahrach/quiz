package com.ailyan.quizz.data.repositories;

import android.app.Application;

import com.ailyan.quizz.data.sources.local.AppDatabase;
import com.ailyan.quizz.data.sources.local.dao.QuestionDao;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class LevelRepository {
    private final QuestionDao questionDao;

    public LevelRepository(Application application) {
        questionDao = AppDatabase.getInstance(application).questionDao();
    }

    public Observable<List<Integer>> loadAllLevels() {
        return questionDao.loadAllLevels();
    }
}
