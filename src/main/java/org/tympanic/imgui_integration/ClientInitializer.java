package org.tympanic.imgui_integration;

import com.github.puzzle.core.loader.launch.provider.mod.entrypoint.impls.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientInitializer implements ClientModInitializer {
    public static String MOD_ID = "assets/imgui_integration";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInit() {
        LOGGER.info("{} init", MOD_ID);
    }
}
