package com.ailyan.quizz.data.sources.remote.beans;

import androidx.annotation.NonNull;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "Reponse", strict = false)
public class AnswerResponse {
    @Element(name = "Succes", required = false)
    public boolean succes;
    @Element(name = "Message", required = false)
    public String message;
    @Element(name = "Objet", required = false)
    public Data data;

    @Root(name = "Objet", strict = false)
    public static class Data {
        @ElementList(entry = "ReponseQuestion", inline = true, required = false)
        public List<Answer> answers;

        @Root(name = "ReponseQuestion", strict = false)
        public static class Answer {
            @Element(name = "Id", required = false)
            public long id;
            @Element(name = "Enonce", required = false)
            public String statement;
            @Element(name = "Image", required = false)
            public String imageUrl;
            @Element(name = "Correcte", required = false)
            public boolean isCorrect;
            @Element(name = "Question", required = false)
            public QuestionResponse.Data.Question question;

            @NonNull
            @Override
            public String toString() {
                return "Answer{" +
                        "id=" + id +
                        ", statement='" + statement + '\'' +
                        ", imageUrl='" + imageUrl + '\'' +
                        ", isCorrect=" + isCorrect +
                        ", question=" + question +
                        '}';
            }
        }

        @NonNull
        @Override
        public String toString() {
            return "Data{" +
                    "answers=" + answers +
                    '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "AnswerResponse{" +
                "succes=" + succes +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}