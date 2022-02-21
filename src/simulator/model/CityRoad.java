package simulator.model;

public class CityRoad extends Road{

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed,
			int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void reduceTotalContamination() {
		if(this.getTotalCO2()>9&&((this.getWeather()!=this.getWeather().STORM)||(this.getWeather()!=this.getWeather().WINDY))){
			// TODO
		}
	}

	@Override
	public void updateSpeedLimit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int calculateVehicleSpeed(Vehicle v) {
		v.setSpeed((int)(((11.0-v.getTotalCO2())/11.0)*this.getSpeedLimit()));
		return 0;
	}

}
