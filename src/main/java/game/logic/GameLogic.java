package game.logic;


import common.game.logic.AbstractLogic;
import common.game.logic.Colour;
import common.gfx.objects.DynamicElement;
import game.policy.KeyStack;

public class GameLogic extends AbstractLogic {
    private KeyStack keyStack;

    public void setPlayer(Colour colour) {
        DynamicElement self = editor.getDynamicElement(colour.getType(), 2, 0).get();
        keyStack = KeyStack.getInstance();
    }

    @Override
    public void handleKeyPress(int keyCode) {
        // TODO : rotate
        for (var keyPolicy : keyStack.getKeyPolicies())
            if (keyPolicy.isEnforceable(keyCode))
                keyPolicy.enforce(keyCode, true);
    }

    @Override
    public void handleKeyRelease(int keyCode) {
        for (var keyPolicy : keyStack.getKeyPolicies())
            if (keyPolicy.isEnforceable(keyCode))
                keyPolicy.enforce(keyCode, false);
    }
}
