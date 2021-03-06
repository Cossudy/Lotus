package lotus.common.service;

/**
 * Internal class to wrap messages to be sent. DO NOT USE IT DIRECTLY!
 * 
 * @author zhaoxun87@gmail.com
 * 
 */
class Xpacket {

    protected Xpacket() {

    }

    protected SimpleAddress xreply;

    // protected Integer xid;

    protected String xtype;

    protected Object xvalue;

    // protected static AtomicInteger xidCounter = new AtomicInteger();

    public static Xpacket createPacket(String name, Object obj,
            SimpleAddress xreply) {
        Xpacket packet = new Xpacket();
        packet.xreply = xreply;
        // packet.xid = xidCounter.incrementAndGet();
        packet.xtype = name;
        packet.xvalue = obj;
        return packet;
    }
}
