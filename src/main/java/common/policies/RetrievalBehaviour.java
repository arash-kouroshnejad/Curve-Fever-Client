package common.policies;

import client.Client;
import common.net.agent.PacketValidator;

public class RetrievalBehaviour implements Runnable {
    Client client;
    PacketValidator validator;

    public RetrievalBehaviour(Client client, PacketValidator validator) {
        this.client = client;
        this.validator = validator;
    }

    @Override
    public void run() {
        var optional = client.readPacket();
        optional.ifPresent(packet -> validator.validate(packet));
    }
}
