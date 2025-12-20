package fuzs.magnumtorch.config;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;
import fuzs.puzzleslib.api.config.v3.serialization.ConfigDataSet;
import fuzs.puzzleslib.api.config.v3.serialization.KeyedValueProvider;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServerConfig implements ConfigCore {
    @Config(name = "diamond_torch")
    public final MagnumTorchConfig diamond = new MagnumTorchConfig();
    @Config(name = "emerald_torch")
    public final MagnumTorchConfig emerald = new MagnumTorchConfig();
    @Config(name = "amethyst_torch")
    public final MagnumTorchConfig amethyst = new MagnumTorchConfig();

    public ServerConfig() {
        // diamond torch
        this.diamond.blockedMobCategoriesConfig.monster = true;
        this.diamond.horizontalChunkRange = 4;
        this.diamond.verticalChunkRange = 2;
        // do not include the event type by default as it will break raids
        this.diamond.blockedSpawnReasonsConfig.natural = true;
        this.diamond.blockedSpawnReasonsConfig.patrol = true;
        // the structure type is only for zombie pigmen from nether portals
        this.diamond.blockedSpawnReasonsConfig.structure = true;
        // emerald torch
        this.emerald.blockedMobCategoriesConfig.creature = true;
        this.emerald.shapeType = ShapeType.CUBOID;
        this.emerald.horizontalChunkRange = 8;
        this.emerald.verticalChunkRange = 4;
        // additionally includes the event type to block wandering trader and llama spawning
        this.emerald.blockedSpawnReasonsConfig.natural = true;
        this.emerald.blockedSpawnReasonsConfig.event = true;
        // amethyst torch
        this.amethyst.blockedMobCategoriesConfig.aquatic = true;
        this.amethyst.horizontalChunkRange = 4;
        this.amethyst.verticalChunkRange = 2;
        this.amethyst.blockedSpawnReasonsConfig.natural = true;
    }

    public static class MagnumTorchConfig implements ConfigCore {
        @Config(name = "blocked_mob_categories", description = {
                "Mobs of this category are prevented from spawning through natural means (meaning mob spawners and breeding will still work).",
                "For refining affected mobs use blacklist and whitelist options.",
                "If you only want to prevent a few specific mobs from spawning leave all these disabled and include them in the blacklist option."
        })
        final BlockedMobCategoriesConfig blockedMobCategoriesConfig = new BlockedMobCategoriesConfig();
        @Config(name = "mob_blacklist", description = {
                "Mobs that should not be allowed to spawn despite being absent from \"mob_category\".",
                ConfigDataSet.CONFIG_DESCRIPTION
        })
        List<String> mobBlacklistRaw = KeyedValueProvider.toString(Registries.ENTITY_TYPE);
        @Config(name = "mob_whitelist", description = {
                "Mobs that should still be allowed to spawn despite being included in \"mob_category\".",
                ConfigDataSet.CONFIG_DESCRIPTION
        })
        List<String> mobWhitelistRaw = KeyedValueProvider.toString(Registries.ENTITY_TYPE);
        @Config(description = {
                "Type of shape used for calculating area in which spawns are prevented.",
                "This basically let's you choose between maximum or euclidean metrics."
        })
        public ShapeType shapeType = ShapeType.ELLIPSOID;
        @Config(description = "Range in chunk sections (16x16x16 blocks) for preventing mob spawns on x-z-plane.")
        @Config.IntRange(min = 1, max = 16)
        int horizontalChunkRange;
        @Config(description = "Range in chunk sections (16x16x16 blocks) for preventing mob spawns on y-dimension.")
        @Config.IntRange(min = 1, max = 16)
        int verticalChunkRange;
        @Config(name = "blocked_spawn_reasons", description = {
                "Types of mob spawns to block.",
                "By default this is configured to only affect natural spawns occurring without player interaction and to not disrupt any game events.",
        })
        final BlockedSpawnReasonsConfig blockedSpawnReasonsConfig = new BlockedSpawnReasonsConfig();

        public Set<MobCategoryGroup> blockedMobCategoryGroups;
        public Set<MobCategory> blockedMobCategories;
        public Set<MobSpawnType> blockedSpawnReasons;
        public ConfigDataSet<EntityType<?>> mobBlacklist;
        public ConfigDataSet<EntityType<?>> mobWhitelist;

        @Override
        public void afterConfigReload() {
            this.blockedMobCategoryGroups = this.blockedMobCategoriesConfig.toGroupSet();
            this.blockedMobCategories = this.blockedMobCategoriesConfig.toValueSet();
            this.blockedSpawnReasons = this.blockedSpawnReasonsConfig.toSet();
            this.mobBlacklist = ConfigDataSet.from(Registries.ENTITY_TYPE, this.mobBlacklistRaw);
            this.mobWhitelist = ConfigDataSet.from(Registries.ENTITY_TYPE, this.mobWhitelistRaw);
        }

        public int horizontalRange() {
            return SectionPos.sectionToBlockCoord(this.horizontalChunkRange);
        }

        public int verticalRange() {
            return SectionPos.sectionToBlockCoord(this.verticalChunkRange);
        }

        public boolean preventSpawning(EntityType<?> entityType) {
            if (this.mobWhitelist.contains(entityType)) {
                return false;
            } else if (this.blockedMobCategories.contains(entityType.getCategory())) {
                return true;
            } else {
                return this.mobBlacklist.contains(entityType);
            }
        }
    }

    public static class BlockedMobCategoriesConfig implements ConfigCore {
        @Config(description = "Night time monsters, illagers, nether creatures, etc.")
        public boolean monster;
        @Config(description = "Animals, passive mobs, villagers, etc.")
        public boolean creature;
        @Config(description = "Squids, fishes, axolotls, etc.")
        public boolean aquatic;

        public Set<MobCategoryGroup> toGroupSet() {
            Set<MobCategoryGroup> set = new HashSet<>();
            if (this.monster) {
                set.add(MobCategoryGroup.MONSTER);
            }

            if (this.creature) {
                set.add(MobCategoryGroup.CREATURE);
            }

            if (this.aquatic) {
                set.add(MobCategoryGroup.AQUATIC);
            }

            return Sets.immutableEnumSet(set);
        }

        public Set<MobCategory> toValueSet() {
            return this.toGroupSet().stream().mapMulti(MobCategoryGroup::addAll).collect(ImmutableSet.toImmutableSet());
        }
    }

    public static class BlockedSpawnReasonsConfig implements ConfigCore {
        @Config(description = "Monsters spawned during night time, cats in villages, phantoms in the sky.")
        public boolean natural;
        @Config(description = "All kinds of mobs summoned by monster spawners.")
        public boolean spawner;
        @Config(description = "Zombified piglin from nether portals.")
        public boolean structure;
        @Config(description = "Iron golems from villagers.")
        public boolean summoned;
        @Config(description = "Mobs spawned by game events, mainly zombie sieges, raids, wandering trader visits.")
        public boolean event;
        @Config(description = "Zombie reinforcements spawned when a zombie is hurt.")
        public boolean reinforcement;
        @Config(description = "Pillager patrols.")
        public boolean patrol;
        @Config(description = "Skeleton horse traps and warden from sculk shriekers.")
        public boolean triggered;

        public Set<MobSpawnType> toSet() {
            Set<MobSpawnType> set = new HashSet<>();
            if (this.natural) {
                set.add(MobSpawnType.NATURAL);
                set.add(MobSpawnType.JOCKEY);
            }

            if (this.spawner) {
                set.add(MobSpawnType.SPAWNER);
                set.add(MobSpawnType.TRIAL_SPAWNER);
            }

            if (this.structure) {
                set.add(MobSpawnType.STRUCTURE);
            }

            if (this.summoned) {
                set.add(MobSpawnType.MOB_SUMMONED);
            }

            if (this.event) {
                set.add(MobSpawnType.EVENT);
            }

            if (this.reinforcement) {
                set.add(MobSpawnType.REINFORCEMENT);
            }

            if (this.patrol) {
                set.add(MobSpawnType.PATROL);
            }
            if (this.triggered) {
                set.add(MobSpawnType.TRIGGERED);
            }

            return Sets.immutableEnumSet(set);
        }
    }
}
