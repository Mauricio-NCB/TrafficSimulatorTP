package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	private Controller ctrl;
	private List<Event> eventsList;
	private String[] colNames;
	
	public EventsTableModel(Controller ctrl) {
		
		this.ctrl = ctrl;
		eventsList = new ArrayList<>();
		colNames = new String[] {"Time", "Desc."};
		
		this.ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return eventsList.size();
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
		Event e = eventsList.get(rowIndex);
		
		switch (columnIndex) {
        case 0:
            return e.getTime();
        case 1:
            return e.toString();
        default:
        	return null;
		}

	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		tableDataChanged(events);
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		tableDataChanged(events);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		tableDataChanged(events);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		tableDataChanged(events);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		tableDataChanged(events);
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	
	private void tableDataChanged(List<Event> events) {
		eventsList = events;
		fireTableDataChanged();
	}
}
