package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {

	private Factory<LightSwitchingStrategy> lssFactory;
	private Factory<DequeuingStrategy> dqsFactory;
	
	NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super("new_junction");
		// TODO Auto-generated constructor stub
		
		this.lssFactory = lssFactory;
		this.dqsFactory = dqsFactory;
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		int time = data.getInt("time");
		String id = data.getString("id");
		int xCoor = data.getJSONArray("coor").getInt(0);
		int yCoor = data.getJSONArray("coor").getInt(1);
		
		LightSwitchingStrategy lsStrategy = lssFactory.createInstance(data.getJSONObject("ls_strategy"));
		DequeuingStrategy dqStrategy = dqsFactory.createInstance(data.getJSONObject("dq_strategy"));
		
		return new NewJunctionEvent(time, id, lsStrategy, dqStrategy, xCoor, yCoor);
	}

}
