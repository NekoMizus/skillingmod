package com.nekomizus.client;

import com.nekomizus.network.SkillData;
import com.nekomizus.skills.SkillsCommands;
import com.nekomizus.skills.SkillsFunctions;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientCache {
    public static int miningXP = 0;
    public static int miningLevel = 0;

    public static int smithingXP = 0;
    public static int smithingLevel = 0;

    public static int woodcuttingXP = 0;
    public static int woodcuttingLevel = 0;

    public static int fishingXP = 0;
    public static int fishingLevel = 0;

    public static int cookingXP = 0;
    public static int cookingLevel = 0;

    public static int farmingXP = 0;
    public static int farmingLevel = 0;

    public static int combatXP = 0;
    public static int combatLevel = 0;

    public static int runeXP = 0;
    public static int runeLevel = 0;

    public static void obtainXP(final SkillData data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            miningXP = data.miningXP();
            miningLevel = SkillsFunctions.toLevel(miningXP);

            woodcuttingXP = data.woodcuttingXP();
            woodcuttingLevel = SkillsFunctions.toLevel(woodcuttingXP);

            farmingXP = data.farmingXP();
            farmingLevel = SkillsFunctions.toLevel(farmingXP);

            combatXP = data.combatXP();
            combatLevel = SkillsFunctions.toLevel(combatXP);
        });
    }


}
