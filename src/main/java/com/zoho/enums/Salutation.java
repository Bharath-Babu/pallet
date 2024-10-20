package com.zoho.enums;

import lombok.Getter;

@Getter
public enum Salutation {
    MR("Mr."),
    MRS("Mrs."),
    MS("Ms."),
    DR("Dr."),
    PROF("Prof.");

    private final String prefix;

    Salutation(String prefix) {
        this.prefix = prefix;
    }


    }
