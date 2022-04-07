package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {

	private String id; 
	private int maxSpeed;
	private int contClass;
	private List<String> itinerary;
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
		// TODO Auto-generated constructor stub
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = itinerary;
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		List<Junction> junctions = new ArrayList<Junction>();
		
		for (String keyItinerary: itinerary) {
			junctions.add(map.getJunction(keyItinerary));
		}
		
		Vehicle v = new Vehicle(id, maxSpeed, contClass, junctions);
		map.addVehicle(v);
		v.moveToNextRoad();
	}

	public String toString() {
		return "New Vehicle " + "'" + id + "'";
	}
	
}
