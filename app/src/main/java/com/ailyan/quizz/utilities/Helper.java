package com.ailyan.quizz.utilities;

import java.util.Random;

public class Helper {
    public static int getRandomNumber(int size) {
        return new Random().nextInt(size);
    }
}
