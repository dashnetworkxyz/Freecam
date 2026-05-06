package xyz.dashnetwork.freecam.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.living.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.dashnetwork.freecam.FreecamMod;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Inject(method = "swingArm", at = @At("HEAD"), cancellable = true)
    private void freecam$onSwing(CallbackInfo ci) {
        if (FreecamMod.get().isEnabled() && equals(Minecraft.getInstance().player)) {
            ci.cancel();
        }
    }

}
