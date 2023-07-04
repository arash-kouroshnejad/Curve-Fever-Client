package control;

import UI.FrameController;
import UI.setup.SetupController;
import UI.welcome.WelcomeController;
import client.Client;
import common.net.data.Command;
import common.policies.RetrievalBehaviour;
import common.policies.Validator;
import common.util.CommandFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameManager {
    private final static GameManager instance = new GameManager();
    private GameManager() {}

    public static GameManager getInstance() {return instance;}

    private final ExecutorService executors = Executors.newSingleThreadExecutor();

    private final Client client = new Client();

    private final CommandFactory commandFactory = new CommandFactory();

    private final FrameController welcomeController = new WelcomeController();
    private final FrameController setupController = new SetupController();

    public void start() {
        client.setRetrievalAction(new RetrievalBehaviour(client, new Validator()));
        try {
            client.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        showWelcome();
    }

    public void showWelcome() {
        welcomeController.show();
    }

    public void showSetup() {
        welcomeController.hide();
        setupController.show();
    }

    public void joinServer() {
        client.send(commandFactory.join(client.getServer()));
    }

    public void setName(String name) {
        client.send(commandFactory.setName(client.getServer()).addHeader("name", name));
    }

    public void execute(Command command) {
        executors.submit(command);
    }
}
