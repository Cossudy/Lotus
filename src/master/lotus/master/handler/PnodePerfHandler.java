package lotus.master.handler;


import java.io.File;
import java.io.IOException;

import lotus.common.service.SimpleAddress;
import lotus.common.service.SimpleHandler;
import lotus.master.message.PnodePerfMessage;
import lotus.common.util.RRDTools;
import lotus.master.models.Pnode;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jrobin.core.RrdDb;
import org.jrobin.core.RrdException;
import org.jrobin.core.Util;

/**
 * Save monitor information into RRD. Some thing todo by zhaoxun
 * 
 * @author zhaoxun87@gmail.com
 * 
 */
public class PnodePerfHandler implements SimpleHandler<PnodePerfMessage> {

    Logger logger = Logger.getLogger(PnodePerfHandler.class);

    @Override
    public void handleMessage(PnodePerfMessage msg, ChannelHandlerContext ctx,
            MessageEvent e, SimpleAddress xreply) {
        // TODO @zhaoxun get pair of uuid/rrdPath from database

        Pnode pnode = Pnode.findByIp(xreply.ip);
        if (pnode != null) {
            String rrdPath = "build/" + pnode.getId() + ".rrd";

            int timeInterval = 5;
            int rrdLength = 50000;

            File file = new File(rrdPath);
            File folder = new File("build");
            if (folder.exists() == false) {
                folder.mkdirs();
            }
            if (file.exists() == false) {
                RRDTools.CreateMonitorInfoRRD(rrdPath, timeInterval, rrdLength);
                logger.info(xreply.ip + ": RRD file is created!");
            }
            try {
                RrdDb rrdDb = new RrdDb(rrdPath);
                RRDTools.addMonitorInfoPMInRRD(rrdDb, msg.infoPM, Util.getTime());
                rrdDb.close();
            } catch (IOException ex) {
                logger.error("Error updating RRD", ex);
            } catch (RrdException ex) {
                logger.error("Error updating RRD", ex);
            }

            logger.info("Got GeneralMonitorInfo from " + xreply);
        }
    }
}
