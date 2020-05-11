import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
//import java.util.Set;
//import java.util.TreeSet;



public class Program {
	private static ArrayList<Flight> flights = new ArrayList<>();
	// private static Set<Flight> tsFlights = new TreeSet<>();
	public static Scanner scn = new Scanner(System.in);
	public static final String FILE_NAMEin = "Input.txt";
	public static final String FILE_NAMEout = "Output.txt";
	
	
	

	public static void main(String[] args) throws FileNotFoundException {

		activition();
		scn.close();

	}

	public static void activition() throws FileNotFoundException {
		System.out.println("Welcome\nPlease Choose by entering number:");

		boolean isOK = false;
		while (!isOK) {
			for (int i = 0; i < 15; i++) {
				System.out.print("- ");
			}

			System.out.println("\n1 - Add new Flight");
			System.out.println("2 - Remove Flight");
			System.out.println("3 - Sort flights by...");
			System.out.println("4 - Load from File");
			System.out.println("5 - Save to File");
			System.out.println("6 - Print");
			System.out.println("7 - print in date range");
			System.out.println("8 - print print by dep airport");

			for (int i = 0; i < 15; i++) {
				System.out.print("- ");
			}

			System.out.print("\nYour Choice: ");
			char userChoice = scn.nextLine().charAt(0);
			switch (userChoice) {
			case '1':
				addFlight();
				isOK = false;
				break;
			case '2':
				removeFlight();
				isOK = false;
				break;
			case '3':
				sortFlights();
				isOK = false;
				break;
			case '4':
				loadFromFile(null);
				isOK = false;
				break;
			case '5':
				saveToFile();
				isOK = false;
				break;

			case '6':
				showFlights();
				isOK = false;
				break;
			case '7':
				showInRange();
				isOK = false;
				break;
			case '8':
				System.out.println("insert departure airport");
				showFlightsByDepAirPort(scn.nextLine());
				isOK = false;
				break;

			default:
				System.out.println("wrong input try again");
				userChoice = scn.nextLine().charAt(0);
				isOK = false;
			}
		}

	}

	private static void showInRange() {
		System.out.println("insert Start time");
		MyDate startTime = getDate(scn);
		System.out.println("insert end time");
		MyDate endTime = getDate(scn);
		showFlightInRange( startTime ,  endTime);
		
		
		
	}
	
	
	private static void showFlightsByDepAirPort(String nextLine) {
		// TODO Auto-generated method stub
		
	}

	private static void showFlightInRange(MyDate startTime, MyDate endTime) {
		// TODO Auto-generated method stub
		
	}
	
	

	private static void loadFromFile(File f1) throws FileNotFoundException {
		Scanner fScn = new Scanner(f1);
		String fLine;
		while (fScn.hasNext()) {
			fLine = fScn.nextLine();
			String[] breakDown = fLine.split(",");
			
			
		}
		
		
		
	}
	
	public static MyDate getDate(Scanner scn) {
		System.out.println("enter date 'day' 'month' 'year' (example :23 06 1994)");
		int day = scn.nextInt();
		int month = scn.nextInt();
		int year = scn.nextInt();
		return  new MyDate(day, month, year);
	}

	private static void saveToFile() throws FileNotFoundException {
		// check if file already exists
		PrintWriter pw = new PrintWriter(new File(FILE_NAMEout));
		for (Flight f : flights) {
			pw.print(f);
			StringBuffer sb = new StringBuffer();
			sb.append(f.getFlightId() + ", " + f.getArriveAirPort() + ", " + f.getDepTime()
				+ ", " + f.getArrTime() + ", " + f.getArrTime());
			pw.append("\n");
		}
		
		pw.close();
		//scn.close();
	}

	private static void addFlight() {
		System.out.print("insert flight arriveAirPort: ");
		String arriveAirPort = scn.nextLine();
		System.out.print("insert flight dep. Time: ");
		String depTime = scn.nextLine();
		System.out.print("insert flight Arrival Time: ");
		String arrTime = scn.nextLine();
		flights.add(new Flight(arriveAirPort, depTime, arrTime));
		// tsFlights.add(new Flight(ID, deptAirPort, arriveAirPort, depTime, arrTime));

		System.out.println("Flight is Added");

	}

	private static void removeFlight() {
		if (flights.size() == 0)
			System.out.println("no flights listed");
		else {
			System.out.println("lets see our flights:");
			showFlights();

			System.out.println("enter flight ID ");

		}

	}

	private static void sortFlights() {
		System.out.println(
				"1) Sort By Departure Time..\n2) Sort By Arriving Time..\n3) Sort Arrival Air-Ports By name..\n4) Sort By Price Ascending.");
		char c = scn.nextLine().charAt(0);
		switch (c) {
		case '1':
			// Sort by Dep. Time //
			Collections.sort(flights, new Comparator<Flight>() {

				@Override
				public int compare(Flight o1, Flight o2) {
					return o1.getDepTime().compareTo(o2.getDepTime());
				}
			});
			break;
		case '2':
			// Sort by Arrival Time //
			Collections.sort(flights, new Comparator<Flight>() {

				@Override
				public int compare(Flight o1, Flight o2) {
					return o1.getArrTime().compareTo(o2.getArrTime());
				}
			});
			break;
		case '3':
			// Sort by Arrival Location //
			Collections.sort(flights, new Comparator<Flight>() {
				@Override
				public int compare(Flight o1, Flight o2) {
					return o1.getArriveAirPort().compareTo(o2.getArriveAirPort());
				}
			});
			break;

		case '4':
			// Sort by Highest Price //
			Collections.sort(flights, new Comparator<Flight>() {
				@Override
				public int compare(Flight o1, Flight o2) {
					return (int) o1.getPrice() - (int) o2.getPrice();
				}
			});
			break;
		default:
			break;
		}

	}

	private static void showFlights() {
		System.out.println();
		for (int i = 0; i < flights.size(); i++) {
			System.out.println((i + 1) + ") " + flights.get(i).toString());
		}
	}

	// know about all the flights
	// simple program
	// menu that

	// Insert // View
	// 1 . outgoin flight
	// incoming flights
	// view outgoin by sorted by time
	// ou go out
	// add freature - save to files
	// uyser can enter the details.
	// the details
	// insertNewFlight(){

	// }

	// viewFlights(){

	// }

}
