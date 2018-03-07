package com.wch.rumormanger.domain;

import com.wch.rumormanger.valueproducer.DateValueProducer;
import com.wch.rumormanger.valueproducer.NumericValueProducer;
import com.wch.rumormanger.valueproducer.StringValueProducer;

/**
 * mysql data type
 */
public enum DataType {

    VARCHAR(50),
    LONGBLOB(100),
    CHAR(50),
    BLOB(100),
    TEXT(100),

    DATE(0),
    DATETIME(0),
    TIMESTAMP(0),

    BIGINT(10),
    INT(5),
    SMALLINT(3),
    DECIMAL(5),
    TINYINT(2),
    DOUBLE(10);
    private int maxLength;

    DataType(int maxLength) {
        this.maxLength = maxLength;
    }

    public static DataType getByName(String name) {
        for (DataType dataType : DataType.values()) {
            if (dataType.name().equalsIgnoreCase(name)) {
                return dataType;
            }
        }
        return null;
    }

    /**
     * @param maxLength
     * @return
     */
    public ColumnValueProducer getValueProducer(Long maxLength) {
        switch (this) {
            case VARCHAR:
            case LONGBLOB:
            case CHAR:
            case BLOB:
            case TEXT:
                return new StringValueProducer().init(Math.min(maxLength.intValue(), this.getMaxLength()));
            case DATE:
            case DATETIME:
            case TIMESTAMP:
                return new DateValueProducer();
            case BIGINT:
            case INT:
            case SMALLINT:
            case DECIMAL:
            case TINYINT:
            case DOUBLE:
                return new NumericValueProducer().init(this.getMaxLength());
        }
        return null;
    }

    public int getMaxLength() {
        return maxLength;
    }
}
