package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {

	private List<Pair<String, Weather>> ws;
	
	public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
		super(time);
		// TODO Auto-generated constructor stub
		if (ws == null) {
			throw new IllegalArgumentException("Pair weather-string cannot be null");
		}
		
		this.ws = ws;
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		for (Pair<String, Weather> w: ws) {
			if (map.getRoad(w.getFirst()) == null) {
				throw new IllegalArgumentException("Certain road does not exist in the list");
			}
			
				map.getRoad(w.getFirst()).setWeather(w.getSecond());
		}
	}

	public String toString() {
		String chain = "Change Weather: [(" + ws.get(0).getFirst() + "," + ws.get(0).getSecond() + ")" ;
		String s;
		
		for (int i = 1; i < ws.size(); i++) {
			s = ", (" + ws.get(i).getFirst() + "," + ws.get(i).getSecond() + ")";
			chain += s;
		}
		
		
		return chain + "]";
	}
	
}
