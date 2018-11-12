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

import robmart.rpgmode.api.potion.RPGPotions;
import robmart.rpgmode.api.reference.RefPotionNames;
import robmart.rpgmode.common.potion.PotionBase;
import robmart.rpgmode.common.potion.PotionLava;

/**
 * @author Robmart
 */
@SuppressWarnings("unused")
public class InitPotions {
    private InitPotions() {
    }

    public static void init() {
        RPGPotions.lava = new PotionLava();
        RPGPotions.strength = new PotionBase(
                false, 155, 24, 24, RefPotionNames.STRENGTH, RPGPotions.STRENGTH_ATTRIBUTE,
                "2f6f5f5f-172b-4f95-97e3-d976823ed50e", 2, 0);
        RPGPotions.weakness = new PotionBase(
                true, 61, 171, 234, RefPotionNames.WEAKNESS, RPGPotions.STRENGTH_ATTRIBUTE,
                "2f6f5f5f-172b-4f95-97e3-d976823ed50e", -2, 0);
        RPGPotions.dexterousness = new PotionBase(
                false, 59, 101, 10, RefPotionNames.DEXTEROUSNESS, RPGPotions.DEXTEROUSNESS_ATTRIBUTE,
                "64591a96-d9f5-4f7d-b39e-ceb834c38649", 2, 0);
        RPGPotions.clumsiness = new PotionBase(
                true, 149, 64, 167, RefPotionNames.CLUMSINESS, RPGPotions.DEXTEROUSNESS_ATTRIBUTE,
                "309bf477-d48d-41ae-a829-33caa0f604e0", -2, 0);
        RPGPotions.fortitude = new PotionBase(
                false, 234, 10, 40, RefPotionNames.FORTITUDE, RPGPotions.FORTITUDE_ATTRIBUTE,
                "fe0e866a-2602-40ac-b22f-a477adad20a7", 2, 0);
        RPGPotions.lethargy = new PotionBase(
                true, 56, 81, 7, RefPotionNames.LETHARGY, RPGPotions.FORTITUDE_ATTRIBUTE,
                "a64eab83-d3a8-4813-acd6-44914a46e0a5",
                -2, 0);
        RPGPotions.intelligence = new PotionBase(
                false, 42, 7, 220, RefPotionNames.INTELLIGENCE, RPGPotions.INTELLIGENCE_ATTRIBUTE,
                "40e166b0-68ff-4072-ae0f-ff2f0c55702f", 2, 0);
        RPGPotions.stupidity = new PotionBase(
                true, 223, 155, 32, RefPotionNames.STUPIDITY, RPGPotions.INTELLIGENCE_ATTRIBUTE,
                "40e166b0-68ff-4072-ae0f-ff2f0c55702f", -2, 0);
        RPGPotions.wisdom = new PotionBase(
                false, 102, 32, 201, RefPotionNames.WISDOM, RPGPotions.WISDOM_ATTRIBUTE,
                "308dbe5f-d86b-4e85-aff0-df6559d0d325",
                2, 0);
        RPGPotions.foolishness = new PotionBase(
                true, 116, 196, 42, RefPotionNames.FOOLISHNESS, RPGPotions.WISDOM_ATTRIBUTE,
                "47544add-a921-4b4d-b247-c5da8db72cd1", -2, 0);
    }
}
