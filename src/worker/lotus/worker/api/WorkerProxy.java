package lotus.worker.api;

import java.net.InetSocketAddress;

import lotus.common.service.SimpleAddress;
import lotus.common.service.SimpleProxy;


/**
 * Connection to worker module.
 * 
 * @author zhaoxun87@gmail.com
 * 
 */
public class WorkerProxy extends SimpleProxy {

    public WorkerProxy(InetSocketAddress bindAddr) {
        super(bindAddr);
    }

    public WorkerProxy(SimpleAddress replyAddr) {
        super(replyAddr);
    }

    /**
     * send message using defined values
     * 
     * author shayf
     * 
     * @param hyperVisor
     *            hypervisor type, use "kvm" or "xen" ignore case
     * 
     * @param name
     *            vm name
     * 
     * @param vAddr
     *            vnode address
     * 
     * @param memSize
     *            memory size, default 524288
     * 
     * @param cpuCount
     *            vcpu num, default 1
     * 
     * @param hdaImage
     *            hdaImage name, default "linux.img"
     * 
     * @param runAgent
     *            if need to run agent, set "true" ignore case
     */
//    public void sendStartVnode(String hyperVisor, String name,
//            SimpleAddress vAddr, String memSize, String cpuCount,
//            String hdaImage, boolean runAgent, String apps[], String ipAddr,
//            String subnetMask, String gateWay, String vnodeID) {
//        // 4th param false means wakeuponly = false
//        super.sendRequest(new StartVnodeMessage(hyperVisor, name, vAddr, false,
//                memSize, cpuCount, hdaImage, runAgent, apps, ipAddr,
//                subnetMask, gateWay, vnodeID));
//    }
//
//    public void sendWakeupVnode(String hyperVisor, boolean runAgent, String uuid) {
//        // 4th param false means wakeuponly = true
//        super.sendRequest(new StartVnodeMessage(hyperVisor, true, runAgent,
//                uuid));
//    }

    /**
     * destroy vm message
     * 
     * author shayf
     * 
     * @param hyperVisor
     *            hypervisor type, use "kvm" or "xen" ignore case
     * @param uuid
     *            uuid of vm to shut down
     */
//    public void sendStopVnode(String hyperVisor, String uuid,
//            boolean suspendonly) {
//        super.sendRequest(new StopVnodeMessage("-1", hyperVisor, uuid,
//                suspendonly));
//    }

    /**
     * author shayf
     * 
     * @param name
     *            the name of image file you want to del
     */
//    public void sendRevokeImage(String name) {
//        super.sendRequest(new RevokeImageMessage(name));
//    }
//
//    public void sendRequestHeartbeat() {
//        super.sendRequest(new QueryHeartbeatMessage());
//    }
//
//    public void sendRequestPnodeInfo() {
//        super.sendRequest(new QueryPnodeInfoMessage());
//    }
//
//    public void sendRequestVnodeInfo() {
//        super.sendRequest(new QueryVnodeInfoMessage());
//    }
//
//    public void sendInstallAppliance(String[] appNames) {
//        super.sendRequest(new InstallApplianceMessage(appNames));
//    }
//
//    public void sendMigrateVnode(String vnodeUuid, SimpleAddress migrateToAddr) {
//        super.sendRequest(new MigrateVnodeMessage(vnodeUuid, migrateToAddr));
//    }
//
//    public void sendObtainSshKeys(String vClusterName, String vmName) {
//        super.sendRequest(new ObtainSshKeysMessage(vClusterName, vmName));
//    }

}
