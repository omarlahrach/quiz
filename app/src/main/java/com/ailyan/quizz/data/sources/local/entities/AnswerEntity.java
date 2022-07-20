package com.ailyan.quizz.data.sources.local.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AnswerEntity implements Parcelable {
    @PrimaryKey
    public long id;
    public String statement;
    public String imageUrl;
    public boolean isCorrect;
    public long questionId;

    public AnswerEntity(long id, String statement, String imageUrl, boolean isCorrect, long questionId) {
        this.id = id;
        this.statement = statement;
        this.imageUrl = imageUrl;
        this.isCorrect = isCorrect;
        this.questionId = questionId;
    }

    protected AnswerEntity(Parcel in) {
        id = in.readLong();
        statement = in.readString();
        imageUrl = in.readString();
        isCorrect = in.readByte() != 0;
        questionId = in.readLong();
    }

    public static final Creator<AnswerEntity> CREATOR = new Creator<AnswerEntity>() {
        @Override
        public AnswerEntity createFromParcel(Parcel in) {
            return new AnswerEntity(in);
        }

        @Override
        public AnswerEntity[] newArray(int size) {
            return new AnswerEntity[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return "AnswerEntity{" +
                "id=" + id +
                ", statement='" + statement + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", isCorrect=" + isCorrect +
                ", questionId=" + questionId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(statement);
        parcel.writeString(imageUrl);
        parcel.writeByte((byte) (isCorrect ? 1 : 0));
        parcel.writeLong(questionId);
    }
}
