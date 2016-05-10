package breakpoints;

import java.util.List;

import entities.ActionLog;
import entities.CleanAction;
import entities.Device;
import entities.IDeviceAction;

public class VictimBlockHasValidPagesDevice extends BreakpointBase {
	private int mCount;
	private int mChipIndex;
	private int mPlaneIndex;
	
	@Override
	public boolean breakpointHit(Device<?, ?, ?, ?> previousDevice,
			Device<?, ?, ?, ?> currentDevice) {
		List<IDeviceAction> cleanActions = ActionLog.getActionsByType(CleanAction.class);
		for(IDeviceAction action : cleanActions){
			CleanAction cleanAction = (CleanAction)action;
			if(cleanAction.getValidPAges() == mCount){
				mPlaneIndex = cleanAction.getPlaneIndex();
				mChipIndex = cleanAction.getChipIndex();
				
				return true;
			}
		}
		
		return false;
	}

	@Override
	public String getDisplayName() {
		return "Victim block in device has number of valid pages";
	}

	@Override
	public String getDescription() {
		return "Victim block has " + mCount + " valid pages";
	}

	@Override
	public void addComponents() {
		mComponents.add(new BreakpointComponent("count", int.class, "Number of valid pages"));

	}

	@Override
	public boolean isEquals(IBreakpoint other) {
		if (!(other instanceof VictimBlockHasValidPagesDevice)) return false; 
		VictimBlockHasValidPagesDevice otherCasted = (VictimBlockHasValidPagesDevice) other;
		
		return mCount == otherCasted.getCount();
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

	@Override
	public String getHitDescription() {
		return "Victim block in plane (<Chip,Plane>): "
				+ "<" + mChipIndex + "," + mPlaneIndex + "> "
				+ "reached " + mCount + " valid pages";
	}
}
