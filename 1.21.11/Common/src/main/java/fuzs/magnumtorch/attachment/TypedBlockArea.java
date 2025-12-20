package fuzs.magnumtorch.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.magnumtorch.world.level.block.MagnumTorchType;
import net.minecraft.core.BlockPos;

public record TypedBlockArea(MagnumTorchType type, BlockArea blockArea) {
    public static final Codec<TypedBlockArea> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MagnumTorchType.CODEC.fieldOf("type").forGetter(TypedBlockArea::type),
            BlockArea.CODEC.forGetter(TypedBlockArea::blockArea)).apply(instance, TypedBlockArea::new));

    TypedBlockArea(MagnumTorchType type, BlockPos position) {
        this(type, new BlockArea(type, position));
    }

    public BlockPos position() {
        return this.blockArea().position();
    }
}
