package com.ailyan.quizz.data.sources.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class CategoryEntity implements Serializable {
    @PrimaryKey
    public int id;
    public String name;
    public int level;
    public int validation;
    public int establishmentValidation;
    @Embedded
    public ScoreEntity score;

    public CategoryEntity(int id, String name, int level, int validation, int establishmentValidation) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.validation = validation;
        this.establishmentValidation = establishmentValidation;
    }

    @NonNull
    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", validation=" + validation +
                ", establishmentValidation=" + establishmentValidation +
                '}';
    }
}