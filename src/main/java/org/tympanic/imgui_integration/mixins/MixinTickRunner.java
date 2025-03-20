package org.tympanic.imgui_integration.mixins;

import finalforeach.cosmicreach.TickRunner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.tympanic.imgui_integration.imgui.ImGuiManager;

@Mixin(TickRunner.class)
public class MixinTickRunner {
    @Inject(method = "runTicks", at = @At("TAIL"))
    private void tick(CallbackInfo callbackInfo) {
        ImGuiManager.INSTANCE.tick();
    }
}
