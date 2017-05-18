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
            new GregorianCalendar(2000,3,1), // divisible by 400 non leap year
            new GregorianCalendar(2004,2,29), // regular leap year
            new GregorianCalendar(2016,12,31), // year day in a leap year
            new GregorianCalendar(2015,12,31), // year day in a non leap year
            new GregorianCalendar(2017,5,17),  // today just because
            new GregorianCalendar(2016,6,18), // leap day ordinal in leap year
            new GregorianCalendar(2016,6,19), // day after leap day ordinal in leap year
            new GregorianCalendar(2015,6,18), // leap day ordinal in nonleap year
            new GregorianCalendar(1969,12,30), // pre-epoch
            new GregorianCalendar(2,7,10) // way back date
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
        assert ifcdate.year_day() == (cal.get(Calendar.MONTH) == 12 && cal.get(Calendar.DAY_OF_MONTH) ==31);
    }
}
