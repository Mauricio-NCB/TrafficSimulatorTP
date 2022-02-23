package simulator.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject {

	private List<Road> inRoadsList;
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
	}

	void addIncommingRoad(Road r) {
		inRoadsList.add(r);  
		
		List<Road> q = new LinkedList<Road>();
		//q.add(r);
		
	}
	
	void addOutGoingRoad(Road r) {
		
	}
	
	void enter(Vehicle v) {
		//qsList.get(v.getRoad().).add(v);
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
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		
		
		JSONObject jo = new JSONObject();
		jo.put("id", super.getId());
		jo.put("green", indexGreenLight); // none si todos están en rojo
		jo.put("queues", 0); // lista de colas de carreteras entrantes
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
