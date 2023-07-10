package common.game.logic;

import common.gfx.util.Logic;
import common.util.Routine;

public class LogicLoop extends Routine {
    public LogicLoop(Logic logic) {
        super(50, logic::check);
    }
}
