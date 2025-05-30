package simulator.model;

public class NewJunctionEvent extends Event {

	private String id;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor;
	private int yCoor;
	
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(time);
		// TODO Auto-generated constructor stub
		this.id = id;
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
		
		Junction j = new Junction(id, lsStrategy, dqStrategy, xCoor, yCoor);
		map.addJunction(j);
	}

	public String toString() {
		return "New Junction " + "'" + id + "'";
	}
}
