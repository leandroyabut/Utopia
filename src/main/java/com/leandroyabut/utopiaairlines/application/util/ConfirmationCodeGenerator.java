package com.leandroyabut.utopiaairlines.application.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class ConfirmationCodeGenerator {

    private int seed;
    private Random random;

    public ConfirmationCodeGenerator(int seed) {
        this.seed = seed;
        this.random = new Random(seed);
    }

    public String generate() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 24;

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
