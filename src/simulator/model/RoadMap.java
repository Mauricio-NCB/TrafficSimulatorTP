package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONArray;

public class RoadMap {
	
	private List<Junction> junctions;
	private List<Road> roads;
	private List<Vehicle> vehicles;
	private Map<String,Junction> mapStringJunction;
	private Map<String,Road> mapStringRoad;
	private Map<String,Vehicle> mapStringVehicle;
	
	RoadMap(){
		this.junctions = new ArrayList<Junction>();
		this.roads = new ArrayList<Road>();
		this.vehicles = new ArrayList<Vehicle>();
		this.mapStringJunction = new HashMap<String, Junction>();
		this.mapStringRoad = new HashMap<String, Road>();
		this.mapStringVehicle = new HashMap<String, Vehicle>();
	}
	
	void addJunction(Junction j){
		
		if(mapStringJunction.containsKey(j.getId())){
			throw new IllegalArgumentException("Id has been taken by another junction");
		}
		
		junctions.add(j);
		mapStringJunction.put(j.getId(), j);
	}
	
	void addRoad(Road r){
		
		if(mapStringRoad.containsKey(r.getId())){
			throw new IllegalArgumentException("Road is already on the map");
		}
		
		if(!mapStringJunction.containsValue(r.getSrc()) || !mapStringJunction.containsValue(r.getDest())) {
			throw new IllegalArgumentException("Junctions connected by r are not on the map");
		}
		
		roads.add(r);
		mapStringRoad.put(r.getId(), r);
	}
	
	void addVehicle(Vehicle v){

		if(mapStringVehicle.containsKey(v.getId())){   
			throw new IllegalArgumentException("Id has already be taken by other vehicle");
		}
			
		for (int i = 0; i < v.getItinerary().size() - 1; i++) {
			if (v.getItinerary().get(i).roadTo(v.getItinerary().get(i + 1)) == null) {
				throw new IllegalArgumentException("A junction defined by itinerary is not connected by a road");
			}
		}

		
		vehicles.add(v);
		mapStringVehicle.put(v.getId(), v);
	}

	public Junction getJunction(String id) {
		
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

	public List<Junction> getJunctions(){
		return Collections.unmodifiableList(junctions);
	}
	
	public List<Road> getRoads(){
		return Collections.unmodifiableList(roads);
	}
	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(vehicles);
	}
	
	void reset(){
		junctions.clear();
		roads.clear();
		vehicles.clear();
		mapStringJunction.clear();
		mapStringRoad.clear();
		mapStringVehicle.clear();
	}
	
	public JSONObject report() {
		JSONArray junctionArray = new JSONArray();
		JSONArray roadArray = new JSONArray();
		JSONArray vehicleArray = new JSONArray();
		
		for (Junction j: junctions) {
			junctionArray.put(j.report());
		}
		
		for (Road r: roads) {
			roadArray.put(r.report());
		}
		
		for (Vehicle v: vehicles) {
			vehicleArray.put(v.report());
		}
		
		JSONObject jo = new JSONObject();
		
		jo.put("junctions", junctionArray);
		jo.put("roads", roadArray);
		jo.put("vehicles", vehicleArray);
		
		return jo;
	}

}
