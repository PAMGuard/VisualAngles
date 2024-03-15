package visualangle.swing;

import PamView.symbol.StandardSymbolManager;
import PamView.symbol.SymbolData;
import PamguardMVC.PamDataBlock;

public class VisAngSymbolManager extends StandardSymbolManager {

	public VisAngSymbolManager(PamDataBlock pamDataBlock) {
		super(pamDataBlock, VisAngOverlayDraw.defaultSymbol.getSymbolData());
	}

}
