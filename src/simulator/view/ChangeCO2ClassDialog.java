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

import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	int status;
	private JComboBox<Vehicle> VehicleCB;
	private DefaultComboBoxModel<Vehicle> vehicleModel;
	private JSpinner ticks;
	private JComboBox<Integer> IntegerCB;
	private DefaultComboBoxModel<Integer> integerModel;
	
	public ChangeCO2ClassDialog(Frame parent) {
		super(parent, true);
		initGUI();
	}
	
	public void initGUI() {
		
		status = 0;

		setTitle("Change CO2 class");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);

		JLabel helpMsg = new JLabel("Change the CO2 class of a vehicle after a given number of simulation ticks from now.");
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
		
		JLabel vehicleMsg = new JLabel("Vehicle: ");
		viewsPanel.add(vehicleMsg);
		
		vehicleModel = new DefaultComboBoxModel<>();
		VehicleCB = new JComboBox<>(vehicleModel);
		viewsPanel.add(VehicleCB);
		
		JLabel integerMsg = new JLabel("Contamination: ");
		viewsPanel.add(integerMsg);
		
		integerModel = new DefaultComboBoxModel<>();
		IntegerCB = new JComboBox<>(integerModel);
		viewsPanel.add(IntegerCB);
		
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
				ChangeCO2ClassDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				status = 1;
				ChangeCO2ClassDialog.this.setVisible(false);
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
	
	public Vehicle getVehicle() {
		return (Vehicle)VehicleCB.getSelectedItem();
	}
	
	public int getCO2Class() {
		return (int)IntegerCB.getSelectedItem();
	}

	
	public int open(List<Vehicle> vehicles) {

		// update the comboxBox model -- if you always use the same no
		// need to update it, you can initialize it in the constructor.
		//
		vehicleModel.removeAllElements();
		for (Vehicle v : vehicles)
			vehicleModel.addElement(v);
		
		integerModel.removeAllElements();
		for (int i=0; i<=10;i++) {
			integerModel.addElement(i);
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