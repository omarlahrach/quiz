package com.ailyan.quizz.data.sources.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ailyan.quizz.data.sources.local.entities.CategoryEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM categoryentity WHERE level = :level")
    Observable<List<CategoryEntity>> loadCategoriesByLevel(int level);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCategory(CategoryEntity category);

    @Update
    void updateCategory(CategoryEntity categoryEntity);
}
