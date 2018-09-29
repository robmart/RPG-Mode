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
import robmart.rpgmode.api.reference.RefPotionNames;
import robmart.rpgmode.api.reference.Reference;
import robmart.rpgmode.common.RPGMode;
import robmart.rpgmode.common.helper.RegistryHelper;

import javax.annotation.Nullable;

/**
 * @author Robmart
 */
@SuppressWarnings({"WeakerAccess", "unused"})
@GameRegistry.ObjectHolder(Reference.MOD_ID)
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class InitPotionTypes {
    private static int potionTypeCounter = 0;

    public static String LONG_PREFIX   = "long_";
    public static String STRONG_PREFIX = "strong_";

    public static int HELPFUL_DURATION_STANDARD = 3600;
    public static int HELPFUL_DURATION_LONG     = 9600;
    public static int HELPFUL_DURATION_STRONG   = 1800;

    public static int HARMFUL_DURATION_STANDARD = 1800;
    public static int HARMFUL_DURATION_LONG     = 4800;
    public static int HARMFUL_DURATION_STRONG   = 900;

    public static IForgeRegistry<Potion> potionRegistry = ForgeRegistries.POTIONS;

    public static final PotionType LAVA;

    public static final PotionType STRENGTH;
    public static final PotionType LONG_STRENGTH;
    public static final PotionType STRONG_STRENGTH;

    public static final PotionType WEAKNESS;
    public static final PotionType LONG_WEAKNESS;
    public static final PotionType STRONG_WEAKNESS;

    public static final PotionType DEXTEROUSNESS;
    public static final PotionType LONG_DEXTEROUSNESS;
    public static final PotionType STRONG_DEXTEROUSNESS;

    public static final PotionType CLUMSINESS;
    public static final PotionType LONG_CLUMSINESS;
    public static final PotionType STRONG_CLUMSINESS;

    public static final PotionType FORTITUDE;
    public static final PotionType LONG_FORTITUDE;
    public static final PotionType STRONG_FORTITUDE;

    public static final PotionType LETHARGY;
    public static final PotionType LONG_LETHARGY;
    public static final PotionType STRONG_LETHARGY;

    public static final PotionType INTELLIGENCE;
    public static final PotionType LONG_INTELLIGENCE;
    public static final PotionType STRONG_INTELLIGENCE;

    public static final PotionType STUPIDITY;
    public static final PotionType LONG_STUPIDITY;
    public static final PotionType STRONG_STUPIDITY;

    public static final PotionType WISDOM;
    public static final PotionType LONG_WISDOM;
    public static final PotionType STRONG_WISDOM;

    public static final PotionType FOOLISHNESS;
    public static final PotionType LONG_FOOLISHNESS;
    public static final PotionType STRONG_FOOLISHNESS;

    static {
        final Potion lava = RegistryHelper.getRegistryEntry(potionRegistry, RefPotionNames.LAVA);
        final Potion strength = RegistryHelper.getRegistryEntry(potionRegistry, RefPotionNames.STRENGTH);
        final Potion weakness = RegistryHelper.getRegistryEntry(potionRegistry, RefPotionNames.WEAKNESS);
        final Potion dexterousness = RegistryHelper.getRegistryEntry(potionRegistry, RefPotionNames.DEXTEROUSNESS);
        final Potion clumsiness = RegistryHelper.getRegistryEntry(potionRegistry, RefPotionNames.CLUMSINESS);
        final Potion fortitude = RegistryHelper.getRegistryEntry(potionRegistry, RefPotionNames.FORTITUDE);
        final Potion lethargy = RegistryHelper.getRegistryEntry(potionRegistry, RefPotionNames.LETHARGY);
        final Potion intelligence = RegistryHelper.getRegistryEntry(potionRegistry, RefPotionNames.INTELLIGENCE);
        final Potion stupidity = RegistryHelper.getRegistryEntry(potionRegistry, RefPotionNames.STUPIDITY);
        final Potion wisdom = RegistryHelper.getRegistryEntry(potionRegistry, RefPotionNames.WISDOM);
        final Potion foolishness = RegistryHelper.getRegistryEntry(potionRegistry, RefPotionNames.FOOLISHNESS);

        RPGMode.logger.info("Adding potion types");
        LAVA = createPotionType(new PotionEffect(lava, -1));

        STRENGTH = createPotionType(new PotionEffect(strength, HELPFUL_DURATION_STANDARD));
        LONG_STRENGTH = createPotionType(new PotionEffect(strength, HELPFUL_DURATION_LONG), LONG_PREFIX);
        STRONG_STRENGTH = createPotionType(new PotionEffect(strength, HELPFUL_DURATION_STRONG, 1), STRONG_PREFIX);

        WEAKNESS = createPotionType(new PotionEffect(weakness, HARMFUL_DURATION_STANDARD));
        LONG_WEAKNESS = createPotionType(new PotionEffect(weakness, HARMFUL_DURATION_LONG), LONG_PREFIX);
        STRONG_WEAKNESS = createPotionType(new PotionEffect(weakness, HARMFUL_DURATION_STRONG, 1), STRONG_PREFIX);

        DEXTEROUSNESS = createPotionType(new PotionEffect(dexterousness, HELPFUL_DURATION_STANDARD));
        LONG_DEXTEROUSNESS = createPotionType(new PotionEffect(dexterousness, HELPFUL_DURATION_LONG), LONG_PREFIX);
        STRONG_DEXTEROUSNESS = createPotionType(
                new PotionEffect(dexterousness, HELPFUL_DURATION_STRONG, 1), STRONG_PREFIX);

        CLUMSINESS = createPotionType(new PotionEffect(clumsiness, HARMFUL_DURATION_STANDARD));
        LONG_CLUMSINESS = createPotionType(new PotionEffect(clumsiness, HARMFUL_DURATION_LONG), LONG_PREFIX);
        STRONG_CLUMSINESS = createPotionType(new PotionEffect(clumsiness, HARMFUL_DURATION_STRONG, 1), STRONG_PREFIX);

        FORTITUDE = createPotionType(new PotionEffect(fortitude, HELPFUL_DURATION_STANDARD));
        LONG_FORTITUDE = createPotionType(new PotionEffect(fortitude, HELPFUL_DURATION_LONG), LONG_PREFIX);
        STRONG_FORTITUDE = createPotionType(new PotionEffect(fortitude, HELPFUL_DURATION_STRONG, 1), STRONG_PREFIX);

        LETHARGY = createPotionType(new PotionEffect(lethargy, HARMFUL_DURATION_STANDARD));
        LONG_LETHARGY = createPotionType(new PotionEffect(lethargy, HARMFUL_DURATION_LONG), LONG_PREFIX);
        STRONG_LETHARGY = createPotionType(new PotionEffect(lethargy, HARMFUL_DURATION_STRONG, 1), STRONG_PREFIX);

        INTELLIGENCE = createPotionType(new PotionEffect(intelligence, HELPFUL_DURATION_STANDARD));
        LONG_INTELLIGENCE = createPotionType(new PotionEffect(intelligence, HELPFUL_DURATION_LONG), LONG_PREFIX);
        STRONG_INTELLIGENCE = createPotionType(
                new PotionEffect(intelligence, HELPFUL_DURATION_STRONG, 1), STRONG_PREFIX);

        STUPIDITY = createPotionType(new PotionEffect(stupidity, HARMFUL_DURATION_STANDARD));
        LONG_STUPIDITY = createPotionType(new PotionEffect(stupidity, HARMFUL_DURATION_LONG), LONG_PREFIX);
        STRONG_STUPIDITY = createPotionType(new PotionEffect(stupidity, HARMFUL_DURATION_STRONG, 1), STRONG_PREFIX);

        WISDOM = createPotionType(new PotionEffect(wisdom, HELPFUL_DURATION_STANDARD));
        LONG_WISDOM = createPotionType(new PotionEffect(wisdom, HELPFUL_DURATION_LONG), LONG_PREFIX);
        STRONG_WISDOM = createPotionType(new PotionEffect(wisdom, HELPFUL_DURATION_STRONG, 1), STRONG_PREFIX);

        FOOLISHNESS = createPotionType(new PotionEffect(foolishness, HARMFUL_DURATION_STANDARD));
        LONG_FOOLISHNESS = createPotionType(new PotionEffect(foolishness, HARMFUL_DURATION_LONG), LONG_PREFIX);
        STRONG_FOOLISHNESS = createPotionType(new PotionEffect(foolishness, HARMFUL_DURATION_STRONG, 1), STRONG_PREFIX);
    }

    private static PotionType createPotionType(final PotionEffect effect) {
        return createPotionType(effect, null);
    }

    private static PotionType createPotionType(final PotionEffect effect, @Nullable final String namePrefix) {
        final ResourceLocation potionName = effect.getPotion().getRegistryName();
        final ResourceLocation potionTypeName;

        if (namePrefix != null) {
            assert potionName != null;
            potionTypeName = new ResourceLocation(
                    potionName.getResourceDomain(), namePrefix + potionName.getResourcePath());
        }
        else {
            assert potionName != null;
            potionTypeName = potionName;
        }

        potionTypeCounter++;
        return new PotionType(potionName.toString(), effect).setRegistryName(potionTypeName);
    }

    @SubscribeEvent
    public static void registerPotionTypes(final RegistryEvent.Register<PotionType> event) {
        event.getRegistry().registerAll(
                LAVA,

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
                STRONG_LETHARGY,

                INTELLIGENCE,
                LONG_INTELLIGENCE,
                STRONG_INTELLIGENCE,

                STUPIDITY,
                LONG_STUPIDITY,
                STRONG_STUPIDITY,

                WISDOM,
                LONG_WISDOM,
                STRONG_WISDOM,

                FOOLISHNESS,
                LONG_FOOLISHNESS,
                STRONG_FOOLISHNESS
                                       );
        RPGMode.logger.info(String.format("%s potion types added", potionTypeCounter));
    }
}
