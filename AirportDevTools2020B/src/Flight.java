import java.text.DecimalFormat;
import java.time.LocalDate;

public class Flight implements Cloneable{
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
	protected String country;
	protected String city;

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
	public String getCountry() {
		return this.country;
	}
	public String getCity() {
		return this.city;
	}

	public String getAirport() {
		if(this.arriveAirPort.contains("Gurion"))
			return this.depAirPort;
		else
			return this.arriveAirPort;
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

//	@Override
//	public String toString() {
//		return "Brand: " + brand;
//	}

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
	
	public void setCity(String city1) {
		this.city=city1;
	}
	public void setCountry(String country1)
	{
		this.country=country1;
	}
	
	
	public static String[] splitAirport(String airportStr) {
		String[] countrY1 = airportStr.split(",");
		String[] citY0 = countrY1[0].split(" ");
//		setCountry(split1[1]);
//		setCity(split2[0]);
		String[] returnMe= {citY0[0],countrY1[1]};
		
				
		return returnMe;
	}

	public void toStringServer() {
		StringBuffer sb = new StringBuffer();		
//		# 									  request.args.get('outformat'), "arrivals",
//		#                                     request.args.get('airline'), request.args.get('country'),
//		#                                     request.args.get('city'), request.args.get('airport'),
//		#                                     request.args.get('day1'), request.args.get('month1'),
//		#                                     request.args.get('year1'), request.args.get('day2'),
//		#                                     request.args.get('month2'), request.args.get('year2'),
//		#                                     request.args.get('sunday'), request.args.get('monday'),
//		#                                     request.args.get('tuesday'), request.args.get('wednesday'),
//		#                                     request.args.get('thursday'), request.args.get('friday'),
//		#                                     request.args.get('saturday')])
	}

}
