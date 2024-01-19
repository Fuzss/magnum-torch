package fuzs.magnumtorch.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Zombie.class)
abstract class ZombieMixin extends Monster {

    protected ZombieMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyConstant(method = "finalizeSpawn", constant = @Constant(doubleValue = 0.05, ordinal = 1))
    public double finalizeSpawn(double oldValue) {
        return 1.0;
    }

    @ModifyConstant(method = "getSpawnAsBabyOdds", constant = @Constant(floatValue = 0.05F))
    private static float getSpawnAsBabyOdds(float oldValue) {
        return 1.0F;
    }

    @ModifyVariable(method = "finalizeSpawn", at = @At("HEAD"), argsOnly = true)
    public SpawnGroupData finalizeSpawn(SpawnGroupData spawnData) {
        return new Zombie.ZombieGroupData(true, true);
    }
}
