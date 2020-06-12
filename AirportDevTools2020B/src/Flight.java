import java.text.DecimalFormat;

public class Flight {
	public static DecimalFormat df = new DecimalFormat("#.##");
	public static int Id = 1000;

	protected MyDate date;
	protected int terminalNum;
	protected String flightId;
	protected String depAirPort;
	protected String arriveAirPort;
	protected String brand;
	protected String depTime;
	protected String arrTime;
	protected boolean flag;

	// -> new -> Incoming Flight Constructor //
	public Flight(String brand, MyDate date, String depTime, String arrTime, String flightId, int terminal,
			boolean flag) {

		setBrand(brand);
		setDate(date);
		setDepTime(depTime);
		setArrTime(arrTime);
		setFlightId(flightId);
		setTerminalNum(terminal);
	}

	// Helpful Methods //
	public static double timeStrToDouble(String time) {
		String[] split = time.split(":");
		double finalTime = Double.parseDouble(split[0]) + (Double.parseDouble(split[1]) / 60);
		return finalTime;
	}

	// Getters //

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

	public boolean isFlag() {
		return flag;
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
		if (flightId.contains("LY"))
			this.flightId = flightId;
		else
			this.flightId = "LY" + flightId;
	}

	public void setDepAirPort(String depAirPort) {
		this.depAirPort = depAirPort;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setArriveAirPort(String arriveAirPort) {
		this.arriveAirPort = arriveAirPort;
	}

	public void setDepTime(String depTime) {
		this.depTime = depTime;
	}

	@Override
	public String toString() {
		return "Brand: " + brand;
	}

	public String getArrTime() {
		return arrTime;
	}

	public void setArrTime(String arrTime) {
		this.arrTime = arrTime;
	}

}
