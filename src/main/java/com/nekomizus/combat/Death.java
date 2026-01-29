package com.nekomizus.combat;

import com.nekomizus.network.PacketToClient;
import com.nekomizus.skills.SkillsFunctions;
import com.nekomizus.skills.SkillsRegistry;
import com.nekomizus.skills.XPMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.MobDespawnEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

public class Death {
    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        DamageSource source = event.getSource();
        if (!(source.getEntity() instanceof Player player)) return;
        if (!(event.getEntity() instanceof LivingEntity entity)) return;

        float health = entity.getMaxHealth();

        int experience = (int) (health * 4f);

        SkillsFunctions.addXP(SkillsRegistry.COMBAT, player, experience);
        XPMessage.message(player, (int) (health + 4f));
        PacketToClient.PacketToClient(player);

    }



}
