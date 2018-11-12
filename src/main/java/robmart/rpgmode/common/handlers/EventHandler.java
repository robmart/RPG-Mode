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

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import robmart.rpgmode.api.potion.RPGPotionTypes;
import robmart.rpgmode.api.reference.Reference;
import robmart.rpgmode.common.capability.attribute.AttributeCapability;
import robmart.rpgmode.common.helper.ItemHelper;
import robmart.rpgmode.common.helper.PlayerHelper;
import robmart.rpgmode.common.helper.PotionHelper;

/**
 * @author Robmart
 */
@GameRegistry.ObjectHolder(Reference.MOD_ID)
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
@SuppressWarnings("unused")
public class EventHandler {

    @SubscribeEvent
    public static void playerLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
        final EntityPlayer player = event.player;

        final NBTTagCompound entityData = player.getEntityData();
        final NBTTagCompound persistedData = entityData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        entityData.setTag(EntityPlayer.PERSISTED_NBT_TAG, persistedData);

        final String key = Reference.MOD_ID + ":LoggedIn";
        final String message;

        if (persistedData.getBoolean(key)) {
            message = "Player has joined before.";
        }
        else {
            persistedData.setBoolean(key, true);
            AttributeCapability.getAttributes(player).setAttributes(5, 5, 5, 5, 5);
            player.setHealth(player.getMaxHealth());

            message = "Player hasn't joined before.";
        }

        final ITextComponent chatComponent = new TextComponentTranslation(message);
        chatComponent.getStyle().setColor(TextFormatting.LIGHT_PURPLE);
        player.sendMessage(chatComponent);
    }

    private EventHandler() {
    }

    @SubscribeEvent
    public static void onPlayerRightClickBlock(final PlayerInteractEvent.RightClickItem event) {
        RayTraceResult look = PlayerHelper.rayTrace(event.getWorld(), event.getEntityPlayer(), true);
        if (look == null) return;
        if (look.typeOfHit != RayTraceResult.Type.BLOCK) return;

        IBlockState state = event.getWorld().getBlockState(look.getBlockPos());

        if (event.getItemStack().getItem() instanceof ItemGlassBottle && state.getMaterial() == Material.LAVA) {
            event.getWorld()
                 .playSound(event.getEntityPlayer(), event.getEntityPlayer().posX, event.getEntityPlayer().posY,
                            event.getEntityPlayer().posZ, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F,
                            1.0F);
            ItemHelper.turnBottleIntoItem(event.getItemStack(), event.getEntityPlayer(),
                                          PotionHelper.getItemStackOfPotion(Items.POTIONITEM, RPGPotionTypes.lava));
        }
    }
}
