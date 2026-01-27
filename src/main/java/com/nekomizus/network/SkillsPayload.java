package com.nekomizus.network;

import com.nekomizus.client.ClientCache;
import com.nekomizus.skilling.SkillingMod;
import com.nekomizus.skills.SkillsRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = SkillingMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class SkillsPayload {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
                SkillData.TYPE,
                SkillData.STREAM_CODEC,
                ClientCache::obtainXP
        );
    }
}

