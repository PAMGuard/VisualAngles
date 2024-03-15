package visualangle;

import java.io.Serializable;

import PamController.PamControlledUnit;
import PamController.PamControlledUnitSettings;
import PamController.PamSettingManager;
import PamController.PamSettings;
import PamView.PamSidePanel;
import PamguardMVC.PamDataBlock;
import PamguardMVC.PamProcess;
import visualangle.io.VisAngLogging;
import visualangle.swing.VisAngOverlayDraw;
import visualangle.swing.VisAngSidePanel;
import visualangle.swing.VisAngSymbolManager;

public class VisAngleControl extends PamControlledUnit implements PamSettings {
	
	public static final String unitType = "Visual to array angle";
	
	private VisAngleParams visAngleParams = new VisAngleParams();

	private VisAngSidePanel visAngSidePanel;
	
	private VisAngProcess visAngProcess;
	
	private VisAngDataBlock visAngDataBlock;
	
	public VisAngleControl( String unitName) {
		super(unitType, unitName);
		PamSettingManager.getInstance().registerSettings(this);
		visAngProcess = new VisAngProcess(this);
		visAngDataBlock = new VisAngDataBlock(this.getUnitName(), visAngProcess);
		visAngProcess.addOutputDataBlock(visAngDataBlock);
		visAngDataBlock.setOverlayDraw(new VisAngOverlayDraw(visAngDataBlock));
		visAngDataBlock.setPamSymbolManager(new VisAngSymbolManager(visAngDataBlock));
		visAngDataBlock.SetLogging(new VisAngLogging(this, visAngDataBlock));
		addPamProcess(visAngProcess);
	}

	@Override
	public Serializable getSettingsReference() {
		return visAngleParams;
	}

	@Override
	public long getSettingsVersion() {
		return VisAngleParams.serialVersionUID;
	}

	@Override
	public boolean restoreSettings(PamControlledUnitSettings pamControlledUnitSettings) {
		this.visAngleParams = (VisAngleParams) pamControlledUnitSettings.getSettings();
		return true;
	}

	@Override
	public PamSidePanel getSidePanel() {
		if (visAngSidePanel == null) {
			visAngSidePanel = new VisAngSidePanel(this);
		}
		return visAngSidePanel;
	}

	/**
	 * @return the visAngleParams
	 */
	public VisAngleParams getVisAngleParams() {
		return visAngleParams;
	}
	
	private class VisAngProcess extends PamProcess {

		public VisAngProcess(PamControlledUnit pamControlledUnit) {
			super(pamControlledUnit, null);
		}

		@Override
		public void pamStart() {
		}

		@Override
		public void pamStop() {
		}
		
	}

	/**
	 * @return the visAngDataBlock
	 */
	public VisAngDataBlock getVisAngDataBlock() {
		return visAngDataBlock;
	}

}
