package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.NewInterCityRoadEvent;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder {

	public NewCityRoadEventBuilder() {
		super("new_city_road");
		// TODO Auto-generated constructor stub
	}

	@Override
	Event createTheRoad() {
		// TODO Auto-generated method stub
		return new NewCityRoadEvent(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
	}
}
