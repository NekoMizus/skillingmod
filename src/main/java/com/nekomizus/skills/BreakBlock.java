package com.nekomizus.skills;

import com.nekomizus.network.PacketToClient;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.level.BlockEvent;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Supplier;

public class BreakBlock {
    private long last = 0;
    private long now = 0;
    private int xpDisplay = 0;
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player.level().isClientSide || player.isCreative()) return;

        Supplier<AttachmentType<Integer>> skill = getSkill(event.getState());
        if (skill == null) return;

        Block block = event.getState().getBlock();
        BlockSkillData blockSkillData = getBlockStats(block, skill);
        int current_xp = SkillsFunctions.getXP(skill, player);

        if (blockSkillData == null) {
            if (skill == SkillsRegistry.FARMING) {
                CropSkillData cropSkillData = CROP_BLOCKS.get(block);
                if (cropSkillData == null) return;

                if (current_xp < cropSkillData.reqXP) {
                    event.setCanceled(true);
                    player.displayClientMessage(
                            Component.literal("Requires level: " + SkillsFunctions.toLevel(cropSkillData.reqXP)),
                            true
                    );
                } else {
                    if (event.getState().getValue(CropBlock.AGE) < cropSkillData.age) return;

                    int old_level = SkillsFunctions.toLevel(SkillsFunctions.getXP(skill, player));
                    SkillsFunctions.addXP(skill, player, (int) (cropSkillData.baseXP));

                    XPMessage.message(player, (int) cropSkillData.baseXP);
                    PacketToClient.PacketToClient(player);
                }

            } else {
                return;
            }
        } else if (current_xp < blockSkillData.reqXP) {
            event.setCanceled(true);
            player.displayClientMessage(
                    Component.literal("Requires level: " + SkillsFunctions.toLevel(blockSkillData.reqXP)),
                    true
            );
        } else {
            System.out.println(blockSkillData);
            int old_level = SkillsFunctions.toLevel(SkillsFunctions.getXP(skill, player));
            SkillsFunctions.addXP(skill, player, (int) (blockSkillData.baseXP));
            XPMessage.message(player, (int) blockSkillData.baseXP);
            PacketToClient.PacketToClient(player);
        }
    }

    record BlockSkillData(int reqXP, float baseXP) {}
    record CropSkillData(int reqXP, float baseXP, int age) {}

    private static final Map<Block, BlockSkillData> MINING_BLOCKS = Map.ofEntries(
            Map.entry(Blocks.STONE, new BlockSkillData(0, 5f)),
            Map.entry(Blocks.DEEPSLATE, new BlockSkillData(0, 5f)),
            Map.entry(Blocks.NETHERRACK, new BlockSkillData(0, 5f)),
            Map.entry(Blocks.COAL_ORE, new BlockSkillData(388, 15f)),
            Map.entry(Blocks.DEEPSLATE_COAL_ORE, new BlockSkillData(388, 15f)),
            Map.entry(Blocks.COPPER_ORE, new BlockSkillData(1154, 25f)),
            Map.entry(Blocks.DEEPSLATE_COPPER_ORE, new BlockSkillData(1154, 25f)),
            Map.entry(Blocks.ANDESITE, new BlockSkillData(1154, 20f)),
            Map.entry(Blocks.GRANITE, new BlockSkillData(2411, 25f)),
            Map.entry(Blocks.GLOWSTONE, new BlockSkillData(2411, 30f)),
            Map.entry(Blocks.IRON_ORE, new BlockSkillData(4470, 40f)),
            Map.entry(Blocks.DEEPSLATE_IRON_ORE, new BlockSkillData(4470, 40f)),
            Map.entry(Blocks.GOLD_ORE, new BlockSkillData(13_363, 80f)),
            Map.entry(Blocks.DEEPSLATE_GOLD_ORE, new BlockSkillData(13_363, 80f)),
            Map.entry(Blocks.REDSTONE_ORE, new BlockSkillData(22_406, 100f)),
            Map.entry(Blocks.DEEPSLATE_REDSTONE_ORE, new BlockSkillData(22_406, 100f)),
            Map.entry(Blocks.LAPIS_ORE, new BlockSkillData(37_224, 120f)),
            Map.entry(Blocks.DEEPSLATE_LAPIS_ORE, new BlockSkillData(37_224, 100f)),
            Map.entry(Blocks.AMETHYST_BLOCK, new BlockSkillData(61_514, 150f)),
            Map.entry(Blocks.AMETHYST_CLUSTER, new BlockSkillData(61_512, 80f)),
            Map.entry(Blocks.DIAMOND_ORE, new BlockSkillData(101_333, 160f)),
            Map.entry(Blocks.DEEPSLATE_DIAMOND_ORE, new BlockSkillData(101_333, 160f)),
            Map.entry(Blocks.EMERALD_ORE, new BlockSkillData(166_636, 200f)),
            Map.entry(Blocks.DEEPSLATE_EMERALD_ORE, new BlockSkillData(166_636, 1000f)),
            Map.entry(Blocks.ANCIENT_DEBRIS, new BlockSkillData(737_627, 2000f))
    );

    private static final Map<Block, BlockSkillData> WOODCUT_BLOCKS = Map.ofEntries(
            Map.entry(Blocks.OAK_LOG, new BlockSkillData(0, 10f)),
            Map.entry(Blocks.SPRUCE_LOG, new BlockSkillData(388, 20f)),
            Map.entry(Blocks.BIRCH_LOG, new BlockSkillData(1154, 30f)),
            Map.entry(Blocks.JUNGLE_LOG, new BlockSkillData(4470, 50f)),
            Map.entry(Blocks.ACACIA_LOG, new BlockSkillData(13_363, 70f)),
            Map.entry(Blocks.DARK_OAK_LOG, new BlockSkillData(37_224, 100f)),
            Map.entry(Blocks.MANGROVE_LOG, new BlockSkillData(101_333, 1400f)),
            Map.entry(Blocks.CHERRY_LOG, new BlockSkillData(273_742, 190f)),
            Map.entry(Blocks.CRIMSON_STEM, new BlockSkillData(1_986_068, 250f)),
            Map.entry(Blocks.WARPED_STEM, new BlockSkillData(5_346_332, 320f))
    );

    private static final Map<Block, CropSkillData> CROP_BLOCKS = Map.ofEntries(
            Map.entry(Blocks.WHEAT, new CropSkillData(0, 5f, 7)),
            Map.entry(Blocks.BEETROOTS, new CropSkillData(388, 20f, 3)),
            Map.entry(Blocks.CACTUS, new CropSkillData(388, 15f, 0)),
            Map.entry(Blocks.CARROTS, new CropSkillData(1154, 40f, 7)),
            Map.entry(Blocks.SUGAR_CANE, new CropSkillData(1154, 35f, 0)),
            Map.entry(Blocks.SWEET_BERRY_BUSH, new CropSkillData(2411, 20f, 3)),
            Map.entry(Blocks.POTATOES, new CropSkillData(4470, 80f, 7)),
            Map.entry(Blocks.KELP, new CropSkillData(4470, 55f, 0)),
            Map.entry(Blocks.COCOA, new CropSkillData(4470, 45f, 2)),
            Map.entry(Blocks.BAMBOO, new CropSkillData(7842, 160f, 1)),
            Map.entry(Blocks.MELON, new CropSkillData(13_363, 320f, 0)),
            Map.entry(Blocks.PUMPKIN, new CropSkillData(37_224, 640f, 0)),
            Map.entry(Blocks.SEA_PICKLE, new CropSkillData(37_224, 200f, 0)),
            Map.entry(Blocks.NETHER_WART, new CropSkillData(61_512, 250f, 3)),
            Map.entry(Blocks.TORCHFLOWER, new CropSkillData(101_333, 500f, 0)),
            Map.entry(Blocks.PITCHER_CROP, new CropSkillData(101_333, 500f, 4)),
            Map.entry(Blocks.CHORUS_PLANT, new CropSkillData(449_428, 1000f, 0))
    );

    @Nullable
    private static BlockSkillData getBlockStats(Block block, Supplier<AttachmentType<Integer>> skill) {
        if (skill == SkillsRegistry.MINING) {
            return MINING_BLOCKS.get(block);
        }

        if (skill == SkillsRegistry.WOODCUTTING) {
            return WOODCUT_BLOCKS.get(block);
        }

        return null;
    }

    @Nullable
    private Supplier<AttachmentType<Integer>> getSkill(BlockState state) {
        System.out.println(state);
        if (state.is(BlockTags.LOGS)) return SkillsRegistry.WOODCUTTING;
        if (state.is(Tags.Blocks.ORES)) return SkillsRegistry.MINING;
        if (state.getBlock() == Blocks.STONE || state.getBlock() == Blocks.DEEPSLATE) return SkillsRegistry.MINING;
        if (state.getBlock() == Blocks.NETHERRACK) return SkillsRegistry.MINING;
        if (state.getBlock() == Blocks.ANCIENT_DEBRIS) return SkillsRegistry.MINING;
        if (state.is(BlockTags.CROPS)) return SkillsRegistry.FARMING;
        if (state.is(Blocks.SUGAR_CANE)) return SkillsRegistry.FARMING;
        if (state.is(Blocks.BAMBOO)) return SkillsRegistry.FARMING;
        if (state.is(Blocks.KELP)) return SkillsRegistry.FARMING;
        if (state.is(Blocks.SWEET_BERRY_BUSH)) return SkillsRegistry.FARMING;
        if (state.is(Blocks.COCOA)) return SkillsRegistry.FARMING;
        if (state.is(Blocks.PUMPKIN)) return SkillsRegistry.FARMING;
        if (state.is(Blocks.MELON)) return SkillsRegistry.FARMING;
        if (state.is(Blocks.CHORUS_PLANT)) return SkillsRegistry.FARMING;
        if (state.is(Blocks.SEA_PICKLE)) return SkillsRegistry.FARMING;

        return null;
    }
}
