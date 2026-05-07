package xyz.dashnetwork.freecam;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.Input;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.world.WorldSettings;
import net.ornithemc.osl.entrypoints.api.client.ClientModInitializer;
import net.ornithemc.osl.keybinds.api.KeyBindingEvents;
import net.ornithemc.osl.lifecycle.api.client.ClientWorldEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import xyz.dashnetwork.freecam.entity.CameraEntity;

public class FreecamMod implements ClientModInitializer {

    private static FreecamMod instance;

    public static FreecamMod get() { return instance; }

    private final Logger logger = LogManager.getLogger("Freecam");
    private KeyBinding freecamBinding;
    private CameraEntity cameraEntity;
    private boolean freecam;

    public FreecamMod() { instance = this; }

    public boolean isEnabled() { return freecam; }

    public CameraEntity getEntity() { return cameraEntity; }

    @Override
    public void initClient() {
        freecamBinding = new KeyBinding("Toggle Freecam", Keyboard.KEY_X, "Freecam");

        KeyBindingEvents.REGISTER_KEYBINDS.register(listener -> listener.register(freecamBinding));
        ClientWorldEvents.TICK_END.register(ignored -> {
            while (freecamBinding.consumeClick()) {
                toggleFreecam();
            }
        });
    }

    public void toggleFreecam() {
        if (freecam)
            disableFreecam();
        else
            enableFreecam();
    }

    public void enableFreecam() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalClientPlayerEntity player = minecraft.player;

        cameraEntity = new CameraEntity(minecraft);
        cameraEntity.spawn();
        cameraEntity.setGameMode(WorldSettings.GameMode.SPECTATOR);
        cameraEntity.setPositionAndAngles(player.x, player.y, player.z, player.yaw, player.pitch);
        cameraEntity.inventory.items = player.inventory.items;
        cameraEntity.input = player.input;

        player.input = new Input();
        player.setJumping(false);
        player.forwardSpeed = 0;
        player.sidewaysSpeed = 0;

        minecraft.setCamera(cameraEntity);
        minecraft.options.perspective = 0;

        minecraft.gui.setOverlayMessage("Freecam enabled", false);
        logger.info("Freecam enabled");
        freecam = true;
    }

    public void disableFreecam() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalClientPlayerEntity player = minecraft.player;

        cameraEntity.despawn();

        player.input = cameraEntity.input;
        minecraft.setCamera(player);
        minecraft.worldRenderer.reload();
        minecraft.getEntityRenderDispatcher().camera = player;

        cameraEntity = null;

        minecraft.gui.setOverlayMessage("Freecam disabled", false);
        logger.info("Freecam disabled");
        freecam = false;
    }

}
