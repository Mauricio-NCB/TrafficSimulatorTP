package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event>{

	public SetWeatherEventBuilder() {
		super("set_weather");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		int time = data.getInt("time");
		
		List<Pair<String, Weather>> pair_rw = new ArrayList<Pair<String, Weather>>();
		JSONArray jsonInfo = data.getJSONArray("info");
		
		for (int i = 0; i < jsonInfo.length(); i++) {
			String roadId = jsonInfo.getJSONObject(i).getString("road");
			Weather weather = Weather.valueOf(jsonInfo.getJSONObject(i).getString("weather"));
			pair_rw.add(new Pair<String, Weather>(roadId, weather));
		}
		
		return new SetWeatherEvent(time, pair_rw);
	}

}
