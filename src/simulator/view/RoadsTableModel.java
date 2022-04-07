package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {

	private Controller ctrl;
	private List<Road> roadsList;
	private String[] colNames;
	
	public RoadsTableModel(Controller ctrl) {
		
		this.ctrl = ctrl;
		roadsList = new ArrayList<>();
		colNames = new String[] {"Id", "Length", "Weather", "Max Speed", "Speed Limit",
				"Total CO2", "CO2 Limit"};
		
		this.ctrl.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return roadsList.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return colNames.length;
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return colNames[column];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Road r = roadsList.get(rowIndex);
		
		switch(columnIndex) {
		
		case 0:
			return r.getId();
		case 1:
			return r.getLength();
		case 2:
			return r.getWeather();
		case 3:
			return r.getMaxSpeed();
		case 4:
			return r.getSpeedLimit();
		case 5:
			return r.getTotalCO2();
		case 6:
			return r.getContLimit();
		default:
			return null;
		}
		
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		tableDataChanged(map);
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		tableDataChanged(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		tableDataChanged(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		tableDataChanged(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		tableDataChanged(map);
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	
	private void tableDataChanged(RoadMap map) {
		roadsList = map.getRoads();
		fireTableDataChanged();
	}

		
}
