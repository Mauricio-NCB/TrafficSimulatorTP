package simulator.model;

public class InterCityRoad extends Road{

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed,
			int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void reduceTotalContamination() {
		switch(this.getWeather()){
		case SUNNY:
			super.totalCont=((int)((100.0-2)/100.0)*super.totalCont);
		case CLOUDY:
			super.totalCont=((int)((100.0-3)/100.0)*super.totalCont);
		case RAINY:
			super.totalCont=((int)((100.0-10)/100.0)*super.totalCont);
		case WINDY:
			super.totalCont=((int)((100.0-15)/100.0)*super.totalCont);
		case STORM:
			super.totalCont=((int)((100.0-20)/100.0)*super.totalCont);
		}
		
	}

	@Override
	public void updateSpeedLimit() {
		if(this.getTotalCO2()>this.getContLimit()){
			super.currentSpeedLimit = (int)(super.maxSpeed*0.5);
		}
		else{
			super.currentSpeedLimit = super.maxSpeed;
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
