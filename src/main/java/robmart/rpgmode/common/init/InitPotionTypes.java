package robmart.rpgmode.common.init;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import robmart.rpgmode.common.helper.RegistryHelper;
import robmart.rpgmode.common.reference.Reference;

import javax.annotation.Nullable;

/**
 * Created by Robmart.
 * <p>
 * This software is a modification for the game Minecraft, intended to give the game RPG elements.
 * Copyright (C) 2017 Robmart
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
@GameRegistry.ObjectHolder(Reference.MOD_ID)
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class InitPotionTypes {
    private static final PotionType STRENGTH;
    private static final PotionType LONG_STRENGTH;
    private static final PotionType STRONG_STRENGTH;

    private static final PotionType WEAKNESS;
    private static final PotionType LONG_WEAKNESS;
    private static final PotionType STRONG_WEAKNESS;

    private static final PotionType DEXTEROUSNESS;
    private static final PotionType LONG_DEXTEROUSNESS;
    private static final PotionType STRONG_DEXTEROUSNESS;

    private static final PotionType CLUMSINESS;
    private static final PotionType LONG_CLUMSINESS;
    private static final PotionType STRONG_CLUMSINESS;

    private static final PotionType FORTITUDE;
    private static final PotionType LONG_FORTITUDE;
    private static final PotionType STRONG_FORTITUDE;

    private static final PotionType LETHARGY;
    private static final PotionType LONG_LETHARGY;
    private static final PotionType STRONG_LETHARGY;

    static {
        final String LONG_PREFIX = "long_";
        final String STRONG_PREFIX = "strong_";

        final int HELPFUL_DURATION_STANDARD = 3600;
        final int HELPFUL_DURATION_LONG = 9600;
        final int HELPFUL_DURATION_STRONG = 1800;

        final int HARMFUL_DURATION_STANDARD = 1800;
        final int HARMFUL_DURATION_LONG = 4800;
        final int HARMFUL_DURATION_STRONG = 900;

        final IForgeRegistry<Potion> potionRegistry = ForgeRegistries.POTIONS;
        final Potion strength = RegistryHelper.getRegistryEntry(potionRegistry, "strength");
        final Potion weakness = RegistryHelper.getRegistryEntry(potionRegistry, "weakness");
        final Potion dexterousness = RegistryHelper.getRegistryEntry(potionRegistry, "dexterousness");
        final Potion clumsiness = RegistryHelper.getRegistryEntry(potionRegistry, "clumsiness");
        final Potion fortitude = RegistryHelper.getRegistryEntry(potionRegistry, "fortitude");
        final Potion lethargy = RegistryHelper.getRegistryEntry(potionRegistry, "lethargy");

        STRENGTH = createPotionType(new PotionEffect(strength, HELPFUL_DURATION_STANDARD));
        LONG_STRENGTH = createPotionType(new PotionEffect(strength, HELPFUL_DURATION_LONG), LONG_PREFIX);
        STRONG_STRENGTH = createPotionType(new PotionEffect(strength, HELPFUL_DURATION_STRONG, 1), STRONG_PREFIX);

        WEAKNESS = createPotionType(new PotionEffect(weakness, HARMFUL_DURATION_STANDARD));
        LONG_WEAKNESS = createPotionType(new PotionEffect(weakness, HARMFUL_DURATION_LONG), LONG_PREFIX);
        STRONG_WEAKNESS = createPotionType(new PotionEffect(weakness, HARMFUL_DURATION_STRONG, 1), STRONG_PREFIX);

        DEXTEROUSNESS = createPotionType(new PotionEffect(dexterousness, HARMFUL_DURATION_STANDARD));
        LONG_DEXTEROUSNESS = createPotionType(new PotionEffect(dexterousness, HARMFUL_DURATION_LONG), LONG_PREFIX);
        STRONG_DEXTEROUSNESS = createPotionType(new PotionEffect(dexterousness, HARMFUL_DURATION_STRONG, 1), STRONG_PREFIX);

        CLUMSINESS = createPotionType(new PotionEffect(clumsiness, HARMFUL_DURATION_STANDARD));
        LONG_CLUMSINESS = createPotionType(new PotionEffect(clumsiness, HARMFUL_DURATION_LONG), LONG_PREFIX);
        STRONG_CLUMSINESS = createPotionType(new PotionEffect(clumsiness, HARMFUL_DURATION_STRONG, 1), STRONG_PREFIX);

        FORTITUDE = createPotionType(new PotionEffect(fortitude, HARMFUL_DURATION_STANDARD));
        LONG_FORTITUDE = createPotionType(new PotionEffect(fortitude, HARMFUL_DURATION_LONG), LONG_PREFIX);
        STRONG_FORTITUDE = createPotionType(new PotionEffect(fortitude, HARMFUL_DURATION_STRONG, 1), STRONG_PREFIX);

        LETHARGY = createPotionType(new PotionEffect(lethargy, HARMFUL_DURATION_STANDARD));
        LONG_LETHARGY = createPotionType(new PotionEffect(lethargy, HARMFUL_DURATION_LONG), LONG_PREFIX);
        STRONG_LETHARGY = createPotionType(new PotionEffect(lethargy, HARMFUL_DURATION_STRONG, 1), STRONG_PREFIX);
    }

    private static PotionType createPotionType(final PotionEffect effect) {
        return createPotionType(effect, null);
    }

    private static PotionType createPotionType(final PotionEffect effect, @Nullable final String namePrefix) {
        final ResourceLocation potionName = effect.getPotion().getRegistryName();
        final ResourceLocation potionTypeName;

        if (namePrefix != null)
            potionTypeName = new ResourceLocation(potionName.getResourceDomain(), namePrefix + potionName.getResourcePath());
        else
            potionTypeName = potionName;

        return new PotionType(potionName.toString(), effect).setRegistryName(potionTypeName);
    }

    @SubscribeEvent
    public static void registerPotionTypes(final RegistryEvent.Register<PotionType> event) {
        event.getRegistry().registerAll(
                STRENGTH,
                LONG_STRENGTH,
                STRONG_STRENGTH,

                WEAKNESS,
                LONG_WEAKNESS,
                STRONG_WEAKNESS,

                DEXTEROUSNESS,
                LONG_DEXTEROUSNESS,
                STRONG_DEXTEROUSNESS,

                CLUMSINESS,
                LONG_CLUMSINESS,
                STRONG_CLUMSINESS,

                FORTITUDE,
                LONG_FORTITUDE,
                STRONG_FORTITUDE,

                LETHARGY,
                LONG_LETHARGY,
                STRONG_LETHARGY
        );
    }
}
