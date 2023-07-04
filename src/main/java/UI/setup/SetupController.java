package UI.setup;

import UI.FrameController;
import control.GameManager;

public class SetupController extends FrameController {
    public SetupController() {
        super(new Setup());
    }

    @Override
    public void select(String selection) {
        switch (selection) {
            case "ok" -> {
                var text = ((Setup) frame).name.getText();
                GameManager.getInstance().setName(text);
            }
            case "cancel" -> {
                hide();
                GameManager.getInstance().showWelcome();
            }
        }
    }
}
