package com.ailyan.quizz.data.repositories;

import android.app.Application;

import com.ailyan.quizz.data.sources.local.AppDatabase;
import com.ailyan.quizz.data.sources.local.dao.AnswerDao;
import com.ailyan.quizz.data.sources.local.entities.AnswerEntity;
import com.ailyan.quizz.data.sources.remote.RetrofitInstance;
import com.ailyan.quizz.data.sources.remote.beans.AuthResponse;
import com.ailyan.quizz.data.sources.remote.services.AnswerService;
import com.ailyan.quizz.utilities.SharedData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.core.Observable;

public class AnswerRepository {
    //private static final String TAG = AnswerRepository.class.getSimpleName();
    private final Executor executor = Executors.newSingleThreadScheduledExecutor();
    private final Application application;
    private final AnswerService answerService;
    private final AnswerDao answerDao;

    public AnswerRepository(Application application) {
        this.application = application;
        answerService = RetrofitInstance.getInstance().create(AnswerService.class);
        answerDao = AppDatabase.getInstance(application).answerDao();
    }

    public Observable<List<AnswerEntity>> loadRemoteAnswersByQuestionId(int questionId) {
        AuthResponse authResponse = (AuthResponse) SharedData.get(application, AuthResponse.class, "auth");
        return answerService.loadAllAnswers(authResponse.username, authResponse.session, questionId)
                .flatMap(answerResponse -> Observable
                        .fromIterable(answerResponse.data.answers)
                        .map(answer -> new AnswerEntity(
                                answer.id,
                                answer.statement,
                                answer.imageUrl,
                                answer.isCorrect,
                                answer.question.id)
                        ))
                .toList().toObservable()
                .map(answerEntities -> {
                    executor.execute(() -> answerDao.insertAllAnswers(answerEntities));
                    return answerEntities;
                });
    }

    public Observable<List<AnswerEntity>> loadLocalAnswersByQuestionId(int questionId) {
        return answerDao.loadAnswersByQuestionId(questionId);
    }
}
