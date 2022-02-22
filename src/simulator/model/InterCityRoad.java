package simulator.model;

public class InterCityRoad extends Road{

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed,
			int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void reduceTotalContamination() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSpeedLimit() {
		if(this.getTotalCO2()>this.getContLimit()){
			
		}
		else{
			
		}
		
	}

	@Override
	public int calculateVehicleSpeed(Vehicle v) {
		if(this.getWeather()==this.getWeather().STORM){
			v.setSpeed((int)(this.getSpeedLimit()*0.8));
		}
		else{
			v.setSpeed((int)(this.getSpeedLimit()));
		}
		return 0;
	}
}
