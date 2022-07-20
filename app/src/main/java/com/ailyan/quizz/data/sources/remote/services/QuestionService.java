package com.ailyan.quizz.data.sources.remote.services;

import com.ailyan.quizz.data.sources.remote.beans.QuestionResponse;
import com.ailyan.quizz.utilities.annotations.Xml;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface QuestionService {
    @FormUrlEncoded
    @POST("questions.php")
    @Xml
    Observable<QuestionResponse> loadAllQuestions(@Field("login") String username, @Field("session") String session);
}
