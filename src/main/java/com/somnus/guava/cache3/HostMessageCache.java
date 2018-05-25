package com.somnus.guava.cache3;

import lombok.extern.slf4j.Slf4j;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author lyl
 * @version V1.0
 * @project monitor-api
 * @package com.gsta.monitor.modules.notice.manager
 * @date 2018/05/25 13:11
 * @description
 */

@Slf4j
public class HostMessageCache extends BaseCache<String, HostMessage> implements ILocalCacheStrategy<String, HostMessage> {

    @Override
    public HostMessage get(String key) {
        try {
            return getValue(key);
        } catch (Exception e) {
            log.error("无法根据key获取缓存数据", key, e);
            return null;
        }
    }

    @Override
    protected HostMessage fetchData(String key) {
        //获取计算机网络地址
        java.net.InetAddress addr = getAddress();
        String host = null;
        String ip = null;
        if (addr != null) {
            host = addr.getHostName();
            ip = addr.getHostAddress();
        }
        HostMessage address = new HostMessage();
        address.setHost(host);
        address.setIp(ip);
        return address;
    }

    public InetAddress getAddress() {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    // 排除loopback类型地址
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            return InetAddress.getLocalHost();
        } catch (Exception e) {
            log.error("获取网络地址信息失败", e);
        }
        return null;
    }
}
