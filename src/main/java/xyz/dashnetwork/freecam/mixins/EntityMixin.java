package xyz.dashnetwork.freecam.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.dashnetwork.freecam.FreecamMod;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    public abstract boolean equals(Object object);

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Inject(method = "updateLocalPlayerCamera", at = @At("HEAD"), cancellable = true)
    private void freecam$onRotationChange(float yaw, float pitch, CallbackInfo ci) {
        if (FreecamMod.get().isEnabled() && equals(Minecraft.getInstance().player)) {
            FreecamMod.get().getEntity().updateLocalPlayerCamera(yaw, pitch);
            ci.cancel();
        }
    }

}
