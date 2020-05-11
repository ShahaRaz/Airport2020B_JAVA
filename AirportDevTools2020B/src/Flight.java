import java.text.DecimalFormat;

public class Flight {
	public static DecimalFormat df = new DecimalFormat("#.##");
	public static int Id = 1000;

	private int flightId;
	private String arriveAirPort;
	private String depTime; // by GMC +2 what ever
	private String arrTime; //
	private double price;

	public Flight(String arriveAirPort, String depTime, String arrTime) {
		this.flightId = Id++;
		this.arriveAirPort = arriveAirPort;
		this.depTime = depTime;
		this.arrTime = arrTime;
		double time = (timeStrToDouble(arrTime) - timeStrToDouble(depTime));
		setPrice(Math.abs(time));
	}

	public Flight() {
		flightId = 0;
		arriveAirPort = "KOKOMAN";
		depTime = "00:00";
		arrTime = "00:00";
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

	public int getFlightId() {
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

	// Setters //

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

	public void setArrTime(String arrTime) {
		this.arrTime = arrTime;
	}

	@Override
	public String toString() {
		double depTimeInt = timeStrToDouble(depTime);
		double arrsTimeInt = timeStrToDouble(arrTime);

		return "Flight Id:" + flightId + ", Arrival A.P: " + arriveAirPort + ", Dept. Time: " + depTime
				+ ", Arrival Time: " + arrTime + ", Estimated Time: " + df.format(Math.abs(arrsTimeInt - depTimeInt))
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
