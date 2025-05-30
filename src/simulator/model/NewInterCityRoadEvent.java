package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent {
	
	public NewInterCityRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {

		super(time, id, srcJunc, destJunc, length, co2Limit,  maxSpeed, weather);
	}

	@Override
	Road createRoadObject(RoadMap map) {
		// TODO Auto-generated method stub
		return new InterCityRoad(id, map.getJunction(srcJunc), map.getJunction(destJunc), maxSpeed, co2Limit, length, weather);
	}

	public String toString() {
		return "New Intercity Road " + "'" + id + "'";
	}
	
}
