package breakpoints;

import entities.Device;

public class WritePpBreakpoint extends BreakpointBase {
	private int mPhysicalPage;
	
	public WritePpBreakpoint() {
		super();
	} 
	
	@Override
	public boolean breakpointHit(Device<?, ?, ?, ?> previousDevice,
			Device<?, ?, ?, ?> currentDevice) {
		boolean currentPageIsClean = currentDevice.getPageByIndex(mPhysicalPage).isClean();
		if(previousDevice == null){
			mIsHit = !currentPageIsClean;
			return mIsHit;
		}
		
		boolean previousPageIsclean = previousDevice.getPageByIndex(mPhysicalPage).isClean();
		mIsHit = previousPageIsclean && !currentPageIsClean;
		return mIsHit;
	}

	public int getPhysicalPage() {
		return mPhysicalPage;
	}

	public void setPhysicalPage(int physicalPage) {
		this.mPhysicalPage = physicalPage;
	}

	@Override
	public String getDescription() {
		return "Write physical page " + mPhysicalPage;
	}

	@Override
	public String getDisplayName() {
		return "Write physical page P";
	}

	@Override
	public void addComponents() {
		mComponents.add(new BreakpointComponent("physicalPage", int.class, "Physical page"));
	}
}
