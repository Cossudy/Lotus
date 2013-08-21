package lotus.master;


import java.net.InetSocketAddress;
import java.util.HashMap;

import lotus.common.db.HibernateUtil;
import lotus.common.service.SimpleAddress;
import lotus.common.service.SimpleDaemon;
import lotus.common.service.SimpleServer;
import lotus.common.util.Conf;

import lotus.master.models.Pnode;
import lotus.worker.api.WorkerProxy;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;

/**
 * Master node of Nova system.
 * 
 * @author zhaoxun87@gmail.com
 * 
 */
public class LotusMaster extends SimpleServer {

    SimpleAddress addr = new SimpleAddress(Conf.getString("master.bind_host"),
            Conf.getInteger("master.bind_port"));

    /**
     * All background working daemons for master node.
     */
   
    //SimpleDaemon daemons[] = { new PnodeCheckerDaemon() };
    SimpleDaemon daemons[] = { };

    HashMap<SimpleAddress, WorkerProxy> workerProxyPool = new HashMap<SimpleAddress, WorkerProxy>();

    /**
     * Constructor made private for singleton pattern.
     */
    private LotusMaster() {

        // register handlers

//        this.registerHandler(PnodeHeartbeatMessage.class,
//                new MasterPnodeHeartbeatHandler());
//
//        this.registerHandler(AddPnodeMessage.class,
//        		new AddPnodeHandler());
//
//        this.registerHandler(PnodePerfMessage.class,
//                new MasterPnodePerfHandler());
//
//        this.registerHandler(VnodePerfMessage.class,
//                new MasterAgentPerfHandler());

        /////////////////////////////////////////////////////
        Conf.setDefaultValue("master.bind_host", "0.0.0.0");
        Conf.setDefaultValue("master.bind_port", 3000);
    }

    public SimpleAddress getAddr() {
        return this.addr;
    }

    /**
     * Start server.
     * 
     * @return
     */
    public Channel start() {
        logger.info("Nova master running @ " + this.addr);
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
        logger.info("Shutting down NovaMaster");
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
        this.addr = null;
        // TODO @zhaoxun more cleanup work
        HibernateUtil.shutdown();

        LotusMaster.instance = null;
    }

    public WorkerProxy getWorkerProxy(final SimpleAddress pAddr) {
        if (workerProxyPool.get(pAddr) == null) {
            WorkerProxy wp = new WorkerProxy(this.addr) {

                @Override
                public void exceptionCaught(ChannelHandlerContext ctx,
                        ExceptionEvent e) {
                    Pnode pnode = Pnode.findByIp(pAddr.ip);
                    if (pnode != null) {
                        pnode.setStatus(Pnode.Status.CONNECT_FAILURE);
                        logger.info("Update status of pnode @ "
                                + pnode.getAddr() + " to " + pnode.getStatus());
                        pnode.save();
                    } else {
                        logger.error("Pnode with host " + pAddr.ip
                                + " not found!");
                    }
                    super.exceptionCaught(ctx, e);
                }

            };
            wp.connect(new InetSocketAddress(pAddr.getIp(), pAddr.getPort()));
            workerProxyPool.put(pAddr, wp);
        }
        return workerProxyPool.get(pAddr);
    }

    /**
     * Log4j logger.
     */
    static Logger logger = Logger.getLogger(LotusMaster.class);

    /**
     * Singleton instance of NovaMaster.
     */
    private static LotusMaster instance = null;

    /**
     * Get the singleton of NovaMaster.
     * 
     * @return NovaMaster instance, singleton.
     */
    public static synchronized LotusMaster getInstance() {
        if (LotusMaster.instance == null) {
            LotusMaster.instance = new LotusMaster();
        }
        return LotusMaster.instance;
    }

    /**
     * Application entry of NovaMaster.
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
                if (LotusMaster.instance != null) {
                    // do cleanup work
                    this.setName("cleanup");
                    LotusMaster.getInstance().shutdown();
                    logger.info("Cleanup work done");
                }
            }

        });
        // TimeInfo.setVClusterInfo("test", "10.0.1.100", 1);
        LotusMaster.getInstance().start();
    }
}
