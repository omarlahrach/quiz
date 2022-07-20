package com.ailyan.quizz.data.repositories;

import android.app.Application;

import com.ailyan.quizz.data.sources.local.AppDatabase;
import com.ailyan.quizz.data.sources.local.dao.CategoryDao;
import com.ailyan.quizz.data.sources.local.entities.CategoryEntity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.core.Observable;

public class CategoryRepository {
    private final Executor executor = Executors.newSingleThreadScheduledExecutor();
    private final CategoryDao categoryDao;

    public CategoryRepository(Application application) {
        categoryDao = AppDatabase.getInstance(application).categoryDao();
    }

    public Observable<List<CategoryEntity>> loadCategoriesByLevel(int level) {
        return categoryDao.loadCategoriesByLevel(level);
    }

    public void updateCategory(CategoryEntity category) {
        executor.execute(() -> categoryDao.updateCategory(category));
    }
}
