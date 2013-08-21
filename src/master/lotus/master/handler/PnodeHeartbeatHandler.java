package lotus.master.handler;

import lotus.common.service.SimpleAddress;
import lotus.common.service.SimpleHandler;
import lotus.master.message.PnodeHeartbeatMessage;
import lotus.master.models.Pnode;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;

public class PnodeHeartbeatHandler implements
        SimpleHandler<PnodeHeartbeatMessage> {

    /**
     * Log4j logger.
     */
    Logger log = Logger.getLogger(PnodeHeartbeatHandler.class);

    @Override
    public void handleMessage(PnodeHeartbeatMessage msg,
            ChannelHandlerContext ctx, MessageEvent e, SimpleAddress xreply) {

        if (xreply == null) {
            log.warn("Got a pnode heartbeat message, but the reply address is null!");
        } else {
            // log.info("Got pnode heartbeat message from: " + xreply);
        }

        // TODO @zhaoxun possibly update vnode
        Pnode pnode = Pnode.findByIp(xreply.ip);
        if (pnode != null) {
            pnode.setStatus(Pnode.Status.RUNNING);
            // log.info("Update status of pnode @ " + pnode.getAddr() + " to "
            // + pnode.getStatus());
            pnode.save();
        } else {
            log.error("Pnode with host " + xreply.ip + " not found!");
        }

    }

}