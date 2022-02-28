package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
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
		try{
			if(mapStringJunction.containsKey(j.getId())){
				throw new Exception("Existe cruce con mismo identificador.");
			}
			junctions.add(j);
			mapStringJunction.put(j._id, j);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void addRoad(Road r){
		try{
			if((mapStringRoad.containsKey(r.getId()))||(!mapStringJunction.containsValue(r.getSrc())&&(!mapStringJunction.containsValue(r.getDest())))){
				throw new Exception("Fallo al añadir carretera.");
			}
			roads.add(r);
			mapStringRoad.put(r._id, r);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void addVehicle(Vehicle v){
		try{
			if(mapStringVehicle.containsKey(v.getId()/*||(*/)){   ////TODO ITERATOR
				throw new Exception("Fallo al añadir vehiculo.");
			}
			vehicles.add(v);
			mapStringVehicle.put(v._id, v);
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void reset(){
		junctions.clear();
		roads.clear();
		vehicles.clear();
		mapStringJunction.clear();
		mapStringRoad.clear();
		mapStringVehicle.clear();
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
	
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("junctions", junctions);
		jo.put("road", roads);
		jo.put("vehicles", vehicles);
		return jo;
	}
}
