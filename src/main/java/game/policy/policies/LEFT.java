package game.policy.policies;


import control.GameManager;
import game.policy.KeyPolicy;

public class LEFT extends KeyPolicy {

    @Override
    public boolean isEnforceable(int keyCode) {
        return keyCode == LEFT;
    }

    @Override
    protected void press() {
        GameManager.getInstance().sendKeyEvent(LEFT, true);
    }

    @Override
    protected void release() {
        GameManager.getInstance().sendKeyEvent(LEFT, false);
    }
}
