package com.wch.rumormanger.valueproducer;

import com.wch.rumormanger.domain.ColumnValueProducer;

import java.util.List;
import java.util.Random;

/**
 * Created by weichunhe on 2018/3/7.
 */
public class NumericValueProducer implements ColumnValueProducer {
    private int maxLength;

    private static char[] NUMBERS = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};
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
