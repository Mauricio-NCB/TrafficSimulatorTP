package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy {

	public MoveAllStrategy() {
		
	}

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		// TODO Auto-generated method stub
		List<Vehicle> extractedList = new ArrayList<Vehicle>();
		
		for (Vehicle v: q) {
			extractedList.add(v);
		}
		
		return extractedList;
	}
}
