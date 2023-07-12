package game;

import common.game.load.GameLoader;
import common.game.logic.Colour;
import common.game.logic.LogicLoop;
import common.gfx.editor.LevelEditor;
import common.gfx.objects.DynamicElement;
import common.gfx.render.GameEngine;
import common.persistence.Config;
import common.util.Routine;
import game.logic.GameLogic;
import game.policy.KeyStack;
import game.policy.policies.LEFT;
import game.policy.policies.RIGHT;
import game.util.Sync;

import java.util.List;
import java.util.Map;

public class GameContainer {
    final List<DynamicElement> dynamics;
    final int dynamicsLayer = Config.getInstance().getProperty("DynamicsLayer", Integer.class);
    final GameEngine engine;
    final LevelEditor levelEditor;
    final GameLogic gameLogic;
    final Routine logicLoop;
    final KeyStack keyStack;
    final Sync sync;



    public GameContainer (Colour colour) {
        engine = new GameEngine();
        gameLogic = new GameLogic();
        engine.init(gameLogic);
        var gameLoader = new GameLoader(Config.getInstance().getProperty("DefaultMapsDir"), engine);
        gameLoader.loadMap(0, 0);
        levelEditor = new LevelEditor();
        levelEditor.enableHeadless(gameLoader, engine);
        gameLogic.init(levelEditor);
        gameLogic.setPlayer(colour);
        logicLoop = new LogicLoop(gameLogic);
        logicLoop.start();
        dynamics = levelEditor.getLayers().getALL_LAYERS().get(dynamicsLayer).getDynamicElements();
        keyStack = KeyStack.getInstance();
        keyStack.getKeyPolicies().add(new RIGHT());
        keyStack.getKeyPolicies().add(new LEFT());
        sync = new Sync(levelEditor);
    }

    public void killGame() {
        keyStack.disableKeys();
        gameLogic.killGame();
        engine.closeGame();
    }

    public void sync(Map<?, ?> headers) {
        sync.updateGame(headers);
    }
}
