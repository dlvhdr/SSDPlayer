package breakpoints;

import entities.Device;

public class WritePp extends BreakpointBase {
	private int mPageIndex;
	private int mBlockIndex;
	private int mPlaneIndex;
	private int mChipIndex;
	
	public WritePp() {
		super();
	} 
	
	@Override
	public boolean breakpointHit(Device<?, ?, ?, ?> previousDevice,
			Device<?, ?, ?, ?> currentDevice) {
		boolean currentPageIsClean = currentDevice.getChip(mChipIndex)
				.getPlane(mPlaneIndex)
				.getBlock(mBlockIndex)
				.getPage(mPageIndex)
				.isClean();
		if(previousDevice == null){
			return !currentPageIsClean;
		}
		
		boolean previousPageIsclean = previousDevice.getChip(mChipIndex)
				.getPlane(mPlaneIndex)
				.getBlock(mBlockIndex)
				.getPage(mPageIndex)
				.isClean();
		return previousPageIsclean && !currentPageIsClean;
	}

	public int getPageIndex() {
		return mPageIndex;
	}

	public void setPageIndex(int pageIndex) throws Exception {
		if (!BreakpointsConstraints.isPageIndexLegal(pageIndex)) {
			throw BreakpointsConstraints.reportSetterException(SetterError.ILLEGAL_PAGE);
		}
		
		mPageIndex = pageIndex;
	}
	
	public int getBlockIndex() {
		return mBlockIndex;
	}
	
	public void setBlockIndex(int blockIndex) throws Exception {
		if (!BreakpointsConstraints.isBlockIndexLegal(blockIndex)) {
			throw BreakpointsConstraints.reportSetterException(SetterError.ILLEGAL_BLOCK);
		}
		
		mBlockIndex = blockIndex;
	}
	
	public int getPlaneIndex() {
		return mPlaneIndex;
	}

	public void setPlaneIndex(int planeIndex) throws Exception {
		if (!BreakpointsConstraints.isPlaneIndexLegal(planeIndex)) {
			throw BreakpointsConstraints.reportSetterException(SetterError.ILLEGAL_PLANE);
		}
		
		mPlaneIndex = planeIndex;
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
	public String getDescription() {
		return "Write physical page (<chip,plane,block,page>): "
				+ "<" 
				+ mChipIndex + ","
				+ mPlaneIndex + ","
				+ mBlockIndex + ","
				+ mPageIndex 
				+ ">";
	}

	@Override
	public String getDisplayName() {
		return "Write physical page P";
	}

	@Override
	public void addComponents() {
		mComponents.add(new BreakpointComponent("chipIndex", int.class, "Chip"));
		mComponents.add(new BreakpointComponent("planeIndex", int.class, "Plane"));
		mComponents.add(new BreakpointComponent("blockIndex", int.class, "Block"));
		mComponents.add(new BreakpointComponent("pageIndex", int.class, "Page index"));
	}

	@Override
	public boolean isEquals(IBreakpoint other) {
		if (!(other instanceof WritePp)) return false; 
		WritePp otherCasted = (WritePp) other;
		
		return mPageIndex == otherCasted.getPageIndex()
				&& mBlockIndex == otherCasted.getBlockIndex()
				&& mPlaneIndex == otherCasted.getPlaneIndex()
				&& mChipIndex == otherCasted.getChipIndex();
	}
	
	@Override
	public String getHitDescription() {
		return "Physical page (<chip,plane,block,page>): "
				+ "<" 
				+ mChipIndex + ","
				+ mPlaneIndex + ","
				+ mBlockIndex + ","
				+ mPageIndex 
				+ "> was written";
	}
}
