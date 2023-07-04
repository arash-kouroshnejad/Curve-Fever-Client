package UI.welcome;

import UI.FrameController;
import control.GameManager;

public class WelcomeController extends FrameController {
    public WelcomeController() {
        super(new WelcomePage());
    }

    @Override
    public void select(String selection) {
        if ("join".equals(selection)) {
            GameManager.getInstance().joinServer();
        }
    }
}
