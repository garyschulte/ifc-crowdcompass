package com.ifc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @since 5/17/17.
 */
@RunWith(Parameterized.class)
public class IFCDateTest {

    static GregorianCalendar someDates[] = {
            new GregorianCalendar(2000, 2, 1), // divisible by 400 non leap year
            new GregorianCalendar(2004, 1, 29), // regular leap year
            new GregorianCalendar(2016, 11, 31), // year day in a leap year
            new GregorianCalendar(2015, 11, 31), // year day in a non leap year
            new GregorianCalendar(2017, 4, 17),  // today just because
            new GregorianCalendar(2016, 0, 17), // zero based january date
            new GregorianCalendar(2016, 5, 17), // leap day ord in leap year
            new GregorianCalendar(2016, 5, 18), // day after leap day ord in leap year
            new GregorianCalendar(2015, 5, 17), // leap day ord in nonleap year
            new GregorianCalendar(1969, 11, 30), // pre-epoch
            new GregorianCalendar(2, 7, 10), // way back date
            new GregorianCalendar(2017, 4, 17, 11, 19, 20) // just to be sure time components are working
    };

    @Parameterized.Parameters
    public static Object[] data() {
        return someDates;
    }

    @Parameterized.Parameter
    public GregorianCalendar cal;

    @Test
    public void testAFewDates() {
        IFCDate ifcdate = new IFCDate(cal);
        assert ifcdate.month() <= 13;
        assert ifcdate.day() <= 29;
        assert ifcdate.leap_day() == (cal.isLeapYear(cal.get(Calendar.YEAR)) && cal.get(Calendar.DAY_OF_YEAR) == 169);
        assert cal.equals(ifcdate.convertBack());
        assert ifcdate.year_day() == (cal.get(Calendar.MONTH) == 11 && cal.get(Calendar.DAY_OF_MONTH) == 31);
    }

    @Test
    public void testAFewDatesMapper() {
        IFCDate ifcdateMap = new IFCDate(cal, new MonthDayResolver.Mapper());
        IFCDate ifcdateArith = new IFCDate(cal);

        Assert.assertEquals(ifcdateArith.month(), ifcdateMap.month());
        Assert.assertEquals(ifcdateArith.day(), ifcdateMap.day());
    }
}
