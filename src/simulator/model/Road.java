package simulator.model;

import java.util.List;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject{
	
	private int length;
	private Junction srcJunc;
	private Junction destJunc;
	private int maxSpeed;
	private int currentSpeedLimit;
	private int contLimit;
	private Weather weather;
	private int totalCont;
	private List<Vehicle> vehicles;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather){
		super(id);
		try{
			// TODO ?????
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	void advance(int time) {
		this.reduceTotalContamination();
		this.updateSpeedLimit();
		for(Vehicle v: vehicles){
			this.calculateVehicleSpeed(v);
			v.advance(time);
		}
			
	}

	@Override
	public JSONObject report() {
		return null;
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
		return vehicles;
	}

	public void setWeather(Weather weather) {
		try{
			if(weather==null){
				throw new Exception("Clima igual a nulo");
			}
			this.weather = weather;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void addContamination(int c){
		try{
			if(c<0){
				throw new Exception("Contaminacion negativa");
			}
			this.totalCont += c;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void enter(Vehicle v){
		try{
			if(v.getLocation()!=0 && v.getSpeed()!=0){
				throw new Exception("Error al meter carretera");
			}
			vehicles.add(v);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void exit(Vehicle v){
		vehicles.remove(v);
	}
	public abstract void reduceTotalContamination();
	
	public abstract void updateSpeedLimit();
	
	public abstract int calculateVehicleSpeed(Vehicle v);
	
	
	
}
