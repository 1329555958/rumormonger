package com.wch.rumormanger.domain;

import com.wch.rumormanger.entity.Columns;
import com.wch.rumormanger.valueproducer.DateValueProducer;
import com.wch.rumormanger.valueproducer.NumericValueProducer;
import com.wch.rumormanger.valueproducer.StringValueProducer;

/**
 * mysql data type
 */
public enum DataType {

    VARCHAR(50),
    LONGBLOB(100),
    CHAR(5),
    BLOB(5),
    TEXT(5),

    DATE(0),
    DATETIME(0),
    TIMESTAMP(0),

    BIGINT(10),
    INT(5),
    SMALLINT(3),
    DECIMAL(20),
    TINYINT(2),
    DOUBLE(5);
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
     * @param
     * @return
     */
    public ColumnValueProducer getValueProducer(Columns columns) {
        switch (this) {
            case VARCHAR:
            case LONGBLOB:
            case CHAR:
            case BLOB:
            case TEXT:
                return new StringValueProducer().init(Math.min(columns.getCharacter_maximum_length().intValue(), this.getMaxLength()));
            case DATE:
            case DATETIME:
            case TIMESTAMP:
                return new DateValueProducer();
            case BIGINT:
            case INT:
            case SMALLINT:
            case TINYINT:
            case DECIMAL:
                return new NumericValueProducer().init(Math.min(columns.getNumeric_precision().intValue() - columns.getNumeric_scale().intValue(), this.getMaxLength()));
            case DOUBLE:
                return new NumericValueProducer().init(Math.min(columns.getNumeric_precision().intValue(), this.getMaxLength()));
        }
        return null;
    }

    public int getMaxLength() {
        return maxLength;
    }
}
