package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {

	private final static String _noEvent = "Go Speed Racer! Go!";
	
	private Controller ctrl;
	private JLabel currentTime;
	private JLabel currentEvent;
	
	public StatusBar(Controller ctrl) {

		this.ctrl = ctrl;
		initGUI();
		this.ctrl.addObserver(this);
	}
	
	private void initGUI() {
		// TODO Auto-generated method stub

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		currentTime = new JLabel(time(0));
		
		this.add(currentTime);
		
		this.add(Box.createRigidArea(new Dimension(50, 0)));
		
		currentEvent = new JLabel(_noEvent);
		this.add(currentEvent);
		
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		currentTime.setText(time(time));
		currentEvent.setText("");
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		currentTime.setText(time(time));
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		currentTime.setText(time(time));
		currentEvent.setText("Event added (" + e.toString() + ")");
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		currentTime.setText(time(time));
		currentEvent.setText("");
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		currentTime.setText(time(time));
		currentEvent.setText(_noEvent);
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	
	public String time(int time) {
		return "Time: " + time;
	}

}
