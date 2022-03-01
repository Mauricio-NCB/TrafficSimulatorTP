package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {

	public SetContClassEventBuilder() {
		super("set_weather");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		int time = data.getInt("time");
		
		List<Pair<String, Integer>> pair_vc = new ArrayList<Pair<String, Integer>>();
		JSONArray jsonInfo = new JSONArray(data.has("info"));
		
		for (int i = 0; i < jsonInfo.length(); i++) {
			String vehicleId = jsonInfo.getJSONObject(i).getString("vehicle");
			Integer contClass = jsonInfo.getJSONObject(i).getInt("class");
			pair_vc.add(new Pair<String, Integer>(vehicleId, contClass));
		}
		
		return new SetContClassEvent(time, pair_vc);
	}

}
