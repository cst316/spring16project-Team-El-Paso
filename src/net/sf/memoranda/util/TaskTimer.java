package net.sf.memoranda.util;

public class TaskTimer {

	private static String clockHours;
	private static String clockMinutes;
	private static String clockSeconds;
	private static String totalTime;
	private static int seconds;
	private static int minutes;
	private static int hours;
	private static String secs = "00";
	private static String mins = "00";
	private static String hrs = "00";

	public static String tickTock(String time) {
		try {
			secs = time.substring(6, 8);
			mins = time.substring(3, 5);
			hrs = time.substring(0, 2);
		} catch (NullPointerException np) {
			secs = "00";
			mins = "00";
			hrs = "00";
		}
		
		try {
			seconds = Integer.parseInt(secs);
		} catch (NumberFormatException nfe) {
			seconds = 00;
		}
		try {
			minutes = Integer.parseInt(mins);
		} catch (NumberFormatException nfe) {
			minutes = 00;
		}
		try {
			hours = Integer.parseInt(hrs);
		} catch (NumberFormatException nfe) {
			hours = 00;
		}
		seconds++;
		if (seconds > 59) {
			seconds = 0;
			minutes += 1;
		}
		if (minutes > 59) {
			minutes = 0;
			hours += 1;
		}
		clockHours = new Integer(hours).toString();
		clockMinutes = new Integer(minutes).toString();
		clockSeconds = new Integer(seconds).toString();

		totalTime = (("00" + hours).substring(clockHours.length()) + ":"
				+ ("00" + minutes).substring(clockMinutes.length()) + ":"
				+ ("00" + seconds).substring(clockSeconds.length()));

		return totalTime;

	}

}