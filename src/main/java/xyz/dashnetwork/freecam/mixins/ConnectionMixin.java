package xyz.dashnetwork.freecam.mixins;

import net.minecraft.network.Connection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ArmSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerHandActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.dashnetwork.freecam.FreecamMod;

@Mixin(Connection.class)
public class ConnectionMixin {

    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void freecam$onSendPacket(Packet<?> packet, CallbackInfo ci) {
        if (FreecamMod.get().isEnabled()) {
            if (packet instanceof PlayerHandActionC2SPacket
                    || packet instanceof ArmSwingC2SPacket
                    || packet instanceof PlayerInteractEntityC2SPacket)
                ci.cancel();
        }
    }

}
