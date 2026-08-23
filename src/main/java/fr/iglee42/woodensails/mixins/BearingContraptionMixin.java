package fr.iglee42.woodensails.mixins;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.bearing.BearingContraption;
import fr.iglee42.woodensails.CWSTags;
import fr.iglee42.woodensails.blocks.WoodenSailBlock;
import fr.iglee42.woodensails.contraptions.WoodenSailsContraption;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = BearingContraption.class, remap = false)
public abstract class BearingContraptionMixin extends Contraption implements WoodenSailsContraption {

    @Shadow
    protected abstract BlockState getSailBlock(Pair<StructureTemplate.StructureBlockInfo, BlockEntity> capture);

    @Unique
    private int cws$woodenSails = 0;

    @Override
    public int cws$getWoodenSailAmount() {
        return cws$woodenSails;
    }

    @Override
    public void cws$setWoodenSailAmount(int amount) {
        cws$woodenSails = amount;
    }

    @Inject(method = "addBlock", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/contraptions/Contraption;addBlock(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lorg/apache/commons/lang3/tuple/Pair;)V"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void cws$countWoodenSails(Level level, BlockPos pos, Pair<StructureTemplate.StructureBlockInfo, BlockEntity> capture, CallbackInfo ci, BlockPos localPos){
        if (!getBlocks().containsKey(localPos) && CWSTags.CWSBlockTags.WOODEN_SAILS.matches(getSailBlock(capture)))
            cws$woodenSails++;
    }

    @Inject(method = "writeNBT", at =@At("RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void cws$saveWoodenSails(HolderLookup.Provider registries, boolean spawnPacket, CallbackInfoReturnable<CompoundTag> cir, CompoundTag tag){
        tag.putInt("WoodenSails", cws$woodenSails);
    }

    @Inject(method = "readNBT", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/contraptions/Contraption;readNBT(Lnet/minecraft/world/level/Level;Lnet/minecraft/nbt/CompoundTag;Z)V"))
    private void cws$readWoodenSails(Level world, CompoundTag tag, boolean spawnData, CallbackInfo ci){
        cws$woodenSails = tag.getInt("WoodenSails");
    }
}
