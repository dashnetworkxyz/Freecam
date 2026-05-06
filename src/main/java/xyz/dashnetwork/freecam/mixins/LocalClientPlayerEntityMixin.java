package xyz.dashnetwork.freecam.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.dashnetwork.freecam.FreecamMod;

@Mixin(LocalClientPlayerEntity.class)
public class LocalClientPlayerEntityMixin {

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Inject(method = "isCamera", at = @At("HEAD"), cancellable = true)
    private void freecam$onIsCamera(CallbackInfoReturnable<Boolean> cir) {
        if (FreecamMod.get().isEnabled() && equals(Minecraft.getInstance().player))
            cir.setReturnValue(true);
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Redirect(
            method = "sendMovementToServer",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/living/player/LocalClientPlayerEntity;isCamera()Z")
    )
    private boolean freecam$onIsViewEntity(LocalClientPlayerEntity entity) {
        return equals(Minecraft.getInstance().player);
    }

}
