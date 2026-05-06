package xyz.dashnetwork.freecam.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.handler.ClientPlayNetworkHandler;
import net.minecraft.network.packet.Packet;

import java.util.UUID;

public class DummyClientPlayNetworkHandler extends ClientPlayNetworkHandler {

    public DummyClientPlayNetworkHandler(Minecraft minecraft) {
        super(minecraft, minecraft.screen, minecraft.getNetworkHandler().getConnection(), new GameProfile(UUID.randomUUID(), "dummy"));
    }

    @Override
    public void sendPacket(Packet packet) {
        // No-op
    }

}
