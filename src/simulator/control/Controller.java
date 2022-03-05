package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {
	
	private TrafficSimulator sim;
	private Factory<Event> eventsFactory;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		
		if (sim == null || eventsFactory == null) {
			throw new IllegalArgumentException ("Simulator and events from factory cannot be null");
		}
		
		this.sim = sim;
		this.eventsFactory = eventsFactory;
	}
	
	public void reset() {
		sim.reset();
	}
	
	public void loadEvents(InputStream in) {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		
		if (!jo.has("events")) {
			throw new IllegalArgumentException("Error, JSON format must be like \"events\": [e_1, . . ., e_n]");
		}
		
		JSONArray jArrayEvents = jo.getJSONArray("events");
		
		for (int i = 0; i < jArrayEvents.length(); i++)  {
			Event e = eventsFactory.createInstance(jArrayEvents.getJSONObject(i));
			sim.addEvent(e);
		}
	}
	
	public void run(int n, OutputStream out) {
		PrintStream p = new PrintStream(out);
		JSONObject jo = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		for (int i = 0; i < n; i++) {
			sim.advance();
			jArray.put(sim.report());
		}
		
		jo.put("states", jArray);
		p.println(jo.toString());
	}
}
