package simulator.view;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JSpinner;

import simulator.model.Road;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog {
	int estado;
	JComboBox<Road> RoadCB;
	JSpinner ticks;
	JComboBox<Weather> WeatherCB;
	
	public int getTicks() {
		return (int)ticks.getValue();
	}
	
	public Road getRoad() {
		return (Road)RoadCB.getSelectedItem();
	}
	
	public Weather getWeather() {
		return (Weather)WeatherCB.getSelectedItem();
	}
}
