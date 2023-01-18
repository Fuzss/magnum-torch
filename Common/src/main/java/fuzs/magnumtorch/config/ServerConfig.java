package fuzs.magnumtorch.config;

import fuzs.puzzleslib.config.ConfigCore;
import fuzs.puzzleslib.config.annotation.Config;
import fuzs.puzzleslib.config.serialization.EntryCollectionBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServerConfig implements ConfigCore {
    @Config(name = "diamond_torch")
    public MagnumTorchConfig diamond = new MagnumTorchConfig();
    @Config(name = "emerald_torch")
    public MagnumTorchConfig emerald = new MagnumTorchConfig();
    @Config(name = "amethyst_torch")
    public MagnumTorchConfig amethyst = new MagnumTorchConfig();

    public ServerConfig() {
        // diamond torch
        this.diamond.mobCategoryRaw = Stream.of(MobCategory.MONSTER).map(Enum::name).collect(Collectors.toList());
        this.diamond.horizontalRange = 64;
        this.diamond.verticalRange = 32;
        // do not include event type by default as it will break raids, structure type is only for zombie pigmen from nether portals
        this.diamond.blockedSpawnTypesRaw = Stream.of(MobSpawnType.NATURAL, MobSpawnType.PATROL, MobSpawnType.STRUCTURE, MobSpawnType.JOCKEY).map(Enum::name).collect(Collectors.toList());
        // emerald torch
        this.emerald.mobCategoryRaw = Stream.of(MobCategory.CREATURE).map(Enum::name).collect(Collectors.toList());
        this.emerald.shapeType = ShapeType.CUBOID;
        this.emerald.horizontalRange = 128;
        this.emerald.verticalRange = 64;
        // additionally includes event type to block wandering trader and llama spawning
        this.emerald.blockedSpawnTypesRaw = Stream.of(MobSpawnType.NATURAL, MobSpawnType.EVENT).map(Enum::name).collect(Collectors.toList());
        // amethyst torch
        this.amethyst.mobCategoryRaw = Stream.of(MobCategory.AMBIENT, MobCategory.AXOLOTLS, MobCategory.WATER_AMBIENT, MobCategory.WATER_CREATURE, MobCategory.UNDERGROUND_WATER_CREATURE).map(Enum::name).collect(Collectors.toList());
        this.amethyst.horizontalRange = 64;
        this.amethyst.verticalRange = 32;
        this.amethyst.blockedSpawnTypesRaw = Stream.of(MobSpawnType.NATURAL).map(Enum::name).collect(Collectors.toList());
    }

    public enum ShapeType {
        ELLIPSOID, CYLINDER, CUBOID
    }

    public static class MagnumTorchConfig implements ConfigCore {
        @Config(name = "mob_category", description = {"Mobs of this category are prevented from spawning through natural means (e.g. mob spawners and breeding will still work).", "For refining affected mobs use blacklist and whitelist options.", "If you only want to prevent a few mob types from spawning that do not fit any category leave this list empty and include them in the blacklist option."})
        @Config.AllowedValues(values = {"MONSTER", "CREATURE", "AMBIENT", "AXOLOTLS", "UNDERGROUND_WATER_CREATURE", "WATER_CREATURE", "WATER_AMBIENT"})
        List<String> mobCategoryRaw = Stream.of(MobCategory.MONSTER).map(Enum::name).collect(Collectors.toList());
        @Config(name = "mob_blacklist", description = {"Mobs that should not be allowed to spawn despite being absent from \"mob_category\".", EntryCollectionBuilder.CONFIG_DESCRIPTION})
        List<String> mobBlacklistRaw = EntryCollectionBuilder.getKeyList(Registry.ENTITY_TYPE_REGISTRY);
        @Config(name = "mob_whitelist", description = {"Mobs that should still be allowed to spawn despite being included in \"mob_category\".", EntryCollectionBuilder.CONFIG_DESCRIPTION})
        List<String> mobWhitelistRaw = EntryCollectionBuilder.getKeyList(Registry.ENTITY_TYPE_REGISTRY);
        @Config(description = {"Type of shape used for calculating area in which spawns are prevented.", "This basically let's you choose between taxi cab or euclidean metrics."})
        public ShapeType shapeType = ShapeType.ELLIPSOID;
        @Config(description = "Range for preventing mob spawns on x-z-plane.")
        @Config.IntRange(min = 0)
        public int horizontalRange;
        @Config(description = "Range for preventing mob spawns on y-dimension.")
        @Config.IntRange(min = 0)
        public int verticalRange;
        @Config(name = "blocked_spawn_types", description = {"Types of mob spawns to block (provided by vanilla, some seem a little arbitrary). By default this is configured to only affect natural spawns occurring without player interaction and to not disrupt any game events such as raids.", "NATURAL: monsters spawned during night time, cats in villages, phantoms in the sky", "SPAWNER: all kinds of mobs summoned by monster spawners", "STRUCTURE: zombified piglin from nether portals", "MOB_SUMMONED: iron golems from villagers", "JOCKEY: mobs spawned as a rider for another mob", "EVENT: mobs spawned by game events, mainly zombie sieges, raids, wandering trader visits", "REINFORCEMENT: zombie reinforcements spawned when a zombie is hurt", "PATROL: pillager patrols", "TRIGGERED: skeleton horse traps and warden from sculk shriekers"})
        @Config.AllowedValues(values = {"NATURAL", "SPAWNER", "STRUCTURE", "MOB_SUMMONED", "JOCKEY", "EVENT", "REINFORCEMENT", "PATROL", "TRIGGERED"})
        List<String> blockedSpawnTypesRaw;

        public Set<MobCategory> mobCategories;
        public Set<MobSpawnType> blockedSpawnTypes;
        public Set<EntityType<?>> mobBlacklist;
        public Set<EntityType<?>> mobWhitelist;

        @Override
        public void afterConfigReload() {
            // manually process enum lists as night config keeps values as strings, making it hard to deal with as generic type suggests an enum value
            this.mobCategories = this.mobCategoryRaw.stream().map(MobCategory::valueOf).collect(Collectors.toSet());
            this.blockedSpawnTypes = this.blockedSpawnTypesRaw.stream().map(MobSpawnType::valueOf).collect(Collectors.toSet());
            this.mobBlacklist = EntryCollectionBuilder.of(Registry.ENTITY_TYPE_REGISTRY).buildSet(this.mobBlacklistRaw);
            this.mobWhitelist = EntryCollectionBuilder.of(Registry.ENTITY_TYPE_REGISTRY).buildSet(this.mobWhitelistRaw);
        }
    }
}
