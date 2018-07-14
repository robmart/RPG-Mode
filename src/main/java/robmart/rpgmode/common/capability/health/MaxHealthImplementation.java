/*
 * Copyright 2015-2017, Choonster
 *
 * This work is licensed under the MIT license
 *
 * To view the license visit https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/1.12.2/LICENSE.txt
 */

package robmart.rpgmode.common.capability.health;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.network.play.server.SPacketEntityProperties;
import net.minecraft.world.WorldServer;
import robmart.rpgmode.api.capability.health.IMaxHealth;
import robmart.rpgmode.common.RPGMode;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.UUID;

/**
 * @author Choonster
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
    protected static final UUID             MODIFIER_ID   = UUID.fromString("a0aab9c7-15f0-40fd-a5ac-f2987668b683");
    /**
     * The name of the {@link AttributeModifier}.
     */
    protected static final String           MODIFIER_NAME = "Bonus Max Health";
    /**
     * The entity this is attached to.
     */
    private final          EntityLivingBase entity;
    /**
     * The bonus max health.
     */
    private                float            bonusMaxHealth;

    MaxHealthImplementation(@Nullable EntityLivingBase entity) {
        this.entity = entity;
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
     * Synchronise the entity's max health to watching clients.
     */
    @Override
    public void synchronise() {
        if (entity != null && !entity.getEntityWorld().isRemote) {
            final IAttributeInstance entityMaxHealthAttribute = entity
                    .getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
            final SPacketEntityProperties packet = new SPacketEntityProperties(
                    entity.getEntityId(), Collections.singleton(entityMaxHealthAttribute));
            ((WorldServer) entity.getEntityWorld()).getEntityTracker().sendToTrackingAndSelf(entity, packet);
        }
    }

    /**
     * Called when the bonus max health changes to re-apply the {@link AttributeModifier}.
     */
    protected void onBonusMaxHealthChanged() {
        if (entity == null) return;

        final IAttributeInstance entityMaxHealthAttribute = entity
                .getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);

        AttributeModifier modifier = createModifier();

        float newAmount = 0f;
        float oldAmount = 0f;

        final AttributeModifier oldModifier = entityMaxHealthAttribute.getModifier(MODIFIER_ID);
        if (oldModifier != null) {
            entityMaxHealthAttribute.removeModifier(oldModifier);

            modifier = createModifier();
            newAmount = getBonusMaxHealth();

            oldAmount = (float) oldModifier.getAmount();

            RPGMode.logger.debug("Max Health Changed! Entity: %s - Old: %s - New: %s", entity,
                                 MaxHealthCapability.formatMaxHealth(oldAmount),
                                 MaxHealthCapability.formatMaxHealth(newAmount));
        }
        else {
            oldAmount = 0.0f;

            RPGMode.logger.debug("Max Health Added! Entity: %s - New: %s", entity,
                                 MaxHealthCapability.formatMaxHealth(newAmount));
        }

        entityMaxHealthAttribute.applyModifier(modifier);

        final float amountToHeal = newAmount - oldAmount;
        if (amountToHeal > 0) {
            entity.heal(amountToHeal);
        }
    }

    /**
     * Create the {@link AttributeModifier}.
     *
     * @return The AttributeModifier
     */
    protected AttributeModifier createModifier() {
        return new AttributeModifier(
                MODIFIER_ID, MODIFIER_NAME, getBonusMaxHealth() - entity.getMaxHealth(),
                ATTRIBUTE_MODIFIER_OPERATION_ADD);
    }
}