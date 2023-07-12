package common.command;


import common.net.data.Command;
import common.net.data.Entity;
import control.GameManager;

import java.io.Serial;

public class Drop extends Command {

    @Serial
    private static final long serialVersionUID = 3065269331400959563L;

    public Drop(Entity entity) {
        super(entity);
        addHeader("connection-type", "tcp");
        addHeader("type", "drop");
    }

    public Drop(Command incoming, Entity sender) {
        super(sender);
        addHeader("layer", incoming.getHeader("layer"));
        addHeader("element", incoming.getHeader("element"));
    }

    @Override
    public boolean isValid(Command command) {
        if ("drop".equals(command.getHeader("type")))
            return (command.getHeader("layer") instanceof Integer ||
                    command.getHeader("element") instanceof String);
        return false;
    }

    @Override
    public void run() {
        GameManager.getInstance().syncWorlds(headers);
    }
}
