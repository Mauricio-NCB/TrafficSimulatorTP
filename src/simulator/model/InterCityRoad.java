package simulator.model;

public class InterCityRoad extends Road {

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed,
			int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void reduceTotalContamination() {
		int x;
		
		switch(this.getWeather()) {
		
		case SUNNY:
			x = 2;
			break;
		case CLOUDY:
			x = 3;
			break;
		case RAINY:
			x = 10;
			break;
		case WINDY:
			x = 15;
			break;
		case STORM:
			x = 20;
			break;
		default: 
			x = 0;
		}
		
		super.totalCont = (int)(((100.0 - x) / 100.0) * this.getTotalCO2());
	}

	@Override
	public void updateSpeedLimit() {
		if(this.getTotalCO2() > this.getContLimit()){
			
			currentSpeedLimit = ((int)(this.getMaxSpeed() * 0.5));
		}
		else{
			
			currentSpeedLimit = this.getMaxSpeed();
		}
		
	}

	@Override
	public int calculateVehicleSpeed(Vehicle v) {
		if(this.getWeather() == Weather.STORM){
			
			return ((int)(getSpeedLimit() * 0.8));
		}
		else{
			
			return getSpeedLimit();
		}
	}
}
