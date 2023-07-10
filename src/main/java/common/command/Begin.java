package common.command;

import common.net.data.Command;
import common.net.data.Entity;
import control.GameManager;

import java.io.Serial;

public class Begin extends Command {
    @Serial
    private static final long serialVersionUID = -7952553533562148062L;
    public Begin(Entity entity) {
        super(entity);
        addHeader("connection-type", "tcp");
        addHeader("type", "begin");
    }

    public Begin(Command incoming, Entity sender) {
        super(sender);
        addHeader("colour", incoming.getHeader("colour"));
    }

    @Override
    public boolean isValid(Command command) {
        return "begin".equals(command.getHeader("type")) && command.getHeader("colour") instanceof String;
    }

    @Override
    public void run() {
        try {
            GameManager.getInstance().setUpFrame((String) getHeader("colour"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
