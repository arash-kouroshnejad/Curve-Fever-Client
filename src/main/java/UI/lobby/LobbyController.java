package UI.lobby;

import UI.FrameController;
import control.GameManager;

public class LobbyController extends FrameController {
    public LobbyController() {
        super(new Lobby());
    }

    public void setUsers(String[] names) {
        ((Lobby)frame).clearNames();
        for (var name : names)
            ((Lobby) frame).addName(name);
    }

    public void showOffer(String sender) {
        ((Lobby) frame).showInvite(sender);
    }

    @Override
    public void select(String selection) {
        if ("back".equals(selection)) {
            hide();
            GameManager.getInstance().showSetup();
        }
        else if ("accept".equals(selection.split(" ")[0]))
            GameManager.getInstance().accept(selection.split(" ")[1]);
        else if ("decline".equals(selection.split(" ")[0]))
            ((Lobby) frame).removeInvite();
        else
            GameManager.getInstance().invitePlayer(selection);
    }
}
