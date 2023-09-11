package org.example;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeDemo {
    public static void main(String[] args) {
        // 旧的时间相关的API Date、Calendar
        Date date = new Date();
        System.out.println(date.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(date));
        // Calendar
        Calendar c = Calendar.getInstance();
        System.out.println(c.getTime() instanceof Date);
        c.add(Calendar.MONTH, -2);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime()));
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DAY_OF_MONTH);
        int w = c.get(Calendar.DAY_OF_WEEK);
        int hh = c.get(Calendar.HOUR_OF_DAY);
        int mm = c.get(Calendar.MINUTE);
        int ss = c.get(Calendar.MILLISECOND);
        System.out.println(y + "-" + m + "-" + d + " " + w + " " + hh + ":" + mm + ":" + ss);
        // 时区转换
        Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/shanghai"));
        var sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // sdf1.setTimeZone(TimeZone.getTimeZone("GMT-9:00"));
        sdf1.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        System.out.println(sdf1.format(c1.getTime()));

        // 新的时间API  输入输出遵循 ISO 8601
        LocalDateTime ldt = LocalDateTime.now();
        LocalDate ld = ldt.toLocalDate();
        LocalTime lt = ldt.toLocalTime();
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(ldt));
        System.out.println(ld);
        System.out.println(lt);

        LocalDateTime ldt1 = LocalDateTime.of(ld, lt);
        LocalDateTime ldt2 = LocalDateTime.of(2023, 5,31, 14, 30,59);
        LocalDateTime ldt3 = LocalDateTime.parse("2023-05-31T14:30:59");
        LocalDateTime ldt4 = LocalDateTime.parse("2023/05/31 14:30:59", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        System.out.println(ldt1);
        System.out.println(ldt2);
        System.out.println(ldt3);
        System.out.println(ldt4);
        System.out.println(ldt2.minusMonths(1).plusDays(2).plusHours(6));
        System.out.println(ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}
