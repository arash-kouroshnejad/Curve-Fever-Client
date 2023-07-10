package common.command;

import common.net.data.Command;
import common.net.data.Entity;

import java.io.Serial;

public class KeyCommand extends Command {
    @Serial
    private static final long serialVersionUID = 4005191642270925226L;
    public KeyCommand(Entity entity) {
        super(entity);
        addHeader("connection-type", "tcp");
        addHeader("type", "key-pressed");
    }
}
