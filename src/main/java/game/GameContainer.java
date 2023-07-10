package game;

import common.game.load.GameLoader;
import common.game.logic.Colour;
import common.game.logic.LogicLoop;
import common.gfx.editor.LevelEditor;
import common.gfx.objects.DynamicElement;
import common.gfx.render.GameEngine;
import common.persistence.Config;
import game.policy.KeyStack;
import game.policy.policies.LEFT;
import game.policy.policies.RIGHT;
import org.json.JSONArray;
import org.json.JSONException;

import java.math.BigDecimal;
import java.util.List;

public class GameContainer {
    final List<DynamicElement> dynamics;
    final int dynamicsLayer = Config.getInstance().getProperty("DynamicsLayer", Integer.class);
    final GameEngine engine;
    final LevelEditor levelEditor;
    final GameLogic gameLogic;

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
        var logicLoop = new LogicLoop(gameLogic);
        logicLoop.start();
        dynamics = levelEditor.getLayers().getALL_LAYERS().get(dynamicsLayer).getDynamicElements();
        var stack = KeyStack.getInstance().getKeyPolicies();
        stack.add(new RIGHT());
        stack.add(new LEFT());
    }

    public void sync(String json) {
        JSONArray array;
        try {
            array = new JSONArray(json);

        } catch (JSONException exception) {
            exception.printStackTrace();
            return;
        }
        try {
            for (int i = 0; i < array.length(); i++) {
                var element = array.getJSONObject(i);
                var type = (String)element.get("type");
                var x = (int) element.get("x");
                var y = (int )element.get("y");
                var speedX = ((BigDecimal) element.get("speedX")).doubleValue();
                var speedY = ((BigDecimal) element.get("speedY")).doubleValue();
                for (var dynamic : dynamics)
                    if (dynamic.getType().equals(type)) {
                        dynamic.setX(x);
                        dynamic.setY(y);
                        dynamic.setSpeedX(speedX);
                        dynamic.setSpeedY(speedY);
                    }
            }
        } catch (ClassCastException | NumberFormatException ignored) {}
    }
}
