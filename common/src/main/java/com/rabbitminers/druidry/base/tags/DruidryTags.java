package com.rabbitminers.druidry.base.tags;

import com.rabbitminers.druidry.Druidry;
import com.rabbitminers.druidry.base.registrate.DruidicRegistrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Locale;

public class DruidryTags {
    private static final DruidicRegistrate REGISTRATE = Druidry.registrate();

    public enum NameSpace {

        MOD(Druidry.MOD_ID, false, true),
        FORGE("forge"),
        FABRIC("c")

        ;

        public final String id;
        public final boolean optionalDefault;
        public final boolean alwaysDatagenDefault;

        NameSpace(String id) {
            this(id, true, false);
        }

        NameSpace(String id, boolean optionalDefault, boolean alwaysDatagenDefault) {
            this.id = id;
            this.optionalDefault = optionalDefault;
            this.alwaysDatagenDefault = alwaysDatagenDefault;
        }

    }


    public enum DruidryItemTags {

        ;

        public final TagKey<Item> tag;
        public final boolean alwaysDatagen;

        DruidryItemTags() {
            this(NameSpace.MOD);
        }

        DruidryItemTags(NameSpace namespace) {
            this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        DruidryItemTags(NameSpace namespace, String path) {
            this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        DruidryItemTags(NameSpace namespace, boolean optional, boolean alwaysDatagen) {
            this(namespace, null, optional, alwaysDatagen);
        }

        DruidryItemTags(NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
            ResourceLocation id = new ResourceLocation(namespace.id, path == null
                    ? name().toLowerCase(Locale.ROOT) : path);
            tag = optionalTag(Registry.ITEM, id);
            this.alwaysDatagen = alwaysDatagen;
        }

        @SuppressWarnings("deprecation")
        public boolean matches(Item item) {
            return item.builtInRegistryHolder()
                    .is(tag);
        }

        public boolean matches(ItemStack stack) {
            return stack.is(tag);
        }

        public void add(Item... values) {
            REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, prov -> builder(prov, tag)
                    .add(values));
        }

        public void addOptional(String namespace, String... ids) {
            REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, prov -> {
                TagsProvider.TagAppender<Item> builder = builder(prov, tag);
                for (String id : ids)
                    builder.addOptional(new ResourceLocation(namespace, id));
            });
        }

        public void addOptional(ResourceLocation location) {
            addOptional(location.getNamespace(), location.getPath());
        }

        public void includeIn(TagKey<Item> parent) {
            REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, prov -> prov.tag(parent)
                    .addTag(tag));
        }

        public void includeAll(TagKey<Item> child) {
            REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, prov -> builder(prov, tag)
                    .addTag(child));
        }
    }

    public enum DruidryBlockTags {
        WOODEN
        ;

        public final TagKey<Block> tag;


        DruidryBlockTags() {
            this(NameSpace.MOD);
        }

        DruidryBlockTags(NameSpace namespace) {
            this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        DruidryBlockTags(NameSpace namespace, String path) {
            this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        DruidryBlockTags(NameSpace namespace, boolean optional, boolean alwaysDatagen) {
            this(namespace, null, optional, alwaysDatagen);
        }

        DruidryBlockTags(NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
            ResourceLocation id = new ResourceLocation(namespace.id, path == null
                    ? name().toLowerCase(Locale.ROOT) : path);
            tag = optionalTag(Registry.BLOCK, id);
            if (alwaysDatagen) {
                REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, prov -> builder(prov, tag));
            }
        }

        @SuppressWarnings("deprecation")
        public boolean matches(Block block) {
            return block.builtInRegistryHolder()
                    .is(tag);
        }

        public boolean matches(ItemStack stack) {
            return stack != null && stack.getItem() instanceof BlockItem blockItem && matches(blockItem.getBlock());
        }

        public boolean matches(BlockState state) {
            return state.is(tag);
        }

        public void add(Block... values) {
            REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, prov -> builder(prov, tag)
                    .add(values));
        }

        public void addOptional(String namespace, String... ids) {
            REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, prov -> {
                TagsProvider.TagAppender<Block> builder = builder(prov, tag);
                for (String id : ids)
                    builder.addOptional(new ResourceLocation(namespace, id));
            });
        }

        public void addOptional(ResourceLocation location) {
            addOptional(location.getNamespace(), location.getPath());
        }

        public void includeIn(TagKey<Block> parent) {
            REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, prov -> prov.tag(parent)
                    .addTag(tag));
        }

        public void includeAll(TagKey<Block> child) {
            REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, prov -> builder(prov, tag)
                    .addTag(child));
        }
    }

    public static <T> TagKey<T> optionalTag(Registry<T> registry, ResourceLocation id) {
        return TagKey.create(registry.key(), id);
    }

    public static void init() {

    }

    @ExpectPlatform
    public static <T> TagsProvider.TagAppender<T> builder(RegistrateTagsProvider<T> prov, TagKey<T> tag) {
        throw new AssertionError();
    }
}

