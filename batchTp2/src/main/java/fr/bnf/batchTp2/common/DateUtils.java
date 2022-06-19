package fr.bnf.batchTp2.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class DateUtils {

	public static final String FORMAT_YYYYMMDD = "yyyyMMdd";
	public static final String FORMAT_YYYYMMDD_HHMMSS = "yyyyMMdd-HHmmss";
	public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String FORMAT_YYYYMMDD_HHMMSS_PRETTY = "yyyy/MM/dd HH:mm:ss";
	public static final String FORMAT_YYYYMMDD_PRETTY = "yyyy/MM/dd";

	/** Day of week for sunday */
	public static final int SUNDAY_DAY_NUMBER = 7;

	private DateUtils() {
		// hidden constructor
	}

	public static String formatDateNowPretty() {
		return formatDateNow(FORMAT_YYYYMMDD_HHMMSS_PRETTY);
	}

	public static String formatDateNow(String pattern) {
		LocalDateTime locDate = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return locDate.format(formatter);
	}

	public static String formatDatePretty(Date date) {
		LocalDateTime locDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		return formatDate(locDate, FORMAT_YYYYMMDD_HHMMSS_PRETTY);
	}

	public static String formatDatePretty(LocalDate locDate) {
		return formatDate(locDate, FORMAT_YYYYMMDD_PRETTY);
	}

	public static String formatDatePretty(LocalDateTime locDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_YYYYMMDD_HHMMSS_PRETTY);
		return locDate.format(formatter);
	}

	public static String formatDate(LocalDateTime locDate, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return locDate.format(formatter);
	}

	public static String formatDate(LocalDate locDate, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return locDate.format(formatter);
	}

	public static String formatDate(Date date, String pattern) {
		LocalDateTime locDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		return formatDate(locDate, pattern);
	}

	public static LocalDate parseDate(String dateStr, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDate.parse(dateStr, formatter);
	}

	public static LocalDateTime parseDateTime(String dateStr, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.parse(dateStr, formatter);
	}

	public static Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date toDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}


	
	/**
	 * Comparer deux dates sans Time
	 * @param date1
	 * @param date2
	 * @return true si égalité et false sinon
	 */
	public static boolean compareTwoDates(Date date1, Date date2) {
		
		LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		return localDate1.isEqual(localDate2);
	}
	
	/**
	 *  Retourne midinight for same date
	 * @param date
	 * @return 
	 */
	public static Date getMidiNight(Date date) {
		
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		LocalTime midnight = LocalTime.MIDNIGHT;
		
		LocalDateTime dateMidnight = LocalDateTime.of(localDate, midnight);
		
		return toDate(dateMidnight);

	}
	
	/**
	 *  Retourne la date avant un nombre de jours specifié
	 * @param date
	 * @return 
	 */
	public static Date getSameDayBeforeDate(Date date, int nbJours) {
		
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						
		return toDate(localDate.minusDays(nbJours));

	}
	
}

