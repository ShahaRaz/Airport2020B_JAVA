
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

	public MyDate(int day, int month, int year) {
		setMonthNDay(month, day);
		setYear(year);

	}


	public MyDate(Scanner scn) {
		int day, month, year;
		System.out.println("insert day (numbers only) : ");
		day = Integer.parseInt(scn.nextLine());
		System.out.println("insert month (numbers only) :");
		month = Integer.parseInt(scn.nextLine());
		System.out.println("insert year (4 Digits) :");
		year = Integer.parseInt(scn.nextLine());
		setMonthNDay(month, day);
		setYear(year);
	} // manually add day
//
//	public void save(PrintWriter pw) {
//		pw.print(getDay() + ", ");
//		pw.print(getMonth() + ", ");
//		pw.println(getYear()); // end and jump pointer to next line.
//	}

	private void setYear(int year) {
		if (year >= 1900 && year < CURRENT_YEAR)
			this.year = year;
		else {
			if (year < 1900) {
				this.year = 2000;
			} else if (year >= 2020) {
				this.year = 2020;
				this.month = 1;
			}
		}
	} // Required logic applied

	private void setMonthNDay(int month, int day) {
		// TODO check if there is a better way to specify the correct day & month
		this.month = month;
		if (month > 12 || month < 1) { // case is invalid is go to January
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
		return  day + "/" + month + "/" + year;
	}

	public int getYear() {
		return this.year;
	}

	public int getMonth() {
		return this.month;
	}

	public String getMonthYearName() {
		monthName monthStr = monthName.values()[(this.month - 1)];
		return monthStr.toString() + this.year;
	}

}
