package simulator.view;

import java.awt.BorderLayout;
import java.awt.Image;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {

	private Controller ctrl;
	
	public ControlPanel(Controller ctrl) {
		super();
		this.ctrl = ctrl;
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		JToolBar toolbar = new JToolBar();
		JFileChooser fc = new JFileChooser();
		JButton Salida = new JButton();
		JButton correr = new JButton();
		JButton parar = new JButton();
		JButton cargar = new JButton();
		try {
			 correr.setIcon(new ImageIcon("resources/icons/run.png"));
			 parar.setIcon(new ImageIcon("resources/icons/stop.png"));
			 Salida.setIcon(new ImageIcon("resources/icons/exit.png"));
			 cargar.setIcon(new ImageIcon("resources/icons/open.png"));
			} catch (Exception ex) {
		 System.out.println(ex);
		}
		toolbar.add(cargar);
		toolbar.add(correr);
		toolbar.add(parar);
		toolbar.add(Salida);
		this.add(toolbar, BorderLayout.PAGE_START);
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
