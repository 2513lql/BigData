package com.wbl.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with Simple_love
 * Date: 2016/5/3.
 * Time: 19:40
 */
public class TimeUtil {
        private static SimpleDateFormat sdf ;
        public static Timestamp getCurrentTime(String format){
                sdf = new SimpleDateFormat(format);
                return Timestamp.valueOf(sdf.format(new Date()));
        }
}
