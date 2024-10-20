package com.zoho.enums;

import lombok.Getter;

@Getter
public enum LeadFormField {
    LEAD_OWNER(1),
    FIRST_NAME(7),
    TITLE(9),
    PHONE(11),
    MOBILE(13),
    ANNUAL_REVENUE(19),

    COMPANY(6),
    LAST_NAME(8),
    EMAIL(10),
    FAX(12),
    WEBSITE(14),
    NO_OF_EMPLOYEES(18),
    Skype_ID(22),
    SECONDARY_EMAIL(23),
    TWITTER(24),
    STREET(25),
    CITY(26),
    STATE(27),
    ZIP_CODE(28),
    COUNTRY(29);

    public final int tabIndex;

    LeadFormField(int tabIndex) {
        this.tabIndex = tabIndex;
    }

}
