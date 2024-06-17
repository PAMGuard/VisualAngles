package visualangle;

import PamModel.PamDependency;
import PamModel.PamPluginInterface;

public class VisAnglePlugin implements PamPluginInterface {

	private String jarFile;

	@Override
	public String getDefaultName() {
		return VisAngleControl.unitType;
	}

	@Override
	public String getHelpSetName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setJarFile(String jarFile) {
		this.jarFile = jarFile;
	}

	@Override
	public String getJarFile() {
		return jarFile;
	}

	@Override
	public String getDeveloperName() {
		return "Doug Gillespie";
	}

	@Override
	public String getContactEmail() {
		return null;
	}

	@Override
	public String getVersion() {
		return "1.1";
	}

	@Override
	public String getPamVerDevelopedOn() {
		return "2.02.10";
	}

	@Override
	public String getPamVerTestedOn() {
		return "2.02.11";
	}

	@Override
	public String getAboutText() {
		return "Convert visual observer angles to acoustic angles";
	}

	@Override
	public String getClassName() {
		return VisAngleControl.class.getName();
	}

	@Override
	public String getDescription() {
		return getDefaultName();
	}

	@Override
	public String getMenuGroup() {
		return "Visual Methods";
	}

	@Override
	public String getToolTip() {
		return null;
	}

	@Override
	public PamDependency getDependency() {
		return null;
	}

	@Override
	public int getMinNumber() {
		return 0;
	}

	@Override
	public int getMaxNumber() {
		return 0;
	}

	@Override
	public int getNInstances() {
		return 0;
	}

	@Override
	public boolean isItHidden() {
		return false;
	}

	@Override
	public int allowedModes() {
		return PamPluginInterface.ALLMODES;
	}

}
