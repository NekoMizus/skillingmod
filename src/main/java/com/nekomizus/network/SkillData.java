package com.nekomizus.network;

import com.nekomizus.skilling.SkillingMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SkillData(
        int miningXP,
        int woodcuttingXP
) implements CustomPacketPayload {
    public static final Type<SkillData> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SkillingMod.MOD_ID, "send_xp"));
    public static final StreamCodec<ByteBuf, SkillData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            SkillData::miningXP,
            ByteBufCodecs.VAR_INT,
            SkillData::woodcuttingXP,
            SkillData::new
    );
    
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
