package client;

import client.tasks.Connect;
import common.net.agent.AbstractAgent;
import common.net.data.Command;
import common.net.data.Entity;
import common.policies.NetworkPolicies;

import java.io.IOException;
import java.util.concurrent.Executors;

public class Client extends AbstractAgent {
    String remoteName = "localhost";
    int remotePort = 9001;
    boolean connected;
    Entity server;

    public Client() {
        super(new NetworkPolicies(), 1);
    }

    public void init() {this.pool = Executors.newSingleThreadExecutor();}

    private void connect() {
        var connectTask = new Connect(remoteName, remotePort, this, policies);
        connectTask.run();
        server = connectTask.getServer();
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
