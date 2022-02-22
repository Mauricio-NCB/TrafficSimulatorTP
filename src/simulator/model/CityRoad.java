package simulator.model;

public class CityRoad extends Road{

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed,
			int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void reduceTotalContamination() {
		if(this.getTotalCO2()>9&&((this.getWeather()==this.getWeather().STORM)||(this.getWeather()==this.getWeather().WINDY))){
			super.totalCont-=10;
		}
		else if(this.getTotalCO2()>1&&((this.getWeather()!=this.getWeather().STORM)||(this.getWeather()!=this.getWeather().WINDY))){
			super.totalCont-=2;
		}
		else{
			super.totalCont=0;
		}
	}

	@Override
	public void updateSpeedLimit() {
		super.maxSpeed = super.maxSpeed;
	}

	@Override
	public int calculateVehicleSpeed(Vehicle v) {
		v.setSpeed((int)(((11.0-v.getTotalCO2())/11.0)*this.getSpeedLimit()));
		return 0;
	}

}
