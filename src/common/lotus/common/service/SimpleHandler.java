package lotus.common.service;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;

/**
 * Interface for message handlers.
 * 
 * @author zhaoxun87@gmail.com
 * 
 * @param <T>
 *            Type of the message to be handled.
 */
public interface SimpleHandler<T> {

    /**
     * Handle a certain message.
     * 
     * @param msg
     *            The message to be handled.
     * @param ctx
     *            The {@link ChannelHandlerContext}.
     * @param e
     *            The {@link MessageEvent}.
     * @param xreply
     *            Where you should reply the message to.
     */
    public void handleMessage(T msg, ChannelHandlerContext ctx, MessageEvent e,
            SimpleAddress xreply);
}
