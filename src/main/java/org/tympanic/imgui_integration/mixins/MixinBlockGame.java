package org.tympanic.imgui_integration.mixins;

import com.badlogic.gdx.utils.Logger;
import com.github.puzzle.game.engine.ClientGameLoader;
import finalforeach.cosmicreach.BlockGame;
import finalforeach.cosmicreach.gamestates.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.tympanic.imgui_integration.imgui.ImGuiManager;
import static org.tympanic.imgui_integration.ClientInitializer.LOGGER;
import static org.tympanic.imgui_integration.ClientInitializer.MOD_ID;

@Mixin(BlockGame.class)
public class MixinBlockGame {
    @Inject(method = "render", at = @At("TAIL"))
    public void render(CallbackInfo callbackInfo) {
        try {
            ImGuiManager.INSTANCE.render();
        } catch (Exception e) {
            LOGGER.error("{} has run into a error during rendering", ImGuiManager.class.getCanonicalName());
            LOGGER.error("GameState during error: {}", GameState.currentGameState.getClass().getCanonicalName());
            e.printStackTrace();
        }
    }

    @Inject(method = "dispose", at = @At("TAIL"))
    public void dispose(CallbackInfo callbackInfo) {
        if (ImGuiManager.INSTANCE.hasBeenInitialized) {
            ImGuiManager.INSTANCE.dispose();
        }
    }
}