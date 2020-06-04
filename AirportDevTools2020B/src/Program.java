import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Program {
	public static boolean isOK = false;
	private static ArrayList<Flight> flightsIn = new ArrayList<>();
	private static ArrayList<Flight> flightsOut = new ArrayList<>();

	// private static Set<Flight> tsFlights = new TreeSet<>();
	public static Scanner scn = new Scanner(System.in);
	public static final String FILE_NAME = "Input.txt";
//	public static final String FILE_NAMEout = "Output.txt"; 

	public static void main(String[] args) throws FileNotFoundException {
		activition();
		scn.close();

	}

	public static void activition() throws FileNotFoundException {
		System.out.println("Welcome\nPlease Choose by entering number:");
		isOK = false;
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
			System.out.println("9 - EXIT");

			for (int i = 0; i < 15; i++) {
				System.out.print("- ");
			}

			System.out.print("\nYour Choice: ");
			int userChoice = Integer.parseInt(scn.nextLine());
			switch (userChoice) {
			case 1:
				addFlight();
				isOK = false;
				break;
			case 2:
				removeFlight();
				isOK = false;
				break;
			case 3:
				sortFlights();
				isOK = false;
				break;
			case 4:
				loadFromFile();
				isOK = false;
				break;
			case 5:
				saveToFile();
				isOK = false;
				break;
			case 6:
				showFlights();
				isOK = false;
				break;
			case 7:
				showInRange();
				isOK = false;
				break;
			case 8:
				System.out.println("insert departure airport");
				showFlightsByDepAirPort(scn.nextLine());
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

	private static void showInRange() {
		System.out.println("insert Start time");
		MyDate startTime = getDate(scn);
		System.out.println("insert end time");
		MyDate endTime = getDate(scn);
		showFlightInRange(startTime, endTime);

	}

	private static void showFlightsByDepAirPort(String nextLine) {
		// TODO Auto-generated method stub

	}

	private static void showFlightInRange(MyDate startTime, MyDate endTime) {
		// TODO Auto-generated method stub

	}

	private static void saveToFile() throws FileNotFoundException {
		try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "rw")) {
			raf.setLength(0);
			// Flights in //

			// size of Flights in //
			raf.writeUTF(flightsIn.size() + "");

			if (flightsIn.size() > 0) {
				for (Flight f : flightsIn) {
					// the Parameters //
					raf.writeUTF(f.getFlightId());
					raf.writeUTF(f.getDate().toString());
					raf.writeUTF(f.getDepAirPort());
					raf.writeUTF(f.getDepTime());
					raf.writeUTF(f.getBrand());
					raf.writeUTF(f.getTerminalNum() + "");
				}
			}
			// size of Flights Out //
			raf.writeUTF(flightsOut.size() + "");

			// Flights Out //
			if (flightsOut.size() > 0) {
				for (Flight f : flightsOut) {
					// the Parameters //

					raf.writeUTF(f.getFlightId());
					raf.writeUTF(f.getDate().toString());
					raf.writeUTF(f.getArriveAirPort());
					raf.writeUTF(f.getDepTime());
					raf.writeUTF(f.getBrand());
					raf.writeUTF(f.getTerminalNum() + "");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// In - > Out //

	private static void loadFromFile() throws FileNotFoundException {
		flightsIn.clear();
		flightsOut.clear();
		try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "r")) {
			int sizeIn = Integer.parseInt(raf.readUTF());
			while (raf.getFilePointer() < raf.length()) {
				while (sizeIn > 0) {
					String flightId = raf.readUTF();
					String str = raf.readUTF();
					String[] split = str.split("/");

					MyDate date = new MyDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]),
							Integer.parseInt(split[2]));
					String depAirPort = raf.readUTF();
					String depTime = raf.readUTF();
					String brand = raf.readUTF();
					int terminalNum = Integer.parseInt(raf.readUTF());

					Flight f = new Flight(flightId, depAirPort, date, depTime, terminalNum, brand);
					flightsIn.add(f);
					sizeIn--;
				}
				int sizeOut = Integer.parseInt(raf.readUTF());
				if (raf.getFilePointer() < raf.length()) {
					while (sizeOut > 0) {
						String flightId = raf.readUTF();
						String str = raf.readUTF();
						String[] split = str.split("/");

						MyDate date = new MyDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]),
								Integer.parseInt(split[2]));
						String depAirPort = raf.readUTF();
						String depTime = raf.readUTF();
						String brand = raf.readUTF();
						int terminalNum = Integer.parseInt(raf.readUTF());

						Flight f = new Flight(flightId, depAirPort, date, depTime, terminalNum, brand);
						flightsOut.add(f);
						sizeOut--;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static MyDate getDate(Scanner scn) {
		System.out.println("enter date 'day' 'month' 'year' (example :23 06 1994)");
		int day = scn.nextInt();
		int month = scn.nextInt();
		int year = scn.nextInt();
		return new MyDate(day, month, year);
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

	private static void addOutFlight() {
		System.out.print("Insert flight ID: ");
		String flightId = scn.nextLine();
		System.out.print("Insert flight Date: ");
		MyDate date = new MyDate(scn);
		System.out.print("Insert flight Arrival. AirPort: ");
		String arriveAirPort = scn.nextLine();
		System.out.print("Insert flight dep. Time: ");
		String depTime = scn.nextLine();
		System.out.print("Insert flight brad: ");
		String brand = scn.nextLine();
		System.out.print("Insert flight Terminam Number: ");
		int terminalNum = Integer.parseInt(scn.nextLine());
		flightsOut.add(new Flight(flightId, date, depTime, terminalNum, brand, arriveAirPort));

	}

	private static void addInFlight() {
		System.out.print("Insert flight ID: ");
		String flightId = scn.nextLine();
		System.out.print("Insert flight Date: ");
		MyDate date = new MyDate(scn);
		System.out.print("Insert flight dep. AirPort: ");
		String depAirPort = scn.nextLine();
		System.out.print("Insert flight dep. Time: ");
		String depTime = scn.nextLine();
		System.out.print("Insert flight brad: ");
		String brand = scn.nextLine();
		System.out.print("Insert flight Terminam Number: ");
		int terminalNum = Integer.parseInt(scn.nextLine());
		flightsIn.add(new Flight(flightId, depAirPort, date, depTime, terminalNum, brand));
	}

	private static void removeFlight() {
		char ch;
		if (flightsIn.size() == 0)
			System.out.println("no flights listed");
		else {
			System.out.println("which flight you want to Remove?");
			while (!isOK) {
				System.out.println("1) A flight-In\n2) A Flight-Out");
				int dec = Integer.parseInt(scn.nextLine());
				int i = 0;
				int res = 0;

				System.out.println("Which exact Flight?");
				if (dec == 1) {
					for (Flight f : flightsIn)
						System.out.println((++i) + ") " + f);
					while (true) {
						res = Integer.parseInt(scn.nextLine());
						if (res <= flightsIn.size() && res > 0) {
							res -= 1;
							// Are You Sure? //
							System.out.println("Are You sure you Want to Remove the flight From "
									+ flightsIn.get(res).getDepAirPort() + " To "
									+ flightsIn.get(res).getArriveAirPort());
							ch = scn.nextLine().charAt(0);
							while (ch - 'y' != 0 && ch - 'Y' != 0 && ch - 'n' != 0 && ch - 'N' != 0) {
								System.out.println("Yes/No");
								ch = scn.nextLine().charAt(0);
							}
							if (ch - 'y' == 0 || ch - 'Y' == 0) {
								flightsIn.remove(res);
								System.out.println("The Flight From " + flightsIn.get(res).getDepAirPort() + " To "
										+ flightsIn.get(res).getArriveAirPort() + " has been Removed.");
							} else
								System.out.println("you decided not to remove. Going back to Main Menu.");
							isOK = true;
							break;
						} else
							System.out.println("Wrong number, try again between 1-" + flightsIn.size());
					}
				} else if (dec == 2) {
					for (Flight f : flightsOut)
						System.out.println((++i) + ") " + f);
					while (true) {
						res = Integer.parseInt(scn.nextLine());
						if (res <= flightsOut.size() && res > 0) {
							res -= 1;
							// Are You Sure? //
							System.out.println("Are You sure you Want to Remove the flight From "
									+ flightsOut.get(res).getDepAirPort() + " To "
									+ flightsOut.get(res).getArriveAirPort());
							ch = scn.nextLine().charAt(0);
							while (ch - 'y' != 0 && ch - 'Y' != 0 && ch - 'n' != 0 && ch - 'N' != 0) {
								System.out.println("Yes/No");
								ch = scn.nextLine().charAt(0);
							}
							if (ch - 'y' == 0 || ch - 'Y' == 0) {
								flightsOut.remove(res);
								System.out.println("The Flight From " + flightsOut.get(res).getDepAirPort() + " To "
										+ flightsOut.get(res).getArriveAirPort() + " has been Removed.");
							} else
								System.out.println("you decided not to remove. Going back to Main Menu.");
							isOK = true;
							break;
						} else
							System.out.println("Wrong number, try again between 1-" + flightsOut.size());
					}
				} else
					System.out.println("Wrong Input,Try again,");
			}
		}
	}

	private static void sortFlights() {
		System.out.println(
				"1) Sort By Departure Time..\n2) Sort By Arriving Time..\n3) Sort Arrival Air-Ports By name..\n4) Sort By Price Ascending.");
		char c = scn.nextLine().charAt(0);
		switch (c) {
		case '1':
			// Sort by Dep. Time //
			Collections.sort(flightsIn, new Comparator<Flight>() {

				@Override
				public int compare(Flight o1, Flight o2) {
					return o1.getDepTime().compareTo(o2.getDepTime());
				}
			});
			break;
		case '2':
			// Sort by Arrival Time -- TO DO //
			Collections.sort(flightsIn, new Comparator<Flight>() {

				@Override
				public int compare(Flight o1, Flight o2) {
					return 0;
				}
			});
			break;
		case '3':
			// Sort by Arrival Location //
			Collections.sort(flightsIn, new Comparator<Flight>() {
				@Override
				public int compare(Flight o1, Flight o2) {
					return o1.getArriveAirPort().compareTo(o2.getArriveAirPort());
				}
			});
			break;

		case '4':
			// Sort by Highest Price //
			Collections.sort(flightsIn, new Comparator<Flight>() {
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
		System.out.println("All Flights in To Ben-Gurion Air-Port, Tel Aviv Israel.");
		if (flightsIn.size() == 0)
			System.out.println("No Incoming Flights listed!");
		else
			for (int i = 0; i < flightsIn.size(); i++) {
				System.out.println((i + 1) + ") " + flightsIn.get(i).toString());
			}
		System.out.println("\nAll Flights Out From Ben-Gurion Air-Port, Tel Aviv Israel:");
		if (flightsOut.size() == 0)
			System.out.println("No Outcoming Flights listed!");
		else
			for (int i = 0; i < flightsOut.size(); i++) {
				System.out.println((i + 1) + ") " + flightsIn.get(i).toString());
			}
	}

}
