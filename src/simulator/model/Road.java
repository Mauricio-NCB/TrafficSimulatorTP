package simulator.model;

import java.util.List;

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
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather){
		super(id);
		try {
			if(maxSpeed < 0 || contLimit < 0 || length < 0 || srcJunc == null || destJunc == null || weather == null){
				throw new Exception("Datos de la carretera invalidos");
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.maxSpeed = maxSpeed;
		this.contLimit = contLimit;
		this.length = length;
		this.weather = weather;
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
		JSONObject jo = new JSONObject();
		jo.put("id", super.getId());
		jo.put("speedlimit", this.currentSpeedLimit);
		jo.put("weather", this.weather);
		jo.put("co2", this.totalCont);
		jo.put("vehicles", this.vehicles);
		return jo;
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
