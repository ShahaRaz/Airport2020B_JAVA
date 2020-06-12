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
	public static boolean flag;

	public static List<String> brands = new ArrayList<>();
	private static List<Flight> allFlights;

	// private static Set<Flight> tsFlights = new TreeSet<>();
	public static Scanner scn = new Scanner(System.in);
	public static final String FILE_NAME = "Input.txt";

	public static void main(String[] args) throws FileNotFoundException {
		activition();
		scn.close();

	}

	public static void activition() throws FileNotFoundException {
		createBrandsSet();
		System.out.println("Welcome\nPlease Choose by entering number:");
		isOK = false;
		while (!isOK) {
			for (int i = 0; i < 15; i++) {
				System.out.print("- ");
			}

			System.out.println("\n1 - Add new Flight");
			System.out.println("2 - Remove Flight");
			System.out.println("3 - Sort flights by...");
			System.out.println("4 - Save from File");
			System.out.println("5 - Load to File");
			System.out.println("6 - Print All Flights");
			System.out.println("7 - Remove All Flights");
			System.out.println("8 - N\\A");
			System.out.println("9 - EXIT");

			for (int i = 0; i < 15; i++) {
				System.out.print("- ");
			}

			System.out.print("\nYour Choice: ");
			int userChoice = Integer.parseInt(scn.nextLine());
			switch (userChoice) {
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

			case 9:
				isOK = true;
				break;

			default:
				System.out.println("wrong input try again");
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
				raf.writeUTF(f.getFlightId());
				raf.writeUTF(f.getDate().toString());
				raf.writeUTF(f.getDepTime());
				raf.writeUTF(f.getArrTime());
				raf.writeUTF(f.getTerminalNum() + "");
				raf.writeUTF(f.isFlag() + "");

			}
		}

	}

	// In - > Out //
	private static void loadFromFile() throws FileNotFoundException {
		Flight f = null;
		MyDate date = null;
		allFlights.clear();
		try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "r")) {
			while (raf.getFilePointer() < raf.length()) {
				brand = raf.readUTF();
				depAirPort = raf.readUTF();
				arriveAirPort = raf.readUTF();
				flightId = raf.readUTF();
				String theDate = raf.readUTF(); // reading the date as a String //
				String[] split = theDate.split("/");
				date = new MyDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
				depTime = raf.readUTF();
				arrTime = raf.readUTF();
				terminalNum = Integer.parseInt(raf.readUTF());
				flag = (boolean) raf.readBoolean();
				if (flag) {
					allFlights
							.add(new FlightIn(brand, depAirPort, date, depTime, arrTime, flightId, terminalNum, true));
				} else
					allFlights.add(
							new FlightOut(brand, arriveAirPort, date, depTime, arrTime, flightId, terminalNum, false));

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
			int inout = Integer.parseInt(scn.nextLine());
			if (inout == 1) {
				addInFlight();
				isOK = true;
			} else if (inout == 2) {
				addOutFlight();
				isOK = true;
			} else
				System.out.println("Wrong input");
		}
		System.out.println("Flight is Added");
	}

	private static void addInFlight() {
		allFlights.add(askMe("1"));

	}

	private static void addOutFlight() {
		allFlights.add(askMe("2"));
	}

	private static void sortFlights() {

		System.out.println("1) Sort By Departure Time..\n2) Sort By Arriving Time..\nPress any key for main menu");
		char c = scn.nextLine().charAt(0);
		switch (c) {
		case '1':
			// list 1 //
//			Collections.sort(flightsIn, new Comparator<Flight>() {
//				long m1, m2;
//
//				@Override
//				public int compare(Flight o1, Flight o2) {
//					String date1 = o1.getDate() + " " + o1.getDepTime();
//					String date2 = o2.getDate() + " " + o2.getDepTime();
//
//					SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy hh:mm");
//					try {
//						m1 = sdf.parse(date1).getTime();
//						m2 = sdf.parse(date2).getTime();
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//
//					return (int) ((-1) * (m2 - m1));
//				}
//			});

			// List 2 //
//			Collections.sort(flightsOut, new Comparator<Flight>() {
//				long m1, m2;
//
//				@Override
//				public int compare(Flight o1, Flight o2) {
//					String date1 = o1.getDate() + " " + o1.getDepTime();
//					String date2 = o2.getDate() + " " + o2.getDepTime();
//
//					SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy hh:mm");
//					try {
//						m1 = sdf.parse(date1).getTime();
//						m2 = sdf.parse(date2).getTime();
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//					return (int) ((-1) * (m2 - m1));
//				}
//			});
			System.out.println("Flight has been Sorted by Departure Time Successfully");

			break;

		case '2':
			// List 1 //
//			Collections.sort(flightsIn, new Comparator<Flight>() {
//				long m1, m2;
//
//				@Override
//				public int compare(Flight o1, Flight o2) {
//					String date1 = o1.getDate() + " " + o1.getArrTime();
//					String date2 = o2.getDate() + " " + o2.getArrTime();
//
//					SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy hh:mm");
//					try {
//						m1 = sdf.parse(date1).getTime();
//						m2 = sdf.parse(date2).getTime();
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//					return (int) ((-1) * (m2 - m1));
//				}
//			});
//			// List 2 //
//			Collections.sort(flightsOut, new Comparator<Flight>() {
//				long m1, m2;
//
//				@Override
//				public int compare(Flight o1, Flight o2) {
//					String date1 = o1.getDate() + " " + o1.getArrTime();
//					String date2 = o2.getDate() + " " + o2.getArrTime();
//
//					SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy hh:mm");
//					try {
//						m1 = sdf.parse(date1).getTime();
//						m2 = sdf.parse(date2).getTime();
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//					return (int) ((-1) * (m2 - m1));
//				}
//			});

			System.out.println("Flight has been Sorted by Arrival Time Successfully");
			break;

		default:
			System.out.println("Going back to main menu");
			break;
		}

	}
	
	private static void showFlights() {
		
//		// Flights in //
//		System.out.println("All Flights in To Ben-Gurion Air-Port, Tel Aviv Israel.");
//		if (flightsIn.size() == 0)
//			System.out.println("No Incoming Flights listed!");
//
//		for (int i = 0; i < flightsIn.size(); i++) {
//			System.out.println((i + 1) + ") " + flightsIn.get(i).toString());
//		}
//
//		// Flights Out //
//		System.out.println("\nAll Flights Out From Ben-Gurion Air-Port, Tel Aviv Israel:");
//		if (flightsOut.size() == 0)
//			System.out.println("No Outcoming Flights listed!");
//
//		for (int i = 0; i < flightsOut.size(); i++) {
//			System.out.println(flightsOut.get(i).getArriveAirPort());
//			System.out.println((i + 1) + ") " + flightsOut.get(i).toString());
//		}
	}

	public static void helpPrintByTerms(List<Flight> arr) {
		String str = "";
		System.out.println("1) by AirLine Brand");
		System.out.println("2) by Country");
		System.out.println("3) by City");
		System.out.println("4) by Air-Port");
		System.out.println("5) by Departure Date");
		System.out.println("6) by Arrival Date");
		System.out.println("7) by days in week");
		System.out.println("8) Back To Main Menu");
		try {
			int res = Integer.parseInt(scn.nextLine());
			switch (res) {
			case 1:
				System.out.println("what is the Airline brand name?");
				str = scn.nextLine();
				for (int i = 0; i < arr.size(); i++) {
					if (arr.get(i).getBrand().compareToIgnoreCase(str) == 0)
						System.out.println(arr.get(i).toString());
				}
				break;
			case 2:

				break;
			case 3:

				break;
			case 4:

				break;
			}
		} catch (Exception e) {
			System.err.println("Error! integers only");
		}

	}

	// HelpFull Methonds //
	
//	public static ArrayList<>

	public static Flight askMe(String n) {
		if (n.equals("1")) { // -->> Flight In ///
			System.out.print("Insert flight ID: ");
			flightId = scn.nextLine();
			System.out.print("Insert flight Date: ");
			date = new MyDate(scn);
			System.out.print("Insert flight Departure Air-Port: ");
			depAirPort = scn.nextLine();
			System.out.print("Insert flight Departure Time: ");
			depTime = scn.nextLine();
			System.out.print("Insert flight Arrival Time: ");
			arrTime = scn.nextLine();
			System.out.println("Insert flight brad: ");
			brand = addBrand();
			System.out.print("Insert flight Terminam Number: ");
			terminalNum = Integer.parseInt(scn.nextLine());
			return new FlightIn(brand, depAirPort, date, depTime, arrTime, flightId, terminalNum, true);
		} else if (n.equals("2")) { // -->> flight out ///
			System.out.print("Insert flight ID: ");
			flightId = scn.nextLine();
			System.out.print("Insert flight Date: ");
			date = new MyDate(scn);
			System.out.print("Insert flight Arrival. AirPort: ");
			arriveAirPort = scn.nextLine();
			System.out.print("Insert flight Departure Time: ");
			depTime = scn.nextLine();
			System.out.print("Insert flight Arrival Time: ");
			arrTime = scn.nextLine();
			System.out.println("Insert flight brad: ");
			brand = addBrand();
			System.out.print("Insert flight Terminam Number: ");
			terminalNum = Integer.parseInt(scn.nextLine());
			return new FlightOut(brand, arriveAirPort, date, depTime, arrTime, flightId, terminalNum, false);

		}

		return null;
	}

	public static void createBrandsSet() {
		brands.add("Arkia");
		brands.add("El-Al");
		brands.add("Israir");
		brands.add("Wizz");
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
		System.out.println(res);
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
}
