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
        return this.strength;
    }

    /**
     * Set strength
     *
     * @param value The new strength value
     */
    @Override
    public void setStrength(int value){
        this.strength = value;
        onAttributeChanged();
    }

    /**
     * Get the dexterity value
     *
     * @return Current dexterity value
     */
    @Override
    public int getDexterity(){
        return this.dexterity;
    }

    /**
     * Set dexterity
     *
     * @param value The new dexterity value
     */
    @Override
    public void setDexterity(int value){
        this.dexterity = value;
        onAttributeChanged();
    }

    /**
     * Get the intelligence value
     *
     * @return Current intelligence value
     */
    @Override
    public int getIntelligence(){
        return this.intelligence;
    }

    /**
     * Set intelligence
     *
     * @param value The new intelligence value
     */
    @Override
    public void setIntelligence(int value){
        this.intelligence = value;
        onAttributeChanged();
    }

    /**
     * Get the constitution
     *
     * @return Current constitution value
     */
    @Override
    public int getConstitution(){
        return this.constitution;
    }

    /**
     * Set constitution
     *
     * @param value The new constitution value
     */
    @Override
    public void setConstitution(int value){
        this.constitution = value;
        onAttributeChanged();
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
        this.wisdom = value;
        onAttributeChanged();
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
     * Changes max mana, hp and other stuff when an attribute is changed
     * Call this from all set methods
     */
    @Override
    public void onAttributeChanged(){
        //TODO: Run on server join

        //TODO: Strength: Str based weapon damage
        //TODO: Dexterity: Dex based weapon damage, defence
        //TODO: Intelligence: Int based weapon damage, spell dmg
        //TODO: Constitution: Breathing, move speed, poison resistance
        MaxHealthCapability.get(this.entity).setBonusMaxHealth((10 * getConstitution()) - 20);
        //TODO: Wisdom: Taming, spell amount
        if (this.entity instanceof EntityPlayer)
            ManaCapability.get((EntityPlayer) this.entity).setMaxMana(10 * getWisdom());
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
        nbt.setInteger("attributePoint", getWisdom());
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