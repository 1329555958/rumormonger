package com.wch.rumormanger.valueproducer;

import com.wch.rumormanger.domain.ColumnValueProducer;

import java.util.Date;

/**
 * Created by weichunhe on 2018/3/7.
 */
public class DateValueProducer implements ColumnValueProducer<Date> {
    @Override
    public Date getValue() {
        return new Date();
    }

    @Override
    public void setMaxLength(int maxLength) {
    }

}
