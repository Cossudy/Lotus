package lotus.worker;


import java.util.HashMap;
import java.util.UUID;

import lotus.common.service.SimpleAddress;
import lotus.common.service.SimpleDaemon;
import lotus.common.service.SimpleServer;
import lotus.common.util.Conf;
import lotus.master.api.MasterProxy;
import lotus.worker.daemon.*;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;



/**
 * The worker module of Lotus.
 * 
 * @author zhaoxun87@gmail.com
 * 
 */
public class LotusWorker extends SimpleServer {

    SimpleAddress addr = new SimpleAddress(Conf.getString("worker.bind_host"),
            Conf.getInteger("worker.bind_port"));

    /**
     * All background working daemons for worker node.
     */
    SimpleDaemon daemons[] = { new WorkerHeartbeatDaemon(),
            new StatPMDaemon(), new StatPMDaemon() };

	//SimpleDaemon daemons[] = { };
    private Object connLock = new Object();

    public Object getConnLock() {
        return connLock;
    }

    public void setConnLock(Object connLock) {
        this.connLock = connLock;
    }

    /**
     * Connection to Lotus master.
     */
    MasterProxy master = null;

    /**
     * currently installed app list
     */
    HashMap<String, String> appStatus = new HashMap<String, String>();

    /**
     * vnode ip address
     */
    HashMap<UUID, String> vnodeIP = new HashMap<UUID, String>();

    public HashMap<UUID, String> getVnodeIP() {
        return vnodeIP;
    }

    public void setVnodeIP(HashMap<UUID, String> vnodeIP) {
        this.vnodeIP = vnodeIP;
    }

    public HashMap<String, String> getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(HashMap<String, String> appStatus) {
        this.appStatus = appStatus;
    }

    /**
     * Constructor made private for singleton pattern.
     */
    private LotusWorker() {
        // TODO @zhaoxun register handlers

        // handle http requests
//        this.registerHandler(DefaultHttpRequest.class, new WorkerHttpHandler());
//
//        this.registerHandler(StartVnodeMessage.class, new StartVnodeHandler());
//
//        this.registerHandler(StopVnodeMessage.class, new StopVnodeHandler());
//
//        this.registerHandler(QueryHeartbeatMessage.class,
//                new WorkerQueryHeartbeatHandler());
//
//        this.registerHandler(RevokeImageMessage.class, new RevokeImageHandler());
//
//        this.registerHandler(QueryPnodeInfoMessage.class,
//                new WorkerQueryPnodeInfoMessageHandler());
//
//        this.registerHandler(QueryVnodeInfoMessage.class,
//                new WorkerQueryVnodeInfoMessageHandler());
//
//        this.registerHandler(InstallApplianceMessage.class,
//                new InstallApplianceHandler());
//
//        this.registerHandler(MigrateVnodeMessage.class,
//                new MigrateVnodeHandler());
//
//        this.registerHandler(ObtainSshKeysMessage.class,
//                new ObtainSshKeysHandler());

    }

    public SimpleAddress getAddr() {
        return this.addr;
    }

    public Channel start() {
        logger.info("Lotus worker running @ " + this.addr);
        Channel chnl = super.bind(this.addr.getInetSocketAddress());
        // start all daemons
        for (SimpleDaemon daemon : this.daemons) {
            daemon.start();
        }
        logger.info("All deamons started");

        return chnl;
    }

    /**
     * Override the shutdown() function, do a few housekeeping work.
     */
    @Override
    public void shutdown() {
        logger.info("Shutting down LotusWorker");
        // stop all daemons
        for (SimpleDaemon daemon : this.daemons) {
            daemon.stopWork();
        }
        for (SimpleDaemon daemon : this.daemons) {
            try {
                daemon.join();
            } catch (InterruptedException e) {
                logger.error("Error joining thread " + daemon.getName(), e);
            }
        }
        logger.info("All deamons stopped");
        super.shutdown();
        // TODO @zhaoxun more cleanup work

        LotusWorker.instance = null;
    }

    /**
     * Get current master proxy.
     * 
     * @return Current master proxy. Could be <code>NULL</code>, when no master
     *         node has connected.
     */
    public MasterProxy getMaster() {
        return this.master;
    }

    public void registerMaster(SimpleAddress masterAddr) {
        MasterProxy proxy = new MasterProxy(this.addr);
        proxy.connect(masterAddr.getInetSocketAddress());
        this.master = proxy;
    }

    /**
     * Log4j logger.
     */
    static Logger logger = Logger.getLogger(LotusWorker.class);

    /**
     * Singleton instance of LotusWorker.
     */
    private static LotusWorker instance = null;

    /**
     * Get the singleton of LotusWorker.
     * 
     * @return LotusWorker instance, singleton.
     */
    public static synchronized LotusWorker getInstance() {
        if (LotusWorker.instance == null) {
            LotusWorker.instance = new LotusWorker();
        }
        return LotusWorker.instance;
    }

    /**
     * Application entry of LotusWorker.
     * 
     * @param args
     *            Environment variables.
     */
    public static void main(String[] args) {
        // add a shutdown hook, so a Ctrl-C or kill signal will be handled
        // gracefully
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (LotusWorker.instance != null) {
                    // do cleanup work
                    this.setName("cleanup");
                    LotusWorker.getInstance().shutdown();
                    logger.info("Cleanup work done");
                }
            }
        });
       
        LotusWorker.getInstance().start();
    }
}
