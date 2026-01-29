package com.nekomizus.skills;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.attachment.AttachmentType;


import java.util.Locale;
import java.util.function.Supplier;

public final class SkillsCommands {
    private SkillsCommands() {}

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("skillxp")
                .requires(src -> src.hasPermission(2)) // OP level 2+
                .then(Commands.literal("get")
                        .then(Commands.argument("skill", SkillArg.skill())
                                .executes(ctx -> {
                                    ServerPlayer player = ctx.getSource().getPlayerOrException();
                                    Supplier<AttachmentType<Integer>> skill = SkillArg.getSkill(ctx, "skill");
                                    int xp = player.getData(skill.get());
                                    ctx.getSource().sendSuccess(() ->
                                            Component.literal("XP(" + SkillArg.nameOf(skill) + ") = " + xp), false);
                                    return xp;
                                })))
                .then(Commands.literal("set")
                        .then(Commands.argument("skill", SkillArg.skill())
                                .then(Commands.argument("value", IntegerArgumentType.integer(0))
                                        .executes(ctx -> {
                                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                                            Supplier<AttachmentType<Integer>> skill = SkillArg.getSkill(ctx, "skill");
                                            int value = IntegerArgumentType.getInteger(ctx, "value");
                                            player.setData(skill.get(), value);
                                            ctx.getSource().sendSuccess(() ->
                                                    Component.literal("Set XP(" + SkillArg.nameOf(skill) + ") = " + value), false);
                                            return 1;
                                        }))))
                .then(Commands.literal("add")
                        .then(Commands.argument("skill", SkillArg.skill())
                                .then(Commands.argument("delta", IntegerArgumentType.integer())
                                        .executes(ctx -> {
                                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                                            Supplier<AttachmentType<Integer>> skill = SkillArg.getSkill(ctx, "skill");
                                            int delta = IntegerArgumentType.getInteger(ctx, "delta");
                                            int cur = player.getData(skill.get());
                                            int next = Math.max(0, cur + delta);
                                            player.setData(skill.get(), next);
                                            ctx.getSource().sendSuccess(() ->
                                                    Component.literal("XP(" + SkillArg.nameOf(skill) + "): " + cur + " -> " + next), false);
                                            return 1;
                                        }))))
        );
    }

    /** Minimal skill argument that supports: mining, smithing, woodcutting... */
    private static final class SkillArg {
        // Brigadier String argument with suggestions
        static com.mojang.brigadier.arguments.StringArgumentType skill() {
            return com.mojang.brigadier.arguments.StringArgumentType.word();
        }

        static Supplier<AttachmentType<Integer>> getSkill(com.mojang.brigadier.context.CommandContext<CommandSourceStack> ctx, String name) {
            String key = com.mojang.brigadier.arguments.StringArgumentType.getString(ctx, name)
                    .toLowerCase(Locale.ROOT);

            // Add more mappings as you add skills
            return switch (key) {
                case "mining" -> SkillsRegistry.MINING;
                case "smithing" -> SkillsRegistry.SMITHING;
                case "woodcutting" -> SkillsRegistry.WOODCUTTING;
                case "fishing" -> SkillsRegistry.FISHING;
                case "combat" -> SkillsRegistry.COMBAT;
                case "crafting" -> SkillsRegistry.CRAFTING;
                case "cooking" -> SkillsRegistry.COOKING;
                case "runecraft" -> SkillsRegistry.RUNECRAFTING;
                case "farming" -> SkillsRegistry.FARMING;
                default -> throw new IllegalArgumentException("Unknown skill: " + key);
            };
        }

        static String nameOf(Supplier<AttachmentType<Integer>> skill) {
            // purely for display; optional
            if (skill == SkillsRegistry.MINING) return "mining";
            if (skill == SkillsRegistry.SMITHING) return "smithing";
            if (skill == SkillsRegistry.WOODCUTTING) return "woodcutting";
            if (skill == SkillsRegistry.FISHING) return "fishing";
            if (skill == SkillsRegistry.COMBAT) return "combat";
            if (skill == SkillsRegistry.CRAFTING) return "crafting";
            if (skill == SkillsRegistry.COOKING) return "cooking";
            if (skill == SkillsRegistry.RUNECRAFTING) return "runecraft";
            if (skill == SkillsRegistry.FARMING) return "farming";
            return "skill";
        }
    }
}
