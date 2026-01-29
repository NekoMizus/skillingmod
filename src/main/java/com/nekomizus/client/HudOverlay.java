package com.nekomizus.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.nekomizus.skilling.SkillingMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;



@EventBusSubscriber(modid = SkillingMod.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public class HudOverlay {
    static int miningXp = 0;
    static int miningLevel = 1;
    static int woodcuttingXp = 0;
    static int woodcuttingLevel = 1;
    static int farmingXp = 0;
    static int farmingLevel = 1;
    static int combatXp = 0;
    static int combatLevel = 1;


    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        GuiGraphics gfx = event.getGuiGraphics();

        miningXp = ClientCache.miningXP;
        miningLevel = ClientCache.miningLevel;

        woodcuttingXp = ClientCache.woodcuttingXP;
        woodcuttingLevel = ClientCache.woodcuttingLevel;

        farmingXp = ClientCache.farmingXP;
        farmingLevel = ClientCache.farmingLevel;

        combatXp = ClientCache.combatXP;
        combatLevel = ClientCache.combatLevel;

        renderIcon("ui/combat.png", 0, gfx, combatLevel, 0xB33A3A, 1);
        renderIcon("ui/mining.png", 1, gfx, miningLevel, 0xB0B0B0, 1);
        renderIcon("ui/woodcutting.png", 0, gfx, woodcuttingLevel, 0x8B5A2B, 2);
        renderIcon("ui/fishing.png", 1, gfx, 99, 0x3A78B8, 2);
        renderIcon("ui/cooking.png", 2, gfx, 99, 0xD28A2E, 2);
        renderIcon("ui/farming.png", 3, gfx, farmingLevel, 0x6FAF3F, 2);
        renderIcon("ui/crafting.png", 2, gfx, 1, 0x2F8F7D, 1);
        renderIcon("ui/magic.png", 3, gfx, 1, 0x7B5CD6, 1);
        renderIcon("ui/smithing.png", 0, gfx, 1, 0xC75A1D, 3);
    }

    public static void renderIcon(String path, int row, GuiGraphics gfx, int level, int color, int column) {
        ResourceLocation icon = ResourceLocation.fromNamespaceAndPath(SkillingMod.MOD_ID, path);
        RenderSystem.setShaderTexture(0, icon);
        gfx.blit(icon, 18 * column + 10, 12 * row + 10, 10, 10, 0, 0, 16,16, 16, 16);
        gfx.drawString(Minecraft.getInstance().font, "" + level, 18 * column + 5, 12 * row + 10, color, true);

    }
}




