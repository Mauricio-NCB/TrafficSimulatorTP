package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {

	//private or protected
	
	private int distance;
	private int location;
	private int speed;
	private int maxSpeed;
	private int contClass;
	private List<Junction> itinerary;
	private VehicleStatus status;
	private int totalCO2;
	private Road road;
	private int currentJunc;
	
	//protected class
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {		
		super(id);
		
		if (maxSpeed < 0 || contClass < 0 || contClass > 10 || itinerary.size() < 2) {
			throw new IllegalArgumentException("Vehicle data is not valid");
		}
		
		distance = 0;
		location = 0;
		speed = 0;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		this.status = VehicleStatus.PENDING;
		road = null;
		currentJunc = 0;
		
		
	}

	//protected methods
	
	void moveToNextRoad() {
		
		if (this.status == VehicleStatus.ARRIVED || this.status == VehicleStatus.TRAVELING) {
			throw new IllegalArgumentException("The vehicle status does not let move to next road");
		}
		
		if (road != null) {
			speed = 0;
			location = 0;
			road.exit(this);
		}
		
		if (currentJunc < itinerary.size() - 1) {
			status = VehicleStatus.TRAVELING;
			
			Junction srcJunc = itinerary.get(currentJunc);
			Junction destJunc = itinerary.get(currentJunc + 1);
			
			road = srcJunc.roadTo(destJunc);
			road.enter(this);
		}
		else {
			status = VehicleStatus.ARRIVED;
			road = null;
		}
	}
	
	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		if (this.status != VehicleStatus.TRAVELING) {
			speed = 0;
			return;
		}
		
		int prevLocation = location;
		location += speed;
		
		if (location > road.getLength()) {
			location = road.getLength();
		}
		distance += (location - prevLocation);
		
		int contProduced = contClass * (location - prevLocation);
		totalCO2 += contProduced;
		road.addContamination(contProduced);
		
		if (location >= road.getLength()) {
			status = VehicleStatus.WAITING;
			speed = 0;
			currentJunc++;
			road.getDest().enter(this);
		}

	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		JSONObject jo = new JSONObject();
		jo.put("id", super.getId());
		jo.put("speed", this.speed);
		jo.put("distance", this.distance);
		jo.put("co2", this.totalCO2);
		jo.put("class", this.contClass);
		jo.put("status", this.status);
		
		if (status != VehicleStatus.PENDING && status != VehicleStatus.ARRIVED) {
			jo.put("road", this.road);
			jo.put("location", this.location);
		}
		
		return jo;
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
	
	void setSpeed(int s) {
		if (s < 0) {
			throw new IllegalArgumentException("Speed must be equal or bigger than zero");
		}

		speed = s;
	}
	
	void setContClass(int c) {
		if (c > 10 || c < 0) {
			throw new IllegalArgumentException("Contamination class must be beetween 0 and 10 (both inclusive)");
		}

		contClass = c;
	}
}
