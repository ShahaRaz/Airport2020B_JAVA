
public class FlightOut extends Flight implements Cloneable {

	public FlightOut(String brand, String arrAirPort, MyDate date, String depTime, String arrTime, String flightId,
			int terminal, int flag) {
		super(brand, date, depTime, arrTime, flightId, terminal, flag);
		setArriveAirPort(arrAirPort);
		setDepAirPort("Ben Gurion, TLV");

	}

	@Override
	public String toString() {
		return "Brand: " + brand + ", Arrival Air-Port: " + arriveAirPort + ", " + getDayInWeek() + ", Deptarture Date: " + getDate().toString()
				+ ", Departure Time: " + depTime + ", Arrival Time: " + arrTime + ", Flight Id:" + flightId
				+ ", Terminal: " + terminalNum;
	}

}
