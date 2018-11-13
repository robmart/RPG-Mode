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

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import vazkii.arl.item.ItemModBlock;
import vazkii.arl.util.ProxyRegistry;

/**
 * @author Robmart
 */
public class BlockRPGBush extends BlockBush implements IRPGBlock {
    protected static final AxisAlignedBB FLOWER_AABB = new AxisAlignedBB(
            0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 1.0D, 0.699999988079071D);

    private final String[]      variants;
    private final String        bareName;
    protected     EnumPlantType plantType;

    public BlockRPGBush(String name, EnumPlantType plantType) {
        super(Material.PLANTS);
        variants = new String[] {name};
        bareName = name;

        this.setUnlocalizedName(name);
        this.setHardness(0F);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.plantType = plantType;
    }

    @Override
    public Block setUnlocalizedName(String name) {
        super.setUnlocalizedName(name);
        setRegistryName(getPrefix() + name);
        ProxyRegistry.register(this);
        ProxyRegistry.register(new ItemModBlock(this, new ResourceLocation(getPrefix() + name)));
        return this;
    }

    /**
     * Return true if the block can sustain a Bush
     */
    @Override
    protected boolean canSustainBush(IBlockState state) {
        switch (plantType) {
            case Desert:
                return state.getBlock() == net.minecraft.init.Blocks.SAND ||
                       state.getBlock() == net.minecraft.init.Blocks.HARDENED_CLAY ||
                       state.getBlock() == net.minecraft.init.Blocks.STAINED_HARDENED_CLAY;
            case Nether:
                return state.getBlock() == net.minecraft.init.Blocks.SOUL_SAND;
            case Crop:
                return state.getBlock() == net.minecraft.init.Blocks.FARMLAND;
            case Plains:
                return state.getBlock() == net.minecraft.init.Blocks.GRASS ||
                       state.getBlock() == net.minecraft.init.Blocks.DIRT ||
                       state.getBlock() == net.minecraft.init.Blocks.FARMLAND;
            case Water:
                return state.getMaterial() == Material.WATER && state.getValue(BlockLiquid.LEVEL) == 0;
            default:
                return false;
            //TODO: Cave, beach
        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FLOWER_AABB;
    }

    @Override
    public EnumPlantType getPlantType(net.minecraft.world.IBlockAccess world, BlockPos pos) {
        return this.plantType;
    }

    @Override
    public String getBareName() {
        return bareName;
    }

    @Override
    public IProperty getVariantProp() {
        return null;
    }

    @Override
    public IProperty[] getIgnoredProperties() {
        return new IProperty[0];
    }

    @Override
    public EnumRarity getBlockRarity(ItemStack stack) {
        return EnumRarity.COMMON;
    }

    @Override
    public String[] getVariants() {
        return variants.clone();
    }

    @Override
    public ItemMeshDefinition getCustomMeshDefinition() {
        return null;
    }

    @Override
    public Class getVariantEnum() {
        return null;
    }
}
