package io.devpl.toolkit.core;

import lombok.Data;

/**
 * SSH连接信息
 */
@Data
public class SSHConnectionInfo {
    private String lport;
    private String rport;
    private String sshPort;
    private String sshHost;
    private String sshUser;
    private String sshPassword;
    private String privateKeyPassword;
    private String privateKey;
}
