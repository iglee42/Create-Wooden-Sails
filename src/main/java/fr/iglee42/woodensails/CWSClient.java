package fr.iglee42.woodensails;

import fr.iglee42.woodensails.ponder.CWSCreatePonderPlugin;
import net.createmod.catnip.config.ui.BaseConfigScreen;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.function.Supplier;

import static fr.iglee42.woodensails.CreateWoodenSails.MODID;

@Mod(value = MODID,dist = Dist.CLIENT)
public class CWSClient {

    public CWSClient(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(FMLLoadCompleteEvent.class,(event)->onLoadComplete(event, modContainer));
    }

    private void onClientSetup(FMLClientSetupEvent event){
        PonderIndex.addPlugin(new CWSCreatePonderPlugin());
    }

    private void onLoadComplete(FMLLoadCompleteEvent event, ModContainer container) {
        Supplier<IConfigScreenFactory> configScreen = () -> (mc, previousScreen) -> new BaseConfigScreen(previousScreen, MODID);
        container.registerExtensionPoint(IConfigScreenFactory.class, configScreen);
    }
}
