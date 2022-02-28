package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetContClassEvent extends Event {

	private List<Pair<String, Integer>> cs;
	
	SetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		// TODO Auto-generated constructor stub
		try {
			if (cs == null) {
				throw new Exception ("Pair contclass-string cannot be null");
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		this.cs = cs;
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		try {
			for (Pair<String, Integer> c: cs) {
				if (map.getVehicle(c.getFirst()) == null) {
					throw new Exception ("Certain vehicle does not exist in the list");
				}
				map.getVehicle(c.getFirst()).setContClass(c.getSecond());
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
