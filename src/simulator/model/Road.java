package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject{
	
	private int length;
	private Junction srcJunc;
	private Junction destJunc;
	protected int maxSpeed;
	protected int currentSpeedLimit;
	private int contLimit;
	protected Weather weather;
	protected int totalCont;
	private List<Vehicle> vehicles;
	
	// protected
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather){
		super(id);
		
		try {
			if(maxSpeed <= 0 || contLimit < 0 || length <= 0 || srcJunc == null || destJunc == null || weather == null){
				throw new Exception("Road data is not valid");
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		this.length = length;
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.maxSpeed = maxSpeed;
		currentSpeedLimit = 0;
		this.contLimit = contLimit;
		this.weather = weather;
		totalCont = 0;
		vehicles = new ArrayList<Vehicle>();
		
		srcJunc.addOutGoingRoad(this);
		destJunc.addIncommingRoad(this);
		
	}
	
	public abstract void reduceTotalContamination();
	
	public abstract void updateSpeedLimit();
	
	public abstract int calculateVehicleSpeed(Vehicle v);

	@Override
	void advance(int time) {
		this.reduceTotalContamination();
		this.updateSpeedLimit();
		
		for(Vehicle v: vehicles) {
			int newVSpeed = this.calculateVehicleSpeed(v);
			
			v.setSpeed(newVSpeed);
			v.advance(time);
		}	
		
		vehicles.sort((v1, v2) -> v2.getLocation() - v1.getLocation());
	}

	@Override
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		
		jo.put("id", super.getId());
		jo.put("speedlimit", this.currentSpeedLimit);
		jo.put("weather", this.weather);
		jo.put("co2", this.totalCont);
		
		JSONArray jVehicles = new JSONArray();
		
		for (Vehicle v: vehicles) {
			jVehicles.put(v.getId());
		}
		
		jo.put("vehicles", jVehicles);
		
		return jo;
	}
	

	public void enter(Vehicle v){
		try{
			if(v.getLocation() != 0 || v.getSpeed() != 0){
				throw new Exception("Error al meter carretera");
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		vehicles.add(v);
	}
	
	public void exit(Vehicle v){
		
		vehicles.remove(v);
	}

	public void setWeather(Weather weather) {
		try{
			if(weather == null){
				throw new Exception("Weather cannot be null");
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		this.weather = weather;
	}
	
	public void addContamination(int c){
		try{
			if(c < 0){
				throw new Exception("Contamination must be equal or bigger than zero");
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		this.totalCont += c;
	}
	
	public int getLength(){
		return length;
	}
	
	public int getContLimit(){
		return contLimit;
	}
	
	public int getMaxSpeed(){
		return maxSpeed;
	}
	
	public int getTotalCO2(){
		return totalCont;
	}
	
	public int getSpeedLimit(){
		return currentSpeedLimit;
	}
	
	public Junction getDest(){
		return destJunc;
	}
	
	public Junction getSrc(){
		return srcJunc;
	}
	
	public Weather getWeather(){
		return weather;
	}
	
	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(vehicles);
	}	
}
