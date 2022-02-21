package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {

	private int timeSlot; // numero de ticks que está el semaforo en verde
	
	public MostCrowdedStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, 
			int lastSwitchingTime, int currTime) {
		// TODO Auto-generated method stub
		
		if (roads.size() == 0) {
			return -1;
		}
		if (currGreen == -1) {
			
			int indexMaxQ = 0;
			
			for (int i = 0; i < qs.size(); i++) {
				if (qs.get(i).size() > indexMaxQ) {
					indexMaxQ = qs.get(i).size();
				}
			}
			
			return indexMaxQ;
		}
		if (currTime - lastSwitchingTime < timeSlot) {
			return currGreen;
		}
		
		int indexMaxQ = ((currGreen + 1) % qs.size());
		
		for (int i = ((currGreen + 1) % qs.size()); i < qs.size(); i++) {
			if (qs.get(i).size() > indexMaxQ) {
				indexMaxQ = qs.get(i).size();
			}
		}
		for (int i = 0; i < ((currGreen + 1) % qs.size()); i++) {
			if (qs.get(i).size() > indexMaxQ) {
				indexMaxQ = qs.get(i).size();
			}
		}
		
		return indexMaxQ;
	}

}
