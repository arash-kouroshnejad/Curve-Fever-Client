package game.util;

import common.gfx.editor.LevelEditor;
import common.gfx.objects.DynamicElement;
import common.persistence.Config;
import org.json.JSONArray;
import org.json.JSONException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Sync {
    final LevelEditor editor;
    final List<DynamicElement> dynamics;
    final int dynamicsLayer = Config.getInstance().getProperty("DynamicsLayer", Integer.class);



    public Sync(LevelEditor editor) {
        this.editor = editor;
        dynamics = editor.getLayers().getALL_LAYERS().get(dynamicsLayer).getDynamicElements();
    }

    public void updateGame(Map<?, ?> input) {
        if (input.containsKey("state"))
            apply((String) input.get("state"));
        else if (input.containsKey("layer"))
            dropLayer((int) input.get("layer"));
        else
            dropElement((String)input.get("element"));
    }

    private void dropElement(String elementType) {
        editor.removeElement(elementType, dynamicsLayer);
    }


    private void dropLayer(int layerIndex) {
        editor.getLayers().getALL_LAYERS().get(layerIndex).getStaticElements().clear();
    }

    private void apply(String json) {
        JSONArray array;
        try {
            array = new JSONArray(json);

        } catch (JSONException exception) {
            exception.printStackTrace();
            return;
        }
        try {
            outer:
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
                        continue outer;
                    }
                editor.insertAt(type, x, y, 0, speedX, speedY, dynamicsLayer);
            }
        } catch (ClassCastException | NumberFormatException ignored) {}
    }
}
