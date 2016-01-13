package breakpoints;

import entities.Device;

public class PagesWrittenInDevice extends BreakpointBase {

	private int mCount;

	public PagesWrittenInDevice(){
		super();
	}
	
	@Override
	public boolean breakpointHit(Device<?, ?, ?, ?> previousDevice,
			Device<?, ?, ?, ?> currentDevice) {
		if(previousDevice == null){
			return currentDevice.getTotalWritten() == mCount; 
		}
		return currentDevice.getTotalWritten() == mCount 
				&& previousDevice.getTotalWritten() != mCount;
	}

	@Override
	public String getDisplayName() {
		return "X pages are written in device";
	}

	@Override
	public String getDescription() {
		return getCount() + " pages are written in device";
	}

	@Override
	public void addComponents() {
		mComponents.add(new BreakpointComponent("count", int.class, "Number of pages written"));
	}

	public int getCount() {
		return mCount;
	}

	public void setCount(int mCount) {
		this.mCount = mCount;
	}

}
