package lotus.master.message;

import lotus.common.perf.InfoVM;

/**
 * General monitor information message contains some. Use
 * getGeneralMonitorInfo() to get an object that contains monitor information
 * 
 * @author zhaoxun87@gmail.com
 * 
 */

public class VnodePerfMessage {

	public VnodePerfMessage(){
		
	}
	
	public VnodePerfMessage(InfoVM infoVM){
		this.infoVM = infoVM;
		
	}
	
	public InfoVM infoVM;

}
