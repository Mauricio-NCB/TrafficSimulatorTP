package simulator.model;

import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {

	private int distance;
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
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = itinerary;
	}

	void moveToNextRoad() {
		try {
			if (this.status == VehicleStatus.ARRIVED || this.status == VehicleStatus.TRAVELING) {
				throw new Exception("El estado del vehiculo no permite el cambio a otra carretera");
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		speed = 0;
		location = 0;
		road.enter(this);
		// Metodo de junction para encontrar la siguiente carretera 
	}
	
	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		if (this.status == VehicleStatus.TRAVELING) {
			int prevLocation = location;
			location += speed;
			
			int contProduced = contClass * (location - prevLocation);
			totalCO2 += contProduced;
			road.addContamination(contProduced);
			
			if (location >= road.getLength()) {
				//llamada al metodo de junction para esperar en la cola
				
			}
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
		jo.put("road", this.road);
		jo.put("location", this.location);
		
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
		try {
			if (s < 0) {
				throw new Exception("Speed must be negative");
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		speed = s;
	}
	
	void setContClass(int c) {
		try {
			if (c > 10 || c < 0) {
				throw new Exception("Contamination class must be beetween 0 and 10 (both inclusive)");
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		contClass = c;
	}
}
