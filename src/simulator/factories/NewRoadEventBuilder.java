package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewRoadEvent;
import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event> {

	public NewRoadEventBuilder(String type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		int time = data.getInt("time");
		String id = data.getString("id");
		String srcJunc = data.getString("src");
		String destJunc = data.getString("destJunc");
		int length = data.getInt("length");
		int co2Limit = data.getInt("co2Limit");
		int maxSpeed = data.getInt("maxspeed");
		Weather weather = Weather.valueOf(data.getString("weather"));
		
		return new NewRoadEvent(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
	}
	
	abstract Event createTheRoad();

}
