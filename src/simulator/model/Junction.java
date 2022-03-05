package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {

	protected List<Road> inRoadsList;
	private Map<Junction, Road> outRoadsMap;
	private List<List<Vehicle>> qsList;
	private Map<Road, List<Vehicle>> rqMap;
	private int indexGreenLight;
	private int lastLightStep;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor;
	private int yCoor;
	
	// protected
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(id);
		// TODO Auto-generated constructor stub
		if (lsStrategy == null) {
			throw new IllegalArgumentException("LightSwitchingStrategy cannot be null");
		}
		if (xCoor < 0 || yCoor < 0 || dqStrategy == null) {
			throw new IllegalArgumentException("Datos incorrectos");
		}
		
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		
		inRoadsList = new ArrayList<>();
		outRoadsMap = new HashMap<>();
		qsList = new ArrayList<>();
		rqMap = new HashMap<>();
		indexGreenLight = -1;
		lastLightStep = 0;
	}

	void addIncommingRoad(Road r) {
		
		if (!r.getDest().equals(this)) {
			throw new IllegalArgumentException("This is not an incomming road");
		}
		
		inRoadsList.add(r);  
		
		List<Vehicle> q = new LinkedList<Vehicle>();
		qsList.add(q);
		rqMap.put(r, q);
		
	}
	
	void addOutGoingRoad(Road r) {
		Junction j = r.getDest();
		
		if (outRoadsMap.containsKey(j) || !r.getSrc().equals(this)) {
			throw new IllegalArgumentException ("There´s another road going to junction or road is not an outgoing road");
		}
		
		outRoadsMap.put(j, r);
	}
	
	void enter(Vehicle v) {
		Road r = v.getRoad();
		
		rqMap.get(r).add(v);
	}
	
	Road roadTo(Junction j) {
		
		return outRoadsMap.get(j);
	}
	
	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		if (indexGreenLight != 1 && qsList.size() > 0) {
		
			List<Vehicle> dequeued = dqStrategy.dequeue(qsList.get(indexGreenLight));
		
			for (Vehicle v: dequeued) {
				v.moveToNextRoad();
				qsList.get(indexGreenLight).remove(v);
			}
		}
		
		int nextGreen = lsStrategy.chooseNextGreen(inRoadsList, qsList, indexGreenLight, lastLightStep, time);
		
		if (nextGreen != indexGreenLight) {
			indexGreenLight = nextGreen;
			lastLightStep = time;
		}
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		
		
		JSONObject jo = new JSONObject();
		jo.put("id", super.getId());
		
		if (indexGreenLight == -1) {
			jo.put("green", "none");
		}
		else {
			jo.put("green", inRoadsList.get(indexGreenLight).getId()); 
		}
		
		JSONArray JSONqueues = new JSONArray();
		
		for (Road r: inRoadsList) {
			
			JSONArray jVehicles = new JSONArray();
			for (Vehicle v: rqMap.get(r)) {
				jVehicles.put(v.getId());
			}
			
			JSONObject jQueue = new JSONObject();
			
			jQueue.put("road", r.getId());
			jQueue.put("vehicles", jVehicles);
			
			JSONqueues.put(jQueue);
		}
		
		jo.put("queues", JSONqueues);
		
		return jo;
	}
	
	public int getX() {
		return xCoor;
	}
	
	public int getY() {
		return yCoor;
	}
}
