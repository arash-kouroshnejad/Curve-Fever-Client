package common.command;

import common.net.data.Command;
import common.net.data.Entity;
import control.GameManager;

import java.io.Serial;

public class Sync extends Command {

    @Serial
    private static final long serialVersionUID = 7508476361235375817L;

    public Sync(Entity entity) {
        super(entity);
        addHeader("connection-type", "tcp");
        addHeader("type", "sync");
    }

    public Sync(Command incoming, Entity sender) {
        super(sender);
        addHeader("state", incoming.getHeader("state"));
    }

    @Override
    public boolean isValid(Command command) {
        return "sync".equals(command.getHeader("type")) && command.getHeader("state") instanceof String;
    }

    @Override
    public void run() {
        GameManager.getInstance().syncWorlds((String) getHeader("state"));
    }
}
