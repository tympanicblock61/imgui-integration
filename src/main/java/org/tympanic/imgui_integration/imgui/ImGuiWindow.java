package org.tympanic.imgui_integration.imgui;

import com.github.puzzle.game.engine.ClientGameLoader;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.PrealphaPreamble;

import java.util.ArrayList;
import java.util.List;

public abstract class ImGuiWindow {
    private List<Class<? extends GameState>> renderIn = new ArrayList<>();
    public boolean hasBeenInitialized = false;

    public void renderIn(Class<? extends GameState> state) {
        if (state == ClientGameLoader.class) {
            throw new RuntimeException("ImGuiWindow cannot be rendered in "+ClientGameLoader.class.getCanonicalName() +" because it causes a wmic error");
        }
        if (state == PrealphaPreamble.class) {
            throw new RuntimeException("ImGuiWindow cannot be rendered in "+PrealphaPreamble.class.getCanonicalName() +" because it causes a wmic error");
        }
        renderIn.add(state);
    }

    public boolean rendersIn(Class<? extends GameState> state) {
        return renderIn.contains(state);
    }

    public abstract void init();
    public abstract void render();
    public abstract void dispose();
}
