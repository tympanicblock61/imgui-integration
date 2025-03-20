package org.tympanic.imgui_integration.imgui;

import com.github.puzzle.game.engine.ClientGameLoader;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.PrealphaPreamble;

import java.util.ArrayList;
import java.util.List;

public abstract class ImGuiWindow {
    private List<Class<? extends GameState>> renderIn = new ArrayList<>();
    public boolean hasBeenInitialized = false;
    public GameState ownState = null;

    public void renderIn(Class<? extends GameState> state) {
        renderIn.add(state);
    }

    public boolean rendersIn(Class<? extends GameState> state) {
        return renderIn.contains(state);
    }

    public abstract void init();
    public abstract void render();
    public abstract void tick();
    public abstract void dispose();

    public boolean hasOwnGamestate() {
        return ownState != null && rendersIn(ownState.getClass());
    }

    public void display() {
        if (!hasBeenInitialized) {
            init();
            hasBeenInitialized = true;
        }
        if (hasOwnGamestate()) GameState.switchToGameState(ownState);
    }
}
