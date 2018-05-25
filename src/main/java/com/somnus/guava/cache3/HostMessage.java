package com.somnus.guava.cache3;

import lombok.Data;

/**
 * @author lyl
 * @version V1.0
 * @project monitor-api
 * @package com.gsta.monitor.modules.notice.model
 * @date 2018/05/25 10:11
 * @description
 */
@Data
public class HostMessage {
    /**
     * ip地址
     **/
    private String ip;
    /**
     * 主机地址
     **/
    private String host;
}
