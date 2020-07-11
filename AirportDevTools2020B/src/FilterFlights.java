import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FilterFlights {
	// Current filter results
	protected List<Flight> originalFlights;
	protected List<Flight> filteredArr;

	// Data structure for day filtering - 1= Sunday , 2= Monday ... 7=Saturday
	public boolean[] weekDays = { false, false, false, false, false, false, false };
	// AirLine (Brands) possibilities
	public List<String> brands = new ArrayList<>();
	// all Parameters
	private String fBrand = "";
	private String fCountry = "";
	private String fCity = "";
	private String fAirport = "";
	// max range
	private MyDate firstDateInRange = new MyDate(1, 1, 1900);
	private MyDate lastDateInRange = new MyDate(1, 1, 2100);

	public FilterFlights(List<Flight> flightsBeforeFiltering, List<String> BrandsInDB) {
		this.originalFlights = flightsBeforeFiltering;
		originalFlights = new ArrayList<Flight>();
		originalFlights.addAll(flightsBeforeFiltering);
		filteredArr = new ArrayList<Flight>();
		filteredArr.addAll(flightsBeforeFiltering);

		this.brands = BrandsInDB;
		filteredArr = SearchByTerms();
	}

	public List<Flight> SearchByTerms() {
		Scanner scn = new Scanner(System.in);
		// max range
		int i = 0; // helper
		boolean stopAddFilters = false;
		try {
			while (!stopAddFilters) {
				printConstrainsMenu();
				int userChoice = Integer.parseInt(scn.nextLine());
				switch (userChoice) {
				case 1:
					// Maybe create a map for airline names <-> numbers
					System.out.println("Air-Lines in Database: (1-" + (brands.size() + 1) + ")");
					i = 0;
					// System.out.println(i + ") Any Flight Company...");

					for (String s : brands)
						System.out.println(++i + ") " + s); // print all brands
					// System.out.println(++i + ") Other brands");
					// try {
					// i = Integer.parseInt(scn.nextLine());
					// } catch (Exception e) {
					// System.out.println("Invalid Input... Try again!");
					// i = Integer.parseInt(scn.nextLine());
					// }
					// if (i == 5) {
					System.out.println("Type Flight Company?");
					fBrand = scn.nextLine();
					filterByAirlineBrand(fBrand);
					//
					// } else if (i == 0)
					// brand = "";
					// else if (i < 5 && i > 0)
					// brand = brands.get(i - 1);
					// else
					// System.out.println("No Company Chosen...");
					break;

				case 2:
					System.out.println("Which Country?");
					fCountry = scn.nextLine();
					filterByAirlineCountry(fCountry);
					break;
				case 3:
					System.out.println("Which City?");
					fCity = scn.nextLine();
					filterByAirlineCity(fCity);
					break;
				case 4:
					System.out.println("Which Air-Port?");
					fAirport = scn.nextLine();
					filterByAirlineAirport(fAirport);
					break;
				case 5: // code duplication (5+6) - TODO see how to fix it
					System.out.println("Searching by date range:");
					System.out.println("What is the first date in range?");
					firstDateInRange = getDateFromUser(scn);
					System.out.println("What is the last date in range?");
					lastDateInRange = getDateFromUser(scn);
					filterByDateRange(firstDateInRange, lastDateInRange);
					break;
				case 6:
					// try {
					System.out.println("Which Day in the Week you Prefer?");
					// filter by me
					ChooseDaysInWeek(scn);
					filterByDateWeekDay(weekDays);
					// weekDays += scn.nextLine() + " ";
					//
					// while (true) {
					// System.out.println("Any other day?");
					// System.out.println("1) yes\n2) no");
					// String res = scn.nextLine();
					// if (res.compareTo("1") == 0) {
					// System.out.println("which day you prefer?");
					// weekDays += scn.nextLine() + " ";
					// } else if (res.compareTo("2") == 0) {
					// System.out.println("OK, Lets continue...");
					// break;
					// } else {
					// System.out.println("Invalid input, Lets continue...");
					// break;
					// }
					// }
					// } catch (Exception e) {
					// System.out.println("Error! Going 1 By-Defualt");
					// }
					break;
				default:
					System.out.println("Wrong input, please choose again");
				}

				Collections.sort(this.filteredArr, Program.compareByDepDate); // TODO move this to program.
				Program.miniShowFlights(this.filteredArr);
				System.out.println("would you like to add another constraint? Yes / No");
				String anotherConstraint = "NOPE";
				anotherConstraint = scn.nextLine();

				if ((anotherConstraint.charAt(0) != 'y' && anotherConstraint.charAt(0) != 'Y')) {
					stopAddFilters = true;
				}
			}
		} catch (Exception e) {
			System.out.println("error, please try again");
		}
		scn.close();
		return this.filteredArr;

	}

	private static void printConstrainsMenu() {
		System.out.println(" Which Filters Would you Like to apply?");
		System.out.println(" 1) Airline Brand ");
		System.out.println(" 2) Country");
		System.out.println(" 3) City");
		System.out.println(" 4) Airport");
		System.out.println(" 5) Date Range");
		System.out.println(" 6) Week Days");
	}

	public void filterByDateWeekDay(boolean[] weekSundaysIndexIs0) {
		Iterator<Flight> itr = filteredArr.iterator();
		Flight f;
		int temp;
		while (itr.hasNext()) {
			f = (Flight) itr.next();
			temp = f.getDate().getDayInWeekSundayInIndex0();
			if (this.weekDays[temp] == false)
				itr.remove();
		}
		//
	}

	public void filterByDateRange(MyDate FromDate, MyDate toDate) {
		Iterator<Flight> itr = filteredArr.iterator();
		Flight f;
		while (itr.hasNext()) {
			f = (Flight) itr.next();

			if (!(f.getDate().before(toDate) && f.getDate().after(FromDate)))
				itr.remove();
		}
		//
	}

	public void filterByAirlineBrand(String Brand) {
		Iterator<Flight> itr = filteredArr.iterator();
		Flight f; // Temporary flight
		while (itr.hasNext()) {
			f = (Flight) itr.next();
			if (!(f.getBrand().equalsIgnoreCase(Brand)))
				itr.remove();
		}
	}

	public void filterByAirlineCountry(String Country) {
		Iterator<Flight> itr = filteredArr.iterator();
		while (itr.hasNext()) {
			if (!(itr.next().getAirport().toLowerCase().contains("," + Country.toLowerCase())));
				itr.remove();
		}
	}//Ethipea

	public void filterByAirlineCity(String City) {
		Iterator<Flight> itr = filteredArr.iterator();
		Flight f; // Temporary flight
		while (itr.hasNext()) {
			f = (Flight) itr.next();
			if (!(f.getCity().equalsIgnoreCase(City)))
				itr.remove();
		}
	}

	public void filterByAirlineAirport(String Airport) {
		Iterator<Flight> itr = filteredArr.iterator();
		Flight f; // Temporary flight
		while (itr.hasNext()) {
			f = (Flight) itr.next();
			if (!(f.getAirport().equalsIgnoreCase(Airport)))
				itr.remove();
		}
	}

	public void ChooseDaysInWeek(Scanner scn) {
		short userChoise = -1;
		boolean finishedChoose = false;
		while (finishedChoose == false) {
			System.err.print("already choose these days:");
			for (int i = 0; i < weekDays.length; i++) {
				if (weekDays[i] == true)
					System.err.print(i);
			}

			System.out.println("please enter day in week:\n1=Sunday\n2=Monday...");
			try {
				userChoise = Short.parseShort(scn.nextLine());
			} catch (Exception e) {
				System.err.println("insert a number between 1 & 7 ");
			}
			if (userChoise < 1 || userChoise > 7)
				continue; // run while loop again
			this.weekDays[userChoise - 1] = true; // marks choosen
			System.out.println("would you like to add another day? \n0-No\n1-Yes");
			userChoise = Short.parseShort(scn.nextLine());
			if (userChoise == 0)
				finishedChoose = true;
		}

	}

	public static List<Flight> FilterByTerms(List<Flight> arr, String brand, String country, String city,
			String airport, MyDate startDate, MyDate endingDate, String weekDays) {
		List<Flight> l = new ArrayList<>();

		int counter = 0;
		for (Flight f : arr) {
			if ((weekDays.toUpperCase()).contains(f.getDayInWeek()) && (f.getBrand().contains(brand))
					&& f.getDate().before(endingDate) && f.getDate().after(startDate)
					&& (f.getDepAirPort().contains(country) || f.getArriveAirPort().contains(country))
					&& (f.getDepAirPort().contains(city) || f.getArriveAirPort().contains(city))
					&& (f.getDepAirPort().contains(airport) || f.getArriveAirPort().contains(airport))) {
				;
				counter++;
				l.add(f);
			}
		}
		if (counter == 0)
			System.out.println("No Match Found");
		return l;
	}

	protected List<Flight> getList() {
		return this.filteredArr;
	}

	public static MyDate getDateFromUser(Scanner scn) {
		System.out.println("enter date 'day' 'month' 'year' (example :23 06 1994)");
		int day = scn.nextInt();
		int month = scn.nextInt();
		int year = scn.nextInt();
		scn.nextLine(); // clear buffer
		return new MyDate(day, month, year);
	}
}
