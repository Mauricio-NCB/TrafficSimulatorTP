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

import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	int estado;
	private JComboBox<Vehicle> VehicleCB;
	private DefaultComboBoxModel<Vehicle> vehicleModel;
	private JSpinner ticks;
	private JComboBox<Integer> IntegerCB;
	
	public ChangeCO2ClassDialog(Frame parent) {
		super(parent, true);
		initGUI();
	}
	
	public int getTicks() {
		return (int)ticks.getValue();
	}
	
	public void initGUI() {
		
		estado = 0;

		setTitle("Change CO2 class");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);

		JLabel helpMsg = new JLabel("Change the CO2 class of a vehicle after a given number of simulation ticks from now.");
		helpMsg.setAlignmentX(CENTER_ALIGNMENT);

		mainPanel.add(helpMsg);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		JPanel viewsPanel = new JPanel();
		viewsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(viewsPanel);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(buttonsPanel);
		
		vehicleModel = new DefaultComboBoxModel<>();
		VehicleCB = new JComboBox<>(vehicleModel);

		viewsPanel.add(VehicleCB);
		
		IntegerCB = new JComboBox<>();
		viewsPanel.add(IntegerCB);


		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				estado = 0;
				ChangeCO2ClassDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				estado = 1;
				ChangeCO2ClassDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(okButton);

		setPreferredSize(new Dimension(500, 200));
		pack();
		setResizable(false);
		setVisible(false);
		
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

		// You can chenge this to place the dialog in the middle of the parent window.
		// It can be done using uing getParent().getWidth, this.getWidth(),
		// getParent().getHeight, and this.getHeight(), etc.
		//
		setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);

		setVisible(true);
		return estado;
	}
}