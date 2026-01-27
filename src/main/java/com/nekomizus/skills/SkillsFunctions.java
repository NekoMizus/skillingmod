package com.nekomizus.skills;


import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.function.Supplier;

public class SkillsFunctions {
    public static int getXP(Supplier<AttachmentType<Integer>> skill, Player player) {
        return player.getData(skill.get());
    }

    public static void setXP(Supplier<AttachmentType<Integer>> skill, Player player, int value) {
        player.setData(skill.get(), value);
    }

    public static void addXP(Supplier<AttachmentType<Integer>> skill, Player player, int delta) {
        player.setData(skill.get(), player.getData(skill.get()) + delta);
    }

    public static int toLevel(int xp) {
        if (xp <= 0) return 1;

        // Optional: set a known max if your game has one (99/120/126/200/etc.)
        // If you don't know it, we can grow the upper bound dynamically.
        int lo = 1;
        int hi = 2;

        // Find an upper bound where next level requires more XP than we have.
        while (xpAtLevel(hi + 1) <= xp) {
            hi *= 2;
            if (hi > 10000) break; // safety
        }

        // Binary search for the greatest level with xpAtLevel(level) <= xp
        while (lo < hi) {
            int mid = lo + (hi - lo + 1) / 2;
            if (xpAtLevel(mid) <= xp) lo = mid;
            else hi = mid - 1;
        }
        return lo;
    }

    private static int xpAtLevel(int level) {
        if (level <= 1) return 0;
        long points = 0;

        for (int l = 1; l <= level - 1; l++) {
            // floor(l + 300 * 2^(l/7))
            double term = l + 300.0 * Math.pow(2.0, l / 7.0);
            points += (long) Math.floor(term);
        }

        // floor(points / 4)
        return (int) (points / 4);
    }
}
