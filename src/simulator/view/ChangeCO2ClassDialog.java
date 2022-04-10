package simulator.view;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JSpinner;

import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog {
		int estado;
		JComboBox<Vehicle> VehicleCB;
		JSpinner ticks;
		JComboBox<Integer> IntegerCB;
	
	public int getTicks() {
		return (int)ticks.getValue();
	}
	
	public Vehicle getVehicle() {
		return (Vehicle)VehicleCB.getSelectedItem();
	}
	
	public int getCO2Class() {
		return (int)IntegerCB.getSelectedItem();
	}
}