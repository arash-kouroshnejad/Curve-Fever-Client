package common.command;

import common.net.data.Command;
import common.net.data.Entity;
import control.GameManager;

import java.io.Serial;

public class Terminate extends Command {
    @Serial
    private static final long serialVersionUID = 5063045053368401026L;
    public Terminate(Entity entity) {
        super(entity);
        addHeader("type", "terminate");
        addHeader("connection-type", "tcp");
    }

    public Terminate(Command incoming, Entity sender) {
        super(sender);
    }

    @Override
    public boolean isValid(Command command) {
        return "terminate".equals(command.getHeader("type"));
    }

    @Override
    public void run() {
        GameManager.getInstance().terminateGame();
    }
}
