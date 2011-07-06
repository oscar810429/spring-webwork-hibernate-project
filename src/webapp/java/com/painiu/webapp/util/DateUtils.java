/*
 * @(#)DateUtils.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import com.opensymphony.xwork.util.LocalizedTextUtil;

/**
 * <p>
 * <a href="DateUtils.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: DateUtils.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class DateUtils {
	
	public static String distanceInWords(Date fromDate) {
		return distanceInWords(fromDate, new Date(), true);
	}
	
	public static Date getISODateFormat(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal = org.apache.commons.lang.time.DateUtils.truncate(cal, Calendar.DATE);
		return cal.getTime();
	}
	
	public static Date[] getDatesBetweenLastdays(int num) {
		Date[] dates = new Date[2];
		Calendar cal = Calendar.getInstance();
		cal = org.apache.commons.lang.time.DateUtils.truncate(cal, Calendar.DATE);
		dates[1] = cal.getTime();
		cal.add(Calendar.DATE, num);
		dates[0] = cal.getTime();
		return dates;
	}
	
	
	public static String distanceInWords(Date fromDate, Date toDate, boolean countSeconds) {
		Locale locale = LocaleContextHolder.getLocale();
		
		long seconds = toDate.getTime()/1000 - fromDate.getTime()/1000;
		long minutes = seconds/60;
		
		String key = null;
		long count = 0;
		
		if (minutes <= 1) {
			if (countSeconds) {
				key = "timedistance.less.than.some.seconds";
				if (seconds < 5) {
					count = 5;
				} else if (5 <= seconds && seconds < 10) {
					count = 10;
				} else if (10 <= seconds && seconds < 20) {
					count = 20;
				} else if (20 <= seconds && seconds < 40) {
					key = "timedistance.half.a.minute";
				} else if (40 <= seconds && seconds < 60) {
					key = "timedistance.less.than.a.minute";
				} else {
					key = "timedistance.one.minute";
				}
			} else {
				key = "timedistance.less.than.a.minute";
			}
		} else if (1 < minutes && minutes <= 45) {
			key = "timedistance.some.minutes";
			count = minutes;
		} else if (45 < minutes && minutes <= 90) {
			key = "timedistance.about.one.hour";
		} else if (90 < minutes && minutes < ONE_DAY) {
			key = "timedistance.some.hours";
			count = minutes/60; 
		} else if (ONE_DAY <= minutes && minutes < TWO_DAYS) {
			key = "timedistance.one.day";
		} else if (TWO_DAYS <= minutes && minutes < ONE_WEEK) {
			key = "timedistance.some.days";
			count = minutes/ONE_DAY;
		} else if (ONE_WEEK <= minutes && minutes < TWO_WEEKS) {
			key = "timedistance.one.week";			
		} else if (TWO_WEEKS <= minutes && minutes <= THREE_WEEKS) {
			key = "timedistance.some.weeks";
			count = minutes/ONE_WEEK;
		} else if (THREE_WEEKS < minutes && minutes < FIVE_WEEKS) {
			key = "timedistance.about.one.month";
		} else if (FIVE_WEEKS <= minutes && minutes < TWO_MONTHS) {
			key = "timedistance.more.than.one.month";
		} else if (TWO_MONTHS <= minutes && minutes < ONE_YEAR) {
			key = "timedistance.some.months";
			count = minutes/ONE_MONTH;
		} else if (ONE_YEAR <= minutes && minutes < TWO_YEARS) {
			key = "timedistance.one.year";
		} else {
			key = "timedistance.some.years";
			count = minutes/ONE_YEAR;
		}
		
		Object[] args = count > 0 ? new Object[] { new Long(count) } : null;
		
		return LocalizedTextUtil.findDefaultText(key, locale, args);
	}
    
    private static final long ONE_DAY = 60 * 24;
    private static final long TWO_DAYS = ONE_DAY * 2;
    private static final long ONE_WEEK = ONE_DAY * 7;
    private static final long TWO_WEEKS = ONE_WEEK * 2;
    private static final long THREE_WEEKS = ONE_WEEK * 3;    
    private static final long FIVE_WEEKS = ONE_WEEK * 5;    
    private static final long ONE_MONTH = ONE_WEEK * 4;
    private static final long TWO_MONTHS = ONE_MONTH * 2;
    private static final long ONE_YEAR = ONE_MONTH * 12;
    private static final long TWO_YEARS = ONE_YEAR * 2;
    
    
	public static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	
	public static Calendar getCalendar(String year, String month, String date) {
		int yearNumber = NumberUtils.toInt(year, 2004);
		int monthNumber = NumberUtils.toInt(month, 1);
		int dateNumber = NumberUtils.toInt(date, 1);
		
		return getCalendar(yearNumber, monthNumber, dateNumber);
	}
	
	public static Calendar getCalendar(int year, int month, int date) {
		if (month > 12) {
			month = 12;
		}
		if (month < 1) {
			month = 1;
		}
		if (date > 31) {
			date = 31;
		}
		if (date < 1) {
			date = 1;
		}
		
		StringBuffer sb = new StringBuffer(10);
		sb.append(year);
		sb.append('-');
		sb.append(month > 9 ? month : "0" + month);
		sb.append('-');
		sb.append(date > 9 ? date : "0" + date);
		
		Date value = null;
		try {
			value = DF.parse(sb.toString());
		} catch (ParseException e) {
			value = new Date();
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(value);
		return calendar;
	}
	
	/**
	 * Parse fromDate and toDate from string.
	 * format: 2007-05-xx
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date[] parseDateBoundary(String dateString) {
		String[] parts = StringUtils.split(dateString, '-');
		
		if (parts.length < 3 || !NumberUtils.isNumber(parts[0])) {
			throw new IllegalArgumentException();
		}
		
		int year = NumberUtils.toInt(parts[0]);
		int month = NumberUtils.toInt(parts[1], -1);
		int date = NumberUtils.toInt(parts[2], -1);
		
		Calendar calendar = com.painiu.webapp.util.DateUtils.getCalendar(year, month, date);
		
		Date fromDate = null;
		Date toDate = null;
		int field;
		
		if (date != -1) {
			field = Calendar.DATE;
		} else if (month != -1) {
			field = Calendar.MONTH;
		} else { // archiveYear should not be null.
			field = Calendar.YEAR;
		}
		
		fromDate = org.apache.commons.lang.time.DateUtils.truncate(calendar, field).getTime();
		toDate = org.apache.commons.lang.time.DateUtils.round(calendar, field).getTime();
		
		if (fromDate.getTime() == toDate.getTime()) {
			Calendar clone = Calendar.getInstance();
			clone.setTime(fromDate);
			clone.add(field, 1);
			toDate = clone.getTime();
		}
		
		return new Date[] { fromDate, toDate };
	}
	
	public static Date getNow() {
		return new Date();
	}
}
