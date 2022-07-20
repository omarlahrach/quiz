package com.ailyan.quizz.data.sources.local.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class QuestionEntity implements Parcelable {
    @PrimaryKey
    public int id;
    public String statement;
    public String imageUrl;
    public int level;
    public int validation;
    public int establishmentValidation;
    public int category_id;

    public QuestionEntity(int id, String statement, String imageUrl, int level, int validation, int establishmentValidation, int category_id) {
        this.id = id;
        this.statement = statement;
        this.imageUrl = imageUrl;
        this.level = level;
        this.validation = validation;
        this.establishmentValidation = establishmentValidation;
        this.category_id = category_id;
    }

    protected QuestionEntity(Parcel in) {
        id = in.readInt();
        statement = in.readString();
        imageUrl = in.readString();
        level = in.readInt();
        validation = in.readInt();
        establishmentValidation = in.readInt();
        category_id = in.readInt();
    }

    public static final Creator<QuestionEntity> CREATOR = new Creator<QuestionEntity>() {
        @Override
        public QuestionEntity createFromParcel(Parcel in) {
            return new QuestionEntity(in);
        }

        @Override
        public QuestionEntity[] newArray(int size) {
            return new QuestionEntity[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return "QuestionEntity{" +
                "id=" + id +
                ", statement='" + statement + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", level=" + level +
                ", validation=" + validation +
                ", establishmentValidation=" + establishmentValidation +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(statement);
        parcel.writeString(imageUrl);
        parcel.writeInt(level);
        parcel.writeInt(category_id);
    }
}
