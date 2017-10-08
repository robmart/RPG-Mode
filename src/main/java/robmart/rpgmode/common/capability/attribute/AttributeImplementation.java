package robmart.rpgmode.common.capability.attribute;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import robmart.rpgmode.common.capability.health.MaxHealthCapability;
import robmart.rpgmode.common.capability.mana.ManaCapability;

/**
 * @author Robmart.
 *         <p>
 *         This software is a modification for the game Minecraft, intended to give the game RPG elements.
 *         Copyright (C) 2017 Robmart
 *         <p>
 *         This program is free software: you can redistribute it and/or modify
 *         it under the terms of the GNU Lesser General Public License as published by
 *         the Free Software Foundation, either version 3 of the License, or
 *         (at your option) any later version.
 *         <p>
 *         This program is distributed in the hope that it will be useful,
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *         GNU Lesser General Public License for more details.
 *         <p>
 *         You should have received a copy of the GNU Lesser General Public License
 *         along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class AttributeImplementation implements IAttribute {
    /**
     * The players strength
     */
    private int strength = 5,
    /**
     * The players strength modifier
     */
    strModifier = 0,
    /**
     * The players dexterity
     */
    dexterity = 5,
    /**
     * The players dexterity modifier
     */
    dexModifier = 0,
    /**
     * The players intelligence
     */
    intelligence = 5,
    /**
     * The players intelligence modifier
     */
    intModifier = 0,
    /**
     * The players constitution
     */
    constitution = 5,
    /**
     * The players constitution modifier
     */
    conModifier = 0,
    /**
     * The players wisdom
     */
    wisdom = 5,
    /**
     * The players wisdom modifier
     */
    wisModifier = 0,
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
        return this.strModifier;
    }

    /**
     * Set strength modifier
     *
     * @param value The new strength modifier
     */
    @Override
    public void setStrMod(int value) {
        this.strModifier = value;
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
        return this.dexModifier;
    }

    /**
     * Set dexterity modifier
     *
     * @param value The new dexterity modifier
     */
    @Override
    public void setDexMod(int value) {
        this.dexModifier = value;
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
    }

    /**
     * Get the intelligence modifier
     *
     * @return Current intelligence modifier
     */
    @Override
    public int getIntMod() {
        return this.intModifier;
    }

    /**
     * Set intelligence modifier
     *
     * @param value The new intelligence modifier
     */
    @Override
    public void setIntMod(int value) {
        this.intModifier = value;
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

        MaxHealthCapability.get(this.entity).setBonusMaxHealth((10 * getConstitution()) - 20);
        /*
        TODO: Fix walk speed
        if (!(this.entity instanceof EntityPlayer))
            this.entity.setAIMoveSpeed(this.entity.getAIMoveSpeed() + (0.01F * getConstitution()));
        else
            ((EntityPlayer) this.entity).capabilities.setPlayerWalkSpeed(((EntityPlayer) this.entity).capabilities.getWalkSpeed() + (0.01F * getConstitution()));
         */
    }

    /**
     * Get the constitution modifier
     *
     * @return Current constitution modifier
     */
    @Override
    public int getConMod() {
        return this.conModifier;
    }

    /**
     * Set constitution modifier
     *
     * @param value The new constitution modifier
     */
    @Override
    public void setConMod(int value) {
        this.conModifier = value;
    }

    /**
     * Get the wisdom
     *
     * @return Current wisdom value
     */
    @Override
    public int getWisdom(){
        return this.wisdom;
    }

    /**
     * Set wisdom
     *
     * @param value The new wisdom value
     */
    @Override
    public void setWisdom(int value){
        //TODO: Wisdom: Taming, spell amount
        this.wisdom = value + getWisMod();

        if (this.entity instanceof EntityPlayer)
            ManaCapability.get((EntityPlayer) this.entity).setMaxMana(10 * getWisdom());
    }

    /**
     * Get the wisdom modifier
     *
     * @return Current wisdom modifier
     */
    @Override
    public int getWisMod() {
        return this.wisModifier;
    }

    /**
     * Set wisdom modifier
     *
     * @param value The new modifier value
     */
    @Override
    public void setWisMod(int value) {
        this.wisModifier = value;
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
        nbt.setInteger("strength", getStrength());
        nbt.setInteger("dexterity", getDexterity());
        nbt.setInteger("intelligence", getIntelligence());
        nbt.setInteger("constitution", getConstitution());
        nbt.setInteger("wisdom", getWisdom());
        nbt.setInteger("attributePoint", getAttributePoint());
        nbt.setInteger("strMod", getStrMod());
        nbt.setInteger("dexMod", getDexMod());
        nbt.setInteger("intMod", getIntMod());
        nbt.setInteger("conMod", getConMod());
        nbt.setInteger("wisMod", getWisMod());
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
        nbt.setInteger("strength", getStrength());
        nbt.setInteger("dexterity", getDexterity());
        nbt.setInteger("intelligence", getIntelligence());
        nbt.setInteger("constitution", getConstitution());
        nbt.setInteger("wisdom", getWisdom());
        nbt.setInteger("attributePoint", getWisdom());
        nbt.setInteger("strMod", getStrMod());
        nbt.setInteger("dexMod", getDexMod());
        nbt.setInteger("intMod", getIntMod());
        nbt.setInteger("conMod", getConMod());
        nbt.setInteger("wisMod", getWisMod());
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
        this.strModifier = nbt.getInteger("strMod");
        this.dexModifier = nbt.getInteger("dexMod");
        this.intModifier = nbt.getInteger("intMod");
        this.conModifier = nbt.getInteger("conMod");
        this.wisModifier = nbt.getInteger("wisMod");
    }
}
