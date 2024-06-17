package visualangle.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import PamUtils.LatLong;
import PamUtils.PamCalendar;
import PamView.PamSidePanel;
import PamView.dialog.PamButton;
import PamView.dialog.PamGridBagContraints;
import PamView.dialog.PamLabel;
import PamView.dialog.PamTextField;
import PamView.panel.PamPanel;
import visualangle.VisAngDataUnit;
import visualangle.VisAngleControl;
import visualangle.VisAngleParams;
import visualangle.VisAngleParams.CALCDIRECTION;

public class VisAngSidePanel implements PamSidePanel {
	
	private VisAngleControl visAngleControl;
	
	private JPanel visPanel;
	
	private JTextField arrayDistance, visAngle, visRange, arrayAngle, arrayRange;
	
	private JButton saveButton;
	
	private int fieldSize = 4;

	public VisAngSidePanel(VisAngleControl visAngleControl) {
		super();
		this.visAngleControl = visAngleControl;
		visPanel = new PamPanel(new GridBagLayout());
		setTitle(visAngleControl.getUnitName());
		
		arrayDistance = new PamTextField(fieldSize);
		visAngle = new PamTextField(fieldSize);
		visRange = new PamTextField(fieldSize);
		arrayAngle = new PamTextField(fieldSize);
		arrayRange = new PamTextField(fieldSize);
//		arrayAngle.setEditable(false);
//		arrayRange.setEditable(false);
		saveButton = new PamButton("Save");
		
		CalcAction calcAction = new CalcAction(VisAngleParams.CALCDIRECTION.VISUALTOARRAY);
		arrayDistance.addActionListener(calcAction);
		visAngle.addActionListener(calcAction);
		visRange.addActionListener(calcAction);
		arrayDistance.addKeyListener(calcAction);
		visAngle.addKeyListener(calcAction);
		visRange.addKeyListener(calcAction);
		CalcAction backCalc = new CalcAction(CALCDIRECTION.ARRAYTOVISUAL);
		arrayAngle.addActionListener(backCalc);
		arrayRange.addActionListener(backCalc);
		arrayAngle.addKeyListener(backCalc);
		arrayRange.addKeyListener(backCalc);
		
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveData();
			}
		});
		
		arrayDistance.setToolTipText("Distance from observers to array (m)");
		visAngle.setToolTipText("Visual angle from ahead (degrees)");
		visRange.setToolTipText("Visual range estimate (m)");
		arrayAngle.setToolTipText("Calculated angle to observation from array");
		arrayRange.setToolTipText("Calculated range to observation from array");
		saveButton.setToolTipText("Save to database and display on map");
		
		GridBagConstraints c = new PamGridBagContraints();
		c.ipady = 0;
		c.insets.bottom = c.insets.top = 1;
		c.insets.left = c.insets.right = 1;
		visPanel.add(new PamLabel("Array distance ", PamLabel.RIGHT), c);
		c.gridx ++;
		visPanel.add(arrayDistance, c);
		c.gridx ++;
		visPanel.add(new PamLabel("m", PamLabel.LEFT), c);
		c.gridx = 0;
		c.gridy++;

		visPanel.add(new PamLabel("Visual Angle ", PamLabel.RIGHT), c);
		c.gridx ++;
		visPanel.add(visAngle, c);
		c.gridx ++;
		visPanel.add(new PamLabel(LatLong.deg, PamLabel.LEFT), c);
		c.gridx = 0;
		c.gridy++;

		visPanel.add(new PamLabel("Visual Range ", PamLabel.RIGHT), c);
		c.gridx ++;
		visPanel.add(visRange, c);
		c.gridx ++;
		visPanel.add(new PamLabel("m", PamLabel.LEFT), c);
		c.gridx = 0;
		c.gridy++;

		visPanel.add(new PamLabel("Array Angle ", PamLabel.RIGHT), c);
		c.gridx ++;
		visPanel.add(arrayAngle, c);
		c.gridx ++;
		visPanel.add(new PamLabel(LatLong.deg, PamLabel.LEFT), c);
		c.gridx = 0;
		c.gridy++;

		visPanel.add(new PamLabel("Array Range ", PamLabel.RIGHT), c);
		c.gridx ++;
		visPanel.add(arrayRange, c);
		c.gridx ++;
		visPanel.add(new PamLabel("m", PamLabel.LEFT), c);
		c.gridx = 0;
		c.gridy++;
		
		c.gridx = 1;
		c.gridwidth = 2;
		visPanel.add(saveButton, c);
		
		setValues();
		saveButton.setEnabled(false);
//		calculate();
	}
	
	/**
	 * Create a dataunit and put into system for saving to database,
	 * display on map, etc. 
	 */
	protected void saveData() {
		VisAngleParams params = getValues(null);
		if (params == null) {
			return;
		}
		VisAngDataUnit dataUnit = new VisAngDataUnit(PamCalendar.getTimeInMillis(), lastCalculation, params.clone());
		visAngleControl.getVisAngDataBlock().addPamData(dataUnit);
	}

	CALCDIRECTION lastCalculation;
	
	public void calculate(CALCDIRECTION calcDirection) {
		lastCalculation = calcDirection;
		VisAngleParams params = getValues(calcDirection);
		if (calcDirection == CALCDIRECTION.VISUALTOARRAY) {
			if (params == null) {
				arrayAngle.setText(null);
				arrayRange.setText(null);
				saveButton.setEnabled(false);
			}
			else {
				params.caclulateFromVisual();
				arrayAngle.setText(String.format("%.1f", params.arrayAngle));
				arrayRange.setText(String.format("%.0f", params.arrayRange));	
				saveButton.setEnabled(true);
			}
		}
		if (calcDirection == CALCDIRECTION.ARRAYTOVISUAL) {
			if (params == null) {
				visAngle.setText(null);
				visRange.setText(null);
				saveButton.setEnabled(false);
			}
			else {
				params.caclulateFromArray();
				visAngle.setText(String.format("%.1f", params.visualAngle));
				visRange.setText(String.format("%.0f", params.visualRange));	
				saveButton.setEnabled(true);
			}
		}
	}
	
	private VisAngleParams getValues(CALCDIRECTION calcDirection) {
		VisAngleParams params = visAngleControl.getVisAngleParams();
		try {
			params.arrayDistance = Double.valueOf(arrayDistance.getText());
		}
		catch (NumberFormatException e) {
			return null;
		}
		if (calcDirection == CALCDIRECTION.VISUALTOARRAY || calcDirection == null) {
			try {
				params.visualAngle = Double.valueOf(visAngle.getText());
				params.visualRange = Double.valueOf(visRange.getText());
			}
			catch (NumberFormatException e) {
				return null;
			}
		}
		if (calcDirection == CALCDIRECTION.ARRAYTOVISUAL || calcDirection == null) {
			try {
				params.arrayAngle = Double.valueOf(arrayAngle.getText());
				params.arrayRange = Double.valueOf(arrayRange.getText());
			}
			catch (NumberFormatException e) {
				return null;
			}
		}
		return params;
	}
	
	
	
	private void setValues() {
		VisAngleParams params = visAngleControl.getVisAngleParams();
		arrayDistance.setText(String.format("%.0f", params.arrayDistance));
		visAngle.setText(String.format("%.1f", params.visualAngle));
		visRange.setText(String.format("%.0f", params.visualRange));
		arrayAngle.setText(String.format("%.1f", params.arrayAngle));
		arrayRange.setText(String.format("%.0f", params.arrayRange));
	}

	private void setTitle(String tit) {
		visPanel.setBorder(new TitledBorder(tit));
	}

	@Override
	public JComponent getPanel() {
		return visPanel;
	}

	@Override
	public void rename(String newName) {
		setTitle(newName);
	}
	
	private class CalcAction extends KeyAdapter implements ActionListener {

		private CALCDIRECTION calcDirection;

		public CalcAction(CALCDIRECTION calcDirection) {
			this.calcDirection = calcDirection;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			calculate(calcDirection);
		}

		@Override
		public void keyTyped(KeyEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					calculate(calcDirection);
				}
			});
		}
		
	}
}
