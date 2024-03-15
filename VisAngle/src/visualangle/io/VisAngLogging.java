package visualangle.io;

import java.sql.Types;

import PamguardMVC.PamDataBlock;
import PamguardMVC.PamDataUnit;
import generalDatabase.PamTableDefinition;
import generalDatabase.PamTableItem;
import generalDatabase.SQLLogging;
import generalDatabase.SQLTypes;
import visualangle.VisAngDataUnit;
import visualangle.VisAngleControl;
import visualangle.VisAngleParams.CALCDIRECTION;

public class VisAngLogging extends SQLLogging {
	
	private PamTableItem arrayDistance, visAng, visRange, arrayAng, arrayRange, calcDirection;
	private VisAngleControl visAngleControl;

	public VisAngLogging(VisAngleControl visAngleControl, PamDataBlock pamDataBlock) {
		super(pamDataBlock);
		this.visAngleControl = visAngleControl;
		
		this.setTableDefinition(createBaseTable(visAngleControl.getUnitName()));
	}

	@Override
	public void setTableData(SQLTypes sqlTypes, PamDataUnit pamDataUnit) {
		VisAngDataUnit vaDataUnit = (VisAngDataUnit) pamDataUnit;
		CALCDIRECTION lastCalc = vaDataUnit.getLastCalculation();
		if (lastCalc == null) {
			calcDirection.setValue(null);
		}
		else {
			calcDirection.setValue(lastCalc.toString());
		}
		arrayDistance.setValue((float) vaDataUnit.getVisAngleParams().arrayDistance);
		visAng.setValue((float) vaDataUnit.getVisAngleParams().visualAngle);
		visRange.setValue((float) vaDataUnit.getVisAngleParams().visualRange);
		arrayAng.setValue((float) vaDataUnit.getVisAngleParams().arrayAngle);
		arrayRange.setValue((float) vaDataUnit.getVisAngleParams().arrayRange);

	}
	
	private PamTableDefinition createBaseTable(String tableName) {
		PamTableDefinition tableDef = new PamTableDefinition(tableName);
		tableDef.addTableItem(calcDirection = new PamTableItem("Calculation", Types.CHAR, 20));
		tableDef.addTableItem(arrayDistance = new PamTableItem("Array Distance", Types.REAL));
		tableDef.addTableItem(visAng = new PamTableItem("Visual Angle", Types.REAL));
		tableDef.addTableItem(visRange = new PamTableItem("Visual Range", Types.REAL));
		tableDef.addTableItem(arrayAng = new PamTableItem("Array Angle", Types.REAL));
		tableDef.addTableItem(arrayRange = new PamTableItem("Array Range", Types.REAL));
		return tableDef;
	}

}
