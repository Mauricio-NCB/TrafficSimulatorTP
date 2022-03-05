package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewRoadEvent;
import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event> {

	int time;
	String id;
	String srcJunc;
	String destJunc;
	int length;
	int co2Limit;
	int maxSpeed;
	Weather weather;
	
	public NewRoadEventBuilder(String type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		time = data.getInt("time");
		id = data.getString("id");
		srcJunc = data.getString("src");
		destJunc = data.getString("dest");
		length = data.getInt("length");
		co2Limit = data.getInt("co2limit");
		maxSpeed = data.getInt("maxspeed");
		weather = Weather.valueOf(data.getString("weather").toUpperCase());
		
		return createTheRoad();
	}
	
	abstract Event createTheRoad();

}
