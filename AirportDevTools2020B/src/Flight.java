import java.text.DecimalFormat;

public class Flight {
	public static DecimalFormat df = new DecimalFormat("#.##");
	public static int Id = 1000;

	private MyDate date;
	private int terminalNum;
	private String flightId;
	private String depAirPort;
	private String arriveAirPort;
	private String brand;
	private String depTime; // by GMC +2 what ever
	private double price;

	// Flight In // --> The Difference between 2 Constructors is the Arrival-AirPort
	// against Dept. AirPort //

	public Flight(String flightId, String depAirPort, MyDate date, String depTime, int terminalNum, String brand) {
		setFlightId(flightId);
		setDepAirPort(depAirPort);
		setDepTime(depTime);
		setDate(date);
		setTerminalNum(terminalNum);
		setBrand(brand);
		this.arriveAirPort = "TLV Ben Gurion";
	}

	// Flights Out // --> The Difference between 2 Constructors is the
	// Arrival-AirPort against Dept. AirPort //
	public Flight(String flightId, MyDate date, String depTime, int terminalNum, String brand, String arriveAirPort) {
		setFlightId(flightId);
		setArriveAirPort(arriveAirPort);
		setDepTime(depTime);
		setDate(date);
		setTerminalNum(terminalNum);
		setBrand(brand);
		this.depAirPort = "TLV Ben Gurion";
	}

	public Flight() {
		flightId = "XX-XXXXXX";
		arriveAirPort = "KOKOMAN";
		depTime = "00:00";
		setPrice(0);
	}

	// Helpful Methods //
	public static double timeStrToDouble(String time) {
		String[] split = time.split(":");
		double finalTime = Double.parseDouble(split[0]) + (Double.parseDouble(split[1]) / 60);
		return finalTime;
	}

	// Getters //

	public double getPrice() {
		return price;
	}

	public static int getId() {
		return Id;
	}

	public MyDate getDate() {
		return date;
	}

	public int getTerminalNum() {
		return terminalNum;
	}

	public String getDepAirPort() {
		return depAirPort;
	}

	public String getBrand() {
		return brand;
	}

	public String getFlightId() {
		return flightId;
	}

	public String getArriveAirPort() {
		return arriveAirPort;
	}

	public String getDepTime() {
		return depTime;
	}

	// Setters //

	public static void setId(int id) {
		Id = id;
	}

	public void setDate(MyDate date) {
		this.date = date;
	}

	public void setTerminalNum(int terminalNum) {
		this.terminalNum = terminalNum;
	}

	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}

	public void setDepAirPort(String depAirPort) {
		this.depAirPort = depAirPort;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setPrice(double time) {
		if (time <= 0 || time >= 100)
			this.price = 99.99;
		else
			this.price = Math.floor((time * 700) / 3);
	}

	public void setArriveAirPort(String arriveAirPort) {
		this.arriveAirPort = arriveAirPort;
	}

	public void setDepTime(String depTime) {
		this.depTime = depTime;
	}

	@Override
	public String toString() {
		double depTimeInt = timeStrToDouble(depTime);

		return "Flight Id:" + flightId + ", Arrival A.P: " + arriveAirPort + ", Dept. Air.P: " + depAirPort
				+ ", Dept. Time: " + depTime + ", Estimated Time: " + df.format(Math.abs(depTimeInt))
				+ " Hours, Price: " + price + "$";

	}

	// know about all the flights
	// simple program
	// menu that
	// 1 . outgoin flight
	// incoming flights
	// view outgoin by sorted by time
	// ou go out
	// add freature - save to files
	// uyser can enter the details.
	// the details
}
