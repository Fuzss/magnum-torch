package fuzs.magnumtorch.client.util;

import com.google.common.base.Predicates;
import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.config.ServerConfig;
import fuzs.magnumtorch.world.level.block.MagnumTorchBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;

public class TorchTooltipHelper {

    public static List<Component> appendHoverText(MagnumTorchBlock block) {
        List<Component> tooltipLines = new ArrayList<>();
        tooltipLines.add(Component.translatable(TooltipComponent.DESCRIPTION.getTranslationKey())
                .withStyle(ChatFormatting.GRAY));
        if (!Screen.hasShiftDown()) {
            tooltipLines.add(Component.translatable(TooltipComponent.ADDITIONAL.getTranslationKey(),
                            Component.translatable(TooltipComponent.SHIFT.getTranslationKey()).withStyle(ChatFormatting.YELLOW))
                    .withStyle(ChatFormatting.GRAY));
        } else {
            for (TooltipComponent tooltipComponent : TooltipComponent.values()) {
                if (tooltipComponent.notEmptyChecker.test(block.getType().getConfig())) {
                    tooltipLines.add(tooltipComponent.getComponent(block.getType().getConfig()));
                }
            }
        }
        return tooltipLines;
    }

    public enum TooltipComponent implements StringRepresentable {
        DESCRIPTION,
        ADDITIONAL,
        SHIFT,
        MOB_TYPES((ServerConfig.MagnumTorchConfig config) -> !config.mobCategories.isEmpty(),
                (ServerConfig.MagnumTorchConfig config) -> {
                    return mergeComponentList(config.mobCategories, ChatFormatting.YELLOW, Enum::name);
                }),
        BLACKLIST((ServerConfig.MagnumTorchConfig config) -> !config.mobBlacklist.isEmpty(),
                (ServerConfig.MagnumTorchConfig config) -> {
                    return mergeComponentList(config.mobBlacklist,
                            ChatFormatting.AQUA,
                            (EntityType<?> entityType) -> BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());
                }),
        WHITELIST((ServerConfig.MagnumTorchConfig config) -> !config.mobWhitelist.isEmpty(),
                (ServerConfig.MagnumTorchConfig config) -> {
                    return mergeComponentList(config.mobWhitelist,
                            ChatFormatting.AQUA,
                            (EntityType<?> entityType) -> BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());
                }),
        SHAPE_TYPE((ServerConfig.MagnumTorchConfig config) -> {
            return Component.literal(config.shapeType.name()).withStyle(ChatFormatting.GOLD);
        }),
        HORIZONTAL_RANGE((ServerConfig.MagnumTorchConfig config) -> {
            return Component.literal(String.valueOf(config.horizontalRange)).withStyle(ChatFormatting.LIGHT_PURPLE);
        }),
        VERTICAL_RANGE((ServerConfig.MagnumTorchConfig config) -> {
            return Component.literal(String.valueOf(config.verticalRange)).withStyle(ChatFormatting.LIGHT_PURPLE);
        });

        final Predicate<ServerConfig.MagnumTorchConfig> notEmptyChecker;
        final Function<ServerConfig.MagnumTorchConfig, Component> componentFactory;

        TooltipComponent() {
            this(Predicates.alwaysFalse(), (ServerConfig.MagnumTorchConfig config) -> CommonComponents.EMPTY);
        }

        TooltipComponent(Function<ServerConfig.MagnumTorchConfig, Component> componentFactory) {
            this(Predicates.alwaysTrue(), componentFactory);
        }

        TooltipComponent(Predicate<ServerConfig.MagnumTorchConfig> notEmptyChecker, Function<ServerConfig.MagnumTorchConfig, Component> componentFactory) {
            this.notEmptyChecker = notEmptyChecker;
            this.componentFactory = componentFactory;
        }

        public String getTranslationKey() {
            return Util.makeDescriptionId(Registries.elementsDirPath(Registries.BLOCK), MagnumTorch.id("magnum_torch"))
                    + ".tooltip." + this.getSerializedName();
        }

        public Component getComponent(ServerConfig.MagnumTorchConfig config) {
            Component component = this.componentFactory.apply(config);
            return Component.translatable(this.getTranslationKey(), component).withStyle(ChatFormatting.GRAY);
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        private static <T> Component mergeComponentList(Collection<? extends T> collection, ChatFormatting format, Function<T, String> keyExtractor) {
            return collection.stream()
                    .map(mob -> Component.literal(keyExtractor.apply(mob)).withStyle(format))
                    .reduce((o1, o2) -> o1.append(", ").append(o2))
                    .orElse(Component.empty());
        }
    }
}
