package com.ifc;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @since 5/18/17.
 */
public interface MonthDayResolver {
    int LEAP_DAY_ORDINAL = 28 * 6 + 1,
            ONE_BASED = 1,
            FIXED_DAYS = 28,
            MONTH_EXTRA_DAY = 29;

    // mostly exists for readability
    enum MONTH {
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

        public final int ord;

        MONTH(int ord) {
            this.ord = ord;
        }
    }

    ImmutablePair<Integer, Integer> getMonthDay(GregorianCalendar cal);


    /**
     * Arithmetic based month day resolver.
     */
    public static class Arithmetic implements MonthDayResolver {


        @Override
        public ImmutablePair<Integer, Integer> getMonthDay(GregorianCalendar cal) {
            int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
            int year = cal.get(Calendar.YEAR);

            // if we are on or past leap day, our leap month offset is 1 for month determination below
            int  leapDayMonthOffset = cal.isLeapYear(year) && dayOfYear >= LEAP_DAY_ORDINAL ? 1 : 0;
            // 'year day' offset for month determination if we happen to be on the last day of the year
            int yearDayMonthOffset = dayOfYear == 365 + leapDayMonthOffset ? 1 : 0;

            // if we are *past* leap day, our leap day offset is 1 for day determination below
            int leapDayDayOffset = cal.isLeapYear(year) && dayOfYear > LEAP_DAY_ORDINAL ? 1 : 0;

            // fidgety, month is fundamentally dayOfYear div 28, with offsets:
            //  minus a leapDayMonthOffset determined above
            //  minus a yearDayMonthOffset determined above
            //  minus one to account for current month's incomplete day
            //  finally add 1 to the result to make months 1 based
            Integer month = (dayOfYear - yearDayMonthOffset - leapDayMonthOffset - 1) / FIXED_DAYS + ONE_BASED;


            // similarly fidgety, day is dayOfYear minus whole months, with offsets:
            //   minus a leapDayOffset
            //   minus 1 from months to make months zero based
            Integer day = dayOfYear - leapDayDayOffset - (month - ONE_BASED) * FIXED_DAYS;
            return new ImmutablePair<Integer, Integer>(month, day);

        }
    }

    /**
     * array mapped / space trade-off resolver mostly because the arithmetic based one hurts to look at
     */
    public static class Mapper implements MonthDayResolver {

        public static final ImmutablePair<Integer, Integer> REGULAR_YEAR [] = populateYear(false)
                , LEAP_YEAR [] = populateYear(true);

        @Override
        public ImmutablePair<Integer, Integer> getMonthDay(GregorianCalendar cal) {
            ImmutablePair<Integer, Integer> [] yearMap;
            int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);

            if (cal.isLeapYear(cal.get(Calendar.YEAR)))
                yearMap = LEAP_YEAR;
            else
                yearMap = REGULAR_YEAR;

            Integer month = yearMap[dayOfYear].getLeft().intValue();
            Integer day = yearMap[dayOfYear].getRight().intValue();
            return new ImmutablePair<Integer, Integer>(month, day);

        }


        private static ImmutablePair<Integer, Integer> [] populateYear(boolean isLeapYear) {
            ImmutablePair[] yearArray = new ImmutablePair[367];
            int index = 0;
            for (int month = 1 ; month <=13 ; month ++) {
                for (int day = 1; day <= 28; day ++) {
                    yearArray[++index] = new ImmutablePair(month, day);
                }
                if (month == MONTH.June.ord && isLeapYear) {
                    yearArray[++index] = new ImmutablePair(MONTH.June.ord, MONTH_EXTRA_DAY);
                }
            }
            yearArray[++index] = new ImmutablePair(MONTH.December.ord, MONTH_EXTRA_DAY);
            return yearArray;
        }


    }
}
