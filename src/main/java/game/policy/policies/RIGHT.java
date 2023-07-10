package game.policy.policies;


import control.GameManager;
import game.policy.KeyPolicy;

public class RIGHT extends KeyPolicy {

    @Override
    public boolean isEnforceable(int keyCode) {
        return keyCode == RIGHT;
    }

    @Override
    protected void press() {
        GameManager.getInstance().sendKeyEvent(RIGHT, true);
    }

    @Override
    protected void release() {
        GameManager.getInstance().sendKeyEvent(RIGHT, false);
    }
}
