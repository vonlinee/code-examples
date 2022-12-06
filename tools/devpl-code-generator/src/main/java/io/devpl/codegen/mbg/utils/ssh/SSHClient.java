package io.devpl.codegen.mbg.utils.ssh;

import org.apache.sshd.client.ClientBuilder;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.future.ConnectFuture;
import org.apache.sshd.client.session.ClientSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;

/**
 * 封装
 */
public class SSHClient {

    private final Logger log = LoggerFactory.getLogger(SSHClient.class);

    private final SshClient sshClient = ClientBuilder.builder()
            .build(true);

    private static final long DEFAULT_TIMEOUT = 3000; // 默认3秒

    public void start() {
        if (!sshClient.isStarted()) {
            sshClient.start();
        }
    }

    public ClientSession connect(String host, int port, String username, String password) throws IOException {
        return connect(host, port, username, password, Duration.ofMillis(DEFAULT_TIMEOUT));
    }

    public ClientSession connect(String host, int port, String username, String password, Duration timeout) throws IOException {
        ConnectFuture future = sshClient.connect(username, host, port);
        ClientSession clientSession = null;
        if (future.await(timeout)) {
            clientSession = future.getClientSession();
        }
        if (clientSession == null) {
            log.error("failed to connect {}:{}", host, port);
            throw new IOException();
        }
        clientSession.addPasswordIdentity(password);
        // 添加公钥文件
        // session.addPublicKeyIdentity(SecurityUtils.loadKeyPairIdentity("keyname", new FileInputStream("priKey.pem"), null));
        if (!clientSession.auth().isSuccess()) {
            log.error("failed to connect: {}:{}, auth failed", host, port);
        }
        return clientSession;
    }

    public static void main(String[] args) throws IOException {
        final SSHClient client = new SSHClient();
        client.start();
        final ClientSession session = client.connect("localhost", 22, "root", "123456");

        System.out.println(session);

        while (true) {

        }
    }
}
