
public class FlightIn extends Flight implements Cloneable {
	private String depCity;

	public FlightIn(String brand, String depAirPort, MyDate date, String depTime, String arrTime, String flightId,
			int terminal, int flag) {
		super(brand, date, depTime, arrTime, flightId, terminal, flag);
		setDepAirPort(depAirPort);
		String[] airportSplit = depAirPort.split(",");
		setCity(airportSplit[0]);
		setCountry(airportSplit[1]);
		setArriveAirPort("Ben-Gurion, TLV");
		
	}

	@Override
	public String toString() {
		return "Brand: " + brand + ", Departure Air-Port: " + depAirPort + ", " + getDayInWeek() + ", Deptarture Date: "
				+ getDate().toString() + ", Departure Time: " + depTime + ", Arrival Time: " + arrTime + ", Flight Id:"
				+ flightId + ", Terminal: " + terminalNum;
	}

	public String getDepCity() {
		return depCity;
	}


}
