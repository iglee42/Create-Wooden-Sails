package fr.iglee42.woodensails.ponder;

import com.simibubi.create.Create;
import com.simibubi.create.infrastructure.ponder.scenes.BearingScenes;
import fr.iglee42.woodensails.CWSBlocks;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

public class CWSCreatePonderPlugin implements PonderPlugin {
    @Override
    public String getModId() {
        return Create.ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemLike> HELPER = helper.withKeyFunction(like-> BuiltInRegistries.ITEM.getKey(like.asItem()));
        HELPER.forComponents(CWSBlocks.OAK_SAIL, CWSBlocks.SPRUCE_SAIL, CWSBlocks.BIRCH_SAIL, CWSBlocks.JUNGLE_SAIL, CWSBlocks.ACACIA_SAIL, CWSBlocks.DARK_OAK_SAIL, CWSBlocks.MANGROVE_SAIL, CWSBlocks.CRIMSON_SAIL, CWSBlocks.WARPED_SAIL, CWSBlocks.BAMBOO_SAIL, CWSBlocks.CHERRY_SAIL)
                .addStoryBoard("sail", BearingScenes::sail);
    }
}
