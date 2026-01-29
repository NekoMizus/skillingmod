package com.nekomizus.network;

import com.nekomizus.skills.SkillsRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

public class PacketToClient {
    public static void PacketToClient(Player player) {
        if (player instanceof ServerPlayer sp) {
            int miningXP = sp.getData(SkillsRegistry.MINING.get());
            int woodcuttingXP = sp.getData(SkillsRegistry.WOODCUTTING.get());
            int farmingXP = sp.getData(SkillsRegistry.FARMING.get());
            int combatXP = sp.getData(SkillsRegistry.COMBAT.get());
            PacketDistributor.sendToPlayer(sp, new SkillData(miningXP, woodcuttingXP, farmingXP, combatXP));
        }
    }
}
