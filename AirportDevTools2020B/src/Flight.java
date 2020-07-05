import java.text.DecimalFormat;
import java.time.LocalDate;

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
	protected int flag;

	// -> new -> Incoming Flight Constructor //
	public Flight(String brand, MyDate date, String depTime, String arrTime, String flightId, int terminal, int flag) {
		setBrand(brand);
		setDate(date);
		setDepTime(depTime);
		setArrTime(arrTime);
		setFlightId(flightId);
		setTerminalNum(terminal);
		setFlag(flag);
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

	public String getArrTime() {
		return arrTime;
	}

	public int getFlag() {
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

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public void setArriveAirPort(String arriveAirPort) {
		this.arriveAirPort = arriveAirPort;
	}

	public void setDepTime(String depTime) {
		this.depTime = setAnyTime(depTime);
	}

	public void setArrTime(String arrTime) {
		this.arrTime = setAnyTime(arrTime);
	}

	@Override
	public String toString() {
		return "Brand: " + brand;
	}

	// Helpful Methods //
	public static double timeStrToDouble(String time) {
		String[] split = time.split(":");
		double finalTime = Double.parseDouble(split[0]) + (Double.parseDouble(split[1]) / 60);
		return finalTime;
	}

	public String setAnyTime(String time) {
		if (time.length() % 5 != 0) {
			String[] split = time.split(":");
			if (Integer.parseInt(split[0]) < 10 && Integer.parseInt(split[1]) < 10)
				return "0" + split[0] + ":0" + split[1];
			else if (Integer.parseInt(split[0]) < 10 && Integer.parseInt(split[1]) > 10)
				return "0" + split[0] + ":" + split[1];
			else if (Integer.parseInt(split[0]) > 10 && Integer.parseInt(split[1]) < 10)
				return split[0] + ":0" + split[1];
		} else
			return time;
		return null;
	}

	public String getDayInWeek() {
		LocalDate me = LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
		return me.getDayOfWeek().toString();
	}

}
