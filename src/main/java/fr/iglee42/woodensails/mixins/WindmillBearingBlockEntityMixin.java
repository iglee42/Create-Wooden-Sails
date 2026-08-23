package fr.iglee42.woodensails.mixins;

import com.simibubi.create.content.contraptions.bearing.BearingContraption;
import com.simibubi.create.content.contraptions.bearing.MechanicalBearingBlockEntity;
import com.simibubi.create.content.contraptions.bearing.WindmillBearingBlockEntity;
import com.simibubi.create.infrastructure.config.AllConfigs;
import fr.iglee42.woodensails.config.CWSConfigs;
import fr.iglee42.woodensails.contraptions.WoodenSailsContraption;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = WindmillBearingBlockEntity.class, remap = false)
public abstract class WindmillBearingBlockEntityMixin extends MechanicalBearingBlockEntity {


    @Shadow
    protected abstract float getAngleSpeedDirection();

    public WindmillBearingBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "getGeneratedSpeed", at = @At(value = "RETURN", ordinal = 2), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void cws$calculateWoodenSailsSpeed(CallbackInfoReturnable<Float> cir, int sails){
        if (CWSConfigs.server().woodenSailsPerRPM.get() <= 0) return;
        if (!(movedContraption.getContraption() instanceof BearingContraption contraption)) return;
        if (!(contraption instanceof WoodenSailsContraption woodenSailsContraption)) return;
        if (woodenSailsContraption.cws$getWoodenSailAmount() <= 0) return;
        int woodenSails = woodenSailsContraption.cws$getWoodenSailAmount();
        int normalSails = contraption.getSailBlocks() - woodenSails;
        float normalRPM = (float) normalSails / AllConfigs.server().kinetics.windmillSailsPerRPM.get();
        float woodenRPM = (float) woodenSails / CWSConfigs.server().woodenSailsPerRPM.get();
        float rpm = normalRPM + woodenRPM;
        cir.setReturnValue(Mth.clamp(rpm, 0, 16) * getAngleSpeedDirection());
    }

}
