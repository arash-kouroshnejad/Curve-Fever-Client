package client;

import common.net.agent.AbstractAgent;
import common.net.agent.Ping;
import common.net.data.Command;
import common.net.data.Entity;
import common.net.tcp.TCPConnection;
import common.net.udp.UDPConnection;
import common.policies.NetworkPolicies;

import java.io.IOException;

public class Client extends AbstractAgent {
    String remoteName = "localhost";
    int remotePort = 9000;
    boolean connected;
    Entity server;

    public Client() {
        super(new NetworkPolicies(), 1);
    }

    private void connect() throws IOException {
        var tcpConnection = new TCPConnection();
        tcpConnection.connectTo(remoteName, remotePort, "localhost", 0);
        var udpConnection = new UDPConnection();
        udpConnection.connectTo(remoteName, remotePort, "localhost", 0);
        var server = new Entity(tcpConnection, udpConnection, 0);
        entities.add(server);
        syncID(server);
        System.out.println("Client connected to remote host : " + remoteName + ":" + remotePort);
        enableReceiving();
        new Ping(this, policies).start();
        this.server = server;
    }
    public void start() throws IOException {
        if (!connected)
            connect();
        else
            throw new RuntimeException("Client already connected!");
    }

    public void syncID(Entity entity) throws IOException {
        var packet = entity.getTcp().fetch();
        entity.setId(packet.id);
    }

    public Entity getServer() {
        return server;
    }

    public Command read() {
        if (inbound.isEmpty())
            return null;
        else return inbound.remove().command;
    }
}
