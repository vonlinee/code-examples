package org.lancoo.crm.utils;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;

public class SSHExample {

    // 192.168.65.130
    static final String host = "165.154.104.175";

    // 需要 RSA 密钥
    public static void main(String[] args) {
        String username = "root";
        String password = "juege-tech-88888888";
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(username, host, 22);
            session.setPassword(password);
            // avoid generating KnownHosts
            session.setConfig("StrictHostKeyChecking", "no");

            // Properties config = new Properties();
            // config.put("cipher.c2s", "aes128-cbc,aes256-cbc");
            // config.put("cipher.s2c", "aes128-cbc,aes256-cbc");

            // jsch.setKnownHosts("C:/Users/nithidol.v/.ssh/known_hosts_web");

            // 以密码方式认证
            session.setConfig("PreferredAuthentications", "password");
//            session.setConfig("userauth.none", "com.jcraft.jsch.UserAuthPassword");

            // https://stackoverflow.com/questions/37280442/jsch-0-1-53-session-connect-throws-end-of-io-stream-read
            // session.setConfig("kex", "diffie-hellman-group1-sha1");

            session.connect();


            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand("ls -l");
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();
            channel.connect();

            byte[] buffer = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int bytesRead = in.read(buffer, 0, 1024);
                    if (bytesRead < 0) break;
                    System.out.print(new String(buffer, 0, bytesRead));
                }
                if (channel.isClosed()) {
                    if (in.available() > 0) continue;
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            }
            channel.disconnect();
            session.disconnect();
        } catch (JSchException | IOException e) {
            e.printStackTrace();
        }
    }
}