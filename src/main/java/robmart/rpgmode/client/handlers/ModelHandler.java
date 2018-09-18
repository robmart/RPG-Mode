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

package robmart.rpgmode.client.handlers;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IRegistryDelegate;
import robmart.rpgmode.client.render.IModelRegister;
import robmart.rpgmode.common.init.InitItems;
import robmart.rpgmode.common.reference.Reference;

import java.util.Map;
import java.util.function.IntFunction;

/**
 * @author Robmart
 */
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Reference.MOD_ID)
@SideOnly(Side.CLIENT)
@SuppressWarnings("unused")
public class ModelHandler {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent evt) {
        for (Block block : Block.REGISTRY) {
            if (block instanceof IModelRegister)
                ((IModelRegister) block).registerModels();
        }

        for (Item item : Item.REGISTRY) {
            if (item instanceof IModelRegister)
                ((IModelRegister) item).registerModels();
        }
    }

    private static final Map<IRegistryDelegate<Block>, IStateMapper> customStateMappers = ReflectionHelper
            .getPrivateValue(ModelLoader.class, null, "customStateMappers");
    private static final DefaultStateMapper                          fallbackMapper     = new DefaultStateMapper();

    private static ModelResourceLocation getMrlForState(IBlockState state) {
        return customStateMappers
                .getOrDefault(state.getBlock().delegate, fallbackMapper)
                .putStateModelLocations(state.getBlock())
                .get(state);
    }

    public static void registerBlockToState(Block b, int meta, IBlockState state) {
        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(b),
                meta,
                getMrlForState(state)
                                                  );
    }

    /**
     * Register the {@link IItemColor} handlers
     *
     * @param event The event
     */
    @SubscribeEvent
    public static void registerItemColorHandlers(final ColorHandlerEvent.Item event) {
        final ItemColors itemColors = event.getItemColors();

        final IItemColor potionColorHandler = (stack, tintIndex) -> tintIndex > 0 ? -1 : PotionUtils.getColor(stack);

        itemColors.registerItemColorHandler(potionColorHandler, InitItems.POTION, InitItems.SPLASH_POTION,
                                            InitItems.LINGERING_POTION);
    }

    public static void registerCustomItemblock(Block block, String path) {
        registerCustomItemblock(block, 1, i -> path);
    }

    public static void registerCustomItemblock(Block block, int maxExclusive, IntFunction<String> metaToPath) {
        Item item = Item.getItemFromBlock(block);
        for (int i = 0; i < maxExclusive; i++) {
            ModelLoader.setCustomModelResourceLocation(
                    item, i,
                    new ModelResourceLocation(Reference.MOD_ID + ":itemblock/" + metaToPath.apply(i), "inventory")
                                                      );
        }
    }

    private ModelHandler() {
    }
}
