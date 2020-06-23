
public class FlightIn extends Flight {
	private String depCity;


	public FlightIn(String brand, String depAirPort, MyDate date, String depTime, String arrTime, String flightId,
		int terminal, int flag) {
		super(brand, date, depTime, arrTime,flightId, terminal, flag);
		setDepAirPort(depAirPort);
		setDepCity(depCity);
		setArriveAirPort("Ben-Gurion, TLV");
	}

	@Override
	public String toString() {
		return "Brand: " + brand + ", Departure Air-Port: " + depAirPort + ", Deptarture Date: " + getDate().toString()
				+ ", Departure Time: " + depTime + ", Arrival Time: " + arrTime +   ", Flight Id:" + flightId + ", Terminal: " + terminalNum;
	}

	public String getDepCity() {
		return depCity;
	}

	public void setDepCity(String depCity) {
		this.depCity = depCity;
	}
}
