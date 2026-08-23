package fr.iglee42.woodensails;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.CreateRegistrate;
import fr.iglee42.woodensails.config.CWSConfigs;
import net.createmod.catnip.config.ui.BaseConfigScreen;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

import java.util.function.Supplier;

@Mod(CreateWoodenSails.MODID)
public class CreateWoodenSails {
    public static final String MODID = "create_wooden_sails";

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null);

    public CreateWoodenSails(IEventBus modEventBus, ModContainer modContainer) {
        IEventBus neoForgeEventBus = NeoForge.EVENT_BUS;

        REGISTRATE.registerEventListeners(modEventBus);

        CWSBlocks.register();
        CWSCreativeModeTabs.register(modEventBus);

        REGISTRATE.addRawLang("itemGroup."+MODID+".base", "Create Wooden Sails");

        CWSConfigs.register(ModLoadingContext.get(), modContainer);
    }
}
