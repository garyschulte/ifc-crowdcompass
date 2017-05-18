package com.ifc;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.*;

import static com.ifc.MonthDayResolver.MONTH;
import static com.ifc.MonthDayResolver.ONE_BASED;

/**
 * Utility class to convert the day and month representation of a gregorian calendar to
 * international fixed, with max precision of a second.  timezone and locale are unhandled
 *
 * just for fun there are two MonthDayResolver's.
 */
public class IFCDate {
    private int day, month, year, hour, minute, second, dayOfYear;
    private MonthDayResolver monthDayResolver;

    // default to the arithmetic resolver
    public IFCDate(GregorianCalendar cal) {
        this(cal, new MonthDayResolver.Arithmetic());
    }

    public IFCDate(GregorianCalendar cal, MonthDayResolver monthDayResolver) {
        this.monthDayResolver = monthDayResolver;
        this.dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        this.year = cal.get(Calendar.YEAR);
        this.hour = cal.get(Calendar.HOUR_OF_DAY);
        this.minute = cal.get(Calendar.MINUTE);
        this.second = cal.get(Calendar.SECOND);
        ImmutablePair<Integer, Integer> monthDay = monthDayResolver.getMonthDay(cal);
        this.month = monthDay.getLeft().intValue();
        this.day = monthDay.getRight().intValue();
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
        return (month == MONTH.December.ord && day == 29);
    }

    public boolean leap_day() {
        return (month == MONTH.June.ord && day == 29);
    }

    /**
     * helpful for testing
     * @return
     */
    public GregorianCalendar convertBack() {
        GregorianCalendar back = new GregorianCalendar(year, 0, 1, hour, minute, second);

        // starting from jan, so go back to zero-based month value
        int addTo = (month - ONE_BASED) * 28;

        // if we are past june and it is a leap year, tack on another day (first case of 29 day month)
        if (month > 6 && back.isLeapYear(year)) {
            addTo += 1;
        }

        // starting on jan 1, so subtract a day from day value
        addTo += day - 1;
        back.add(Calendar.DAY_OF_YEAR, addTo);

        return back;
    }
}
