package visualangle;

import PamguardMVC.PamDataBlock;
import PamguardMVC.PamProcess;

public class VisAngDataBlock extends PamDataBlock<VisAngDataUnit> {

	public VisAngDataBlock(String dataName, PamProcess parentProcess) {
		super(VisAngDataUnit.class, dataName, parentProcess, 0);
		setClearAtStart(false);
	}

}
