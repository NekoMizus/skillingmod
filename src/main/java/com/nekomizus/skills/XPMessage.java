package com.nekomizus.skills;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.function.Supplier;

public class XPMessage {
    static long now;
    static long last;
    static int xp;
    public static void message(Player player, int xpDisplay) {
        now = System.currentTimeMillis();

        if (now - last < 7000) {
            xp += xpDisplay;
        } else {
            xp = xpDisplay;
        }
        last = now;
        System.out.println(xp);
        player.displayClientMessage(
                Component.literal(xp + " XP gained!"),
                true
        );
    }
}
