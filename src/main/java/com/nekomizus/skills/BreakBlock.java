package com.nekomizus.skills;

import com.nekomizus.network.PacketToClient;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.function.Supplier;

public class BreakBlock {
    private long last = 0;
    private long now;
    private int xpDisplay = 0;
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player.level().isClientSide || player.isCreative()) return;

        Supplier<AttachmentType<Integer>> skill = getSkill(event.getState());
        if (skill != null) {
            Block block = event.getState().getBlock();
            int current_xp = SkillsFunctions.getXP(skill, player);
            if (current_xp >= blockReq(block, skill)) {
                SkillsFunctions.addXP(skill, player, blockXP(block, skill));
                now = System.currentTimeMillis();
                if (now - last <= 7000) {
                    xpDisplay += blockXP(block, skill);
                } else {
                    xpDisplay = blockXP(block, skill);
                }
                last = now;
                player.displayClientMessage(
                        Component.literal(xpDisplay + " XP gained!"),
                        true
                );
                PacketToClient.PacketToClient(player);

            } else {
                event.setCanceled(true);
                player.displayClientMessage(
                        Component.literal("Requires level: " + SkillsFunctions.toLevel(blockReq(block, skill))),
                        true
                );
            }
        }
    }

    private int blockReq(Block block, Supplier<AttachmentType<Integer>> skill) {
        if (skill == SkillsRegistry.MINING) {
            if (block == Blocks.STONE || block == Blocks.DEEPSLATE || block == Blocks.NETHERRACK) return 0;
            if (block == Blocks.COAL_ORE || block == Blocks.DEEPSLATE_COAL_ORE) return 1154;
            if (block == Blocks.COPPER_ORE || block == Blocks.DEEPSLATE_COPPER_ORE) return 1154;
            if (block == Blocks.IRON_ORE || block == Blocks.DEEPSLATE_IRON_ORE) return 4470;
            if (block == Blocks.GOLD_ORE || block == Blocks.DEEPSLATE_GOLD_ORE) return 13_363;
            if (block == Blocks.REDSTONE_ORE || block == Blocks.DEEPSLATE_REDSTONE_ORE) return 22_406;
            if (block == Blocks.LAPIS_ORE || block == Blocks.DEEPSLATE_LAPIS_ORE) return 37_224;
            if (block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE) return 101_333;
            if (block == Blocks.EMERALD_ORE || block == Blocks.DEEPSLATE_EMERALD_ORE) return 166_636;
            if (block == Blocks.ANCIENT_DEBRIS) return 737_627;
        }
        if (skill == SkillsRegistry.WOODCUTTING) {
            if (block == Blocks.OAK_LOG) return 0;
            if (block == Blocks.SPRUCE_LOG) return 388;
            if (block == Blocks.BIRCH_LOG) return 1154;
            if (block == Blocks.JUNGLE_LOG) return 4470;
            if (block == Blocks.ACACIA_LOG) return 13_363;
            if (block == Blocks.DARK_OAK_LOG) return 37_224;
            if (block == Blocks.MANGROVE_LOG) return 101_333;
            if (block == Blocks.CHERRY_LOG) return 273_742;
            if (block == Blocks.CRIMSON_STEM) return 1_986_068;
            if (block == Blocks.WARPED_STEM) return 5_346_332;
        }


        return 0;
    }

    private int blockXP(Block block, Supplier<AttachmentType<Integer>> skill) {
        if (skill == SkillsRegistry.MINING) {
            if (block == Blocks.STONE || block == Blocks.DEEPSLATE || block == Blocks.NETHERRACK) return 5;
            if (block == Blocks.COAL_ORE || block == Blocks.DEEPSLATE_COAL_ORE) return 20;
            if (block == Blocks.COPPER_ORE || block == Blocks.DEEPSLATE_COPPER_ORE) return 25;
            if (block == Blocks.IRON_ORE || block == Blocks.DEEPSLATE_IRON_ORE) return 40;
            if (block == Blocks.GOLD_ORE || block == Blocks.DEEPSLATE_GOLD_ORE) return 80;
            if (block == Blocks.REDSTONE_ORE || block == Blocks.DEEPSLATE_REDSTONE_ORE) return 100;
            if (block == Blocks.LAPIS_ORE || block == Blocks.DEEPSLATE_LAPIS_ORE) return 100;
            if (block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE) return 200;
            if (block == Blocks.EMERALD_ORE || block == Blocks.DEEPSLATE_EMERALD_ORE) return 1000;
            if (block == Blocks.ANCIENT_DEBRIS) return 1500;
        }
        if (skill == SkillsRegistry.WOODCUTTING) {
            if (block == Blocks.OAK_LOG) return 10;
            if (block == Blocks.SPRUCE_LOG) return 20;
            if (block == Blocks.BIRCH_LOG) return 40;
            if (block == Blocks.JUNGLE_LOG) return 80;
            if (block == Blocks.ACACIA_LOG) return 160;
            if (block == Blocks.DARK_OAK_LOG) return 320;
            if (block == Blocks.MANGROVE_LOG) return 640;
            if (block == Blocks.CHERRY_LOG) return 1280;
            if (block == Blocks.CRIMSON_STEM) return 2560;
            if (block == Blocks.WARPED_STEM) return 5120;
        }

        return 0;
    }

    private Supplier<AttachmentType<Integer>> getSkill(BlockState state) {
        if (state.is(BlockTags.LOGS)) return SkillsRegistry.WOODCUTTING;
        if (state.is(Tags.Blocks.ORES)) return SkillsRegistry.MINING;
        if (state.getBlock() == Blocks.STONE || state.getBlock() == Blocks.DEEPSLATE) return SkillsRegistry.MINING;
        if (state.getBlock() == Blocks.NETHERRACK) return SkillsRegistry.MINING;
        if (state.getBlock() == Blocks.ANCIENT_DEBRIS) return SkillsRegistry.MINING;

        return null;
    }
}
