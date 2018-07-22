/*
 * <p>
 * This software is a modification for the game Minecraft, intended to give the game RPG elements.
 * Copyright (C) 2018 Robmart
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package robmart.rpgmode.common.handlers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.brewing.PotionBrewEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import robmart.rpgmode.common.init.InitPotionTypes;
import robmart.rpgmode.common.init.InitPotions;
import robmart.rpgmode.common.potion.PotionBase;
import robmart.rpgmode.common.recipe.brewing.BrewingHelper;
import robmart.rpgmode.common.reference.Reference;

import java.util.Objects;

/**
 * @author Robmart
 */
@GameRegistry.ObjectHolder(Reference.MOD_ID)
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
@SuppressWarnings("unused")
public class PotionHandler {

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void onAttemptBrew(PotionBrewEvent.Pre event) {
        //TODO: Clean this
        boolean consumeIngredient = false;
        for (int i = 0; i <= event.getLength() - 3; i++) {
            boolean isChanged = false;
            ItemStack output = BrewingRecipeRegistry.getOutput(event.getItem(i), event.getItem(3));
            if (output.getItem() instanceof ItemPotion) {
                if (Objects.equals(
                        output.getTagCompound().getString("Potion"),
                        BrewingHelper.getItemStackOfPotion(Items.POTIONITEM, PotionTypes.STRENGTH).getTagCompound()
                                     .getString("Potion"))) {
                    event.setItem(i, BrewingHelper.getItemStackOfPotion(Items.POTIONITEM, InitPotionTypes.STRENGTH));
                    isChanged = true;
                }
                else if (Objects.equals(
                        output.getTagCompound().getString("Potion"),
                        BrewingHelper.getItemStackOfPotion(Items.POTIONITEM, PotionTypes.WEAKNESS).getTagCompound()
                                     .getString("Potion"))) {
                    event.setItem(i, BrewingHelper.getItemStackOfPotion(Items.POTIONITEM, InitPotionTypes.WEAKNESS));
                    isChanged = true;
                }
                if (isChanged)
                    output = event.getItem(i);
                else
                    output = BrewingRecipeRegistry.getOutput(event.getItem(i), event.getItem(3));

                event.setItem(i, BrewingHelper.convertPotionStack(output));
                consumeIngredient = true;
                event.setCanceled(true);
            }
        }
        if (consumeIngredient)
            event.getItem(3).shrink(1);
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent event) {
        if (event.getObject() instanceof EntityLivingBase) {
            ((EntityLivingBase) event.getObject()).getAttributeMap().registerAttribute(PotionBase.STRENGTH);
            ((EntityLivingBase) event.getObject()).getAttributeMap().registerAttribute(PotionBase.DEXTEROUSNESS);
            ((EntityLivingBase) event.getObject()).getAttributeMap().registerAttribute(PotionBase.FORTITUDE);
            ((EntityLivingBase) event.getObject()).getAttributeMap().registerAttribute(PotionBase.INTELLIGENCE);
            ((EntityLivingBase) event.getObject()).getAttributeMap().registerAttribute(PotionBase.WISDOM);
        }
    }

    @SubscribeEvent
    public static void inLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        //TODO: make this less cluttered
        EntityLivingBase entity = event.getEntityLiving();
        NBTTagCompound persisted = entity.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);

        //Strength
        if (entity.ticksExisted % 9 == 0 && InitPotions.STRENGTH.instance != null && !entity.isDead) {

            if (!persisted.getBoolean(InitPotions.STRENGTH.TAG_NAME)) {
                if (entity.getEntityAttribute(PotionBase.STRENGTH).getAttributeValue() !=
                    PotionBase.STRENGTH.getDefaultValue()) {
                    persisted.setBoolean(InitPotions.STRENGTH.TAG_NAME, true);
                }
            }
            else {
                if (entity.getEntityAttribute(PotionBase.STRENGTH).getAttributeValue() ==
                    PotionBase.STRENGTH.getDefaultValue()) {
                    persisted.setBoolean(InitPotions.STRENGTH.TAG_NAME, false);
                }
            }

        }

        //Weakness
        if (entity.ticksExisted % 9 == 0 && InitPotions.WEAKNESS.instance != null && !entity.isDead) {

            if (!persisted.getBoolean(InitPotions.WEAKNESS.TAG_NAME)) {
                if (entity.getEntityAttribute(PotionBase.STRENGTH).getAttributeValue() !=
                    PotionBase.STRENGTH.getDefaultValue()) {
                    persisted.setBoolean(InitPotions.WEAKNESS.TAG_NAME, true);
                }
            }
            else {
                if (entity.getEntityAttribute(PotionBase.STRENGTH).getAttributeValue() ==
                    PotionBase.STRENGTH.getDefaultValue()) {
                    persisted.setBoolean(InitPotions.WEAKNESS.TAG_NAME, false);
                }
            }
        }

        //Dexterousness
        if (entity.ticksExisted % 9 == 0 && InitPotions.DEXTEROUSNESS.instance != null && !entity.isDead) {

            if (!persisted.getBoolean(InitPotions.DEXTEROUSNESS.TAG_NAME)) {
                if (entity.getEntityAttribute(PotionBase.DEXTEROUSNESS).getAttributeValue() !=
                    PotionBase.DEXTEROUSNESS.getDefaultValue()) {
                    persisted.setBoolean(InitPotions.DEXTEROUSNESS.TAG_NAME, true);
                }
            }
            else {
                if (entity.getEntityAttribute(PotionBase.DEXTEROUSNESS).getAttributeValue() ==
                    PotionBase.DEXTEROUSNESS.getDefaultValue()) {
                    persisted.setBoolean(InitPotions.DEXTEROUSNESS.TAG_NAME, false);
                }
            }
        }

        //Clumsiness
        if (entity.ticksExisted % 9 == 0 && InitPotions.CLUMSINESS.instance != null && !entity.isDead) {

            if (!persisted.getBoolean(InitPotions.CLUMSINESS.TAG_NAME)) {
                if (entity.getEntityAttribute(PotionBase.DEXTEROUSNESS).getAttributeValue() !=
                    PotionBase.DEXTEROUSNESS.getDefaultValue()) {
                    persisted.setBoolean(InitPotions.CLUMSINESS.TAG_NAME, true);
                }
            }
            else {
                if (entity.getEntityAttribute(PotionBase.DEXTEROUSNESS).getAttributeValue() ==
                    PotionBase.DEXTEROUSNESS.getDefaultValue()) {
                    persisted.setBoolean(InitPotions.CLUMSINESS.TAG_NAME, false);
                }
            }
        }

        //Fortitude
        if (entity.ticksExisted % 9 == 0 && InitPotions.FORTITUDE.instance != null && !entity.isDead) {

            if (!persisted.getBoolean(InitPotions.FORTITUDE.TAG_NAME)) {
                if (entity.getEntityAttribute(PotionBase.FORTITUDE).getAttributeValue() !=
                    PotionBase.FORTITUDE.getDefaultValue()) {
                    persisted.setBoolean(InitPotions.FORTITUDE.TAG_NAME, true);
                }
            }
            else {
                if (entity.getEntityAttribute(PotionBase.FORTITUDE).getAttributeValue() ==
                    PotionBase.FORTITUDE.getDefaultValue()) {
                    persisted.setBoolean(InitPotions.FORTITUDE.TAG_NAME, false);
                }
            }
        }

        //Lethargy
        if (entity.ticksExisted % 9 == 0 && InitPotions.LETHARGY.instance != null && !entity.isDead) {

            if (!persisted.getBoolean(InitPotions.LETHARGY.TAG_NAME)) {
                if (entity.getEntityAttribute(PotionBase.FORTITUDE).getAttributeValue() !=
                    PotionBase.FORTITUDE.getDefaultValue()) {
                    persisted.setBoolean(InitPotions.LETHARGY.TAG_NAME, true);
                }
            }
            else {
                if (entity.getEntityAttribute(PotionBase.FORTITUDE).getAttributeValue() ==
                    PotionBase.FORTITUDE.getDefaultValue()) {
                    persisted.setBoolean(InitPotions.LETHARGY.TAG_NAME, false);
                }
            }
        }

        //Intelligence
        if (entity.ticksExisted % 9 == 0 && InitPotions.INTELLIGENCE.instance != null && !entity.isDead) {

            if (!persisted.getBoolean(InitPotions.INTELLIGENCE.TAG_NAME)) {
                if (entity.getEntityAttribute(PotionBase.INTELLIGENCE).getAttributeValue() !=
                    PotionBase.INTELLIGENCE.getDefaultValue()) {
                    persisted.setBoolean(InitPotions.INTELLIGENCE.TAG_NAME, true);
                }
            }
            else {
                if (entity.getEntityAttribute(PotionBase.INTELLIGENCE).getAttributeValue() ==
                    PotionBase.INTELLIGENCE.getDefaultValue()) {
                    persisted.setBoolean(InitPotions.INTELLIGENCE.TAG_NAME, false);
                }
            }
        }

        //Stupidity
        if (entity.ticksExisted % 9 == 0 && InitPotions.STUPIDITY.instance != null && !entity.isDead) {

            if (!persisted.getBoolean(InitPotions.STUPIDITY.TAG_NAME)) {
                if (entity.getEntityAttribute(PotionBase.INTELLIGENCE).getAttributeValue() !=
                    PotionBase.INTELLIGENCE.getDefaultValue()) {
                    persisted.setBoolean(InitPotions.STUPIDITY.TAG_NAME, true);
                }
            }
            else {
                if (entity.getEntityAttribute(PotionBase.INTELLIGENCE).getAttributeValue() ==
                    PotionBase.INTELLIGENCE.getDefaultValue()) {
                    persisted.setBoolean(InitPotions.STUPIDITY.TAG_NAME, false);
                }
            }
        }

        //Wisdom
        if (entity.ticksExisted % 9 == 0 && InitPotions.WISDOM.instance != null && !entity.isDead) {

            if (!persisted.getBoolean(InitPotions.WISDOM.TAG_NAME)) {
                if (entity.getEntityAttribute(PotionBase.WISDOM).getAttributeValue() !=
                    PotionBase.WISDOM.getDefaultValue()) {
                    persisted.setBoolean(InitPotions.WISDOM.TAG_NAME, true);
                }
            }
            else {
                if (entity.getEntityAttribute(PotionBase.WISDOM).getAttributeValue() ==
                    PotionBase.WISDOM.getDefaultValue()) {
                    persisted.setBoolean(InitPotions.WISDOM.TAG_NAME, false);
                }
            }
        }

        //Foolishness
        if (entity.ticksExisted % 9 == 0 && InitPotions.FOOLISHNESS.instance != null && !entity.isDead) {

            if (!persisted.getBoolean(InitPotions.FOOLISHNESS.TAG_NAME)) {
                if (entity.getEntityAttribute(PotionBase.WISDOM).getAttributeValue() !=
                    PotionBase.WISDOM.getDefaultValue()) {
                    persisted.setBoolean(InitPotions.FOOLISHNESS.TAG_NAME, true);
                }
            }
            else {
                if (entity.getEntityAttribute(PotionBase.WISDOM).getAttributeValue() ==
                    PotionBase.WISDOM.getDefaultValue()) {
                    persisted.setBoolean(InitPotions.FOOLISHNESS.TAG_NAME, false);
                }
            }
        }


        if (!entity.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
            entity.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, persisted);
        }
    }
}
