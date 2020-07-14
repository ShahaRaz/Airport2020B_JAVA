import java.util.ArrayList;
import java.util.List;

import interfaces.Massageable;
import interfaces.consoleUI;
import interfaces.htmlUI;

public class AirPortBoard {

	public static final String FILE_NAME = "Input.txt";

	public static List<Flight> allFlights = new ArrayList<>();
	private static List<Flight> flightsInBoard = new ArrayList<>();
	private static List<Flight> flightsOutBoard = new ArrayList<>();
	public static Massageable ui;
	private static FilterFlights filtered;
	
	// Args: 
	// 0 - User Interface kind
	// 1 - departures or Arrivials
	// 2 - airline brand
	// 3 - country
	// 4 - Airport
	// 5 - starting date
	// 6 - ending date
	// 7 - week days [ 1= Sunday , 2=Monday... 7=Saturday] 

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("no args, Exiting");
			exit();
		}
		if (args[0] == "html")
			ui = new htmlUI();
		else if (args[0] == "console") 
			ui = new consoleUI();
		else {
			ui.showMassage("first arg must be ui type (html or console)");
			exit();
		}
	
		try {
			Program.loadFromFile(allFlights, flightsInBoard, flightsOutBoard); // load all flights from file

		} catch (Exception e) {
			ui.showErrMassage("Error! can't load flights from data base. Try Again!");
			e.printStackTrace();
			exit();
		}
		// 1 Depar / Arrivial
		if (args[1].equalsIgnoreCase("departure")) {
			Program.miniShowFlights(flightsOutBoard);
			 filtered = new FilterFlights(flightsOutBoard,null);
		}
		if (args[1].equalsIgnoreCase("arrivals")) {
			Program.miniShowFlights(flightsInBoard);
			 filtered = new FilterFlights(flightsOutBoard,null);

		}
		// 2 - airline brand
		if (args[2].length()!=0) {
//			String[] splitBrands = args[2].split(",");
//			for (String b:splitBrands) {
//				filtered.filterByAirlineBrand(b);	
//			}
			filtered.filterByAirlineBrand(args[2]);	
		
		}
		
		// 3 - country
		if (args[3].length()!=0)
		{
//			String[] splitCountry = args[2].split(",");
//			for (String c:splitCountry) {
//				filtered.filterByAirlineCountry(c);	
//			}
			filtered.filterByAirlineCountry(args[3]);	
			
		}
		// 4 - Airport
		if (args[4].length()!=0)
		{
//			String[] splitAirport = args[2].split(",");
//			for (String a:splitAirport) {
//				filtered.filterByAirlineAirport(a);	
//			}
			filtered.filterByAirlineAirport(args[4]);
			
		}
		// 5 - Starting Date
		if (args[5].length()!=0)
		{
			filtered.filterByAirlineAirport(args[5]);	
			
		}
		// 6 - Ending Date
		if (args[6].length()!=0)
		{
			filtered.filterByAirlineAirport(args[6]);	
			// TODO add ending time of flight 
			// with term like so: 
			// if(departure time+ flight time > 24) 
			// 		landing date = departureDate+1;
			// remember to change days in weeks accordingly
			
		}
		
		// 7 - week Days
		if (args[7].length()!=0)
		{
			filtered.toggleIntDaysInWeekFromStr(args[7]);
			filtered.filterByDateWeekDay();
		}


	}

	private static void exit() {
		ui.showMassage("Goodbye");
	}
}

/// Program in python // (stage 3) 
//def dep():
//    return subprocess.check_output(["java", "-classpath",
//                                    "/home/afeka/workspace/g1/bin", "NatbagMain",
//                                    request.args.get('outformat'), "departures",
//                                    request.args.get('airline'), request.args.get('country'),
//                                    request.args.get('city'), request.args.get('airport'),
//                                    request.args.get('day1'), request.args.get('month1'),
//                                    request.args.get('year1'), request.args.get('day2'),
//                                    request.args.get('month2'), request.args.get('year2'),
//                                    request.args.get('sunday'), request.args.get('monday'),
//                                    request.args.get('tuesday'), request.args.get('wednesday'),
//                                    request.args.get('thursday'), request.args.get('friday'),
//                                    request.args.get('saturday')])
//@app.route("/arrivals")
//def arr():
//    return subprocess.check_output(["java", "-classpath",
//                                    "Users/SRazStudent/git/Airport2020B_JAVA/AirportDevTools2020B/src/bin", "Program",
//                                    request.args.get('outformat'), "arrivals",
//                                    request.args.get('airline'), request.args.get('country'),
//                                    request.args.get('city'), request.args.get('airport'),
//                                    request.args.get('day1'), request.args.get('month1'),
//                                    request.args.get('year1'), request.args.get('day2'),
//                                    request.args.get('month2'), request.args.get('year2'),
//                                    request.args.get('sunday'), request.args.get('monday'),
//                                    request.args.get('tuesday'), request.args.get('wednesday'),
//                                    request.args.get('thursday'), request.args.get('friday'),
//                                    request.args.get('saturday')])

//		
//	} catch (Exception e) {
//	}
//	List<Flight> l = new ArrayList<>();
//	String brand = "";
//	String country = "";
//	String city = "";
//	String airport = "";
//	MyDate startDate = null;
//	MyDate endingDate = null;
//	String weekDays = "";
//	
//	System.out.println("Which Air-Line Brand? (1-" + (brands.size() + 1) + ")");
//	int i = 0;
//	System.out.println(i + ") Any Flight Company...");
//	
//	for (String s : brands)
//		System.out.println(++i + ") " + s);
//	System.out.println(++i + ") Other brands");
//	try {
//		i = Integer.parseInt(scn.nextLine());
//	} catch (Exception e) {
//		System.out.println("Invalid Input... Try again!");
//		i = Integer.parseInt(scn.nextLine());
//	}
//	
//	if (i == 5) {
//		System.out.println("Whitch Flight Company?");
//		brand = scn.nextLine();
//		
//	} else if (i == 0)
//		brand = "";
//	else if (i < 5 && i > 0)
//		brand = brands.get(i - 1);
//	else
//		System.out.println("No Company Chosen...");
//	System.out.println("Which Country?");
//	country = scn.nextLine();
//	System.out.println("Which City?");
//	city = scn.nextLine();
//	System.out.println("Which Air-Port?");
//	airport = scn.nextLine();
//	System.out.println("What is the Starting Date?");
//	try {
//		System.out.print("Insert a day: ");
//		int days = Integer.parseInt(scn.nextLine());
//		System.out.print("Insert Month: ");
//		int mos = Integer.parseInt(scn.nextLine());
//		System.out.print("Insert Year: ");
//		int year = Integer.parseInt(scn.nextLine());
//		startDate = new MyDate(days, mos, year);
//	} catch (Exception e) {
//		System.out.println("Didnt Work Out");
//		startDate = new MyDate(1, 1, 1990);
//	}
//	System.out.println("What is the Ending Date?");
//	try {
//		System.out.print("Insert a day: ");
//		int days = Integer.parseInt(scn.nextLine());
//		System.out.print("Insert Month: ");
//		int mos = Integer.parseInt(scn.nextLine());
//		System.out.print("Insert Year: ");
//		int year = Integer.parseInt(scn.nextLine());
//		endingDate = new MyDate(days, mos, year);
//	} catch (Exception e) {
//		System.out.println("Didnt Work Out");
//		endingDate = new MyDate(30, 12, 2050);
//	}
//	// String => Sunday Friday Monday <= String//
//	try {
//		System.out.println("Which Day in the Week you Prefer?");
//		weekDays += scn.nextLine() + " ";
//		
//		while (true) {
//			System.out.println("Any other day?");
//			System.out.println("1) yes\n2) no");
//			String res = scn.nextLine();
//			if (res.compareTo("1") == 0) {
//				System.out.println("which day you prefer?");
//				weekDays += scn.nextLine() + " ";
//			} else if (res.compareTo("2") == 0) {
//				System.out.println("OK, Lets go forward...");
//				break;
//			} else {
//				System.out.println("Invalid input, Lets go forward...");
//				break;
//			}
//		}
//		System.out.println("Error! Going 1 By-Defualt");
//	}
//	int counter = 0;
//	for (Flight f : arr) {
//		if ((weekDays.toUpperCase()).contains(f.getDayInWeek()) && (f.getBrand().contains(brand))
//				&& f.getDate().before(endingDate) && f.getDate().after(startDate)
//				&& (f.getDepAirPort().contains(country) || f.getArriveAirPort().contains(country))
//				&& (f.getDepAirPort().contains(city) || f.getArriveAirPort().contains(city))
//				&& (f.getDepAirPort().contains(airport) || f.getArriveAirPort().contains(airport))) {
//			;
//			counter++;
//			l.add(f);
//		}
//	}
//	if (counter == 0)
//		System.out.println("No Match Found");
//	return l;
//
//	
//	
//	
//}
