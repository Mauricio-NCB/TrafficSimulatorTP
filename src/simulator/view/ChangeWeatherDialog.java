package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.model.Road;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	int status;
	private JComboBox<Road> RoadCB;
	private DefaultComboBoxModel<Road> roadModel;
	private JSpinner ticks;
	private JComboBox<Weather> WeatherCB;
	private DefaultComboBoxModel<Weather> weatherModel;
	private Weather[] weathers = Weather.values();
	
	public ChangeWeatherDialog(Frame parent) {
		super(parent, true);
		initGUI();
	}
	
	public void initGUI() {

		status = 0;

		setTitle("Change Road Weather");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);

		JLabel helpMsg = new JLabel("Change the weather of a road after a given number of simulation ticks from now.");
		helpMsg.setAlignmentX(CENTER_ALIGNMENT);

		mainPanel.add(helpMsg);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

		JPanel viewsPanel = new JPanel();
		viewsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(viewsPanel);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(buttonsPanel);
		
		JLabel roadMsg = new JLabel("Road: ");
		viewsPanel.add(roadMsg);
		
		roadModel = new DefaultComboBoxModel<>();
		RoadCB = new JComboBox<>(roadModel);
		viewsPanel.add(RoadCB);
		
		JLabel weatherMsg = new JLabel("Weather: ");
		viewsPanel.add(weatherMsg);
		
		weatherModel = new DefaultComboBoxModel<>();
		WeatherCB = new JComboBox<>(weatherModel);
		viewsPanel.add(WeatherCB);
		
		JLabel ticksMsg = new JLabel("Ticks: ", JLabel.CENTER);
		ticks = new JSpinner(new SpinnerNumberModel(10, 1, 99999, 1));
		ticks.setMinimumSize(new Dimension(80, 30));
		ticks.setMaximumSize(new Dimension(200, 30));
		ticks.setPreferredSize(new Dimension(80, 30));
		
		viewsPanel.add(ticksMsg);
		viewsPanel.add(ticks);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				status = 0;
				ChangeWeatherDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				status = 1;
				ChangeWeatherDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(okButton);

		setPreferredSize(new Dimension(500, 200));
		pack();
		setResizable(false);
		setVisible(false);
		
	} 
	
	public int getTicks() {
		return (int)ticks.getValue();
	}
	
	public Road getRoad() {
		return (Road)RoadCB.getSelectedItem();
	}
	
	public Weather getWeather() {
		return (Weather)WeatherCB.getSelectedItem();
	}
	
	public int open(List<Road> roads) {
		roadModel.removeAllElements();
		
		for (Road r : roads)
			roadModel.addElement(r);
		
		weatherModel.removeAllElements();
		for (Weather w : weathers) {
			weatherModel.addElement(w);
		}

		// You can chenge this to place the dialog in the middle of the parent window.
		// It can be done using uing getParent().getWidth, this.getWidth(),
		// getParent().getHeight, and this.getHeight(), etc.
		//
		setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);

		setVisible(true);
		
		return status;
	}
}
