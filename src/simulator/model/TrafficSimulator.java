package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>{
	private List<TrafficSimObserver> observersList;
	private RoadMap roadMap;
	private List<Event> eventsList;
	private int time;
	
	public TrafficSimulator() {
		observersList = new ArrayList<>();
		roadMap = new RoadMap();
		eventsList = new SortedArrayList<>();
		time = 0;
	}

	public void addEvent(Event e) {
		eventsList.add(e);
		
		for (TrafficSimObserver o: observersList) {
			o.onEventAdded(roadMap, eventsList, e, time);
		}
	}
	
	public void advance() {
		time++;

		for (TrafficSimObserver o: observersList) {
			o.onAdvanceStart(roadMap, eventsList, time);
		}
		
		try {
			
			while (!eventsList.isEmpty() && eventsList.get(0).getTime() == time) {
				eventsList.get(0).execute(roadMap);
				eventsList.remove(0);
			}
			
			for (Junction j: roadMap.getJunctions()) {
				j.advance(time);
			}
			
			for (Road r: roadMap.getRoads()) {
				r.advance(time);
			}
			
			for (TrafficSimObserver o: observersList ) {
				o.onAdvanceEnd(roadMap, eventsList, time);
			}
		}
		catch(Exception e) {
			for (TrafficSimObserver o: observersList) {
				o.onError(e.getMessage());
			}
			throw e;
		}
	}
	
	public void reset() {
		roadMap.reset();
		eventsList.clear();
		time = 0;
		
		for (TrafficSimObserver o: observersList) {
			o.onReset(roadMap, eventsList, time);
		}
	}
	
	public JSONObject report() {
		
		JSONObject jo = new JSONObject();
		jo.put("time", time);
		jo.put("state", roadMap.report());
		
		return jo;
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		// TODO Auto-generated method stub
		observersList.add(o);
		o.onRegister(roadMap, eventsList, time);
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		// TODO Auto-generated method stub
		observersList.remove(o);
	}
}
