package simulator.model;

public abstract class NewRoadEvent extends Event {

	private String id;
	private String srcJunc;
	private String destJunc;
	private int length;
	private int co2Limit;
	private int maxSpeed;
	private Weather weather;
	
	NewRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
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
		Road r = createRoadObject();
		//Road r = new InterCityRoad(id, map.getJunction(srcJunc), map.getJunction(destJunc), maxSpeed, co2Limit, length, weather);
		map.addRoad(r);
	}

	abstract Road createRoadObject();
}
