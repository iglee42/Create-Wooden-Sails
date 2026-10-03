package fr.iglee42.woodensails;

import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.providers.ProviderType;
import fr.iglee42.woodensails.config.CWSConfigs;
import net.createmod.catnip.config.ui.BaseConfigScreen;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Supplier;

@Mod(CreateWoodenSails.MODID)
public class CreateWoodenSails {
    public static final String MODID = "create_wooden_sails";

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
            );

    public CreateWoodenSails() {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        IEventBus modEventBus = FMLJavaModLoadingContext.get()
                .getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        REGISTRATE.registerEventListeners(modEventBus);

        CWSBlocks.register();
        CWSCreativeModeTabs.register(modEventBus);

        REGISTRATE.addRawLang("itemGroup."+MODID+".base", "Create Wooden Sails");
        REGISTRATE.addRawLang("block.create_wooden_sails.wooden_sails.tooltip.summary", "A _cheaper sail_ for windmills.");
        REGISTRATE.addRawLang("block.create_wooden_sails.wooden_sails.tooltip.behaviour1", "Toggles the _hollow_ variant.");
        REGISTRATE.addRawLang("block.create_wooden_sails.wooden_sails.tooltip.condition1", "When R-Clicked with a Wrench");
        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS,
                prov->prov.addTag(AllTags.AllBlockTags.WINDMILL_SAILS.tag).addOptionalTag(CWSTags.CWSBlockTags.WOODEN_SAILS.tag));
        CWSConfigs.register(modLoadingContext);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> CWSClient.init(modEventBus));
    }
}
