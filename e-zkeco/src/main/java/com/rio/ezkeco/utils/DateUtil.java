package com.rio.ezkeco.utils;/**
 * @author vic fu
 **/

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *@author vic fu
 */
public class DateUtil {

    public static final String FORMAT = "yyyy-MM-dd";
    public static final String YEAR = "year";
    public static final String MONTH = "month";

    /**
     * 获得当前系统日期:格式(yyyy-MM-dd)
     *
     * @return
     * @Description: 获得当前系统日期:格式(yyyy-MM-dd)
     */
    public static String getCurDate() {
        return getCurDateTime("yyyy-MM-dd");
    }

    /**
     * 获得当前系统日期:格式(yyyyMMdd)
     *
     * @return
     * @Description: 获得当前系统日期:格式(yyyyMMdd)
     */
    public static String getCurDate_yyyyMMdd() {
        return getCurDateTime("yyyyMMdd");
    }

    /**
     * 获得当前系统日期:格式(yyyyMM);
     *
     * @return
     * @Description: 获得当前系统日期:格式(yyyyMM);
     */
    public static String getCurDate_yyyyMM() {
        return getCurDateTime("yyyyMM");
    }

    /**
     * 获得当前系统日期:指定格式(yyyy-MM-dd HH:mm:ss)
     *
     * @param format
     * @return
     * @Description: 获得当前系统日期:指定格式(yyyy-MM-dd HH:mm:ss)
     */
    public static String getCurDateTime(String format) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 获得当前系统日期:格式(yyyy-MM-dd HH:mm:ss)
     *
     * @return
     * @Description: 获得当前系统日期:格式(yyyy-MM-dd HH:mm:ss)
     */
    public static String getCurDateTime() {
        return getCurDateTime("yyyy-MM-dd HH:mm:ss");
    }

    public static String getCreateTime() {
        return "Created on:"+getCurDateTime("MM/dd/yyyy HH:mm:ss");
    }

    /**
     * 获得当前系统日期:格式(yyyyMMddHHmmss)
     *
     * @return
     * @Description: 获得当前系统日期:格式(yyyyMMddHHmmss)
     */
    public static String getCurDateTime_yyyyMMddHHmmss() {
        return getCurDateTime("yyyyMMddHHmmss");
    }

    /**
     * 获得当前系统日期:格式(yyyyMMddHHmmssSSS)
     *
     * @return
     * @Description: 获得当前系统日期:格式(yyyyMMddHHmmssSSS)
     */
    public static String getCurDateTime_yyyyMMddHHmmssSSS() {
        return getCurDateTime("yyyyMMddHHmmssSSS");
    }

    /**
     * 获得当前系统时间:格式(HH:mm:ss)
     *
     * @return
     * @Description: 获得当前系统时间:格式(HH:mm:ss)
     */
    public static String getCurTime() {
        return getCurDateTime("HH:mm:ss");
    }

    /**
     * 日期加减年、月、天数、小时、分钟
     *
     * @param dateYMDHMS 日期字符串（格式为format指定的格式）
     * @param format     指定dateYMDHMS的日期格式
     * @param unit       日期加减的单位（可以是年、月、天数、小时、分钟）
     * @param amount     要加减的数量
     * @return 返回加减后的日期（格式为format指定的格式）
     * @Description: 日期加减年、月、天数、小时、分钟
     */
    public static String get0perationDate(String dateYMDHMS, String format, String unit, int amount) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Calendar calendar = Calendar.getInstance();
            if (dateYMDHMS != null && !"".equals(dateYMDHMS)) {
                Date date = dateFormat.parse(dateYMDHMS);
                calendar.setTime(date);
            } else {
                Date date = new Date(System.currentTimeMillis());
                calendar.setTime(date);
            }
            if (unit.equals("year") || unit.equals("years")) {
                calendar.add(Calendar.YEAR, amount);
            } else if (unit.equals("month") || unit.equals("months")) {
                calendar.add(Calendar.MONTH, amount);
            } else if (unit.equals("day") || unit.equals("days")) {
                calendar.add(Calendar.DAY_OF_YEAR, amount);
            } else if (unit.equals("hour") || unit.equals("hours")) {
                calendar.add(Calendar.HOUR_OF_DAY, amount);
            } else if (unit.equals("minute") || unit.equals("minutes")) {
                calendar.add(Calendar.MINUTE, amount);
            }
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 获取传入日期所在月的第一天或者最后一天
     *
     * @param dateYMDHMS 日期字符串（格式为format指定的格式）
     * @param format     指定dateYMDHMS的日期格式
     * @param isFirst    true:第一天 false:最后一天
     * @return
     */
    public static String getFirstDayDateOfMonth(String dateYMDHMS, String format, boolean isFirst) {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Calendar calendar = Calendar.getInstance();
            if (dateYMDHMS != null && !"".equals(dateYMDHMS)) {
                Date date = dateFormat.parse(dateYMDHMS);
                calendar.setTime(date);
            } else {
                Date date = new Date(System.currentTimeMillis());
                calendar.setTime(date);
            }
            if (isFirst) {
                final int last = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);

                calendar.set(Calendar.DAY_OF_MONTH, last);
            } else {
                final int last = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                calendar.set(Calendar.DAY_OF_MONTH, last);
            }
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 获取英文日期的 年月 30 October 2012 转成 Oct 2012
     *
     * @param dateYMDHMS
     * @return
     */
    public static String getMonthYearEn(String dateYMDHMS) {

        if (StringUtil.isEmpty(dateYMDHMS)) return "";

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);

        String formatDate = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);

        try {
            Date date = dateFormat.parse(dateYMDHMS);
            formatDate = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] strs = formatDate.split(" ");

        return strs[1].substring(0, 3) + " " + strs[2];
    }

    /**
     * 获取英文日期的 年月 30 October 2012 转成 Oct-12
     *
     * @param dateYMDHMS
     * @return
     */
    public static String getMonthYearEn2(String dateYMDHMS) {

        if (StringUtil.isEmpty(dateYMDHMS)) return "";

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);

        String formatDate = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);

        try {
            Date date = dateFormat.parse(dateYMDHMS);
            formatDate = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] strs = formatDate.split(" ");

        return strs[1].substring(0, 3) + "-" + strs[2].substring(2,4);
    }

    /**
     * 获取月年  yyyyMM   201912
     *
     * @param dateYMDHMS
     * @return
     */
    public static String getYearMonth(String dateYMDHMS) {
        if (StringUtil.isEmpty(dateYMDHMS)) return "";
        return getFormatDate(dateYMDHMS).replace("-", "").substring(0, 6);
    }


    /**
     * 获取年
     *
     * @param dateYMDHMS
     * @return
     */
    public static String getYear(String dateYMDHMS) {

        if (StringUtil.isEmpty(dateYMDHMS)) return "";
        return getFormatDate(dateYMDHMS).substring(0, 4);
    }

    /**
     * 获取月
     *
     * @param dateYMDHMS
     * @return
     */
    public static String getMonth(String dateYMDHMS) {
        if (StringUtil.isEmpty(dateYMDHMS)) return "";
        return getFormatDate(dateYMDHMS).substring(5, 7);

    }

    /**
     * 得到指定格式日期
     *
     * @param dateYMDHMS
     * @return
     */
    public static String getFormatDate(String dateYMDHMS) {

        if (StringUtil.isEmpty(dateYMDHMS)) return "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);

            Date date = dateFormat.parse(dateYMDHMS);

            return dateFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String replaceSplit(String dateYMDHMS) {
        if (StringUtil.isEmpty(dateYMDHMS)) return "";
        return dateYMDHMS.replace("-", "");
    }
}
