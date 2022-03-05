package simulator.model;

public class CityRoad extends Road{

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed,
			int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void reduceTotalContamination() {
		int x = 2;
		
		if (this.getWeather() == Weather.STORM || this.getWeather() == Weather.WINDY) {
			x = 10;
		}
		
		if (this.getTotalCO2() - x < 0) {
			this.totalCont = 0; 
		}
		else {
			this.totalCont = this.getTotalCO2() - x;
		}
	}

	@Override
	public void updateSpeedLimit() {
		this.currentSpeedLimit = this.getMaxSpeed();
	}

	@Override
	public int calculateVehicleSpeed(Vehicle v) {
		
		return (int)(((11.0 - v.getContClass()) / 11.0) * this.getSpeedLimit());
	}

}
