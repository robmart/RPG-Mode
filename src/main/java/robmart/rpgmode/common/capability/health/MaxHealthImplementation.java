package robmart.rpgmode.common.capability.health;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import robmart.rpgmode.common.RPGMode;

import javax.annotation.Nullable;
import java.util.UUID;

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
public class MaxHealthImplementation implements IMaxHealth {
    /**
     * {@link AttributeModifier} operation 0.
     * <p>
     * Add the modifier's amount to the attribute's amount.
     */
    public static final int ATTRIBUTE_MODIFIER_OPERATION_ADD = 0;
    /**
     * The ID of the {@link AttributeModifier}.
     */
    protected static final UUID MODIFIER_ID = UUID.fromString("d5d0d878-b3c2-469b-ba89-ac01c0635a9c");
    /**
     * The name of the {@link AttributeModifier}.
     */
    protected static final String MODIFIER_NAME = "Bonus Max Health";
    /**
     * The minimum max health a player can have.
     */
    protected static final float MIN_AMOUNT = 2.0f;
    /**
     * The entity this is attached to.
     */
    private final EntityLivingBase entity;
    /**
     * The dummy max health attribute. Used to avoid setting the entity's actual attribute to an invalid value.
     */
    private final IAttributeInstance dummyMaxHealthAttribute = new AttributeMap().registerAttribute(SharedMonsterAttributes.MAX_HEALTH);
    /**
     * The bonus max health.
     */
    private float bonusMaxHealth;

    @Override
    public String getOwnerType(){
        return "Default Implementation";
    }

    MaxHealthImplementation() {
        entity = null;
    }

    MaxHealthImplementation(@Nullable EntityLivingBase entity) {
        this.entity = entity;
    }

    /**
     * Format a max health value.
     *
     * @param maxHealth The max health value
     * @return The formatted text.
     */
    public static String formatMaxHealth(float maxHealth) {
        return ItemStack.DECIMALFORMAT.format(maxHealth);
    }

    /**
     * Get the bonus max health.
     *
     * @return The bonus max health
     */
    @Override
    public final float getBonusMaxHealth() {
        return bonusMaxHealth;
    }

    /**
     * Set the bonus max health.
     *
     * @param bonusMaxHealth The bonus max health
     */
    @Override
    public final void setBonusMaxHealth(float bonusMaxHealth) {
        this.bonusMaxHealth = bonusMaxHealth;

        onBonusMaxHealthChanged();
    }

    /**
     * Add an amount to the current bonus max health.
     *
     * @param healthToAdd The amount of health to add
     */
    @Override
    public final void addBonusMaxHealth(float healthToAdd) {
        setBonusMaxHealth(getBonusMaxHealth() + healthToAdd);
    }

    /**
     * Create the {@link AttributeModifier}.
     *
     * @return The AttributeModifier
     */
    protected AttributeModifier createModifier() {
        return new AttributeModifier(MODIFIER_ID, MODIFIER_NAME, getBonusMaxHealth() - entity.getMaxHealth(), ATTRIBUTE_MODIFIER_OPERATION_ADD);
    }

    /**
     * Called when the bonus max health changes to re-apply the {@link AttributeModifier}.
     */
    protected void onBonusMaxHealthChanged() {
        if (entity == null) return;

        final IAttributeInstance entityMaxHealthAttribute = entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);

        // Remove all modifiers from the dummy attribute
        dummyMaxHealthAttribute.getModifiers().forEach(dummyMaxHealthAttribute::removeModifier);

        // Copy the base value and modifiers except this class's from the entity's attribute to the dummy attribute
        dummyMaxHealthAttribute.setBaseValue(entityMaxHealthAttribute.getBaseValue());
        entityMaxHealthAttribute.getModifiers().stream().filter(modifier -> !modifier.getID().equals(MODIFIER_ID)).forEach(dummyMaxHealthAttribute::applyModifier);

        AttributeModifier modifier = createModifier();
        dummyMaxHealthAttribute.applyModifier(modifier);

        // Increment bonus max health by 0.5 until the max health is at least 2.0 (1 heart).
        // We do this to avoid setting the entity's max health to 0, which would kill it (and prevent it from respawning if it's a player).
        // The attribute itself will prevent its value from exceeding the maximum, so adding more than the maximum max health is harmless.
        while (dummyMaxHealthAttribute.getAttributeValue() < MIN_AMOUNT) {
            dummyMaxHealthAttribute.removeModifier(modifier);
            bonusMaxHealth += 0.5f;
            modifier = createModifier();
            dummyMaxHealthAttribute.applyModifier(modifier);
        }

        final float newAmount = getBonusMaxHealth();
        final float oldAmount;

        final AttributeModifier oldModifier = entityMaxHealthAttribute.getModifier(MODIFIER_ID);
        if (oldModifier != null) {
            entityMaxHealthAttribute.removeModifier(oldModifier);

            modifier = createModifier();

            oldAmount = (float) oldModifier.getAmount();

            RPGMode.logger.debug("Max Health Changed! Entity: %s - Old: %s - New: %s", entity, formatMaxHealth(oldAmount), formatMaxHealth(newAmount));
        } else {
            RPGMode.logger.debug("Max Health Added! Entity: %s - New: %s", entity, formatMaxHealth(newAmount));
        }

        entityMaxHealthAttribute.applyModifier(modifier);

        if (entity.getHealth() > entity.getMaxHealth())
            entity.setHealth(entity.getMaxHealth());
    }

    /**
     * Saves the NBT data
     *
     * @return The saved NBT Data
     */
    @Override
    public NBTTagCompound saveNBTData() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setFloat("bonusMaxHealth", this.getBonusMaxHealth());
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
        nbt.setFloat("bonusMaxHealth", this.getBonusMaxHealth());
        return nbt;
    }

    /**
     * Loads the NBT data from NBT compound
     *
     * @param nbt The compound
     */
    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        this.setBonusMaxHealth(nbt.getFloat("bonusMaxHealth"));
    }
}