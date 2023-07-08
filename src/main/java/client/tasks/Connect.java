package client.tasks;

import common.net.agent.AbstractAgent;
import common.net.agent.Ping;
import common.net.agent.PolicyStack;
import common.net.data.Entity;
import common.net.tcp.TCPConnection;
import common.net.udp.UDPConnection;

import java.io.IOException;

public class Connect implements Runnable{

    String remoteName;
    int remotePort;
    AbstractAgent self;
    Entity server;
    PolicyStack policies;


    public Connect(String remoteName, int remotePort, AbstractAgent self, PolicyStack policies) {
        this.remoteName = remoteName;
        this.remotePort = remotePort;
        this.self = self;
        this.policies = policies;
    }

    @Override
    public void run() {
        TCPConnection tcpConnection = null;
        UDPConnection udpConnection = null;
        try {
            tcpConnection = new TCPConnection();
            tcpConnection.connectTo(remoteName, remotePort, "localhost", 0);
            udpConnection = new UDPConnection();
            udpConnection.connectTo(remoteName, remotePort, "localhost", 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        var server = new Entity(tcpConnection, udpConnection, 0);
        /*try {
            self.syncID(server);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        // new Ping(self, policies).start();
        this.server = server;
        self.registerEntity(server);
        System.out.println("Client connected to remote host : " + remoteName + ":" + remotePort);
    }

    public Entity getServer() {
        return server;
    }
}
