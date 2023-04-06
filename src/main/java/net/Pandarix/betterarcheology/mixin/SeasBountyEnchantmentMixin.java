package net.Pandarix.betterarcheology.mixin;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.enchantment.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(FishingBobberEntity.class)
public abstract class SeasBountyEnchantmentMixin {
    private static final Identifier enchantedLootTable = new Identifier(BetterArcheology.MOD_ID, "seas_bounty");

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/criterion/FishingRodHookedCriterion;trigger(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/projectile/FishingBobberEntity;Ljava/util/Collection;)V",
            ordinal = 1),
            locals = LocalCapture.CAPTURE_FAILSOFT)
    //INJECTED CODE-------------------------------------------------------------------------//
    private void injectMethod(ItemStack usedItem, CallbackInfoReturnable<Integer> cir, PlayerEntity playerEntity, int i, LootContext.Builder builder, LootTable lootTable, List list) {
        //if there are no enchantments, we can save ourselves the more complex check
        if (usedItem.hasEnchantments()) {
            //if the used Item has the SeasBounty Enchantment at Level 1
            if (EnchantmentHelper.getLevel(ModEnchantments.SEAS_BOUNTY, usedItem) == 1) {
                list = builder.getWorld().getServer().getLootManager().getTable(enchantedLootTable).generateLoot(builder.build(LootContextTypes.FISHING));
            }
        }
    }

}