/*
 * Copyright 2015-2017, Choonster
 *
 * This work is licensed under the MIT license
 *
 * To view the license visit https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/1.12.2/LICENSE.txt
 */

package robmart.rpgmode.api.capability.health;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import static robmart.rpgmode.api.util.InjectionUtil.Null;

/**
 * A capability to provide a max health bonus to an entity.
 *
 * @author Choonster
 */
public interface IMaxHealth {
    /**
     * The {@link Capability} instance.
     */
    @CapabilityInject(IMaxHealth.class)
    Capability<IMaxHealth> MAX_HEALTH_CAPABILITY = Null();

    /**
     * Get the bonus max health.
     *
     * @return The bonus max health
     */
    float getBonusMaxHealth();

    /**
     * Set the bonus max health.
     *
     * @param bonusMaxHealth The bonus max health
     */
    void setBonusMaxHealth(final float bonusMaxHealth);

    /**
     * Add an amount to the current bonus max health.
     *
     * @param healthToAdd The amount of health to add
     */
    void addBonusMaxHealth(final float healthToAdd);

    /**
     * Synchronise the entity's max health to watching clients.
     */
    void synchronise();
}
