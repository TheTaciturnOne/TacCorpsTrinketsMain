package com.thetaciturnone.taccorpstrinkets.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record HammerVariantComponent(int variant, boolean heldBySupporter) {

	public static final Codec<HammerVariantComponent> CODEC = RecordCodecBuilder.create(builder -> {
		return builder.group(
			Codec.INT.fieldOf("variant").forGetter(HammerVariantComponent::variant),
			Codec.BOOL.fieldOf("heldBySupporter").forGetter(HammerVariantComponent::heldBySupporter)
		).apply(builder, HammerVariantComponent::new);
	});
	public static final PacketCodec<ByteBuf, HammerVariantComponent> PACKET_CODEC = PacketCodec.tuple(
		PacketCodecs.INTEGER, HammerVariantComponent::variant,
		PacketCodecs.BOOL, HammerVariantComponent::heldBySupporter, HammerVariantComponent::new);

	public HammerVariantComponent updateComponent(int newVariant, boolean newHeldStatus) {
		return new HammerVariantComponent(newVariant, newHeldStatus);
	}

}
