package fuzs.magnumtorch.mixin;

import fuzs.magnumtorch.api.event.player.LivingCheckSpawnCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(EntityType.class)
abstract class EntityTypeMixin<T extends Entity> {
    @Shadow
    @Final
    private MobCategory category;

    @Inject(method = "spawn(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/nbt/CompoundTag;Ljava/util/function/Consumer;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/MobSpawnType;ZZ)Lnet/minecraft/world/entity/Entity;", at = @At("HEAD"), cancellable = true)
    public void spawn(ServerLevel serverLevel, @Nullable CompoundTag compoundTag, @Nullable Consumer<T> consumer, BlockPos blockPos, MobSpawnType mobSpawnType, boolean shouldOffsetY, boolean shouldOffsetYMore, CallbackInfoReturnable<T> callback) {
        if (this.category == MobCategory.MISC) return;
        if (!LivingCheckSpawnCallback.EVENT.invoker().onLivingCheckSpawn(EntityType.class.cast(this), serverLevel, blockPos.getX(), blockPos.getY(), blockPos.getZ(), mobSpawnType)) {
            callback.setReturnValue(null);
        }
    }
}
