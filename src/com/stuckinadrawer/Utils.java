package com.stuckinadrawer;

import java.util.Random;

public class Utils {

    private static Random random = new Random();

    /** Returns a random number between 0 (inclusive) and the specified value (inclusive). */
    static public int random (int range) {
        return random.nextInt(range + 1);
    }

    /** Returns a random number between start (inclusive) and end (inclusive). */
    static public int random (int start, int end) {
        return start + random.nextInt(end - start + 1);
    }

    static public boolean randomBoolean () {
        return random.nextBoolean();
    }

    static public float random () {
        return random.nextFloat();
    }

    /** Returns a random number between 0 (inclusive) and the specified value (inclusive). */
    static public float random (float range) {
        return random.nextFloat() * range;
    }

    /** Returns a random number between start (inclusive) and end (inclusive). */
    static public float random (float start, float end) {
        return start + random.nextFloat() * (end - start);
    }

}
