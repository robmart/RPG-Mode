package robmart.rpgmode.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import robmart.rpgmode.api.RPGModeAPI;

import javax.annotation.Nullable;

/**
 * Utility methods for Capabilities.
 *
 * @author Choonster
 */
public class CapabilityUtils {
    /**
     * Get a capability handler from an {@link ICapabilityProvider} if it exists.
     *
     * @param provider   The provider
     * @param capability The capability
     * @param facing     The facing
     * @param <T>        The handler type
     *
     * @return The handler, if any.
     */
    @Nullable
    public static <T> T getCapability(
            @Nullable final ICapabilityProvider provider, final Capability<T> capability,
            @Nullable final EnumFacing facing) {
        return provider != null && provider.hasCapability(capability, facing) ?
               provider.getCapability(capability, facing) : null;
    }

    /**
     * Checks if Entity should have the attribute
     */
    public static boolean shouldHaveAttribute(Entity entity) {
        return entity instanceof EntityLivingBase &&
               (entity instanceof EntityPlayer || entity.isCreatureType(EnumCreatureType.MONSTER, false) ||
                RPGModeAPI.shouldMobHaveAttributes(entity.getClass()));
    }

    private CapabilityUtils() {
    }
}
