package fr.iglee42.woodensails;

import fr.iglee42.woodensails.ponder.CWSCreatePonderPlugin;
import net.createmod.catnip.config.ui.BaseConfigScreen;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

import java.util.function.Supplier;

import static fr.iglee42.woodensails.CreateWoodenSails.MODID;

public class CWSClient {

    static void init(IEventBus modEventBus) {
        modEventBus.addListener(CWSClient::onClientSetup);
        modEventBus.addListener(CWSClient::onLoadComplete);
    }

    private static void onClientSetup(FMLClientSetupEvent event){
        PonderIndex.addPlugin(new CWSCreatePonderPlugin());
    }

    private static void onLoadComplete(FMLLoadCompleteEvent event) {
        ModContainer container = ModList.get()
                .getModContainerById(MODID)
                .orElseThrow(() -> new IllegalStateException("Create Wooden Sails mod container missing on LoadComplete"));
        container.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, ()-> new ConfigScreenHandler.ConfigScreenFactory((mc, previousScreen) -> new BaseConfigScreen(previousScreen, MODID)));
    }
}
