package simulator.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public abstract class Road extends SimulatedObject{
	
	private int length;
	private Junction srcJunc;
	private Junction destJunc;
	protected int maxSpeed;
	protected int currentSpeedLimit;
	private int contLimit;
	protected Weather weather;
	protected int totalCont;
	private ComparatorVehicles compVLocation;	
	private List<Vehicle> vehicles;
	
	// protected
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather){
		super(id);
		
		if(maxSpeed <= 0) throw new IllegalArgumentException("Max speed limit must be bigger than zero");
		if (contLimit < 0) throw new IllegalArgumentException("Contamination limit must be equal or bigger than zero");
		if (length <= 0) throw new IllegalArgumentException("Road´s length must be bigger than zero");
		if (srcJunc == null) throw new IllegalArgumentException("Source junction cannot be null");
		if (destJunc == null) throw new IllegalArgumentException("Destination junction cannot be null"); 
		if (weather == null) throw new IllegalArgumentException("Weather cannot be null");	
		
		this.length = length;
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.maxSpeed = maxSpeed;
		currentSpeedLimit = maxSpeed;
		this.contLimit = contLimit;
		this.weather = weather;
		totalCont = 0;
		compVLocation = new ComparatorVehicles();
		vehicles = new SortedArrayList<Vehicle>(compVLocation);
		
		srcJunc.addOutGoingRoad(this);
		destJunc.addIncommingRoad(this);
		
	}
	
	public static class ComparatorVehicles implements Comparator<Vehicle> {

		@Override
		public int compare(Vehicle o1, Vehicle o2) {
			// TODO Auto-generated method stub
			return o2.getLocation() - o1.getLocation();
		}
		
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
		
		vehicles.sort(compVLocation);
	}

	@Override
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		
		jo.put("id", super.getId());
		jo.put("speedlimit", this.getSpeedLimit());
		jo.put("weather", this.weather.name());
		jo.put("co2", this.totalCont);
		
		JSONArray jVehicles = new JSONArray();
		
		for (Vehicle v: vehicles) {
			jVehicles.put(v.getId());
		}
		
		jo.put("vehicles", jVehicles);
		
		return jo;
	}
	

	public void enter(Vehicle v){

		if(v.getLocation() != 0 || v.getSpeed() != 0){
			throw new IllegalArgumentException("Error al meter carretera");
		}
		
		vehicles.add(v);
	}
	
	public void exit(Vehicle v){
		
		vehicles.remove(v);
	}

	public void setWeather(Weather weather) {

		if(weather == null){
			throw new IllegalArgumentException("Weather cannot be null");
		}
		
		this.weather = weather;
	}
	
	public void addContamination(int c){

		if(c < 0){
			throw new IllegalArgumentException("Contamination must be equal or bigger than zero");
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
