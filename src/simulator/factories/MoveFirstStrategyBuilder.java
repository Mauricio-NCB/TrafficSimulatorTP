package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveFirstStrategy;

public class MoveFirstStrategyBuilder extends Builder<DequeuingStrategy>{

	public MoveFirstStrategyBuilder() {
		super("move_first_dqs");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected DequeuingStrategy createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		return new MoveFirstStrategy();
	}

}
