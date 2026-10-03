package fr.iglee42.woodensails;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.item.ItemDescription;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import fr.iglee42.woodensails.blocks.WoodenSailBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.Tags;

import static com.simibubi.create.foundation.data.TagGen.axeOnly;
import static fr.iglee42.woodensails.CreateWoodenSails.REGISTRATE;

public class CWSBlocks {

    static {
        REGISTRATE.setCreativeTab(CWSCreativeModeTabs.BASE_CREATIVE_TAB);
    }

    public static final BlockEntry<WoodenSailBlock> OAK_SAIL = createWoodenSail(WoodType.OAK, Blocks.OAK_PLANKS),
            SPRUCE_SAIL = createWoodenSail(WoodType.SPRUCE, Blocks.SPRUCE_PLANKS),
            BIRCH_SAIL = createWoodenSail(WoodType.BIRCH, Blocks.BIRCH_PLANKS),
            JUNGLE_SAIL = createWoodenSail(WoodType.JUNGLE, Blocks.JUNGLE_PLANKS),
            ACACIA_SAIL = createWoodenSail(WoodType.ACACIA, Blocks.ACACIA_PLANKS),
            DARK_OAK_SAIL = createWoodenSail(WoodType.DARK_OAK, Blocks.DARK_OAK_PLANKS),
            MANGROVE_SAIL = createWoodenSail(WoodType.MANGROVE, Blocks.MANGROVE_PLANKS),
            CRIMSON_SAIL = createWoodenSail(WoodType.CRIMSON, Blocks.CRIMSON_PLANKS),
            WARPED_SAIL = createWoodenSail(WoodType.WARPED, Blocks.WARPED_PLANKS),
            CHERRY_SAIL = createWoodenSail(WoodType.CHERRY, Blocks.CHERRY_PLANKS),
            BAMBOO_SAIL = createWoodenSail(WoodType.BAMBOO, Blocks.BAMBOO_PLANKS);

    public static BlockEntry<WoodenSailBlock> createWoodenSail(WoodType type, Block plankBlock) {
        return REGISTRATE.block(type.name() + "_sail", WoodenSailBlock::new)
                .initialProperties(SharedProperties::wooden)
                .properties(p -> p.mapColor(MapColor.SNOW)
                        .sound(SoundType.SCAFFOLDING)
                        .noOcclusion())
                .transform(axeOnly())
                .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.create_wooden_sails.wooden_sails"))
                .blockstate((c, p) -> p.directionalBlock(c.get(), state -> {
                    boolean alt = state.getValue(WoodenSailBlock.ALT);
                    return p.models()
                            .withExistingParent((alt ? "hollow_" : "") + type.name() + "_sail", p.modLoc("block/"+(alt ? "hollow_" : "") + "wooden_sail"))
                            .texture("sail", p.modLoc("block/sail" + (alt ? "_hollow" : "") + "/" + type.name()));
                }))
                .tag(CWSTags.CWSBlockTags.WOODEN_SAILS.tag)
                .recipe((ctx, prov) -> ShapedRecipeBuilder
                        .shaped(RecipeCategory.MISC, ctx.get(), 2)
                        .pattern("PS")
                        .pattern("SA")
                        .define('P', plankBlock)
                        .define('S', Tags.Items.RODS_WOODEN)
                        .define('A', AllItems.ANDESITE_ALLOY)
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(AllItems.ANDESITE_ALLOY))
                        .save(prov))
                .simpleItem()
                .register();

    }

    static void register() {
    }
}
