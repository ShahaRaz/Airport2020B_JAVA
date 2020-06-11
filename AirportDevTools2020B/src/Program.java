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
	public static String depTime;
	public static String arrTime;
	public static String brand;
	public static int terminalNum;
	public static MyDate date = null;

	public static List<String> brands = new ArrayList<>();
	private static List<Flight> flightsIn = new ArrayList<>();
	private static List<Flight> flightsOut = new ArrayList<>();

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
				removeFlight();
				System.out.println("Flight has been added Successfully");
				isOK = false;
				break;
			case 3:
				sortFlights();
				isOK = false;
				break;
			case 4:
				saveToFile();
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
					raf.writeUTF(f.getArrTime());
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
					raf.writeUTF(f.getArrTime());
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
		MyDate date = null;
		flightsIn.clear();
		flightsOut.clear();
		try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "r")) {
			int sizeIn = Integer.parseInt(raf.readUTF());
			while (raf.getFilePointer() < raf.length()) {
				while (sizeIn > 0) {
					flightId = raf.readUTF();
					String str = raf.readUTF();
					String[] split = str.split("/");

					date = new MyDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]),
							Integer.parseInt(split[2]));
					depAirPort = raf.readUTF();
					depTime = raf.readUTF();
					arrTime = raf.readUTF();
					brand = raf.readUTF();
					terminalNum = Integer.parseInt(raf.readUTF());

					Flight f = new Flight(flightId, depAirPort, date, depTime, arrTime, terminalNum, brand);
					flightsIn.add(f);
					sizeIn--;
				}
				int sizeOut = Integer.parseInt(raf.readUTF());
				if (raf.getFilePointer() < raf.length()) {
					while (sizeOut > 0) {
						flightId = raf.readUTF();
						String str = raf.readUTF();
						String[] split = str.split("/");

						date = new MyDate(Integer.parseInt(split[0]), Integer.parseInt(split[1]),
								Integer.parseInt(split[2]));
						depAirPort = raf.readUTF();
						depTime = raf.readUTF();
						arrTime = raf.readUTF();
						brand = raf.readUTF();
						terminalNum = Integer.parseInt(raf.readUTF());

						Flight f = new Flight(flightId, depAirPort, date, depTime, arrTime, terminalNum, brand);
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

	private static void addOutFlight() {
		System.out.print("Insert flight ID: ");
		String flightId = scn.nextLine();
		System.out.print("Insert flight Date: ");
		MyDate date = new MyDate(scn);
		System.out.print("Insert flight Arrival. AirPort: ");
		String arriveAirPort = scn.nextLine();
		System.out.print("Insert flight Departure Time: ");
		String depTime = scn.nextLine();
		System.out.print("Insert flight Arrival Time: ");
		String arrTime = scn.nextLine();
		System.out.println("Insert flight brad: ");
		String brand = addBrand();
		System.out.print("Insert flight Terminam Number: ");
		int terminalNum = Integer.parseInt(scn.nextLine());
		flightsOut.add(new Flight(flightId, date, depTime, arrTime, terminalNum, brand, arriveAirPort));
	}

	private static void addInFlight() {
		System.out.print("Insert flight ID: ");
		flightId = scn.nextLine();
		System.out.print("Insert flight Date: ");
		date = new MyDate(scn);
		System.out.print("Insert flight Departure AirPort: ");
		String depAirPort = scn.nextLine();
		System.out.print("Insert flight Departure Time: ");
		String depTime = scn.nextLine();
		System.out.print("Insert flight Arrival Time: ");
		String arrTime = scn.nextLine();
		System.out.println("Insert flight brand: ");
		String brand = addBrand();
		System.out.print("Insert flight Terminal Number: ");
		int terminalNum = Integer.parseInt(scn.nextLine());
		flightsIn.add(new Flight(flightId, depAirPort, date, depTime, arrTime, terminalNum, brand));
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

		System.out.println("1) Sort By Departure Time..\n2) Sort By Arriving Time..\nPress any key for main menu");
		char c = scn.nextLine().charAt(0);
		switch (c) {
		case '1':
			// list 1 //
			Collections.sort(flightsIn, new Comparator<Flight>() {
				long m1, m2;

				@Override
				public int compare(Flight o1, Flight o2) {
					String date1 = o1.getDate() + " " + o1.getDepTime();
					String date2 = o2.getDate() + " " + o2.getDepTime();

					SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy hh:mm");
					try {
						m1 = sdf.parse(date1).getTime();
						m2 = sdf.parse(date2).getTime();
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					return (int) ((-1)*(m2 - m1));
				}
			});

			// List 2 //
			Collections.sort(flightsOut, new Comparator<Flight>() {
				long m1, m2;

				@Override
				public int compare(Flight o1, Flight o2) {
					String date1 = o1.getDate() + " " + o1.getDepTime();
					String date2 = o2.getDate() + " " + o2.getDepTime();

					SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy hh:mm");
					try {
						m1 = sdf.parse(date1).getTime();
						m2 = sdf.parse(date2).getTime();
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return (int) ((-1)*(m2 - m1));
				}
			});
			System.out.println("Flight has been Sorted by Departure Time Successfully");

			break;

		case '2':
			// List 1 //
			Collections.sort(flightsIn, new Comparator<Flight>() {
				long m1, m2;

				@Override
				public int compare(Flight o1, Flight o2) {
					String date1 = o1.getDate() + " " + o1.getArrTime();
					String date2 = o2.getDate() + " " + o2.getArrTime();

					SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy hh:mm");
					try {
						m1 = sdf.parse(date1).getTime();
						m2 = sdf.parse(date2).getTime();
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return (int) ((-1)*(m2 - m1));
				}
			});
			// List 2 //
			Collections.sort(flightsOut, new Comparator<Flight>() {
				long m1, m2;

				@Override
				public int compare(Flight o1, Flight o2) {
					String date1 = o1.getDate() + " " + o1.getArrTime();
					String date2 = o2.getDate() + " " + o2.getArrTime();

					SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy hh:mm");
					try {
						m1 = sdf.parse(date1).getTime();
						m2 = sdf.parse(date2).getTime();
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return(int) ((-1)*(m2 - m1));
				}
			});

			System.out.println("Flight has been Sorted by Arrival Time Successfully");
			break;

		default:
			System.out.println("Going back to main menu");
			break;
		}

	}

	private static void showFlights() {
		System.out.println("All Flights in To Ben-Gurion Air-Port, Tel Aviv Israel.");
		if (flightsIn.size() == 0)
			System.out.println("No Incoming Flights listed!");

		for (int i = 0; i < flightsIn.size(); i++) {
			System.out.println((i + 1) + ") " + flightsIn.get(i).toString());
		}

		System.out.println("\nAll Flights Out From Ben-Gurion Air-Port, Tel Aviv Israel:");
		if (flightsOut.size() == 0)
			System.out.println("No Outcoming Flights listed!");

		for (int i = 0; i < flightsOut.size(); i++) {
			System.out.println((i + 1) + ") " + flightsOut.get(i).toString());
		}
	}

	// HelpFull Methonds //
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
