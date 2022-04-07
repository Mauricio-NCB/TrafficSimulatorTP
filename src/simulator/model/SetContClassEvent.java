package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetContClassEvent extends Event {

	private List<Pair<String, Integer>> cs;
	
	public SetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		// TODO Auto-generated constructor stub
		if (cs == null) {
			throw new IllegalArgumentException ("Pair contclass-string cannot be null");
		}
		
		this.cs = cs;
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		for (Pair<String, Integer> c: cs) {
			if (map.getVehicle(c.getFirst()) == null) {
				throw new IllegalArgumentException ("Certain vehicle does not exist in the list");
			}
			else {
				map.getVehicle(c.getFirst()).setContClass(c.getSecond());
			}
		}
	}

	public String toString() {
		
		String chain = "Change CO2 Class: [(" + cs.get(0).getFirst() + "," + cs.get(0).getSecond() + ")" ;
		String s;
		
		for (int i = 1; i < cs.size(); i++) {
			s = ", (" + cs.get(i).getFirst() + "," + cs.get(i).getSecond() + ")";
			chain += s;
		}
		
		
		return chain + "]";
	}
	
}
