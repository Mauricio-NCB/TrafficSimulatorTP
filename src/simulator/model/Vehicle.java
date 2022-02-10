package simulator.model;

import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {

	private int location;
	private int speed;
	private int maxSpeed;
	private int contClass;
	private List<Junction> itinerary;
	private VehicleStatus status;
	private int totalCO2;
	private Road road;
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		try {
			if (maxSpeed < 0 || contClass < 0 || contClass > 10 || itinerary.size() < 2) {
				throw new Exception("Datos del vehiculo son invalidos");
			}
			this.maxSpeed = maxSpeed;
			this.contClass = contClass;
			this.itinerary = itinerary;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getLocation() {
		return location;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getMaxSpeed() {
		return maxSpeed;
	}
	
	public int getContClass() {
		return contClass;
	}
	
	public VehicleStatus getStatus() {
		return status;
	}
	
	public int getTotalCO2() {
		return totalCO2;
	}
	
	public List<Junction> getItinerary() {
		return itinerary;
	}
	
	public Road getRoad() {
		return road;
	}
}
