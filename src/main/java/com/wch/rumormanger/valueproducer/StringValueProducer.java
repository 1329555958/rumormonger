package com.wch.rumormanger.valueproducer;

import com.wch.rumormanger.domain.ColumnValueProducer;

import java.util.Random;

/**
 * Created by weichunhe on 2018/3/7.
 */
public class StringValueProducer implements ColumnValueProducer {
    private int maxLength;

    private static char[] NUMBERS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '_', '-',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static Random RANDOM = new Random();

    @Override
    public Object getValue() {
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < maxLength; i++) {
            number.append(NUMBERS[RANDOM.nextInt(NUMBERS.length - 1)]);
        }
        return number.toString();
    }

    @Override
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

}
