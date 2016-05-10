package breakpoints;

import entities.Device;
import entities.reusable.ReusableBlock;
import entities.reusable.ReusableBlockStatus;
import entities.reusable.ReusableDevice;
import manager.ReusableSSDManager;
import manager.ReusableVisualizationSSDManager;
import manager.SSDManager;

public class ReusableBlockRecycled extends BreakpointBase {

	private int mBlockIndex;
	private int mPlaneIndex;
	private int mChipIndex;

	public ReusableBlockRecycled() {
		super();
	}
	
	@Override
	public boolean breakpointHit(Device<?, ?, ?, ?> previousDevice,
			Device<?, ?, ?, ?> currentDevice) {
		if(!(currentDevice instanceof ReusableDevice)){
			return false;
		}
		if(previousDevice == null){
			return false;
		}
		
		ReusableBlock currentBlock = (ReusableBlock) currentDevice.getChip(mChipIndex)
				.getPlane(mPlaneIndex)
				.getBlock(mBlockIndex);
		ReusableBlock prevBlock = (ReusableBlock) previousDevice.getChip(mChipIndex)
				.getPlane(mPlaneIndex)
				.getBlock(mBlockIndex);
		return (currentBlock.getStatus() == ReusableBlockStatus.RECYCLED 
				&& prevBlock.getStatus() != ReusableBlockStatus.RECYCLED);
	}

	@Override
	public String getDisplayName() {
		return "Reusable block B is recycled";
	}

	@Override
	public String getDescription() {
		return "Reusable block (<chip,plane,block>): "
				+ "<" 
				+ mChipIndex + ","
				+ mPlaneIndex + ","
				+ mBlockIndex
				+ "> is recycled";
	}

	@Override
	public void addComponents() {
		mComponents.add(new BreakpointComponent("chipIndex", int.class, "Chip"));
		mComponents.add(new BreakpointComponent("planeIndex", int.class, "Plane"));
		mComponents.add(new BreakpointComponent("blockIndex", int.class, "Block"));
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
	public boolean isEquals(IBreakpoint other) {
		if (!(other instanceof ReusableBlockRecycled)) return false; 
		ReusableBlockRecycled otherCasted = (ReusableBlockRecycled) other;
		
		return mBlockIndex == otherCasted.getBlockIndex()
				&& mPlaneIndex == otherCasted.getPlaneIndex()
				&& mChipIndex == otherCasted.getChipIndex();
	}

	@Override
	public boolean isManagerSupported(SSDManager<?, ?, ?, ?, ?> manager) {
		if (manager instanceof ReusableSSDManager 
				|| manager instanceof ReusableVisualizationSSDManager) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public String getHitDescription() {
		return "Reusable block (<chip,plane,block>): "
				+ "<" 
				+ mChipIndex + ","
				+ mPlaneIndex + ","
				+ mBlockIndex
				+ "> was recycled";
	}
}
