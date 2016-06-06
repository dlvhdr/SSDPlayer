package breakpoints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Element;

import ui.breakpoints.BreakpointUIComponent;

public class BreakpointFactory {
	private static HashMap<String, BreakpointDescriptor> mTypeMap;
	
	public static void initBreakpointFactory() {
		mTypeMap = new HashMap<>();
		
		register(WriteLp.class);
		register(WritePp.class);
		register(AllocateActiveBlock.class);
		register(EraseBlock.class);
		register(WriteAmplification.class);
		register(WritesPerErase.class);
		register(EraseCountBlock.class);
		register(EraseCountAnyBlock.class);
		register(CleanBlocksPlane.class);
		register(CleanBlocksChip.class);
		register(CleanBlocksDevice.class);
		register(PagesWrittenDevice.class);
		register(PagesWrittenChip.class);
		register(PagesWrittenPlane.class);
		register(HotColdPartitionHoldsPercentOfBlocks.class);
		register(ReusableLevelBlocksPercent.class);
		register(ReusableBlockRecycled.class);
		register(GCNthTimePlane.class);
		register(GCNthTimeChip.class);
		register(GCNthTimeDevice.class);
		register(HotColdWriteAmplification.class);
		register(ReusableAnyBlockRecycled.class);
		register(VictimBlockHasValidPagesPlane.class);
		register(VictimBlockHasValidPagesChip.class);
		register(VictimBlockHasValidPagesDevice.class);
		register(ParityOverhead.class);
		register(WriteInStripe.class);
	}
	
	public static BreakpointBase getBreakpoint(String type, Element breakpointElement) throws Exception {
		BreakpointBase breakpoint = (BreakpointBase) mTypeMap.get(type).getBreakpointClass().newInstance();
		breakpoint.readXml(breakpointElement);
		return breakpoint;
	}
	
	public static List<Class<? extends BreakpointBase>> getBreakpointClasses() {
		List<Class<? extends BreakpointBase>> result = new ArrayList<>();
		for (BreakpointDescriptor descriptor : mTypeMap.values()) {
			result.add(descriptor.getBreakpointClass());
		}
		
		return result;
	}
	
	public static List<BreakpointUIComponent> getBreakpointUIComponents(Class<? extends IBreakpoint> bpClass) {
		return mTypeMap.get(bpClass.getSimpleName()).getUIComponents();
	}
	
	private static void register(Class<? extends BreakpointBase> bpClass) {
		mTypeMap.put(bpClass.getSimpleName(), new BreakpointDescriptor(bpClass));
	}
}
