package simulator.model;

import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class RoadMap {
	
	private List<Junction> junctions;
	private List<Road> roads;
	private List<Vehicle> vehicles;
	private Map<String,Junction> mapStringJunction;
	private Map<String,Road> mapStringRoad;
	private Map<String,Vehicle> mapStringVehicle;
	
	protected RoadMap(){
		this.junctions = junctions;
		this.roads = roads;
		this.vehicles = vehicles;
		this.mapStringJunction = mapStringJunction;
		this.mapStringRoad = mapStringRoad;
		this.mapStringVehicle = mapStringVehicle;
	}
	
	public void addJunction(Junction j){
		
	}
	public void addRoad(Road r){
		
	}
	public void addVehicle(Vehicle v){
		
	}
	public void reset(){
		
	}
	/*public Vehicle getVehicle(String id){
		return;
	}
	public Road getRoad(String id){
		return;
	}
	public Junction getJunction(String id){
		return;
	}
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
	
	
	
	
}
