package com.github.neboskreb.red.and.blue.dto;

public class EntityDTO {
    private String fieldString;
    private Integer fieldInteger;
//    private Long fieldLong; // <-- TODO [5] Uncomment this line to simulate a bug

    public String getFieldString() {
        return fieldString;
    }

    public void setFieldString(String fieldString) {
        this.fieldString = fieldString; // <-- TODO [6] Comment this line out to simulate a bug
    }

    public Integer getFieldInteger() {
        return fieldInteger;
    }

    public void setFieldInteger(Integer fieldInteger) {
        this.fieldInteger = fieldInteger;
    }
}
