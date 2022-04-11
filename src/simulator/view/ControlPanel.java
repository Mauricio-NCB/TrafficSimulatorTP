package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {

	private Controller ctrl;
	private boolean stopped;
	private RoadMap roadMap;
	private int time;
	
	private JToolBar toolBar;
	private JButton loadEventFileButton;
	private JFileChooser eventFileChooser;
	private JButton changeContClassButton;
	private JButton changeWeatherButton;
	private JButton runButton;
	private JButton stopButton;
	private JLabel ticksLabel;
	private JSpinner ticksSpinner;
	private JButton exitButton;

	
	public ControlPanel(Controller ctrl) {

		this.ctrl = ctrl;
		stopped = false;
		initGUI();
		this.ctrl.addObserver(this);
	}
	
	private void initGUI() {

		toolBar = new JToolBar();
		this.setLayout(new BorderLayout());
		this.add(toolBar, BorderLayout.PAGE_START);
		
		createLoadFileButton();
		
		toolBar.addSeparator();
		
		createContClassButton();
		createWeatherButton();
		
		toolBar.addSeparator();
		
		createRunButton();
		createStopButton();
		
		toolBar.addSeparator();
		
		createTicksSpinner();
		
		toolBar.add(Box.createHorizontalGlue());
		
		createExitButton();
		
		toolBar.setFloatable(false);
		this.add(toolBar, BorderLayout.PAGE_START);
	}
	
	
	
	//Loading file
	
	private void createLoadFileButton() {
		
		eventFileChooser = new JFileChooser();
		eventFileChooser.setCurrentDirectory(new File("resources/examples/"));
		
		loadEventFileButton = new JButton(new ImageIcon("resources/icons/open.png"));
		
		loadEventFileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				loadFile();
			}
		});
		
		toolBar.add(loadEventFileButton);
	}
	
	private void loadFile() {
		//
		try {
			int returnVal = eventFileChooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				
				File file = eventFileChooser.getSelectedFile();
				InputStream in = new FileInputStream(file);
				
				ctrl.reset();
				ctrl.loadEvents(in);
			}
		}
		catch (FileNotFoundException | NullPointerException ex) {
			JOptionPane.showMessageDialog(this.getParent(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
	//Setting vehicle contamination class
	
	private void createContClassButton() {
		
		changeContClassButton = new JButton(new ImageIcon("resources/icons/co2class.png"));
		
		changeContClassButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changeCO2Class();
				setVehicleContClass();
			}
			
		});
		
		toolBar.add(changeContClassButton);
	}
	
	private void setVehicleContClass() {
		//
	}
	
	
	public void changeCO2Class() {
		ChangeCO2ClassDialog CO2D = new ChangeCO2ClassDialog();
		CO2D.initGUI();
		CO2D.open(roadMap.getVehicles());
	}
	//Setting road weather
	
	private void createWeatherButton() {
		
		changeWeatherButton = new JButton(new ImageIcon("resources/icons/weather.png"));
		
		changeWeatherButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				changeWeather();
				setRoadWeather();
			}
		});
		
		toolBar.add(changeWeatherButton);
	}
	
	private void setRoadWeather() {
		//
	}
	
	public void changeWeather(){
		ChangeWeatherDialog WD = new ChangeWeatherDialog();
		
	}
	
	
	
	//Running simulation
	
	private void createRunButton() {
		
		runButton = new JButton(new ImageIcon("resources/icons/run.png"));
		runButton.setToolTipText("Run the simulation");
		
		runButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				enableToolBar(false);
				stopped = false;
				run_sim((Integer) ticksSpinner.getValue());
			}
			
		});
		
		toolBar.add(runButton);
	}
	
	private void run_sim(int n) {
		if (n > 0 && !stopped) {
			try {
				ctrl.run(1, null);
			} catch (Exception e) {
				// TODO show error message
				enableToolBar(true);
				stopped = true;
				return;
			}
			
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					run_sim(n - 1);
				}
			});
		} 
		else {
			enableToolBar(true);
			stopped = true;
		}
	}
	
	
	
	//Stopping simulation
	
	private void createStopButton() {
		
		stopButton = new JButton(new ImageIcon("resources/icons/stop.png"));
		
		stopButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				stop_sim();
			}
		});
		
		toolBar.add(stopButton);
	}
	
	private void stop_sim() {
		stopped = true;
	}
	
	
	
	//Setting ticks of simulation
	
	private void createTicksSpinner() {
		
		ticksLabel = new JLabel("Ticks: ", JLabel.CENTER);
		
		ticksSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 99999, 1));
		ticksSpinner.setMinimumSize(new Dimension(80, 30));
		ticksSpinner.setMaximumSize(new Dimension(200, 30));
		ticksSpinner.setPreferredSize(new Dimension(80, 30));
		
		
		toolBar.add(ticksLabel);
		toolBar.add(ticksSpinner);
	}
	
	
	
	//Exiting from simulation
	
	private void createExitButton() {
		
		exitButton = new JButton(new ImageIcon("resources/icons/exit.png"));
		
		exitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int seleccion = JOptionPane.showOptionDialog(null,"Quieres Salir?",null,JOptionPane.YES_NO_OPTION,
						   JOptionPane.QUESTION_MESSAGE,null,new Object[] { "Sí", "No"},"opcion 1");
				if(seleccion == 0) {
					exit_sim();
				}
			}
		});
		
		toolBar.add(exitButton);
	}
	
	private void exit_sim() {
		System.exit(0);
	}
	
	
	
	//Enable-Disable toolbar
	
	private void enableToolBar(boolean state) {
		loadEventFileButton.setEnabled(state);
		changeContClassButton.setEnabled(state);
		changeWeatherButton.setEnabled(state);
		runButton.setEnabled(state);
		ticksSpinner.setEnabled(state);
		exitButton.setEnabled(state);
	}
	
	
	
	//
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.time = time;
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		roadMap = map;
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		roadMap = map;
		this.time = time;
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		roadMap = map;
		this.time = time;
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
