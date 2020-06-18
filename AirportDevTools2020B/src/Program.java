import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Program {
	public static boolean isOK = false;

// Global parameters // 
	public static String flightId;
	public static String depAirPort;
	public static String arriveAirPort;
	public static String depTime;
	public static String arrTime;
	public static String brand;
	public static int terminalNum;
	public static MyDate date = null;
	public static int flag;
	// MY LISTS //
	public static List<String> brands = new ArrayList<>();
	public static List<Flight> allFlights = new ArrayList<>();
	private static List<Flight> flightsIn = new ArrayList<>();
	private static List<Flight> flightsOut = new ArrayList<>();
	public static List<String> airPorts = new ArrayList<String>();
	// I/O VARIABLES //
	public static Scanner scn = new Scanner(System.in);
	public static final String FILE_NAME = "Input.txt";

	public static void main(String[] args) throws FileNotFoundException {
		activition();
		scn.close();

	}

	public static void activition() throws FileNotFoundException {
		createBrandsSet();
		addAirPorts();
		System.out.println("Welcome\nPlease Choose by entering number:");
		isOK = false;
		while (!isOK) {
			for (int i = 0; i < 15; i++) {
				System.out.print("- ");
			}
			System.out.println("\n0 - Auto-Add new Flight");
			System.out.println("1 - Add new Flight");
			System.out.println("2 - Remove Flight");
			System.out.println("3 - Sort flights by...");
			System.out.println("4 - Save from File");
			System.out.println("5 - Load to File");
			System.out.println("6 - Print All Flights");
			System.out.println("7 - Remove All Flights");
			System.out.println("8 - Search Flights by Terms ");
			System.out.println("9 - EXIT");

			for (int i = 0; i < 15; i++) {
				System.out.print("- ");
			}

			System.out.print("\nYour Choice: ");
			int userChoice = Integer.parseInt(scn.nextLine());
			switch (userChoice) {
			case 0:
				AutoAdd();
				System.out.println("Flight has been added Successfully");
				isOK = false;
				break;

			case 1:
				addFlight();
				System.out.println("Flight has been added Successfully");
				isOK = false;
				break;
			case 2:
//				removeFlight();
				System.out.println("Flight has been added Successfully");
				isOK = false;
				break;
			case 3:
				sortFlights();
				isOK = false;
				break;
			case 4:
				try {
					saveToFile();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				System.out.println("Flight has been Saved Successfully");
				isOK = false;
				break;
			case 5:
				try {
					loadFromFile();
				} catch (Exception e) {
					System.err.println("Error! Something Went Wrong. Try Again!");
					e.printStackTrace();
					break;
				}
				System.out.println("Flight has been Loaded Successfully");
				isOK = false;
				break;
			case 6:
				showFlights();
				isOK = false;
				break;
			case 8:
				SearchByTerms(allFlights);

			case 9:
				isOK = true;
				break;

			default:
				System.out.println("Wrong input try again");
				isOK = false;
			}
		}

	}

	private static void saveToFile() throws Exception {
		try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "rw")) {
			raf.setLength(0);

			// All Flights //
			for (Flight f : allFlights) {
				// the Parameters //
				raf.writeUTF(f.getBrand());
				raf.writeUTF(f.getDepAirPort());
				raf.writeUTF(f.getArriveAirPort());
				raf.writeUTF(f.getDate().toString());
				raf.writeUTF(f.getDepTime());
				raf.writeUTF(f.getArrTime());
				raf.writeUTF(f.getFlightId());
				raf.writeUTF(f.getTerminalNum() + "");
				raf.writeUTF(f.getFlag() + "");
			}
		}

	}

	// In - > Out //
	private static void loadFromFile() throws FileNotFoundException {
		MyDate date = null;
		allFlights.clear();
		flightsIn.clear();
		flightsOut.clear();

		try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "r")) {
			while (raf.getFilePointer() < raf.length()) {
				brand = raf.readUTF();
				depAirPort = raf.readUTF();
				arriveAirPort = raf.readUTF();
				String theDate = raf.readUTF(); // reading the date as a String //
				String[] split = theDate.split("/");
				date = new MyDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
				depTime = raf.readUTF();
				arrTime = raf.readUTF();
				flightId = raf.readUTF();
				terminalNum = Integer.parseInt(raf.readUTF());
				flag = Integer.parseInt(raf.readUTF());
				if (flag == 1)
					allFlights.add(new FlightIn(brand, depAirPort, date, depTime, arrTime, flightId, terminalNum, 1));
				else
					allFlights
							.add(new FlightOut(brand, arriveAirPort, date, depTime, arrTime, flightId, terminalNum, 2));

				spreadFlights();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static MyDate getDate(Scanner scn) {
		System.out.println("enter date 'day' 'month' 'year' (example :23 06 1994)");

		return new MyDate(scn);
	}

	private static void addFlight() {
		System.out.println("1) Flight in\n2) Flight Out");
		while (!isOK) {
			String inout = scn.nextLine();
			if (inout.compareTo("1") == 0 || inout.compareTo("2") == 0) {
				pushFlight(inout);
				isOK = true;
			} else
				System.out.println("Wrong input");
		}
	}

	private static void sortFlights() {
		allFlights.clear();
		System.out.println("1) Sort By Departure Time..\n2) Sort By Arriving Time..\nPress any key for main menu");
		char c = scn.nextLine().charAt(0);

		switch (c) {
		case '1':
//			 list 1 //
			Collections.sort(flightsIn, new Comparator<Flight>() {
				@Override
				public int compare(Flight o1, Flight o2) {
					String date1 = o1.getDate().toString();
					String date2 = o2.getDate().toString();

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					int res = 0;
					try {
						res = sdf.parse(date1).compareTo(sdf.parse(date2));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (res != 0)
						return res;
					return o1.getDepTime().compareTo(o2.getDepTime());

				}
			});
//			 list 2 //
			Collections.sort(flightsOut, new Comparator<Flight>() {
				@Override
				public int compare(Flight o1, Flight o2) {
					String date1 = o1.getDate().toString();
					String date2 = o2.getDate().toString();

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					int res = 0;
					try {
						res = sdf.parse(date1).compareTo(sdf.parse(date2));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (res != 0)
						return res;
					return o1.getDepTime().compareTo(o2.getDepTime());

				}
			});

			for (Flight f : flightsIn) {
				allFlights.add(f);
			}
			for (Flight f : flightsOut) {
				allFlights.add(f);
			}
			System.out.println("Flight has been Sorted by Departure Time Successfully");
			break;

		case '2':
//			 list 1 //
			Collections.sort(flightsIn, new Comparator<Flight>() {
				@Override
				public int compare(Flight o1, Flight o2) {
					String date1 = o1.getDate().toString();
					String date2 = o2.getDate().toString();

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					int res = 0;
					try {
						res = sdf.parse(date1).compareTo(sdf.parse(date2));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (res != 0)
						return res;
					return o1.getArrTime().compareTo(o2.getArrTime());

				}
			});
//			 list 2 //
			Collections.sort(flightsOut, new Comparator<Flight>() {
				@Override
				public int compare(Flight o1, Flight o2) {
					String date1 = o1.getDate().toString();
					String date2 = o2.getDate().toString();

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					int res = 0;
					try {
						res = sdf.parse(date1).compareTo(sdf.parse(date2));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (res != 0)
						return res;
					return o1.getArrTime().compareTo(o2.getArrTime());

				}
			});

			for (Flight f : flightsIn) {
				allFlights.add(f);
			}
			for (Flight f : flightsOut) {
				allFlights.add(f);
			}
			System.out.println("Flight has been Sorted by Departure Time Successfully");
			break;

		default:
			System.out.println("Going back to main menu");
			break;
		}

	}

	private static void showFlights() {
		flightsIn.clear();
		flightsOut.clear();

		if (!allFlights.isEmpty() && flightsIn.isEmpty() && flightsOut.isEmpty())
			spreadFlights();
		System.out.println(allFlights.size() + " " + flightsIn.size() + " " + flightsOut.size());

		System.out.println("\nIncoming Flights: ");
		if (flightsIn.size() == 0)
			System.out.println("No Incoming Flights...");
		else
			for (Flight f : flightsIn) {
				System.out.println(f);
			}

		System.out.println("\nOutcoming Flights: ");
		if (flightsOut.size() == 0)
			System.out.println("No Outcoming Flights...");
		else
			for (Flight f : flightsOut) {
				System.out.println(f);
			}

	}

	public static void SearchByTerms(List<Flight> arr) {
		String brand = "";
		String country = "";
		String city = "";
		String airport = "";
		MyDate startDate = null;
		MyDate endingDate = null;
		int weekDays = 1;

		System.out.println("Which Air-Line Brand? (1-" + (brands.size() + 1) + ")");
		int i = 1;
		for (String s : brands)
			System.out.println(i++ + ") " + s);
		
		do {
			i = Integer.parseInt(scn.nextLine());
		}
		
		while (i > 5 || i < 0);
			System.out.println(i);
			if (i == 5) {
				System.out.println("Whitch Flight Company?");
				brand = scn.nextLine();
			} else
				brand = brands.get(i - 1);
		

		System.out.println("Which Country?");
		country = scn.nextLine();
		System.out.println("Which City?");
		city = scn.nextLine();
		System.out.println("4) by Air-Port");
		airport = scn.nextLine();
		System.out.println("What is the Starting Date?");
		try {
			int days = Integer.parseInt(scn.nextLine());
			int mos = Integer.parseInt(scn.nextLine());
			int year = Integer.parseInt(scn.nextLine());
			startDate = new MyDate(days, mos, year);
		} catch (Exception e) {
			System.out.println("Didnt Work Out");
		}
		System.out.println("What is the Ending Date?");
		try {
			int days = Integer.parseInt(scn.nextLine());
			int mos = Integer.parseInt(scn.nextLine());
			int year = Integer.parseInt(scn.nextLine());
			endingDate = new MyDate(days, mos, year);
		} catch (Exception e) {
			System.out.println("Didnt Work Out");
		}

		System.out.println("How many Days in a Week?");
		try {
			weekDays = Integer.parseInt(scn.nextLine());
		} catch (Exception e) {
			System.out.println("Error! Going 1 By-Defualt");
		}
		int counter = 0;
		for (Flight f : arr) {
//			System.out.println((f.getBrand().compareToIgnoreCase(brand) == 0
//					&& (f.getDepAirPort().contains(country) || f.getArriveAirPort().contains(country))
//					&& (f.getDepAirPort().contains(city) || f.getArriveAirPort().contains(city))));
			if ((f.getBrand().contains(brand))
					&& (f.getDepAirPort().contains(country) || f.getArriveAirPort().contains(country))
					&& (f.getDepAirPort().contains(city) || f.getArriveAirPort().contains(city))) {
				;
				counter++;
				System.out.println(f);
			}
		}
		if (counter == 0)
			System.out.println("No Match Found");

	}

	// HelpFull Methods //

//	public static ArrayList<>

	// Auto add //
	private static void AutoAdd() {
		int hrsIn = (int) (1 + Math.random() * 23);
		int minIn = (int) (1 + Math.random() * 60);
		int hrsOut = (int) (1 + Math.random() * 23);
		int minOut = (int) (1 + Math.random() * 60);
		int mons = (int) (1 + Math.random() * 12);
		int day = (int) (1 + Math.random() * 28);

		while (hrsOut - hrsIn >= 3) {
			hrsIn = (int) (1 + Math.random() * 23);
			hrsOut = (int) (1 + Math.random() * 23);
		}
		flag = (int) (1 + Math.random() * 2);
		System.out.println("min --->>" + minIn);
		Collections.shuffle(airPorts);
		int size = airPorts.size() - 1;
		int p = (int) (1 + Math.random() * size);
		if (flag == 1)
			allFlights.add(new FlightIn(brands.get((int) (1 + Math.random() * 3)), airPorts.get(p),
					new MyDate(day, mons, 2020), hrsOut + ":" + minOut, hrsIn + ":" + minIn,
					(100 + Math.random() * 5) + "", (int) (1 + Math.random() * 3), flag));
		else
			allFlights.add(new FlightOut(brands.get((int) (1 + Math.random() * 3)), airPorts.get(p),
					new MyDate(day, mons, 2020), hrsOut + ":" + minOut, hrsIn + ":" + minIn,
					(100 + Math.random() * 5) + "", (int) (1 + Math.random() * 3), flag));

	}

	// ask method for detail //
	private static void pushFlight(String n) {
		allFlights.add(askMe(n));
	}

	public static Flight askMe(String n) {
		System.out.print("Insert flight ID: ");
		flightId = scn.nextLine();
		System.out.print("Insert flight Date: ");
		date = new MyDate(scn);
		System.out.print("Insert flight Departure Time: ");
		depTime = scn.nextLine();
		System.out.print("Insert flight Arrival Time: ");
		arrTime = scn.nextLine();
		System.out.println("Insert flight brad: ");
		brand = addBrand();
		System.out.print("Insert flight Terminal Number: ");
		terminalNum = Integer.parseInt(scn.nextLine());

		if (n.equals("1")) { // -->> Flight In ///
			System.out.print("Insert flight Departure Air-Port: ");
			depAirPort = scn.nextLine();
			return new FlightIn(brand, depAirPort, date, depTime, arrTime, flightId, terminalNum, 1);
		} else if (n.equals("2")) { // -->> flight out ///

			System.out.print("Insert flight Arrival AirPort: ");
			arriveAirPort = scn.nextLine();
			return new FlightOut(brand, arriveAirPort, date, depTime, arrTime, flightId, terminalNum, 2);
		}

		return null;
	}

	public static void createBrandsSet() {
		brands.add("Arkia");
		brands.add("El-Al");
		brands.add("Israir");
		brands.add("Wizz");
	}

	// Spreads Flights from AllFlights to FlightIn and FlightOut //
	public static void spreadFlights() {
		for (Flight f : allFlights) {
			if (f.getFlag() == 1)
				flightsIn.add((FlightIn) f);
			else
				flightsOut.add((FlightOut) f);
		}
	}

	public static String addBrand() {
		String brand = "";
		// prints all brands //
		for (int i = 0; i < brands.size(); i++) {
			System.out.println((i + 1) + ") " + brands.get(i));
			if (i == brands.size() - 1)
				System.out.println((i + 2) + ") Other ...");
		}
		// chooses the brand //
		int res = Integer.parseInt(scn.nextLine());
		while (true) {
			if (res < 0 || res > brands.size() + 1) {
				System.out.println("Wrong input! Try Again!");
				res = Integer.parseInt(scn.nextLine());
			} else
				break;
		}
		while (res >= 0 && res <= brands.size() + 1) {
			if (res == brands.size() + 1) {
				System.out.println("Whitch Brand?");
				brand = scn.nextLine();
				break;
			} else
				brand = brands.get(res - 1);
			break;
		}
		return brand;

	}

	public static void addAirPorts() {
		airPorts.add("Prague Air-Port, Czech Republic");
		airPorts.add("Paris Air-Port, France");
		airPorts.add("Moscow Air-Port, Russia");
		airPorts.add("Brussel Air-Port, Belguim");
		airPorts.add("Berlin Air-Port, Germany");
		airPorts.add("Devlin Air-Port, Ireland");
		airPorts.add("London Air-Port, UK");
		airPorts.add("NYC Air-Port, USA");
		airPorts.add("New-Deli Air-Port, India");
		airPorts.add("Adiss-Abba Air-Port, Ethipea");
		airPorts.add("Washington DC Air-Port, USA");
		airPorts.add("Helsinki Air-Port, Finland");
		airPorts.add("Bangkok Air-Port, China");
		airPorts.add("Tokyo Air-Port, Japan");
		airPorts.add("Menila Air-Port, The Philippines");
		airPorts.add("Shenghai Air-Port, China");
		airPorts.add("Kiev Air-Port, Ukrain");
		airPorts.add("Odessa Air-Port, Ukrain");
		airPorts.add("Sant-Peternurg Air-Port, Russia");
		airPorts.add("Nairobi Air-Port, Kenya");
		airPorts.add("Oslo Air-Port, Norway");
		airPorts.add("Riga Air-Port, Latvia");
		airPorts.add("Vienna Air-Port, Austria");
		airPorts.add("Wellington Air-Port, New Zealand");
		airPorts.add("Barcelona Air-Port, Spain");
		airPorts.add("Zagreb Air-Port, Croatia");
		airPorts.add("Madrid Air-Port, Spain");
	}

}
