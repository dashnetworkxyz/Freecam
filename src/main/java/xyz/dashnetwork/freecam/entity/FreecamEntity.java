package xyz.dashnetwork.freecam.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;

public class FreecamEntity extends LocalClientPlayerEntity {

    public FreecamEntity(Minecraft minecraft) {
        super(minecraft, minecraft.world, new DummyClientPlayNetworkHandler(minecraft), minecraft.player.getStats());
        abilities.canFly = true;
        abilities.flying = true;
    }

    public void spawn() { world.addEntity(this); }

    public void despawn() { world.removeEntity(this); }

    @Override
    public boolean isInWater() { return false; }

    @Override
    public void tick() {
        super.tick();
        abilities.setFlySpeed(0.05F); // TODO: User controlled speed?
        inventory.selectedSlot = minecraft.player.inventory.selectedSlot;
        setHealth(minecraft.player.getHealth());
    }

}
