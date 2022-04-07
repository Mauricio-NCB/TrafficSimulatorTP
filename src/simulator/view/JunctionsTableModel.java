package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	private Controller ctrl;
	private List<Junction> junctionsList;
	private String[] colNames;
	
	public JunctionsTableModel(Controller ctrl) {

		this.ctrl = ctrl;
		junctionsList = new ArrayList<>();
		colNames = new String[] {"Id", "Green", "Queues"};
		
		this.ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return junctionsList.size();
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
		Junction j = junctionsList.get(rowIndex);
		
		switch(columnIndex) {
		case 0:
			return j.getId();
		case 1:
			if (j.getGreenLightIndex() != -1) {
				return j.getInRoads().get(j.getGreenLightIndex());
			}
			else {
				return "NONE";
			}
		case 2:
			String queue = "";
			
			for (Road r: j.getInRoads()) {
				queue += r.getId() + ": " + r.getVehicles().toString() + " ";
			}
			
			return queue;
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
		junctionsList = map.getJunctions();
		fireTableDataChanged();
	}
}
