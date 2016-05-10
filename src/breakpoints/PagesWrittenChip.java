package breakpoints;

import entities.Device;

public class PagesWrittenChip extends BreakpointBase {

	private int mCount;
	private int mChipIndex;

	public PagesWrittenChip(){
		super();
	}
	
	@Override
	public boolean breakpointHit(Device<?, ?, ?, ?> previousDevice,
			Device<?, ?, ?, ?> currentDevice) {
		if(previousDevice == null){
			return currentDevice.getChip(mChipIndex).getTotalWritten() == mCount; 
		}
		return currentDevice.getChip(mChipIndex).getTotalWritten() == mCount 
				&& previousDevice.getChip(mChipIndex).getTotalWritten() != mCount;
	}

	@Override
	public String getDisplayName() {
		return "X pages are written in chip";
	}

	@Override
	public String getDescription() {
		return getCount() + " pages are written in chip " + mChipIndex;
	}

	@Override
	public void addComponents() {
		mComponents.add(new BreakpointComponent("count", int.class, "Number of pages written"));
		mComponents.add(new BreakpointComponent("chipIndex", int.class, "Chip index"));
	}

	public int getCount() {
		return mCount;
	}

	public void setCount(int count) throws Exception {
		if (!BreakpointsConstraints.isCountValueLegal(count)) {
			throw BreakpointsConstraints.reportSetterException(SetterError.ILLEGAL_COUNT);
		}
		
		mCount = count;
	}

	public int getChipIndex() {
		return mChipIndex;
	}

	public void setChipIndex(int chipIndex) throws Exception {
		if (!BreakpointsConstraints.isChipIndexLegal(chipIndex)) {
			throw BreakpointsConstraints.reportSetterException(SetterError.ILLEGAL_CHIP);
		}
		
		mChipIndex = chipIndex;
	}

	@Override
	public boolean isEquals(IBreakpoint other) {
		if (!(other instanceof PagesWrittenChip)) return false; 
		PagesWrittenChip otherCasted = (PagesWrittenChip) other;
		
		return mCount == otherCasted.getCount()
				&& mChipIndex == otherCasted.getChipIndex();
	}

	@Override
	public String getHitDescription() {
		return getCount() + " pages were written in chip " + mChipIndex;
	}
}
