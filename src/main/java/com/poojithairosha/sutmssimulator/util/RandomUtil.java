package com.poojithairosha.sutmssimulator.util;

import java.util.Random;

public class RandomUtil {

    private static final Random random = new Random();

    public static int generateRandomNumber(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public static int generateRandomNumber(int num) {
        return random.nextInt(num);
    }

}
