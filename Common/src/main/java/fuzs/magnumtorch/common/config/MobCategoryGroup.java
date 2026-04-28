package fuzs.magnumtorch.common.config;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.MobCategory;

import java.util.Locale;
import java.util.function.Consumer;

public enum MobCategoryGroup implements StringRepresentable {
    MONSTER(MobCategory.MONSTER),
    CREATURE(MobCategory.CREATURE, MobCategory.AMBIENT),
    AQUATIC(MobCategory.AXOLOTLS,
            MobCategory.UNDERGROUND_WATER_CREATURE,
            MobCategory.WATER_CREATURE,
            MobCategory.WATER_AMBIENT);

    private final MobCategory[] mobCategories;

    MobCategoryGroup(MobCategory... mobCategories) {
        this.mobCategories = mobCategories;
    }

    public void addAll(Consumer<MobCategory> mobCategoryConsumer) {
        for (MobCategory mobCategory : this.mobCategories) {
            mobCategoryConsumer.accept(mobCategory);
        }
    }

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
