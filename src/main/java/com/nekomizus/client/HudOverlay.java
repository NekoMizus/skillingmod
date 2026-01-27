package com.nekomizus.client;

import com.nekomizus.skilling.SkillingMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
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

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        GuiGraphics gfx = event.getGuiGraphics();

        miningXp = ClientCache.miningXP;
        miningLevel = ClientCache.miningLevel;

        woodcuttingXp = ClientCache.woodcuttingXP;
        woodcuttingLevel = ClientCache.woodcuttingLevel;


        gfx.drawString(mc.font, "Mining: " + miningLevel + " (" + miningXp + " xp)", 10, 10, 0xFFFFFF, true);
        gfx.drawString(mc.font, "Woodcutting: " + miningLevel + " (" + woodcuttingXp + " xp)", 10, 20, 0xFFFFFF, true);
    }


}
