package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {

	private Controller ctrl;
	private List<Vehicle> vehiclesList;
	private String[] colNames;
	
	public VehiclesTableModel(Controller ctrl) {
		
		this.ctrl = ctrl;
		vehiclesList = new ArrayList<>();
		colNames = new String[] {"Id", "Location", "Itinerary", "CO2 Class", "Max speed", "Speed",
				"Total CO2", "Distance"};
		
		ctrl.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return vehiclesList.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return colNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Vehicle v = vehiclesList.get(rowIndex);
		
		switch(columnIndex) {
		
		case 0:
			return v.getId();
		case 1:
			StringBuilder status = new StringBuilder();
			
			switch(v.getStatus()) {
			
			case PENDING:
				return status.append("Pending");
			case TRAVELING:
				return status.append(v.getRoad() + ": " + v.getLocation());
			case WAITING:
				return status.append("Waiting: " + v.getItinerary());
			case ARRIVED:
				return status.append("Arrived");
			}
		case 2:
			return v.getItinerary();
		case 3:
			return v.getContClass();
		case 4:
			return v.getMaxSpeed();
		case 5:
			return v.getSpeed();
		case 6:
			return v.getTotalCO2();
		case 7:
			return v.getDistance();
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
		vehiclesList = map.getVehicles();
		fireTableDataChanged();
	}

}
