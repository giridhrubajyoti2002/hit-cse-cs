package org.upriser.hitcsecs.enums;

import java.util.HashMap;
import java.util.Map;

public enum YearType {
    FIRST("FIRST"),
    SECOND("SECOND"),
    THIRD("THIRD"),
    FOURTH("FOURTH")
    ;

    private final String year;
    YearType(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return this.year;
    }

    private static final Map<YearType, YearType> nextYear = new HashMap<>(){{
            put(YearType.FIRST,YearType.SECOND);
            put(YearType.SECOND,YearType.THIRD);
            put(YearType.THIRD,YearType.FOURTH);
    }};
    public static YearType nextYear(YearType yearType){
        return nextYear.get(yearType);
    }
}
