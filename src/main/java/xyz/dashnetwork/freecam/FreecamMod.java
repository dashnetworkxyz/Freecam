package xyz.dashnetwork.freecam;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.Input;
import net.minecraft.client.entity.living.player.KeyboardInput;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.world.WorldSettings;
import net.ornithemc.osl.entrypoints.api.client.ClientModInitializer;
import net.ornithemc.osl.keybinds.api.KeyBindingEvents;
import net.ornithemc.osl.lifecycle.api.client.ClientWorldEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import xyz.dashnetwork.freecam.entity.FreecamEntity;

public class FreecamMod implements ClientModInitializer {

    private static FreecamMod instance;

    public static FreecamMod get() { return instance; }

    private final Logger logger = LogManager.getLogger("Freecam");
    private KeyBinding freecamBinding;
    private FreecamEntity freecamEntity;
    private boolean freecam;

    public FreecamMod() { instance = this; }

    public boolean isEnabled() { return freecam; }

    public FreecamEntity getEntity() { return freecamEntity; }

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

        freecamEntity = new FreecamEntity(minecraft);
        freecamEntity.spawn();
        freecamEntity.setPositionAndAngles(player.x, player.y, player.z, player.yaw, player.pitch);
        freecamEntity.inventory.items = player.inventory.items;
        freecamEntity.input = new KeyboardInput(minecraft.options);
        player.input = new Input();
        freecamEntity.setGameMode(WorldSettings.GameMode.SPECTATOR);
        minecraft.setCamera(freecamEntity);
        minecraft.options.perspective = 0;
        player.setJumping(false);
        player.forwardSpeed = 0;
        player.sidewaysSpeed = 0;

        minecraft.gui.setOverlayMessage("Freecam enabled", false);
        logger.info("Freecam enabled");
        freecam = true;
    }

    public void disableFreecam() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalClientPlayerEntity player = minecraft.player;

        freecamEntity.despawn();

        player.input = freecamEntity.input;
        minecraft.setCamera(player);
        minecraft.worldRenderer.reload();
        minecraft.getEntityRenderDispatcher().camera = player;

        freecamEntity = null;

        minecraft.gui.setOverlayMessage("Freecam disabled", false);
        logger.info("Freecam disabled");
        freecam = false;
    }

}
