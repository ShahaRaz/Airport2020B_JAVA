package boardActivition;
import java.util.ArrayList;
import java.util.List;

import baseModel.FilterFlights;
import baseModel.Flight;
import baseModel.MyDate;
import baseModel.Program;
import interfaces.Massageable;
import interfaces.SilentUi;
import interfaces.ConsoleUI;
import interfaces.htmlUI;

public class AirPortBoard {

	public static final String FILE_NAME = "Input.txt";

	private List<Flight> allFlights = new ArrayList<>();
	private List<Flight> flightsInBoard = new ArrayList<>();
	private List<Flight> flightsOutBoard = new ArrayList<>();
	public Massageable ui = new ConsoleUI();
	private Massageable devUi = new SilentUi();
	private FilterFlights filtered;
	private  MyDate LAST_DAY_IN_BOARD = new MyDate(1, 1, 2100); 
	private  MyDate FIRST_DAY_IN_BOARD = new MyDate(1,1,2020);

	// Args:
	// 0 - User Interface kind
	// 1 - departures or Arrivials
	// 2 - airline brand
	// 3 - country
	// 4 - City
	// 5 - Airport
	// 6 - starting date
	// 7 - ending date
	// 8 - week days [ 1= Sunday , 2=Monday... 7=Saturday]
	// EX : "html" "departures" "Arkia" "USA" "New-York" "JFK" "23/04/2020"
	// "01/05/2020" "435"

	
	
	public AirPortBoard(String[] args) {
		AirportActivition(args);
	}

	

	void AirportActivition(String[] args) {
		if (args.length == 0) {
			devUi.showErrMassage("Expected values: ui kind + dep/arr + airline brand + country + airport + Take off date "
					+ "landing date + weekDays[1sunday->7]");
			ui.showErrMassage("no args, Exiting");
			exit();
		}
		
		devUi.showErrMassage("entered JAVA program!");
		int i = 0;
		for (String s : args) {
			i++;
			devUi.showMassage((s));
		}
		devUi.showMassage(args.toString());
		devUi.showErrMassage("java recived :" + args.length + " && arguments" + " Number of strings: " + i);
		
		
		
		if (args[0].contains("html")) {
			ui = new htmlUI(); // overWrites the console ui
			devUi.showErrMassage("html it is");
		} else if (args[0].contains("console"))
			devUi.showErrMassage("console it is");
		else {
			devUi.showErrMassage("first arg must be ui type (html or console)");
			exit();
		}
		
		try {
			Program.loadFromFile(this.allFlights, this.flightsInBoard, this.flightsOutBoard,FILE_NAME); // load all flights from file

		} catch (Exception e) {
			ui.showErrMassage("Error! can't load flights from data base. Try Again!");
			e.printStackTrace();
			exit();
		}

		// 1 Departure / Arriviall__________________________________________________
		if (args[1].contains("dep")) {
		//	Program.simpleMiniShowFlights(flightsOutBoard);
			filtered = new FilterFlights(flightsOutBoard, null);
		}
		if (args[1].contains("arr")) {
		//	Program.simpleMiniShowFlights(flightsInBoard);
			filtered = new FilterFlights(flightsOutBoard, null);
		}

		
		// 2 - airline brand__________________________________________________
		devUi.showMassage("args[2]: " + args[2]);
		if (args[2].length() != 0) {
			filtered.filterByAirlineBrand(args[2]);
		}
		devUi.showMassage("filtered stage 2");
		devUi.showErrMassage(filtered.toStringServer(ui.dropLineChar()));
		
		// 3 - country__________________________________________________
		devUi.showMassage("args[3]: " + args[3]);
		if (args[3].length() != 0) {
//			String[] splitCountry = args[2].split(",");
//			for (String c:splitCountry) {
//				filtered.filterByAirlineCountry(c);	
//			}
			filtered.filterByAirlineCountry(args[3]);

		}
		devUi.showMassage("filtered stage 3");
		devUi.showErrMassage(filtered.toStringServer(ui.dropLineChar()));
		
		
		// 4 - City__________________________________________________
		devUi.showMassage("args[4]: " + args[4]);
		if (args[4].length() != 0) {
			filtered.filterByAirlineCity(args[4]);
		}
		devUi.showMassage("filtered stage 4");
		devUi.showErrMassage(filtered.toStringServer(ui.dropLineChar()));
		
		

		// 5 - Airport__________________________________________________
		devUi.showMassage("args[5]: " + args[5]);
		if (args[5].length() != 0) {
			filtered.filterByAirlineAirport(args[5]);
		}
		devUi.showMassage("filtered stage 5");
		devUi.showErrMassage(filtered.toStringServer(ui.dropLineChar()));
		
		
		// 6 - Starting Date__________________________________________________
		devUi.showMassage("args[6]: " + args[6]);
		if (args[6].length() != 0) {
			filtered.filterByDateRange(MyDate.ParseFromString(args[6]), LAST_DAY_IN_BOARD);
		}
		devUi.showMassage("filtered stage 6");
		devUi.showErrMassage(filtered.toStringServer(ui.dropLineChar()));
		
		
		// 7 - Ending Date__________________________________________________
		devUi.showMassage("args[7]: " + args[7]);
		if (args[7].length() != 0) {
			filtered.filterByDateRange(FIRST_DAY_IN_BOARD,MyDate.ParseFromString(args[7]));
			// TODO add ending time of flight
			// with term like so:
			// if(departure time+ flight time > 24)
			// landing date = departureDate+1;
			// remember to change days in weeks accordingly
			///ORRR
			// if(departure time < takeOff time) day++
		}
		
		devUi.showErrMassage("filtered stage 7");
		devUi.showErrMassage(filtered.toStringServer(ui.dropLineChar()));

		// 8 - week Days__________________________________________________
		devUi.showMassage("args[8]: " + args[8]);
		if (args[8].length() != 0) {
			filtered.toggleIntDaysInWeekFromStr(args[8]);
			filtered.filterByDateWeekDay();
		}

		// printOut
		//ui.showErrMassage("Results are:");
		ui.showMassage(filtered.toStringServer(ui.dropLineChar()));

	}


	private void exit() {
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
//	devUi.showMassage("Which Air-Line Brand? (1-" + (brands.size() + 1) + ")");
//	int i = 0;
//	devUi.showMassage(i + ") Any Flight Company...");
//	
//	for (String s : brands)
//		devUi.showMassage(++i + ") " + s);
//	devUi.showMassage(++i + ") Other brands");
//	try {
//		i = Integer.parseInt(scn.nextLine());
//	} catch (Exception e) {
//		devUi.showMassage("Invalid Input... Try again!");
//		i = Integer.parseInt(scn.nextLine());
//	}
//	
//	if (i == 5) {
//		devUi.showMassage("Whitch Flight Company?");
//		brand = scn.nextLine();
//		
//	} else if (i == 0)
//		brand = "";
//	else if (i < 5 && i > 0)
//		brand = brands.get(i - 1);
//	else
//		devUi.showMassage("No Company Chosen...");
//	devUi.showMassage("Which Country?");
//	country = scn.nextLine();
//	devUi.showMassage("Which City?");
//	city = scn.nextLine();
//	devUi.showMassage("Which Air-Port?");
//	airport = scn.nextLine();
//	devUi.showMassage("What is the Starting Date?");
//	try {
//		System.out.print("Insert a day: ");
//		int days = Integer.parseInt(scn.nextLine());
//		System.out.print("Insert Month: ");
//		int mos = Integer.parseInt(scn.nextLine());
//		System.out.print("Insert Year: ");
//		int year = Integer.parseInt(scn.nextLine());
//		startDate = new MyDate(days, mos, year);
//	} catch (Exception e) {
//		devUi.showMassage("Didnt Work Out");
//		startDate = new MyDate(1, 1, 1990);
//	}
//	devUi.showMassage("What is the Ending Date?");
//	try {
//		System.out.print("Insert a day: ");
//		int days = Integer.parseInt(scn.nextLine());
//		System.out.print("Insert Month: ");
//		int mos = Integer.parseInt(scn.nextLine());
//		System.out.print("Insert Year: ");
//		int year = Integer.parseInt(scn.nextLine());
//		endingDate = new MyDate(days, mos, year);
//	} catch (Exception e) {
//		devUi.showMassage("Didnt Work Out");
//		endingDate = new MyDate(30, 12, 2050);
//	}
//	// String => Sunday Friday Monday <= String//
//	try {
//		devUi.showMassage("Which Day in the Week you Prefer?");
//		weekDays += scn.nextLine() + " ";
//		
//		while (true) {
//			devUi.showMassage("Any other day?");
//			devUi.showMassage("1) yes\n2) no");
//			String res = scn.nextLine();
//			if (res.compareTo("1") == 0) {
//				devUi.showMassage("which day you prefer?");
//				weekDays += scn.nextLine() + " ";
//			} else if (res.compareTo("2") == 0) {
//				devUi.showMassage("OK, Lets go forward...");
//				break;
//			} else {
//				devUi.showMassage("Invalid input, Lets go forward...");
//				break;
//			}
//		}
//		devUi.showMassage("Error! Going 1 By-Defualt");
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
//		devUi.showMassage("No Match Found");
//	return l;
//
//	
//	
//	
//}
