package com.github.neboskreb.red.and.blue.model;

public class ImmutableEntity {
    private final String fieldString;
    private final Integer fieldInteger;
//    private final Long fieldLong = 42L; // <-- TODO [3] Uncomment this line to simulate a bug

    private ImmutableEntity(Builder builder) {
        this.fieldString = builder.fieldString;
        this.fieldInteger = builder.fieldInteger;
    }

    public String getFieldString() {
        return fieldString;
    }

    public Integer getFieldInteger() {
        return fieldInteger;
    }



    public static class Builder {
        private String fieldString;
        private Integer fieldInteger;

        /** Default constructor */
        public Builder() {}

        /** Copy constructor */
        public Builder(ImmutableEntity original) {
            this.fieldString = original.fieldString;
            this.fieldInteger = original.fieldInteger; // <-- TODO [4] Comment this line out to simulate a bug
        }

        public Builder setFieldString(String fieldString) {
            this.fieldString = fieldString;
            return this;
        }

        public Builder setFieldInteger(Integer fieldInteger) {
            this.fieldInteger = fieldInteger;
            return this;
        }

        public ImmutableEntity build() {
            return new ImmutableEntity(this);
        }
    }
}
