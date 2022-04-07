package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
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
		
		if (out != null) {
			
			PrintStream p = new PrintStream(out);
			
			p.println("{");
			p.println("  " + "\"states\": [");
			
			for (int i = 0; i < n - 1; i++) {
				sim.advance();
				p.println(sim.report().toString() + ",");
			}
			
			sim.advance();
			p.println(sim.report().toString() + "");
			p.println("]");
			p.println("}");
		}
		else {
			
			for (int i = 0; i < n; i++) {
				sim.advance();
			}
		}

	}
	
	public void addObserver(TrafficSimObserver o) {
		sim.addObserver(o);
	}
	
	public void removeObserver(TrafficSimObserver o) {
		sim.removeObserver(o);
	}
	
	public void addEvent (Event e) {
		sim.addEvent(e);
	}
}