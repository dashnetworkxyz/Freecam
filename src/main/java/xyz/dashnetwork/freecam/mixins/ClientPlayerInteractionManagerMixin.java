package xyz.dashnetwork.freecam.mixins;

import net.minecraft.client.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.dashnetwork.freecam.FreecamMod;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
    private void freecam$onAttackEntity(CallbackInfo ci) {
        if (FreecamMod.get().isEnabled())
            ci.cancel();
    }

    @Inject(method = "interactEntity", at = @At("HEAD"), cancellable = true)
    private void freecam$onInteract(CallbackInfoReturnable<Boolean> cir) {
        if (FreecamMod.get().isEnabled())
            cir.setReturnValue(false);
    }

    @Inject(method = "tickBlockMining", at = @At("HEAD"), cancellable = true)
    private void freecam$onAttackBlock(CallbackInfoReturnable<Boolean> cir) {
        if (FreecamMod.get().isEnabled())
            cir.setReturnValue(false);
    }

    @Inject(method = "startMiningBlock", at = @At("HEAD"), cancellable = true)
    private void freecam$onMineBlock(CallbackInfoReturnable<Boolean> cir) {
        if (FreecamMod.get().isEnabled())
            cir.setReturnValue(false);
    }

    @Inject(method = "useBlock", at = @At("HEAD"), cancellable = true)
    private void freecam$onUseBlock(CallbackInfoReturnable<Boolean> cir) {
        if (FreecamMod.get().isEnabled())
            cir.setReturnValue(false);
    }

}
