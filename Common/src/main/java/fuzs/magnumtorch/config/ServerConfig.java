package fuzs.magnumtorch.config;

import com.google.common.collect.Sets;
import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;
import fuzs.puzzleslib.api.config.v3.serialization.ConfigDataSet;
import fuzs.puzzleslib.api.config.v3.serialization.KeyedValueProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

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
        this.diamond.horizontalRange = 64;
        this.diamond.verticalRange = 32;
        // do not include the event type by default as it will break raids, the structure type is only for zombie pigmen from nether portals
        this.diamond.blockedSpawnReasonsConfig.natural = true;
        this.diamond.blockedSpawnReasonsConfig.patrol = true;
        this.diamond.blockedSpawnReasonsConfig.structure = true;
        // emerald torch
        this.emerald.blockedMobCategoriesConfig.creature = true;
        this.emerald.shapeType = SpawnShapeType.CUBOID;
        this.emerald.horizontalRange = 128;
        this.emerald.verticalRange = 64;
        // additionally includes the event type to block wandering trader and llama spawning
        this.emerald.blockedSpawnReasonsConfig.natural = true;
        this.emerald.blockedSpawnReasonsConfig.event = true;
        // amethyst torch
        this.amethyst.blockedMobCategoriesConfig.aquatic = true;
        this.amethyst.horizontalRange = 64;
        this.amethyst.verticalRange = 32;
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
        List<String> mobBlacklistRaw = KeyedValueProvider.asString(Registries.ENTITY_TYPE);
        @Config(name = "mob_whitelist", description = {
                "Mobs that should still be allowed to spawn despite being included in \"mob_category\".",
                ConfigDataSet.CONFIG_DESCRIPTION
        })
        List<String> mobWhitelistRaw = KeyedValueProvider.asString(Registries.ENTITY_TYPE);
        @Config(description = {
                "Type of shape used for calculating area in which spawns are prevented.",
                "This basically let's you choose between maximum or euclidean metrics."
        })
        public SpawnShapeType shapeType = SpawnShapeType.ELLIPSOID;
        @Config(description = "Range for preventing mob spawns on x-z-plane.")
        @Config.IntRange(min = 0)
        public int horizontalRange;
        @Config(description = "Range for preventing mob spawns on y-dimension.")
        @Config.IntRange(min = 0)
        public int verticalRange;
        @Config(name = "blocked_spawn_reasons", description = {
                "Types of mob spawns to block.",
                "By default this is configured to only affect natural spawns occurring without player interaction and to not disrupt any game events.",
        })
        final BlockedSpawnReasonsConfig blockedSpawnReasonsConfig = new BlockedSpawnReasonsConfig();

        public Set<MobCategory> blockedMobCategories;
        public Set<EntitySpawnReason> blockedSpawnReasons;
        public ConfigDataSet<EntityType<?>> mobBlacklist;
        public ConfigDataSet<EntityType<?>> mobWhitelist;

        @Override
        public void afterConfigReload() {
            this.blockedMobCategories = this.blockedMobCategoriesConfig.toSet();
            this.blockedSpawnReasons = this.blockedSpawnReasonsConfig.toSet();
            this.mobBlacklist = ConfigDataSet.from(Registries.ENTITY_TYPE, this.mobBlacklistRaw);
            this.mobWhitelist = ConfigDataSet.from(Registries.ENTITY_TYPE, this.mobWhitelistRaw);
        }

        public boolean isAffected(EntityType<?> entityType) {
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

        public Set<MobCategory> toSet() {
            Set<MobCategory> set = new HashSet<>();
            if (this.monster) {
                set.add(MobCategory.MONSTER);
            }

            if (this.creature) {
                set.add(MobCategory.CREATURE);
                set.add(MobCategory.AMBIENT);
            }

            if (this.aquatic) {
                set.add(MobCategory.AXOLOTLS);
                set.add(MobCategory.UNDERGROUND_WATER_CREATURE);
                set.add(MobCategory.WATER_CREATURE);
                set.add(MobCategory.WATER_AMBIENT);
            }

            return Sets.immutableEnumSet(set);
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

        public Set<EntitySpawnReason> toSet() {
            Set<EntitySpawnReason> set = new HashSet<>();
            if (this.natural) {
                set.add(EntitySpawnReason.NATURAL);
                set.add(EntitySpawnReason.JOCKEY);
            }

            if (this.spawner) {
                set.add(EntitySpawnReason.SPAWNER);
                set.add(EntitySpawnReason.TRIAL_SPAWNER);
            }

            if (this.structure) {
                set.add(EntitySpawnReason.STRUCTURE);
            }

            if (this.summoned) {
                set.add(EntitySpawnReason.MOB_SUMMONED);
            }

            if (this.event) {
                set.add(EntitySpawnReason.EVENT);
            }

            if (this.reinforcement) {
                set.add(EntitySpawnReason.REINFORCEMENT);
            }

            if (this.patrol) {
                set.add(EntitySpawnReason.PATROL);
            }
            if (this.triggered) {
                set.add(EntitySpawnReason.TRIGGERED);
            }

            return Sets.immutableEnumSet(set);
        }
    }
}
