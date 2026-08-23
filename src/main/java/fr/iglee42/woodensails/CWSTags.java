package fr.iglee42.woodensails;

import com.simibubi.create.Create;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import static fr.iglee42.woodensails.CWSTags.NameSpace.*;


public class CWSTags {

    public enum NameSpace {
        MOD(CreateWoodenSails.MODID),
        COMMON("c"),
        CREATE(Create.ID)
        ;

        public final String id;

        NameSpace(String id) {
            this.id = id;
        }

        public ResourceLocation id(String path) {
            return ResourceLocation.fromNamespaceAndPath(this.id, path);
        }

        public ResourceLocation id(Enum<?> entry, @Nullable String pathOverride) {
            return this.id(pathOverride != null ? pathOverride : Lang.asId(entry.name()));
        }
    }

    public enum CWSBlockTags {
        WOODEN_SAILS,
        ;

        public final TagKey<Block> tag;

        CWSBlockTags() {
            this(MOD);
        }

        CWSBlockTags(NameSpace namespace) {
            this(namespace, null);
        }

        CWSBlockTags(NameSpace namespace, @Nullable String pathOverride) {
            this.tag = TagKey.create(Registries.BLOCK, namespace.id(this, pathOverride));
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

    }
}
