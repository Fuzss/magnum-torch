package fuzs.magnumtorch.client.handler;

import com.google.common.base.Predicates;
import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.config.ServerConfig;
import fuzs.magnumtorch.world.level.block.MagnumTorchBlock;
import fuzs.puzzleslib.api.util.v1.ComponentHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;

public class TorchTooltipHandler {
    public static final String TRANSLATION_KEY_MAGNUM_TORCH_INFO = "block.magnumtorch.magnum_torch.info";
    public static final String TRANSLATION_KEY_MAGNUM_TORCH_MORE = TRANSLATION_KEY_MAGNUM_TORCH_INFO + ".more";
    public static final String TRANSLATION_KEY_MAGNUM_TORCH_SHIFT = TRANSLATION_KEY_MAGNUM_TORCH_INFO + ".shift";

    public static void onItemTooltip(ItemStack itemStack, List<Component> tooltipLines, Item.TooltipContext tooltipContext, @Nullable Player player, TooltipFlag tooltipFlag) {
        if (itemStack.getItem() instanceof BlockItem blockItem &&
                blockItem.getBlock() instanceof MagnumTorchBlock block) {
            tooltipLines.add(Component.translatable(TRANSLATION_KEY_MAGNUM_TORCH_INFO).withStyle(ChatFormatting.GRAY));
            if (tooltipContext != Item.TooltipContext.EMPTY) {
                if (!ComponentHelper.hasShiftDown()) {
                    tooltipLines.add(Component.translatable(TRANSLATION_KEY_MAGNUM_TORCH_MORE,
                                    Component.translatable(TRANSLATION_KEY_MAGNUM_TORCH_SHIFT).withStyle(ChatFormatting.YELLOW))
                            .withStyle(ChatFormatting.GRAY));
                } else if (MagnumTorch.CONFIG.getHolder(ServerConfig.class).isAvailable()) {
                    for (DescriptionComponent descriptionComponent : DescriptionComponent.values()) {
                        if (descriptionComponent.notEmptyChecker.test(block.getType().getConfig())) {
                            tooltipLines.add(descriptionComponent.getComponent(block.getType().getConfig()));
                        }
                    }
                }
            }
        }
    }

    public enum DescriptionComponent {
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

        DescriptionComponent(Function<ServerConfig.MagnumTorchConfig, Component> componentFactory) {
            this(Predicates.alwaysTrue(), componentFactory);
        }

        DescriptionComponent(Predicate<ServerConfig.MagnumTorchConfig> notEmptyChecker, Function<ServerConfig.MagnumTorchConfig, Component> componentFactory) {
            this.notEmptyChecker = notEmptyChecker;
            this.componentFactory = componentFactory;
        }

        @Override
        public String toString() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        public String getTranslationKey() {
            return TRANSLATION_KEY_MAGNUM_TORCH_INFO + "." + this;
        }

        public Component getComponent(ServerConfig.MagnumTorchConfig config) {
            Component component = this.componentFactory.apply(config);
            return Component.translatable(this.getTranslationKey(), component).withStyle(ChatFormatting.GRAY);
        }

        private static <T> Component mergeComponentList(Collection<? extends T> collection, ChatFormatting format, Function<T, String> keyExtractor) {
            return collection.stream()
                    .map(mob -> Component.literal(keyExtractor.apply(mob)).withStyle(format))
                    .reduce((o1, o2) -> o1.append(", ").append(o2))
                    .orElse(Component.empty());
        }
    }
}
