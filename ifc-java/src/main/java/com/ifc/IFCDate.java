package com.ifc;

import java.util.*;

/**
 * Utility class to convert the day and month representation of a gregorian calendar to
 * international fixed, with max precision of a second.  timezone and locale are unhandled
 */
public class IFCDate {
    private final int day, month, year, hour, minute, second, dayOfYear;
    public static final int
            LEAP_DAY_ORDINAL = 28 * 6 + 1,
            ONE_BASED = 1,
            FIXED_DAYS = 28;


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
    public IFCDate(GregorianCalendar cal) {

        this.dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        this.year = cal.get(Calendar.YEAR);
        this.hour = cal.get(Calendar.HOUR_OF_DAY);
        this.minute = cal.get(Calendar.MINUTE);
        this.second = cal.get(Calendar.SECOND);

        // if we are on or past leap day, our leap month offset is 1 for month determination below
        int leapMonthOffset = cal.isLeapYear(year) && dayOfYear >= LEAP_DAY_ORDINAL ? 1 : 0;

        // fidgety, month is fundamentally dayOfYear div 28, with offsets:
        //  minus a leapMonthOffset calculated above
        //  minus one to account for current month 28th day reporting
        //  finally add 1 to the result to make months 1 based
        this.month = (dayOfYear - leapMonthOffset - 1) / FIXED_DAYS + ONE_BASED;


        // if we are past leap day, our leap day offset is 1 for day determination below
        int leapDayOffset = cal.isLeapYear(year) && dayOfYear > LEAP_DAY_ORDINAL ? 1 : 0;

        // similarly fidgety, day is dayOfYear minus whole months, with offsets:
        //   minus a leapDayOffset
        //   minus 1 from months to make months zero based
        this.day = dayOfYear - leapDayOffset - (month - ONE_BASED) * FIXED_DAYS;
    }

    public int day() {
        return day;
    }

    public int month() {
        return month;
    }

    @CoverageIgnore
    public int year() {
        return year;
    }

    @CoverageIgnore
    public int hour() {
        return hour;
    }

    @CoverageIgnore
    public int minute() {
        return minute;
    }

    @CoverageIgnore
    public int second() {
        return second;
    }

    /**
     * ow.  these method names hurt.
     */
    public boolean year_day() {
        return (month == MONTH.December.ordinal && day == 29);
    }

    public boolean leap_day() {
        return (month == MONTH.June.ordinal && day == 29);
    }

    /**
     * helpful for testing
     * @return
     */
    public GregorianCalendar convertBack() {
        GregorianCalendar back = new GregorianCalendar(year, 0, 1, hour, minute, second);

        // starting from jan, so go back to zero-based month value
        int addTo = (month - ONE_BASED) * 28;

        // if we are past june and it is a leap year, tack on another day
        if (month > 6 && back.isLeapYear(year)) {
            addTo +=1;
        }

        // starting on jan 1, so subtract a day from day value
        addTo += day - 1;
        back.add(Calendar.DAY_OF_YEAR, addTo);

        return back;
    }

}
