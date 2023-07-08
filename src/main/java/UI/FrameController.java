package UI;

public abstract class FrameController {
    protected final Navigable frame;

    private boolean onScreen;

    public FrameController(Navigable frame) {
        this.frame = frame;
        frame.setController(this);
    }

    public void show() {
        if (!onScreen) {
            frame.setVisible(true);
            onScreen = true;
        }
    }

    public void hide() {
        if (onScreen) {
            frame.setVisible(false);
            onScreen = false;
        }
    }

    public abstract void select(String selection);
}
