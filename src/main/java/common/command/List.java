package common.command;

import common.net.data.Command;
import common.net.data.Entity;
import control.GameManager;

import java.util.Arrays;

public class List extends Command {

    public List(Entity recipient) {
        super(recipient);
        addHeader("connection-type", "tcp");
        addHeader("type", "list");
    }

    public List(Command incoming, Entity sender) {
        super(sender);
        addHeader("users", incoming.getHeader("users"));
    }

    @Override
    public boolean isValid(Command command) {
        return "list".equals(command.getHeader("type")) && command.getHeader("users") instanceof String;
    }

    @Override
    public void run() {
        var raw = (String) headers.get("users");
        var parsed = raw.substring(1, raw.length() - 1).split(",");
        var out = new String[parsed.length];
        for (int i = 0; i < parsed.length; i++) {
            out[i] = parsed[i].trim();
        }
        GameManager.getInstance().showLobby(out);
    }
}
