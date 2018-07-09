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

package robmart.rpgmode.common.capability.attribute;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import robmart.rpgmode.common.capability.health.MaxHealthCapability;
import robmart.rpgmode.common.capability.mana.ManaCapability;
import robmart.rpgmode.common.helper.PotionHelper;

/**
 * @author Robmart
 */
public class AttributeImplementation implements IAttribute {
    /**
     * The players strength
     */
    private int strength = 5,
    /**
     * The players dexterity
     */
    dexterity = 5,
    /**
     * The players intelligence
     */
    intelligence = 5,
    /**
     * The players constitution
     */
    constitution = 5,
    /**
     * The players wisdom
     */
    wisdom = 5,
    /**
     * The players available attribute points
     */
    attributePoint = 5;

    /**
     * The entity which the capability is attached to
     */
    private EntityLivingBase entity;

    AttributeImplementation(){
        this.entity = null;
    }

    AttributeImplementation(EntityLivingBase entity){
        this.entity = entity;
    }

    @Override
    public String getOwnerType(){
        return "Default Implementation";
    }

    /**
     * Get the strength value
     *
     * @return Current strength value
     */
    @Override
    public int getStrength(){
        return this.strength + getStrMod();
    }

    /**
     * Set strength
     *
     * @param value The new strength value
     */
    @Override
    public void setStrength(int value) {
        //TODO: Strength: Str based weapon damage
        this.strength = value;
    }

    /**
     * Get the strength modifier
     *
     * @return Current strength modifier
     */
    @Override
    public int getStrMod() {
        return (int) entity.getEntityAttribute(PotionHelper.STRENGTH).getAttributeValue();
    }

    /**
     * Get the dexterity value
     *
     * @return Current dexterity value
     */
    @Override
    public int getDexterity(){
        return this.dexterity + getDexMod();
    }

    /**
     * Set dexterity
     *
     * @param value The new dexterity value
     */
    @Override
    public void setDexterity(int value){
        //TODO: Dexterity: Dex based weapon damage, defence
        this.dexterity = value;
    }

    /**
     * Get the dexterity modifier
     *
     * @return Current dexterity modifier
     */
    @Override
    public int getDexMod() {
        return (int) entity.getEntityAttribute(PotionHelper.DEXTEROUSNESS).getAttributeValue();
    }

    /**
     * Get the intelligence value
     *
     * @return Current intelligence value
     */
    @Override
    public int getIntelligence(){
        return this.intelligence + getIntMod();
    }

    /**
     * Set intelligence
     *
     * @param value The new intelligence value
     */
    @Override
    public void setIntelligence(int value){
        //TODO: Intelligence: Int based weapon damage, spell dmg
        this.intelligence = value;

        ManaCapability.get(entity).setMaxMana(((10.0F * getIntelligence())));
    }

    /**
     * Get the intelligence modifier
     *
     * @return Current intelligence modifier
     */
    @Override
    public int getIntMod() {
        return (int) entity.getEntityAttribute(PotionHelper.INTELLIGENCE).getAttributeValue();
    }

    /**
     * Get the constitution
     *
     * @return Current constitution value
     */
    @Override
    public int getConstitution(){
        return this.constitution + getConMod();
    }

    /**
     * Set constitution
     *
     * @param value The new constitution value
     */
    @Override
    public void setConstitution(int value){
        //TODO: Constitution: Breathing, poison resistance
        this.constitution = value;

        //Set max health
        MaxHealthCapability.get(entity).setBonusMaxHealth(((10.0F * getConstitution())));

        //Increases hostile mobs walk speed
        if (!(entity instanceof EntityPlayer) && entity.isCreatureType(EnumCreatureType.MONSTER, false))
            if (getConstitution() <= 20)
                entity.setAIMoveSpeed(0.1F + (0.0025F * getConstitution()));
            else
                //Cap at 50% boost
                entity.setAIMoveSpeed(0.1F + (0.0025F * 20));

        //Increases player walk speed
        if (entity instanceof EntityPlayer)
            if (getConstitution() <= 20)
                ((EntityPlayer)entity).capabilities.setPlayerWalkSpeed((0.1F + (0.0025F * getConstitution())));
            else
                //Cap at 50% boost
                ((EntityPlayer)entity).capabilities.setPlayerWalkSpeed((0.1F + (0.0025F * 20)));
    }

    /**
     * Get the constitution modifier
     *
     * @return Current constitution modifier
     */
    @Override
    public int getConMod() {
        return (int) entity.getEntityAttribute(PotionHelper.FORTITUDE).getAttributeValue();
    }

    /**
     * Get the wisdom
     *
     * @return Current wisdom value
     */
    @Override
    public int getWisdom(){
        return this.wisdom + getWisMod();
    }

    /**
     * Set wisdom
     *
     * @param value The new wisdom value
     */
    @Override
    public void setWisdom(int value){
        //TODO: Wisdom: Taming, spell amount
        this.wisdom = value;
    }

    /**
     * Get the wisdom modifier
     *
     * @return Current wisdom modifier
     */
    @Override
    public int getWisMod() {
        return (int) entity.getEntityAttribute(PotionHelper.WISDOM).getAttributeValue();
    }

    /**
     * Sets all attributes
     * @param strength New strength value
     * @param dexterity New dexterity value
     * @param intelligence New intelligence value
     * @param constitution New constitution value
     * @param wisdom new wisdom value
     */
    @Override
    public void setAttributes(int strength, int dexterity, int intelligence, int constitution, int wisdom){
        setStrength(strength);
        setDexterity(dexterity);
        setIntelligence(intelligence);
        setConstitution(constitution);
        setWisdom(wisdom);
    }

    /**
     * Gets the attribute points
     *
     * @return Current attribute point value
     */
    @Override
    public int getAttributePoint(){
        return this.attributePoint;
    }

    /**
     * Sets attribute points
     *
     * @param value The new attribute point value
     */
    @Override
    public void setAttributePoint(int value){
        this.attributePoint = value;
    }

    /**
     * Saves the NBT data
     *
     * @return The saved NBT Data
     */
    @Override
    public NBTTagCompound saveNBTData() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("strength", this.strength);
        nbt.setInteger("dexterity", this.dexterity);
        nbt.setInteger("intelligence", this.intelligence);
        nbt.setInteger("constitution", this.constitution);
        nbt.setInteger("wisdom", this.wisdom);
        nbt.setInteger("attributePoint", getAttributePoint());
        return nbt;
    }

    /**
     * Saves the NBT data into specified NBT compound
     *
     * @param nbt The compound
     * @return The saved nbt data
     */
    @Override
    public NBTTagCompound saveNBTData(NBTTagCompound nbt) {
        nbt.setInteger("strength", this.strength);
        nbt.setInteger("dexterity", this.dexterity);
        nbt.setInteger("intelligence", this.intelligence);
        nbt.setInteger("constitution", this.constitution);
        nbt.setInteger("wisdom", this.wisdom);
        nbt.setInteger("attributePoint", getAttributePoint());
        return nbt;
    }

    /**
     * Loads the NBT data from NBT compound
     *
     * @param nbt The compound
     */
    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        this.strength = nbt.getInteger("strength");
        this.dexterity = nbt.getInteger("dexterity");
        this.intelligence = nbt.getInteger("intelligence");
        this.constitution = nbt.getInteger("constitution");
        this.wisdom = nbt.getInteger("wisdom");
        this.attributePoint = nbt.getInteger("attributePoint");
    }
}
