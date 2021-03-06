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

package robmart.rpgmode.api.potion;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.potion.Potion;
import robmart.rpgmode.api.reference.Reference;

import static robmart.rpgmode.api.util.InjectionUtil.Null;

/**
 * @author Robmart
 * Created on 11/10/2018
 */
public class RPGPotions {
    public static final IAttribute STRENGTH_ATTRIBUTE =
            new RangedAttribute(null, Reference.MOD_ID + ".strength", 0,
                                -10, 10).setDescription("Strength").setShouldWatch(true);
    public static final IAttribute DEXTEROUSNESS_ATTRIBUTE = new
            RangedAttribute(null, Reference.MOD_ID + ".dexterousness", 0,
                            -10, 10).setDescription("Dexterity").setShouldWatch(true);
    public static final IAttribute FORTITUDE_ATTRIBUTE = new
            RangedAttribute(null, Reference.MOD_ID + ".fortitude", 0,
                            -10, 10).setDescription("Constitution").setShouldWatch(true);
    public static final IAttribute INTELLIGENCE_ATTRIBUTE = new
            RangedAttribute(null, Reference.MOD_ID + ".intelligence", 0,
                            -10, 10).setDescription("Intelligence").setShouldWatch(true);
    public static final IAttribute WISDOM_ATTRIBUTE = new
            RangedAttribute(null, Reference.MOD_ID + ".wisdom", 0,
                            -10, 10).setDescription("Wisdom").setShouldWatch(true);

    public static final Potion LAVA          = Null();
    public static final Potion STRENGTH      = Null();
    public static final Potion WEAKNESS      = Null();
    public static final Potion DEXTEROUSNESS = Null();
    public static final Potion CLUMSINESS    = Null();
    public static final Potion FORTITUDE     = Null();
    public static final Potion LETHARGY      = Null();
    public static final Potion INTELLIGENCE  = Null();
    public static final Potion STUPIDITY     = Null();
    public static final Potion WISDOM        = Null();
    public static final Potion FOOLISHNESS   = Null();

    private RPGPotions() {
    }
}
