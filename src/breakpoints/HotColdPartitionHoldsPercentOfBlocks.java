package breakpoints;

import entities.Device;
import entities.hot_cold.HotColdBlock;
import entities.hot_cold.HotColdDevice;
import general.ConfigProperties;
import manager.HotColdPartition;
import manager.HotColdReusableSSDManager;
import manager.HotColdSSDManager;
import manager.SSDManager;

public class HotColdPartitionHoldsPercentOfBlocks extends BreakpointBase {
	private int mPartition = 1;
	private int mPercent;

	public HotColdPartitionHoldsPercentOfBlocks() {
		super();
	}
	
	@Override
	public boolean breakpointHit(Device<?, ?, ?, ?> previousDevice,
			Device<?, ?, ?, ?> currentDevice) {
		if (!(currentDevice instanceof HotColdDevice)){
			return false;
		}
		
		int totalNumberOfBlocks = 0, numberOfBlocksInPartition = 0;
		HotColdPartition partition = ((HotColdDevice)currentDevice).getPartitions().get(mPartition);
		
		for (int i=0; i<ConfigProperties.getBlocksInDevice(); i++){
			HotColdBlock currentBlock = (HotColdBlock)currentDevice.getBlockByIndex(i);
			if(currentBlock.getPartition() == null){
				continue;
			}
			totalNumberOfBlocks++;
			if(currentBlock.getPartition().getDsiplayName().equals(partition.getDsiplayName())){
				numberOfBlocksInPartition++;
			}
		}
		if(totalNumberOfBlocks == 0){
			return false;
		}
		double percent = (numberOfBlocksInPartition / (double)totalNumberOfBlocks) * 100;
		return Math.abs(percent  - mPercent) < 1 ;
	}

	@Override
	public String getDisplayName() {
		return "HotCold partition holds percent of blocks";
	}

	@Override
	public String getDescription() {
		return "HotCold partition " + getPartition() + " holds " + getPercent() + " percent of blocks";
	}

	@Override
	public void addComponents() {
		mComponents.add(new BreakpointComponent("percent", int.class, "Percent of pages"));
		mComponents.add(new BreakpointComponent("partition", int.class, "Partition"));
	}

	public int getPartition() {
		return mPartition;
	}

	public void setPartition(int partition) throws Exception {
		if (!BreakpointsConstraints.isPartitionIndexLegal(partition)) {
			throw BreakpointsConstraints.reportSetterException(SetterError.ILLEGAL_PARTITION);
		}
		
		mPartition = partition;
	}

	public int getPercent() {
		return mPercent;
	}

	public void setPercent(int percent) throws Exception {
		if (!BreakpointsConstraints.isPercentValueLegal(percent)) {
			throw BreakpointsConstraints.reportSetterException(SetterError.ILLEGAL_PERCENT);
		}
		
		mPercent = percent;
	}

	@Override
	public boolean isEquals(IBreakpoint other) {
		if (!(other instanceof HotColdPartitionHoldsPercentOfBlocks)) return false; 
		HotColdPartitionHoldsPercentOfBlocks otherCasted = (HotColdPartitionHoldsPercentOfBlocks) other;
		
		return mPercent == otherCasted.getPercent()
				&& mPartition == otherCasted.getPartition();
	}

	@Override
	public boolean isManagerSupported(SSDManager<?, ?, ?, ?, ?> manager) {
		if (manager instanceof HotColdSSDManager 
				|| manager instanceof HotColdReusableSSDManager) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public String getHitDescription() {
		return "HotCold partition " + getPartition() + " now holds " + getPercent() + " percent of blocks";
	}
}