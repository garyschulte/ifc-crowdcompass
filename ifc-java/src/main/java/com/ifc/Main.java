package com.ifc;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        {

            GregorianCalendar date1 = new GregorianCalendar(2015, 0, 31);
            GregorianCalendar date2 = new GregorianCalendar(2015, 11, 31);
            GregorianCalendar date3 = new GregorianCalendar(2016, 5, 17);

            IFCDate idate1 = new IFCDate(date1);
            assert idate1.month() == 2 : "date 1 month should be 2, but is:" + idate1.month();
            assert idate1.day() == 3 : "date 1 day should be 3, but is:" + idate1.day();

            IFCDate idate2 = new IFCDate(date2);
            assert idate2.month() == 13 : "date 2 month should be 13, but is:" + idate2.month();
            assert idate2.day() == 29 : "date 2 day should be 3, but is:" + idate2.day();
            assert idate2.year_day() : "date 2 it should be year day";

            IFCDate idate3 = new IFCDate(date3);
            assert idate3.month() == 6 : "date 3 month should be 6, but is:" + idate3.month();
            assert idate3.day() == 29 : "date 3 day should be 29, but is:" + idate3.day();
            assert idate3.leap_day() : "date 3 it should be leap day";


            System.out.println("You made it!");
        }
    }

}