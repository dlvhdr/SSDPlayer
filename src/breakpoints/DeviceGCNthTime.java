package breakpoints;

import entities.Device;

public class DeviceGCNthTime extends BreakpointBase {
	private int mValue;
	
	public DeviceGCNthTime() {
		super();
	}
	
	@Override
	public boolean breakpointHit(Device<?, ?, ?, ?> previousDevice, Device<?, ?, ?, ?> currentDevice) {
		if (previousDevice == null) {
			return false;
		}
		
		int prevGCCount = previousDevice.getTotalGCInvocations();
		int currGCCount = currentDevice.getTotalGCInvocations();
		
		return prevGCCount != mValue && currGCCount == mValue;
	}
	
	public int getValue() {
		return mValue;
	}

	public void setValue(int value) {
		mValue = value;
	}

	@Override
	public String getDisplayName() {
		return "Invoke garbage collection in the i-th time in the device";
	}

	@Override
	public String getDescription() {
		return "Number of garbage collection invocations is " + getValue() + " in the device";
	}

	@Override
	public void addComponents() {
		mComponents.add(new BreakpointComponent("value", int.class, "Value"));
	}

}
