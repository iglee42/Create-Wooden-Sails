package fr.iglee42.woodensails;

import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CWSCreativeModeTabs {

    private static final DeferredRegister<CreativeModeTab> REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateWoodenSails.MODID);

    public static final RegistryObject<CreativeModeTab> BASE_CREATIVE_TAB = REGISTER.register("base",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup."+CreateWoodenSails.MODID+".base"))
                    .withTabsBefore(AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getKey())
                    .icon(() -> CWSBlocks.OAK_SAIL.asStack())
                    .displayItems(new ItemDisplayer())
                    .build());

    static void register(IEventBus bus){
        REGISTER.register(bus);
    }


    private static class ItemDisplayer implements CreativeModeTab.DisplayItemsGenerator {
        @Override
        public void accept(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
            for (RegistryEntry<Block> entry : CreateWoodenSails.REGISTRATE.getAll(Registries.BLOCK)) {
                if (!CreateRegistrate.isInCreativeTab(entry, BASE_CREATIVE_TAB))
                    continue;
                Item item = entry.get()
                        .asItem();
                if (item == Items.AIR)
                    continue;
                output.accept(item);
            }
        }


    }
}
