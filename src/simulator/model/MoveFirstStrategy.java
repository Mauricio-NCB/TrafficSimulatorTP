package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy {

	public MoveFirstStrategy() {
		
	}
	
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		// TODO Auto-generated method stub
		List<Vehicle> extractedList = new ArrayList<Vehicle>();
		extractedList.add(q.get(0));
		
		return extractedList;
	}

}
