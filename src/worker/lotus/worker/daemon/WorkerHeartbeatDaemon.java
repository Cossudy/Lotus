package lotus.worker.daemon;

import lotus.common.service.SimpleDaemon;
import lotus.master.api.MasterProxy;
import lotus.worker.LotusWorker;

/**
 * A daemons that sends hearbeat message to Master node.
 * 
 * @author zhaoxun87@gmail.com
 * 
 */
public class WorkerHeartbeatDaemon extends SimpleDaemon {

    public static final long HEARTBEAT_INTERVAL = 1000;

    public WorkerHeartbeatDaemon() {
        super(HEARTBEAT_INTERVAL);
    }

    /**
     * send heart beat to master
     */
    @Override
    protected void workOneRound() {
        if (this.isStopping() == false) {
            MasterProxy master = LotusWorker.getInstance().getMaster();
            if (master != null) {
                master.sendPnodeHeartbeat();
            }
        }
    }
}
