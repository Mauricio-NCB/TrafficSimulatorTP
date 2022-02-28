package simulator.model;

public abstract class NewRoadEvent extends Event {

	String id;
	String srcJunc;
	String destJunc;
	int length;
	int co2Limit;
	int maxSpeed;
	Weather weather;
	
	public NewRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		// TODO Auto-generated constructor stub
		this.id = id;
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.length = length;
		this.co2Limit = co2Limit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		map.addRoad(createRoadObject(map));
	}

	abstract Road createRoadObject(RoadMap map);
}
