package com.wch.rumormanger.domain;

import com.wch.rumormanger.entity.Columns;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Created by weichunhe on 2018/3/7.
 */
public class Column {

    private String columnName;
    private DataType dataType;
    private boolean nullable;
    private boolean autoIncrement;
    private ColumnValueProducer columnValueProducer;

    /**
     * parse a column from columns info
     *
     * @param info
     * @return
     */
    public static Column fromColumns(Columns info) {
        Column column = new Column();
        column.setColumnName(info.getColumn_name());
        column.setDataType(DataType.getByName(info.getData_type()));
        column.setNullable("YES".equalsIgnoreCase(info.getIs_nullable()));
        column.setAutoIncrement("auto_increment".equalsIgnoreCase(info.getExtra()));
        //auto increment column don't need value producer
        if (!column.isAutoIncrement()) {
            column.setColumnValueProducer(column.getDataType().getValueProducer(info.getCharacter_maximum_length()));
        }
        return column;
    }

    public boolean isInsertColumn() {
        return columnValueProducer != null;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public ColumnValueProducer getColumnValueProducer() {
        return columnValueProducer;
    }

    public void setColumnValueProducer(ColumnValueProducer columnValueProducer) {
        this.columnValueProducer = columnValueProducer;
    }
}
