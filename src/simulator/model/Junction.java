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
	private int indexGreenLight;
	private int lastLightStep;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor;
	private int yCoor;
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(id);
		// TODO Auto-generated constructor stub
		try {
			if (xCoor < 0 || yCoor < 0 || lsStrategy == null || dqStrategy == null) {
				throw new Exception("Datos incorrectos");
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		
		inRoadsList = new ArrayList<Road>();
		outRoadsMap = new HashMap<Junction, Road>();
		qsList = new ArrayList<List<Vehicle>>();
	}

	void addIncommingRoad(Road r) {
		inRoadsList.add(r);  
		
		List<Vehicle> q = new LinkedList<Vehicle>();
		qsList.add(q);
		//Exception
		
	}
	
	void addOutGoingRoad(Road r) {
		outRoadsMap.put(r.getDest(), r);
		//Exception si sale mal la verificiación
	}
	
	void enter(Vehicle v) {
		int i = 0;
		boolean found = false;
		
		while (i < inRoadsList.size() && !found) {
			if (inRoadsList.get(i).getId() == v.getRoad().getId()) { 
				found = true;	//Busca en que indice de nuestra lista de carreteras entrantes está la carretera de v 
			}
			else {
				i++;
			}
		}
		
		qsList.get(i).add(v);	// En el i-esimo puesto de la carretera entrante está la cola que queremos añadir
	}
	
	Road roadTo(Junction j) {
		if (outRoadsMap.containsKey(j)) {
			return outRoadsMap.get(j);
		}
		else {
			return null;
		}
	}
	
	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		List<Vehicle> dequeued = dqStrategy.dequeue(qsList.get(indexGreenLight));
		
		for (Vehicle v: dequeued) {
			v.moveToNextRoad();
			qsList.get(indexGreenLight).remove(v);
		}
		
		int nextGreen;
		nextGreen = lsStrategy.chooseNextGreen(inRoadsList, qsList, indexGreenLight, lastLightStep, time);
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
			jo.put("green", indexGreenLight); // none si todos están en rojo
		}
		JSONArray j_queues = new JSONArray();
		jo.put("queues", j_queues); // lista de colas de carreteras entrantes
		jo.put("road", 0); // identificador de las carreteras
		jo.put("vehicles", 0); // lista de vehiculos en el orden que aparecen en la cola
		
		return jo;
	}
	
	public int getX() {
		return xCoor;
	}
	
	public int getY() {
		return yCoor;
	}
}
