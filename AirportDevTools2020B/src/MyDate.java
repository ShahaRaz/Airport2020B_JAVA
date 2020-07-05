
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class MyDate {
	public static enum monthName {
		Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec
	};

	final static int CURRENT_YEAR = 2020;
	// MyDate date=new MyDate(19, 6, 2015);
	private int day;
	private int month;
	private int year;
	private final static int[] DAYS_MONTHS = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	public static String specDay(String date) throws ParseException {
		return "";
	}

	public int getFirstDayInMyWeek() {
		LocalDate me = LocalDate.of(this.year, this.month, this.day-7);
		me = me.with(DayOfWeek.SUNDAY); // TODO check if works
		return me.getDayOfMonth();
//		DayOfWeek temp = (me.getDayOfWeek());
//		int formatMe = temp.getValue();// weeks here from monday=1 to sunday=7
//		if (formatMe == 7)
//			return 1;
//		else
//			return formatMe + 1;
	}

	public MyDate(int day, int month, int year) {
		setMonthNDay(day, month);
		setYear(year);
	}

	// date Exception //
	public static int makeDate(Scanner s) {
		int dateNum = 0;
		while (dateNum == 0) {
			try {
				dateNum = Integer.parseInt(s.nextLine());
			} catch (Exception e) {
				System.err.println("Error! Wrong input. Try again!");
			}
		}
		return dateNum;
	}

	public MyDate(Scanner scn) {
		System.out.println("Insert Day and Month (numbers only) : ");
		setMonthNDay(makeDate(scn), makeDate(scn));
		System.out.println("Insert year (4 Digits) :");
		setYear(makeDate(scn));
	}

	private void setYear(int year) {
		if (year < CURRENT_YEAR) {
			System.out.println("We take flights from 2020 and on ...\n going by default: 2020");
			this.year = CURRENT_YEAR;
		} else
			this.year = year;
	}

	private void setMonthNDay(int day, int month) {
		this.month = month;
		if (month > 12 || month < 1) { // case is invalid is go to January
			System.out.println(day + "/" + month);
			System.out.println("Wrong month input - goes January by Default.");
			this.month = 1;
			this.day = 31;
		} else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			this.day = 31; // max days in month
		} else if (month == 2) {
			this.day = 28;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			this.day = 30;
		}

		setDay(day); // here we really set object's day.
	}

	private void setDay(int day) {
		if (day > 0 && day < this.day)
			this.day = day;
		else
			this.day = 1;
	}

	public int daysCount(MyDate d) {
		LocalDate enter = LocalDate.of(year, month, day);
		LocalDate out = LocalDate.of(d.year, d.month, d.day);
		Period period = Period.between(enter, out);
		int diff = Math.abs(period.getDays() + period.getMonths() * DAYS_MONTHS[month - 1] + period.getYears());
		return diff;
	}

	public int yearsCount(MyDate endDate) {
		return (int) (daysCount(endDate)) / 365;
		// not considering leap year and maybe other stuff
	}

	@Override
	public String toString() {
		String nDay = day + "";
		String nMonth = month + "";

		if (day < 10)
			nDay = "0" + nDay;
		if (month < 10)
			nMonth = "0" + nMonth;

		return nDay + "/" + nMonth + "/" + year;
	}

	public int getYear() {
		return this.year;
	}

	public int getDay() {
		return this.day;
	}

	public int getMonth() {
		return this.month;
	}

	public String getMonthYearName() {
		monthName monthStr = monthName.values()[(this.month - 1)];
		return monthStr.toString() + this.year;
	}

	public boolean before(MyDate date) {
		if (date == null)
			return true;
		if (this.year < date.getYear())
			return true;
		else if (this.year == date.getYear() && this.month < date.getMonth())
			return true;
		else if (this.year == date.getYear() && this.month == date.getMonth() && this.day < date.getDay())
			return true;
		return false;
	}

	public boolean after(MyDate date) {
		if (date == null)
			return true;
		if (this.year > date.getYear())
			return true;
		else if (this.year == date.getYear() && this.month > date.getMonth())
			return true;
		else if (this.year == date.getYear() && this.month == date.getMonth() && this.day > date.getDay())
			return true;
		return false;
	}

}
