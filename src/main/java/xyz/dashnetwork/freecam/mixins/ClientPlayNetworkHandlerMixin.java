package xyz.dashnetwork.freecam.mixins;

import net.minecraft.client.network.handler.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.dashnetwork.freecam.FreecamMod;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(method = "handlePlayerRespawn", at = @At("TAIL"))
    private void freecam$onRespawn(CallbackInfo ci) {
        if (FreecamMod.get().isEnabled())
            FreecamMod.get().disableFreecam();
    }

}
