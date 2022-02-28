package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent {
	
	NewInterCityRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {

		super(time, id, srcJunc, destJunc, length, co2Limit,  maxSpeed, weather);
	}
/*
	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		Road r = new InterCityRoad(id, map.getJunction(srcJunc), map.getJunction(destJunc), maxSpeed, co2Limit, length, weather);
		map.addRoad(r);
	}
*/
	@Override
	Road createRoadObject() {
		// TODO Auto-generated method stub
		return new InterCityRoad(id, map.getJunction(srcJunc), map.getJunction(destJunc), maxSpeed, co2Limit, length, weather);
	}
	
}
