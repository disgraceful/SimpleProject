package com.simpleproj.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class SPDateUtils {
	private static Logger LOG = LogManager.getLogger(SPDateUtils.class);

	public static boolean isWithinDaysFuture(Calendar cal, int days) {
		if (cal == null) {
			return false;
		}
		Calendar today = Calendar.getInstance();
		Calendar future = Calendar.getInstance();
		future.add(Calendar.DAY_OF_YEAR, days);
		return (isSameDay(cal, today) || isAfterDay(cal, today) || isSameDay(cal, future)) && !isAfterDay(cal, future);
	}

	public static boolean isAfterDay(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			return false;
		}
		if (cal1.get(Calendar.ERA) < cal2.get(Calendar.ERA))
			return false;
		if (cal1.get(Calendar.ERA) > cal2.get(Calendar.ERA))
			return true;
		if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR))
			return false;
		if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR))
			return true;
		return cal1.get(Calendar.DAY_OF_YEAR) > cal2.get(Calendar.DAY_OF_YEAR);
	}

	public static boolean isSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameDay(cal1, cal2);
	}

	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			return false;
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
	}

	public static boolean isToday(Date date) {
		return isSameDay(date, Calendar.getInstance().getTime());
	}

	public static boolean isToday(Calendar cal) {
		return isSameDay(cal, Calendar.getInstance());
	}
}
