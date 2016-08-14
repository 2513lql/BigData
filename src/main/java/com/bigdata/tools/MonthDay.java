package com.bigdata.tools;

/**
 * Created by stone on 2016/7/8.
 */
public class MonthDay {
    public boolean IsLeapYear(String year){
        int y = Integer.parseInt(year);
        if(y%400==0)
            return true;
        else if(y%100==0)
            return false;
        else if(y%4==0)
            return true;
        else
            return false;
    }
    public int CountDay(String year,String month)
    {
        int m = Integer.parseInt(month);
        int fix;
        if(IsLeapYear(year))
        {
            fix =1;
        }
        else {
            fix = 0;
        }
        switch (m)
        {
            case 1:
                return 31;
            case 2:
                return 28+fix;
            case 3:
                return 31;
            case 4:
                return 30;
            case 5:
                return 31;
            case 6:
                return 30;
            case 7:
                return 31;
            case 8:
                return 31;
            case 9:
                return 30;
            case 10:
                return 31;
            case 11:
                return 30;
            case 12:
                return 31;
            default:
                return 0;
        }
    }
}
