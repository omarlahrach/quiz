package com.ailyan.quizz.data.sources.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ailyan.quizz.data.sources.local.entities.QuestionEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface QuestionDao {
    @Query("SELECT DISTINCT level FROM questionentity")
    Observable<List<Integer>> loadAllLevels();

    @Query("SELECT * FROM questionentity WHERE category_id = :categoryId")
    Observable<List<QuestionEntity>> loadQuestionsByCategory(int categoryId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllQuestions(List<QuestionEntity> questionEntities);
}
