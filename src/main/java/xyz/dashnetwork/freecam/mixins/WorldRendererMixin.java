package xyz.dashnetwork.freecam.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.world.WorldRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Redirect(
            method = "renderEntities",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getCamera()Lnet/minecraft/entity/Entity;", ordinal = 1)
    )
    private Entity freecam$renderSelf(Minecraft minecraft) {
        return minecraft.player;
    }

}
