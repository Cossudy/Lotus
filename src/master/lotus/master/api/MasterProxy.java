package lotus.master.api;

import java.net.InetSocketAddress;

import lotus.common.perf.InfoPM;
import lotus.common.perf.InfoVM;
import lotus.common.service.SimpleAddress;
import lotus.common.service.SimpleProxy;
import lotus.master.message.*;


/**
 * Proxy for Master node.
 * 
 * @author zhaoxun87@gmail.com
 * 
 */
public class MasterProxy extends SimpleProxy {

    public MasterProxy(InetSocketAddress bindAddr) {
        super(bindAddr);
    }

    public MasterProxy(SimpleAddress replyAddr) {
        super(replyAddr);
    }

    /**
     * Report a heartbeat to Master node.
     */
    public void sendPnodeHeartbeat() {
        super.sendRequest(new PnodeHeartbeatMessage());
    }

    /**
     * Send a monitor info to master node.
     */
    public void sendPnodePerInfo(InfoPM infoPM) {
        super.sendRequest(new PnodePerfMessage(infoPM));
    }

    public void sendVnodePerInfo(InfoVM infoVM) {
        super.sendRequest(new VnodePerfMessage(infoVM));
    }
    
    public void sendAddPnode(SimpleAddress pAddr) {
        super.sendRequest(new AddPnodeMessage(pAddr));
    }

    public void sendDeletePnode(long id) {
        super.sendRequest(new DeletePnodeMessage(id));
    }

}
