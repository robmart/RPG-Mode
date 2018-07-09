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

package robmart.rpgmode.common.block;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import robmart.rpgmode.client.handlers.ModelHandler;
import robmart.rpgmode.client.render.IModelRegister;

/**
 * @author Robmart
 */
public class BlockRPGFlower extends BlockBush implements IModelRegister {
    protected static final AxisAlignedBB FLOWER_AABB = new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 1.0D, 0.699999988079071D);
    protected EnumPlantType plantType;
    protected String name;

    public BlockRPGFlower(String name, EnumPlantType plantType) {
        super(Material.PLANTS);
        this.name = name;
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setHardness(0F);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.plantType = plantType;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FLOWER_AABB;
    }

    @Override
    public EnumPlantType getPlantType(net.minecraft.world.IBlockAccess world, BlockPos pos) {
        return this.plantType;
    }

    /**
     * Return true if the block can sustain a Bush
     */
    protected boolean canSustainBush(IBlockState state) {
        switch (plantType) {
            case Desert:
                return state.getBlock() == net.minecraft.init.Blocks.SAND || state.getBlock() == net.minecraft.init.Blocks.HARDENED_CLAY || state.getBlock() == net.minecraft.init.Blocks.STAINED_HARDENED_CLAY;
            case Nether:
                return state.getBlock() == net.minecraft.init.Blocks.SOUL_SAND;
            case Crop:
                return state.getBlock() == net.minecraft.init.Blocks.FARMLAND;
            case Plains:
                return state.getBlock() == net.minecraft.init.Blocks.GRASS || state.getBlock() == net.minecraft.init.Blocks.DIRT || state.getBlock() == net.minecraft.init.Blocks.FARMLAND;
            case Water:
                return state.getMaterial() == Material.WATER && state.getValue(BlockLiquid.LEVEL) == 0;
            //TODO: Cave, beach
        }

        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels() {
        if (Item.getItemFromBlock(this) != Items.AIR)
            ModelHandler.registerBlockToState(this, 0, getDefaultState());
        ModelHandler.registerCustomItemblock(this, name.replaceAll(".*:", ""));
    }
}
