package com.wch.rumormanger.domain;

/**
 * produce a random value for a column
 */
public interface ColumnValueProducer<T> {
    T getValue();
    void setMaxLength(int maxLength);

    default ColumnValueProducer init(int maxLength){
        this.setMaxLength(maxLength);
        return this;
    }
}
