package xyz.dashnetwork.freecam.mixins;

import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.dashnetwork.freecam.FreecamMod;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Redirect(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;isSpectator()Z")
    )
    private boolean freecam$onTick(PlayerEntity entity) {
        return entity.isSpectator() || (FreecamMod.get().isEnabled() && entity.equals(FreecamMod.get().getEntity()));
    }

}
