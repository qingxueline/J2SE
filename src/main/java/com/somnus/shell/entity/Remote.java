package com.somnus.shell.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author lyl
 * @version 2020/5/31 0031 23:34:13
 */
@Builder
@Data
public class Remote {
    private String user;
    private String host;
    private int port;
    private String password;
}
