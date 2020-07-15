import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import interfaces.Massageable;
import interfaces.consoleUI;

public class FilterFlights {
	// Current filter results
	private List<Flight> originalFlights;
	private List<Flight> filteredArr;
	public static Massageable ui = new consoleUI();
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
		
	}

	public List<Flight> filterByTerms() {
		Scanner scn = new Scanner(System.in);
		// max range
		int i = 0; // helper
		boolean stopAddFilters = false;
		try {
			while (!stopAddFilters) {
				printConstrainsMenu();
				String userChoice = scn.nextLine();
				switch (userChoice) {
				case "1":
					if(brands!= null) {
					// Maybe create a map for airline names <-> numbers
					ui.showMassage("Air-Lines in Database: (1-" + (brands.size() + 1) + ")");
					i = 0;
					for (String s : brands)
						ui.showMassage(++i + ") " + s); // print all brands
					}
					ui.showMassage("Type Flight Company:");
					fBrand = scn.nextLine();
					filterByAirlineBrand(fBrand);
					break;

				case "2":
					ui.showMassage("Which Country?");
					fCountry = scn.nextLine();
					filterByAirlineCountry(fCountry);
					break;
				case "3":
					ui.showMassage("Which City?");
					fCity = scn.nextLine();
					filterByAirlineCity(fCity);
					break;
				case "4":
					ui.showMassage("Which Air-Port?");
					fAirport = scn.nextLine();
					filterByAirlineAirport(fAirport);
					break;
				case "5": 
					ui.showMassage("Searching by date range:");
					ui.showMassage("What is the first date in range?");
					firstDateInRange = MyDate.getDateFromUser(scn);
					ui.showMassage("What is the last date in range?");
					lastDateInRange = MyDate.getDateFromUser(scn);
					filterByDateRange(firstDateInRange, lastDateInRange);
					break;
				case "6":
					// try {
					ui.showMassage("Which Day in the Week you Prefer?");
					// filter by me
					ChooseDaysInWeek(scn);
					filterByDateWeekDay();

					break;
				default:
					ui.showMassage("Wrong input, please choose again");
				}

				Collections.sort(this.filteredArr, Program.compareByDepDate); // TODO move this to program.
				Program.miniShowFlights(this.filteredArr);
				String anotherConstraint = "NOPE";
				anotherConstraint = ui.getString("would you like to add another day? Yes / No");
				if(anotherConstraint.length()>0)
					if ((anotherConstraint.charAt(0) != 'y' && anotherConstraint.charAt(0) != 'Y')) {
						stopAddFilters = true;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			ui.showMassage("error, please try again");
		}
		scn.close();
		return this.filteredArr;

	}

	private static void printConstrainsMenu() {
		ui.showMassage(" Which Filters Would you Like to apply?");
		ui.showMassage(" 1) Airline Brand ");
		ui.showMassage(" 2) Country");
		ui.showMassage(" 3) City");
		ui.showMassage(" 4) Airport");
		ui.showMassage(" 5) Date Range");
		ui.showMassage(" 6) Week Days");
	}

	public void filterByDateWeekDay() {
		//using an external array INORDER to enable accessing 
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
	
	public void toggleIntDaysInWeekFromStr(String daysAsInts) {
		int temp; // temporary
		for(int i=0;i<daysAsInts.length();i++) {
			temp=daysAsInts.charAt(i)-'0'; // temporary = the day specified in index i in the string
			weekDays[temp]=true;
		}
		
	}
	public void parseDatesFromStringNFilter(String dates) {
		// expected format: "DD/MM/YYYY & DD/MM/YYYY"
		String[] splitDates = dates.split("&");
		for (String d:splitDates)
			d.trim(); // removes white spaces
		MyDate firstDay = MyDate.ParseFromString(splitDates[0]);
		MyDate lastDay =  MyDate.ParseFromString(splitDates[1]);
		filterByDateRange(firstDay,lastDay);
		//splitDates[0] = //... look maybe MyDate class has a way
		
		
		
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

	public void filterByAirlineBrand(String brands)  {	
		Iterator<Flight> itr = filteredArr.iterator();
		Flight f; // Temporary flight
		while (itr.hasNext()) {
			f = (Flight) itr.next();
			if (!(brands.contains(f.getBrand())))
				itr.remove();
		}
	}
	

	public void filterByAirlineCountry(String Countries) {
		Iterator<Flight> itr = filteredArr.iterator();
		Flight f; // Temporary flight
		while (itr.hasNext()) {
			f = (Flight) itr.next();
			if (!(Countries.contains(f.getCountry())))
				itr.remove();
		}
	}

	public void filterByAirlineCity(String cities) {
		Iterator<Flight> itr = filteredArr.iterator();
		Flight f; // Temporary flight
		while (itr.hasNext()) {
			f = (Flight) itr.next();
			if (!(cities.contains(f.getCity())))
				itr.remove();
		}
	}

	public void filterByAirlineAirport(String Airports) {
		Iterator<Flight> itr = filteredArr.iterator();
		Flight f; // Temporary flight
		while (itr.hasNext()) {
			f = (Flight) itr.next();
			if (!(Airports.contains(f.getAirport())))
				itr.remove();
		}
	}

	public void ChooseDaysInWeek(Scanner scn) {
		int userChoise = -1;
		boolean finishedChoose = false;
		while (finishedChoose == false) {
			System.err.print("already choose these days:");
			for (int i = 0; i < weekDays.length; i++) {
				if (weekDays[i] == true)
					ui.showErrMassage((i+1) + " ");
			}

			ui.showMassage("\nplease enter day in week:\n1=Sunday\n2=Monday...");
			try {
				userChoise = Short.parseShort(scn.nextLine());
			} catch (Exception e) {
				System.err.println("insert a number between 1 & 7 ");
			}
			if (userChoise < 1 || userChoise > 7)
				continue; // user inserted wrong value, run loop again.
			this.weekDays[userChoise - 1] = true; // marks choosen
			
			String anotherConstraint = "NOPE";
			anotherConstraint = ui.getString("would you like to add another day? Yes / No");
			if(anotherConstraint.length()>0)
				if ((anotherConstraint.charAt(0) != 'y' && anotherConstraint.charAt(0) != 'Y')) {
					finishedChoose = true;
			}
			
			
		}

	}



	protected List<Flight> getList() {
		return this.filteredArr;
	}

	public String toStringServer() {
		for(Flight f:filteredArr) {
			f.toStringServer();
		}
		return null;
	}
}





//Get Day with string ____________________ Start
// weekDays += scn.nextLine() + " ";
//
// while (true) {
// ui.showMassage("Any other day?");
// ui.showMassage("1) yes\n2) no");
// String res = scn.nextLine();
// if (res.compareTo("1") == 0) {
// ui.showMassage("which day you prefer?");
// weekDays += scn.nextLine() + " ";
// } else if (res.compareTo("2") == 0) {
// ui.showMassage("OK, Lets continue...");
// break;
// } else {
// ui.showMassage("Invalid input, Lets continue...");
// break;
// }
// }
// } catch (Exception e) {
// ui.showMassage("Error! Going 1 By-Defualt");
// }
//Get Day with string ____________________ Finish



//FilterFlightsAtOnce______________________Start
//public static List<Flight> FilterByTerms(List<Flight> arr, String brand, String country, String city,
//String airport, MyDate startDate, MyDate endingDate, String weekDays) {
//List<Flight> l = new ArrayList<>();
//
//int counter = 0;
//for (Flight f : arr) {
//if ((weekDays.toUpperCase()).contains(f.getDayInWeek()) && (f.getBrand().contains(brand))
//		&& f.getDate().before(endingDate) && f.getDate().after(startDate)
//		&& (f.getDepAirPort().contains(country) || f.getArriveAirPort().contains(country))
//		&& (f.getDepAirPort().contains(city) || f.getArriveAirPort().contains(city))
//		&& (f.getDepAirPort().contains(airport) || f.getArriveAirPort().contains(airport))) {
//	;
//	counter++;
//	l.add(f);
//}
//}
//if (counter == 0)
//ui.showMassage("No Match Found");
//return l;
//}
//FilterFlightsAtOnce______________________Finish