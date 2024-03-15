package visualangle;

import PamUtils.LatLong;
import PamUtils.PamCalendar;
import PamguardMVC.PamDataUnit;
import visualangle.VisAngleParams.CALCDIRECTION;

public class VisAngDataUnit extends PamDataUnit {

	private VisAngleParams visAngleParams;
	
	private CALCDIRECTION lastCalculation;

	public VisAngDataUnit(long timeMilliseconds, CALCDIRECTION lastCalculation, VisAngleParams visAngleParams) {
		super(timeMilliseconds);
		this.setLastCalculation(lastCalculation);
		this.visAngleParams = visAngleParams;
	}


	@Override
	public String getSummaryString() {
		String str = String.format("<html>Visual angle %s", PamCalendar.formatDBDateTime(getTimeMilliseconds()));
		str += String.format("<br>Array distance %.0fm<br>Visual angle %.0f%s, range %.0fm", visAngleParams.arrayDistance,
				visAngleParams.visualAngle, LatLong.deg, visAngleParams.visualRange);
		str += String.format("<br>Array angle %.0f%s, range %.0fm</html>", visAngleParams.arrayAngle, LatLong.deg, visAngleParams.arrayRange);
		return str;
	}

	/**
	 * @return the visAngleParams
	 */
	public VisAngleParams getVisAngleParams() {
		return visAngleParams;
	}


	/**
	 * @return the lastCalculation
	 */
	public CALCDIRECTION getLastCalculation() {
		return lastCalculation;
	}


	/**
	 * @param lastCalculation the lastCalculation to set
	 */
	public void setLastCalculation(CALCDIRECTION lastCalculation) {
		this.lastCalculation = lastCalculation;
	}


}
