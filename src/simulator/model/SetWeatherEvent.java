package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {

	private List<Pair<String, Weather>> ws;
	
	public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
		super(time);
		// TODO Auto-generated constructor stub
		try {
			if (ws == null) {
				throw new Exception("Pair weather-string cannot be null");
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		this.ws = ws;
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		try {
			for (Pair<String, Weather> w: ws) {
				if (map.getRoad(w.getFirst()) == null) {
					throw new Exception("Certain road does not exist in the list");
				}
				map.getRoad(w.getFirst()).setWeather(w.getSecond());
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

}
