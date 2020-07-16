
public class FlightOut extends Flight implements Cloneable {

	public FlightOut(String brand, String arrAirPort, MyDate date, String depTime, String arrTime, String flightId,
			int terminal, boolean isIncomingFlight) {
		super(brand, date, depTime, arrTime, flightId, terminal, isIncomingFlight);
		setArriveAirPort(arrAirPort);
		String[] airportSplit = splitAirport(arrAirPort);
		setCity(airportSplit[0]);
		setCountry(airportSplit[1]);
		setDepAirPort("Ben Gurion, TLV");

	}

	@Override
	public String toString() {
		return "Brand: " + brand + ", Arrival Air-Port: " + arriveAirPort + ", " + getDayInWeek() + ", Deptarture Date: " + getDate().toString()
				+ ", Departure Time: " + depTime + ", Arrival Time: " + arrTime + ", Flight Id:" + flightId
				+ ", Terminal: " + terminalNum;
	
	}

//	public String toStrBoard() {
//		return "airline=" + this.brand + " country=" + country + " city=" + city +
//				"airport=" +arriveAirPort; 
//		
//	}
	
//	# 									  request.args.get('outformat'), "arrivals",
//	#                                     request.args.get('airline'), request.args.get('country'),
//	#                                     request.args.get('city'), request.args.get('airport'),
//	#                                     request.args.get('day1'), request.args.get('month1'),
//	#                                     request.args.get('year1'), request.args.get('day2'),
//	#                                     request.args.get('month2'), request.args.get('year2'),
//	#                                     request.args.get('sunday'), request.args.get('monday'),
//	#                                     request.args.get('tuesday'), request.args.get('wednesday'),
//	#                                     request.args.get('thursday'), request.args.get('friday'),
//	#                                     request.args.get('saturday')])
}
