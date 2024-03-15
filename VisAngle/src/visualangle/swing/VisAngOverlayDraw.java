package visualangle.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import Array.SnapshotGeometry;
import GPS.GpsData;
import PamUtils.Coordinate3d;
import PamView.GeneralProjector;
import PamView.PamDetectionOverlayGraphics;
import PamView.PamSymbol;
import PamView.PamSymbolType;
import PamguardMVC.PamDataBlock;
import PamguardMVC.PamDataUnit;
import visualangle.VisAngDataUnit;
import visualangle.VisAngleParams;

public class VisAngOverlayDraw extends PamDetectionOverlayGraphics {

	
	public static PamSymbol defaultSymbol = new PamSymbol(PamSymbolType.SYMBOL_CIRCLE, 11, 11, true, Color.RED, Color.BLUE);
	
	public VisAngOverlayDraw(PamDataBlock parentDataBlock) {
		super(parentDataBlock, defaultSymbol);
	}

	@Override
	protected Rectangle drawOnMap(Graphics g, PamDataUnit pamDetection, GeneralProjector generalProjector) {
		VisAngDataUnit vaDataUnit = (VisAngDataUnit) pamDetection;
		GpsData oll = vaDataUnit.getOriginLatLong(true);
		if (oll == null) {
//			System.out.println("Null origin for " + vaDataUnit.getSummaryString());
			return null;
		}
		double head = oll.getHeading();
		VisAngleParams values = vaDataUnit.getVisAngleParams();
		double visAngle = Math.toRadians(values.visualAngle+head);
		double visRange = values.visualRange;
		double x = Math.sin(visAngle)*visRange;
		double y = Math.cos(visAngle)*visRange;
		GpsData endLL = oll.addDistanceMeters(x, y);
		Coordinate3d cOrig = generalProjector.getCoord3d(oll);
		Coordinate3d cEnd = generalProjector.getCoord3d(endLL);
		if (cOrig == null || cEnd == null) {
			return null;
		}
		PamSymbol symbol = getPamSymbol(vaDataUnit, generalProjector);
		g.setColor(symbol.getLineColor());
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(symbol.getLineThickness()));
		Point2D start = cOrig.getXYPoint();
		Point2D end = cEnd.getXYPoint();
		g2d.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
		Rectangle rect = symbol.draw(g2d, cEnd.getXYPoint());
		
		generalProjector.addHoverData(cEnd, vaDataUnit);
		
		return rect;
	}

}
