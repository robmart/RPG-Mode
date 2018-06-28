package robmart.rpgmode.common.init;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import robmart.rpgmode.common.RPGMode;
import robmart.rpgmode.common.potion.*;
import robmart.rpgmode.common.reference.Reference;

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
@SuppressWarnings("unused")
public class InitPotions {
    public static final PotionBase LAVA = new PotionLava();
    public static final PotionBase STRENGTH = new PotionStrength();
    public static final PotionBase WEAKNESS = new PotionWeakness();
    public static final PotionBase DEXTEROUSNESS = new PotionDexterousness();
    public static final PotionBase CLUMSINESS = new PotionClumsiness();
    public static final PotionBase FORTITUDE = new PotionFortitude();
    public static final PotionBase LETHARGY = new PotionLethargy();
    public static final PotionBase INTELLIGENCE = new PotionIntelligence();
    public static final PotionBase STUPIDITY = new PotionStupidity();
    public static final PotionBase WISDOM = new PotionWisdom();
    public static final PotionBase FOOLISHNESS = new PotionFoolishness();

    @SubscribeEvent
    public static void registerPotions(final RegistryEvent.Register<Potion> event) {
        RPGMode.logger.info("Adding potions");
        event.getRegistry().registerAll(
                LAVA,

                STRENGTH,
                WEAKNESS,

                DEXTEROUSNESS,
                CLUMSINESS,

                FORTITUDE,
                LETHARGY,

                INTELLIGENCE,
                STUPIDITY,

                WISDOM,
                FOOLISHNESS
        );
        RPGMode.logger.info(String.format("%s potions added", PotionBase.potionCounter));
    }
}
