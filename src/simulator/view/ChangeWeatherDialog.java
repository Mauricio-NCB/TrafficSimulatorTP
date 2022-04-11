package simulator.view;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JSpinner;

import simulator.model.Road;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	int estado;
	private JComboBox<Road> RoadCB;
	private JSpinner ticks;
	private JComboBox<Weather> WeatherCB;
	
	public int getTicks() {
		return (int)ticks.getValue();
	}
	
	public Road getRoad() {
		return (Road)RoadCB.getSelectedItem();
	}
	
	public Weather getWeather() {
		return (Weather)WeatherCB.getSelectedItem();
	}
	
	public int open() {
		return estado;
	}
}
