package com.nekomizus.skills;

import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static com.nekomizus.skilling.SkillingMod.MOD_ID;

public class SkillsRegistry {
    private static DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);
    // private static final Supplier<AttachmentType<Integer>> MINING = ATTACHMENT_TYPES.register(
    //            "mining", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    //    );
    public static final Supplier<AttachmentType<Integer>> MINING = ATTACHMENT_TYPES.register(
            "mining", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );
    public static final Supplier<AttachmentType<Integer>> SMITHING = ATTACHMENT_TYPES.register(
            "smithing", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );
    public static final Supplier<AttachmentType<Integer>> WOODCUTTING = ATTACHMENT_TYPES.register(
            "woodcutting", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );
    public static final Supplier<AttachmentType<Integer>> FISHING = ATTACHMENT_TYPES.register(
            "fishing", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );
    public static final Supplier<AttachmentType<Integer>> COOKING = ATTACHMENT_TYPES.register(
            "cooking", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );
    public static final Supplier<AttachmentType<Integer>> CRAFTING = ATTACHMENT_TYPES.register(
            "crafting", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );
    public static final Supplier<AttachmentType<Integer>> COMBAT = ATTACHMENT_TYPES.register(
            "combat", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );
    public static final Supplier<AttachmentType<Integer>> RUNECRAFTING = ATTACHMENT_TYPES.register(
            "runecraft", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );

    public static void register(IEventBus bus) {
        ATTACHMENT_TYPES.register(bus);
    }
}
