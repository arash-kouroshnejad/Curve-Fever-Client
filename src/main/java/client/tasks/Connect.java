package client.tasks;

import common.net.agent.AbstractAgent;
import common.net.agent.NetworkingPolicies;
import common.net.data.Entity;
import common.net.rdp.RDPConnection;
import common.net.tcp.TCPConnection;
import common.net.udp.UDPConnection;

import java.io.IOException;

public class Connect implements Runnable{

    String remoteName;
    int remotePort;
    AbstractAgent self;
    Entity server;
    NetworkingPolicies policies;


    public Connect(String remoteName, int remotePort, AbstractAgent self, NetworkingPolicies policies) {
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
            udpConnection = new RDPConnection();
            udpConnection.connectTo(remoteName, remotePort, "localhost", 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        var server = new Entity(tcpConnection, udpConnection, 0);
        this.server = server;
        self.registerEntity(server);
        System.out.println("Client connected to remote host : " + remoteName + ":" + remotePort);
    }

    public Entity getServer() {
        return server;
    }
}
