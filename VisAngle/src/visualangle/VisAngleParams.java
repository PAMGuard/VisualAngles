package visualangle;

import java.io.Serializable;

public class VisAngleParams implements Serializable, Cloneable {
	
	public enum CALCDIRECTION {VISUALTOARRAY, ARRAYTOVISUAL};

	public static final long serialVersionUID = 1L;
	
	
	public CALCDIRECTION lastCalcDirection = null;
	
	/**
	 * Distance to the array from the visual observers.
	 */
	public double arrayDistance = 300;
	
	/**
	 * Last entered range (probably don't really need to store this, but might as well)
	 */
	public double visualRange;
	
	/**
	 * Last entered or calculated angle (probably don't really need to store this, but might as well)
	 */
	public double visualAngle;
	
	/**
	 * Last entered or calculated array range. 
	 */
	public double arrayRange;

	/**
	 * Last entered or calculated array angle. 
	 */
	public double arrayAngle;

	@Override
	public VisAngleParams clone() {
		try {
			return (VisAngleParams) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * From existing values, calculate the angle and range from the array
	 * return null if any values are invalid. 
	 * @return angle and range in degrees and metres as 2 element array. 
	 */
	public boolean caclulateFromVisual() {
		double angRad = Math.toRadians(visualAngle);
		double x = Math.sin(angRad)*visualRange;
		double y = Math.cos(angRad)*visualRange + arrayDistance;
		double newAng = Math.atan2(x, y);
		arrayAngle = Math.toDegrees(newAng);
		arrayRange = Math.sqrt(x*x + y*y);
		return true;
	}

	public boolean caclulateFromArray() {
		double angRad = Math.toRadians(arrayAngle);
		double x = Math.sin(angRad)*arrayRange;
		double y = Math.cos(angRad)*arrayRange - arrayDistance;
		double newAng = Math.atan2(x,  y);
		visualAngle = Math.toDegrees(newAng);
		visualRange = Math.sqrt(x*x + y*y);
		return true;
	}

}
