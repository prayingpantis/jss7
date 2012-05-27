/*
 * TeleStax, Open Source Cloud Communications  Copyright 2012.
 * and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.mobicents.protocols.ss7.tools.simulatorgui;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.mobicents.protocols.ss7.map.api.primitives.AddressNature;
import org.mobicents.protocols.ss7.map.api.primitives.NumberingPlan;
import org.mobicents.protocols.ss7.tools.simulator.common.AddressNatureType;
import org.mobicents.protocols.ss7.tools.simulator.common.NumberingPlanType;
import org.mobicents.protocols.ss7.tools.simulator.level3.MapManMBean;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import java.awt.Color;

/**
 * 
 * @author sergey vetyutnev
 * 
 */
public class MapForm extends JDialog {

	private MapManMBean map;
	
	private static final long serialVersionUID = -2799708291291364182L;
	private JTextField tbLocalSsn;
	private JTextField tbRemoteSsn;
	private JTextField tbOrigReference;
	private JTextField tbDestReference;
	private JTextField tbRemoteAddressDigits;
	private JComboBox cbOrigReferenceAddressNature;
	private JComboBox cbOrigReferenceNumberingPlan;
	private JComboBox cbDestReferenceAddressNature;
	private JComboBox cbDestReferenceNumberingPlan;

	public MapForm(JFrame owner) {
		super(owner, true);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("MAP settings");
		setBounds(100, 100, 582, 485);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblLocalSsn = new JLabel("Local SSN");
		lblLocalSsn.setBounds(10, 14, 183, 14);
		panel.add(lblLocalSsn);
		
		tbLocalSsn = new JTextField();
		tbLocalSsn.setColumns(10);
		tbLocalSsn.setBounds(203, 11, 129, 20);
		panel.add(tbLocalSsn);
		
		JButton button = new JButton("Load default values for side A");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadDataA();
			}
		});
		button.setBounds(10, 391, 254, 23);
		panel.add(button);
		
		JButton button_1 = new JButton("Load default values for side B");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadDataB();
			}
		});
		button_1.setBounds(274, 391, 244, 23);
		panel.add(button_1);
		
		JButton button_2 = new JButton("Reload");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reloadData();
			}
		});
		button_2.setBounds(10, 425, 144, 23);
		panel.add(button_2);
		
		JButton button_3 = new JButton("Save");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (saveData()) {
					getJFrame().dispose();
				}
			}
		});
		button_3.setBounds(274, 425, 117, 23);
		panel.add(button_3);
		
		JButton button_4 = new JButton("Cancel");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getJFrame().dispose();
			}
		});
		button_4.setBounds(401, 425, 117, 23);
		panel.add(button_4);
		
		tbRemoteSsn = new JTextField();
		tbRemoteSsn.setColumns(10);
		tbRemoteSsn.setBounds(203, 42, 129, 20);
		panel.add(tbRemoteSsn);
		
		JLabel lblRemoteSsn = new JLabel("Remote SSN");
		lblRemoteSsn.setBounds(10, 45, 183, 14);
		panel.add(lblRemoteSsn);
		
		JLabel lblRemoteAddressDigits = new JLabel("Remote address digits");
		lblRemoteAddressDigits.setBounds(10, 76, 183, 14);
		panel.add(lblRemoteAddressDigits);

		tbRemoteAddressDigits = new JTextField();
		tbRemoteAddressDigits.setColumns(10);
		tbRemoteAddressDigits.setBounds(203, 73, 270, 20);
		panel.add(tbRemoteAddressDigits);

		JLabel lblIfEmptyRoutingbasedondpcandssn = new JLabel("If empty ROUTING_BASED_ON_DPC_AND_SSN is used for CalledPartyAddress (remoteSpc from SCCP)");
		lblIfEmptyRoutingbasedondpcandssn.setBounds(10, 101, 556, 14);
		panel.add(lblIfEmptyRoutingbasedondpcandssn);
		
		JLabel lblIfNotEmpty = new JLabel("if not empty ROUTING_BASED_ON_GLOBAL_TITLE is used (address and Ssn from MAP)");
		lblIfNotEmpty.setBounds(10, 119, 556, 14);
		panel.add(lblIfNotEmpty);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setLayout(null);
		panel_1.setBounds(7, 144, 511, 108);
		panel.add(panel_1);
		
		JLabel lblOriginationReference = new JLabel("Origination reference");
		lblOriginationReference.setBounds(10, 0, 147, 14);
		panel_1.add(lblOriginationReference);
		
		JLabel label_2 = new JLabel("AddressNature");
		label_2.setBounds(10, 53, 136, 14);
		panel_1.add(label_2);
		
		JLabel label_3 = new JLabel("NumberingPlan");
		label_3.setBounds(10, 84, 136, 14);
		panel_1.add(label_3);
		
		cbOrigReferenceAddressNature = new JComboBox();
		cbOrigReferenceAddressNature.setBounds(179, 47, 294, 20);
		panel_1.add(cbOrigReferenceAddressNature);
		
		cbOrigReferenceNumberingPlan = new JComboBox();
		cbOrigReferenceNumberingPlan.setBounds(179, 78, 294, 20);
		panel_1.add(cbOrigReferenceNumberingPlan);
		
		JLabel lblOriginationReferenceString = new JLabel("Address digits");
		lblOriginationReferenceString.setBounds(10, 22, 169, 14);
		panel_1.add(lblOriginationReferenceString);
		
		tbOrigReference = new JTextField();
		tbOrigReference.setBounds(203, 19, 270, 20);
		panel_1.add(tbOrigReference);
		tbOrigReference.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setLayout(null);
		panel_2.setBounds(7, 259, 511, 108);
		panel.add(panel_2);
		
		JLabel lblDestinationReference = new JLabel("Destination reference");
		lblDestinationReference.setBounds(10, 0, 147, 14);
		panel_2.add(lblDestinationReference);
		
		JLabel label_1 = new JLabel("AddressNature");
		label_1.setBounds(10, 53, 136, 14);
		panel_2.add(label_1);
		
		JLabel label_4 = new JLabel("NumberingPlan");
		label_4.setBounds(10, 84, 136, 14);
		panel_2.add(label_4);
		
		cbDestReferenceAddressNature = new JComboBox();
		cbDestReferenceAddressNature.setBounds(179, 47, 294, 20);
		panel_2.add(cbDestReferenceAddressNature);
		
		cbDestReferenceNumberingPlan = new JComboBox();
		cbDestReferenceNumberingPlan.setBounds(179, 78, 294, 20);
		panel_2.add(cbDestReferenceNumberingPlan);
		
		JLabel label_5 = new JLabel("Address digits");
		label_5.setBounds(10, 22, 169, 14);
		panel_2.add(label_5);
		
		tbDestReference = new JTextField();
		tbDestReference.setBounds(203, 19, 270, 20);
		panel_2.add(tbDestReference);
		tbDestReference.setColumns(10);
	}

	public void setData(MapManMBean map) {
		this.map = map;

		this.reloadData();
	}

	private JDialog getJFrame() {
		return this;
	}

	private void reloadData() {
		M3uaForm.setEnumeratedBaseComboBox(cbOrigReferenceAddressNature, this.map.getOrigReferenceAddressNature());
		M3uaForm.setEnumeratedBaseComboBox(cbOrigReferenceNumberingPlan, this.map.getOrigReferenceNumberingPlan());
		M3uaForm.setEnumeratedBaseComboBox(cbDestReferenceAddressNature, this.map.getDestReferenceAddressNature());
		M3uaForm.setEnumeratedBaseComboBox(cbDestReferenceNumberingPlan, this.map.getDestReferenceNumberingPlan());

		tbLocalSsn.setText(((Integer) this.map.getLocalSsn()).toString());
		tbRemoteSsn.setText(((Integer) this.map.getRemoteSsn()).toString());

		tbOrigReference.setText(this.map.getOrigReference());
		tbDestReference.setText(this.map.getDestReference());
		tbRemoteAddressDigits.setText(this.map.getRemoteAddressDigits());
	}

	private void loadDataA() {
		M3uaForm.setEnumeratedBaseComboBox(cbOrigReferenceAddressNature, new AddressNatureType(AddressNature.international_number.getIndicator()));
		M3uaForm.setEnumeratedBaseComboBox(cbOrigReferenceNumberingPlan, new NumberingPlanType(NumberingPlan.ISDN.getIndicator()));
		M3uaForm.setEnumeratedBaseComboBox(cbDestReferenceAddressNature, new AddressNatureType(AddressNature.international_number.getIndicator()));
		M3uaForm.setEnumeratedBaseComboBox(cbDestReferenceNumberingPlan, new NumberingPlanType(NumberingPlan.ISDN.getIndicator()));

		tbLocalSsn.setText("8");
		tbRemoteSsn.setText("8");

		tbOrigReference.setText("");
		tbDestReference.setText("");
		tbRemoteAddressDigits.setText("");
	}

	private void loadDataB() {
		M3uaForm.setEnumeratedBaseComboBox(cbOrigReferenceAddressNature, new AddressNatureType(AddressNature.international_number.getIndicator()));
		M3uaForm.setEnumeratedBaseComboBox(cbOrigReferenceNumberingPlan, new NumberingPlanType(NumberingPlan.ISDN.getIndicator()));
		M3uaForm.setEnumeratedBaseComboBox(cbDestReferenceAddressNature, new AddressNatureType(AddressNature.international_number.getIndicator()));
		M3uaForm.setEnumeratedBaseComboBox(cbDestReferenceNumberingPlan, new NumberingPlanType(NumberingPlan.ISDN.getIndicator()));

		tbLocalSsn.setText("8");
		tbRemoteSsn.setText("8");

		tbOrigReference.setText("");
		tbDestReference.setText("");
		tbRemoteAddressDigits.setText("");
	}

	private boolean saveData() {
		int localSsn = 0;
		int remoteSsn = 0;
		try {
			localSsn = Integer.parseInt(tbLocalSsn.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Exception when parsing Local Ssn value: " + e.toString());
			return false;
		}
		try {
			remoteSsn = Integer.parseInt(tbRemoteSsn.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Exception when parsing Remote Ssn value: " + e.toString());
			return false;
		}

		this.map.setOrigReferenceAddressNature((AddressNatureType) cbOrigReferenceAddressNature.getSelectedItem());
		this.map.setOrigReferenceNumberingPlan((NumberingPlanType) cbOrigReferenceNumberingPlan.getSelectedItem());
		this.map.setDestReferenceAddressNature((AddressNatureType) cbDestReferenceAddressNature.getSelectedItem());
		this.map.setDestReferenceNumberingPlan((NumberingPlanType) cbDestReferenceNumberingPlan.getSelectedItem());

		this.map.setLocalSsn(localSsn);
		this.map.setRemoteSsn(remoteSsn);

		this.map.setOrigReference(tbOrigReference.getText());
		this.map.setDestReference(tbDestReference.getText());
		this.map.setRemoteAddressDigits(tbRemoteAddressDigits.getText());

		return true;
	}
}

