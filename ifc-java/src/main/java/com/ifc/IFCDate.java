package com.ifc;

import java.util.*;

/**
 * Utility class to convert the day and month representation of a gregorian calendar to
 * international fixed, with max precision of a second.  timezone and locale are unhandled
 */
public class IFCDate {
    private final int day, month, year, hour, minute, second, dayOfYear;
    public static final int LEAP_DAY_ORDINAL = 28 * 6 + 1;

    // just here for readability, reference and/or external use
    public enum MONTH {
        January(1),
        February(2),
        March(3),
        April(4),
        May(5),
        June(6),
        Sol(7),
        July(8),
        August(9),
        September(10),
        October(11),
        November(12),
        December(13);

        public final int ordinal;

        MONTH(int ord) {
            this.ordinal = ord;
        }
    }
    public IFCDate(GregorianCalendar cal){

        this.dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        this.year = cal.get(Calendar.YEAR);
        this.hour = cal.get(Calendar.HOUR_OF_DAY);
        this.minute = cal.get(Calendar.MINUTE);
        this.second = cal.get(Calendar.SECOND);

        // cheap way to let us have rational arithmetic for month/day determination
        int leapOffset = cal.isLeapYear(year) && dayOfYear >= LEAP_DAY_ORDINAL?1:0;

        // month is just the ordinal day of the year div 28, accounting for leap day
        this.month = (dayOfYear - leapOffset) / 28;

        // similarly, day is the modulo
        this.day = (dayOfYear - leapOffset) % 28;
    }

    public int day() {
        return day;
    }

    public int month() {
        return month;
    }

    public int year() {
        return year;
    }

    /**
     * ow.  these method names hurt.
     */
    public boolean year_day() {
        return (month == MONTH.December.ordinal && day == 29 );
    }

    public boolean leap_day() {
        return (month == MONTH.June.ordinal && day == 29);
    }

    /**
     * helpful for testing
     * @return
     */
    public GregorianCalendar convertBack() {
        GregorianCalendar back = new GregorianCalendar(year,0,1, hour, minute, second);
        back.add(Calendar.DAY_OF_YEAR, dayOfYear - 1);
        return back;
    }

}
