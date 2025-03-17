package org.tympanic.imgui_integration.imgui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import finalforeach.cosmicreach.gamestates.GameState;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.opengl.GL32;

import java.util.ArrayList;
import java.util.List;

public class ImGuiManager {
    private ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();
    private ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private InputProcessor tmpProcessor;
    public boolean hasBeenInitialized = false;
    public List<ImGuiWindow> windows = new ArrayList<>();
    public static ImGuiManager INSTANCE = new ImGuiManager();


    public void init() {
        if (!hasBeenInitialized) {
            long windowHandle = ((Lwjgl3Graphics) Gdx.graphics).getWindow().getWindowHandle();
            ImGui.createContext();
            ImGuiIO io = ImGui.getIO();
            io.setIniFilename(null);
            io.getFonts().addFontDefault();
            io.getFonts().build();
            imGuiGlfw.init(windowHandle, true);
            imGuiGl3.init("#version 150");
            int major = GL32.glGetInteger(GL32.GL_MAJOR_VERSION);
            int minor = GL32.glGetInteger(GL32.GL_MINOR_VERSION);
            System.out.println("OpenGL Version: " + major + "." + minor);
            hasBeenInitialized = true;
        }
    }

    public void start() {
        if (tmpProcessor != null) {
            Gdx.input.setInputProcessor(tmpProcessor);
            tmpProcessor = null;
        }

        imGuiGl3.newFrame();
        imGuiGlfw.newFrame();
        ImGui.newFrame();
    }

    public void render() {
        start();
        for (ImGuiWindow window : windows) {
            if (!window.hasBeenInitialized) {
                window.init();
                window.hasBeenInitialized = true;
            }
            if (window.rendersIn(GameState.currentGameState.getClass()) || window.rendersIn(GameState.class)) {
                window.render();
            }
        }
        end();
    }

    public void end() {
        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        if (ImGui.getIO().getWantCaptureKeyboard() || ImGui.getIO().getWantCaptureMouse()) {
            tmpProcessor = Gdx.input.getInputProcessor();
            Gdx.input.setInputProcessor(null);
        }
    }

    public void dispose() {
        imGuiGl3.shutdown();
        imGuiGl3 = null;
        imGuiGlfw.shutdown();
        imGuiGlfw = null;
        ImGui.destroyContext();

        for (ImGuiWindow window : windows) {
            if (window.hasBeenInitialized) {
                window.dispose();
            }
        }
    }
}