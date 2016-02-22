package net.sf.memoranda.ui;

import javax.swing.Timer;

public class TaskTimer {
	private int ONE_SEC;
	private Timer timer;
	private int hours;
	private int minutes;
	private int seconds;
	private String clockHours;
	private String clockMinutes;
	private String clockSeconds;

	public TaskTimer(int ONE_SEC, int hours, int minutes, int seconds) {
		this.ONE_SEC = ONE_SEC;
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}

	public int getONE_SEC() {
		return ONE_SEC;
	}

	public void setONE_SEC(int ONE_SEC) {
		this.ONE_SEC = ONE_SEC;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public String getClockHours() {
		return clockHours;
	}

	public void setClockHours(String clockHours) {
		this.clockHours = clockHours;
	}

	public String getClockMinutes() {
		return clockMinutes;
	}

	public void setClockMinutes(String clockMinutes) {
		this.clockMinutes = clockMinutes;
	}

	public String getClockSeconds() {
		return clockSeconds;
	}

	public void setClockSeconds(String clockSeconds) {
		this.clockSeconds = clockSeconds;
	}
}