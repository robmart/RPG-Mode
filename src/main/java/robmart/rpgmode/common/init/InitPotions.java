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
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import robmart.rpgmode.common.RPGMode;
import robmart.rpgmode.common.potion.PotionBase;
import robmart.rpgmode.common.potion.PotionLava;
import robmart.rpgmode.common.reference.RefPotionNames;
import robmart.rpgmode.common.reference.Reference;

/**
 * @author Robmart
 */
@GameRegistry.ObjectHolder(Reference.MOD_ID)
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
@SuppressWarnings("unused")
public class InitPotions {
    public static final PotionBase LAVA          = new PotionLava();
    public static final PotionBase STRENGTH      = new PotionBase(
            false, 155, 24, 24, RefPotionNames.STRENGTH, PotionBase.STRENGTH, "2f6f5f5f-172b-4f95-97e3-d976823ed50e",
            2, 0);
    public static final PotionBase WEAKNESS      = new PotionBase(
            true, 61, 171, 234, RefPotionNames.WEAKNESS, PotionBase.STRENGTH, "2f6f5f5f-172b-4f95-97e3-d976823ed50e",
            -2, 0);
    public static final PotionBase DEXTEROUSNESS = new PotionBase(
            false, 59, 101, 10, RefPotionNames.DEXTEROUSNESS, PotionBase.DEXTEROUSNESS,
            "64591a96-d9f5-4f7d-b39e-ceb834c38649", 2, 0);
    public static final PotionBase CLUMSINESS    = new PotionBase(
            true, 149, 64, 167, RefPotionNames.CLUMSINESS, PotionBase.DEXTEROUSNESS,
            "309bf477-d48d-41ae-a829-33caa0f604e0", -2, 0);
    public static final PotionBase FORTITUDE     = new PotionBase(
            false, 234, 10, 40, RefPotionNames.FORTITUDE, PotionBase.FORTITUDE,
            "fe0e866a-2602-40ac-b22f-a477adad20a7", 2, 0);
    public static final PotionBase LETHARGY      = new PotionBase(
            true, 56, 81, 7, RefPotionNames.LETHARGY, PotionBase.FORTITUDE, "a64eab83-d3a8-4813-acd6-44914a46e0a5",
            -2, 0);
    public static final PotionBase INTELLIGENCE  = new PotionBase(
            false, 42, 7, 220, RefPotionNames.INTELLIGENCE, PotionBase.INTELLIGENCE,
            "40e166b0-68ff-4072-ae0f-ff2f0c55702f", 2, 0);
    public static final PotionBase STUPIDITY     = new PotionBase(
            true, 223, 155, 32, RefPotionNames.STUPIDITY, PotionBase.INTELLIGENCE,
            "40e166b0-68ff-4072-ae0f-ff2f0c55702f", -2, 0);
    public static final PotionBase WISDOM        = new PotionBase(
            false, 102, 32, 201, RefPotionNames.WISDOM, PotionBase.WISDOM, "308dbe5f-d86b-4e85-aff0-df6559d0d325", 2,
            0);
    public static final PotionBase FOOLISHNESS   = new PotionBase(
            true, 116, 196, 42, RefPotionNames.FOOLISHNESS, PotionBase.WISDOM, "47544add-a921-4b4d-b247-c5da8db72cd1",
            -2, 0);

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
