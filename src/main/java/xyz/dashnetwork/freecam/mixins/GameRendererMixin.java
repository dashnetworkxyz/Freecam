package xyz.dashnetwork.freecam.mixins;

import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.dashnetwork.freecam.FreecamMod;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "shouldRenderBlockOutline", at = @At("HEAD"), cancellable = true)
    private void freecam$drawBlockOutline(CallbackInfoReturnable<Boolean> cir) {
        if (FreecamMod.get().isEnabled())
            cir.setReturnValue(false);
    }

}
