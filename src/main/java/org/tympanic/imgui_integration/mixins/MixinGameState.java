package org.tympanic.imgui_integration.mixins;

import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.MainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.tympanic.imgui_integration.imgui.ImGuiManager;

@Mixin(GameState.class)
public class MixinGameState {
    @Unique
    GameState _this = (GameState) (Object) this;

    @Inject(method = "create", at = @At(value = "TAIL"))
    public void create(CallbackInfo callbackInfo) {
        if (_this instanceof MainMenu && !ImGuiManager.INSTANCE.hasBeenInitialized) {
            ImGuiManager.INSTANCE.init();
        }
    }
}
