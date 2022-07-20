package com.ailyan.quizz.data.repositories;

import android.app.Application;

import com.ailyan.quizz.data.sources.local.AppDatabase;
import com.ailyan.quizz.data.sources.local.dao.CategoryDao;
import com.ailyan.quizz.data.sources.local.dao.QuestionDao;
import com.ailyan.quizz.data.sources.local.entities.CategoryEntity;
import com.ailyan.quizz.data.sources.local.entities.QuestionEntity;
import com.ailyan.quizz.data.sources.local.entities.ScoreEntity;
import com.ailyan.quizz.data.sources.remote.RetrofitInstance;
import com.ailyan.quizz.data.sources.remote.beans.AuthResponse;
import com.ailyan.quizz.data.sources.remote.services.QuestionService;
import com.ailyan.quizz.utilities.SharedData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.core.Observable;

public class QuestionRepository {
    //private static final String TAG = QuestionRepository.class.getSimpleName();
    private final Executor executor = Executors.newSingleThreadScheduledExecutor();
    private final Application application;
    private final QuestionService questionService;
    private final CategoryDao categoryDao;
    private final QuestionDao questionDao;

    public QuestionRepository(Application application) {
        this.application = application;
        questionService = RetrofitInstance.getInstance().create(QuestionService.class);
        categoryDao = AppDatabase.getInstance(application).categoryDao();
        questionDao = AppDatabase.getInstance(application).questionDao();
    }

    public Observable<List<QuestionEntity>> loadAllRemoteQuestions() {
        AuthResponse authResponse = (AuthResponse) SharedData.get(application, AuthResponse.class, "auth");
        return questionService.loadAllQuestions(authResponse.username, authResponse.session)
                .flatMap(questionResponse -> Observable
                        .fromIterable(questionResponse.data.questions)
                        .filter(question -> question.establishment == authResponse.establishment)
                        .map(question -> {
                            CategoryEntity categoryEntity = new CategoryEntity(
                                    question.category.id,
                                    question.category.name,
                                    question.level,
                                    question.category.validation,
                                    question.category.establishmentValidation
                            );
                            categoryEntity.score = new ScoreEntity();
                            executor.execute(() -> categoryDao.insertCategory(categoryEntity));
                            return new QuestionEntity(
                                    question.id,
                                    question.statement,
                                    question.imageUrl,
                                    question.level,
                                    question.validation,
                                    question.establishmentValidation,
                                    question.category.id);
                        })
                        .toList().toObservable())
                .map(questionEntities -> {
                    executor.execute(() -> questionDao.insertAllQuestions(questionEntities));
                    return questionEntities;
                });
    }

    public Observable<List<QuestionEntity>> loadLocalQuestionsByCategory(int category) {
        return questionDao.loadQuestionsByCategory(category);
    }
}
