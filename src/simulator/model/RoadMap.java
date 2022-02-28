package simulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.json.JSONObject;

public class RoadMap {
	
	private List<Junction> junctions;
	private List<Road> roads;
	private List<Vehicle> vehicles;
	private Map<String,Junction> mapStringJunction;
	private Map<String,Road> mapStringRoad;
	private Map<String,Vehicle> mapStringVehicle;
	
	protected RoadMap(){
		this.junctions = new ArrayList<Junction>();
		this.roads = new ArrayList<Road>();
		this.vehicles = new ArrayList<Vehicle>();
		this.mapStringJunction = new HashMap<String, Junction>();
		this.mapStringRoad = new HashMap<String, Road>();
		this.mapStringVehicle = new HashMap<String, Vehicle>();
	}
	
	public void addJunction(Junction j){
		
	}
	public void addRoad(Road r){
		
	}
	public void addVehicle(Vehicle v){
		
	}
	public void reset(){
		
	}
	/*
	
	public List<Junction> getJunctions(){
		return;
	}
	public List<Road> getRoads(){
		return;
	}
	public List<Vehicle> getVehicles(){
		return;
	}
	public JSONObject report() {
		
	}*/
	
	public Junction getJunction(String id){
		
		if (mapStringJunction.containsKey(id)) {
			return mapStringJunction.get(id);
		}
		else {
			return null;
		}
	}
	
	public Road getRoad(String id){
		if (mapStringRoad.containsKey(id)) {
			return mapStringRoad.get(id);
		}
		else {
			return null;
		}
	}
	
	public Vehicle getVehicle(String id){
		if (mapStringVehicle.containsKey(id)) {
			return mapStringVehicle.get(id);
		}
		else {
			return null;
		}
	}
}
