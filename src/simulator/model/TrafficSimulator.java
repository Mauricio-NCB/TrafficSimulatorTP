package simulator.model;

import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator {
	private RoadMap roadMap;
	private List<Event> eventsList;
	private int time;
	
	public TrafficSimulator() {
		roadMap = new RoadMap();
		eventsList = new SortedArrayList<Event>();
		time = 0;
	}

	public void addEvent(Event e) {
		eventsList.add(e);
	}
	
	public void advance() {
		time++;
		
		while (eventsList.size() > 0 && eventsList.get(0).getTime() == time) {
			eventsList.remove(0).execute(roadMap);
		}
		
		for (Junction j: roadMap.getJunctions()) {
			j.advance(time);
		}
		
		for (Road r: roadMap.getRoads()) {
			r.advance(time);
		}
	}
	
	public void reset() {
		roadMap.reset();
		eventsList.clear();
		time = 0;
	}
	
	public JSONObject report() {
		
		JSONObject jo = new JSONObject();
		jo.put("time", time);
		jo.put("state", roadMap.report());
		
		return jo;
	}
}
