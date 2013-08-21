package lotus.master.message;

import lotus.common.perf.InfoPM;



/**
 * General monitor information message contains some. Use
 * getGeneralMonitorInfo() to get an object that contains monitor information
 * 
 * @author zhaoxun87@gmail.com
 * 
 */
public class PnodePerfMessage {
	
	public PnodePerfMessage(){
		
	}
	
	public PnodePerfMessage(InfoPM infoPM){
		this.infoPM = infoPM;
		
	}
	
	public InfoPM infoPM;
	
}
