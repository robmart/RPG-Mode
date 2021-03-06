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

package robmart.rpgmode.common.potion;

import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import vazkii.arl.util.ProxyRegistry;

import javax.annotation.Nullable;

/**
 * @author Robmart
 * Created on 11/11/2018
 */
public class PotionTypeBase extends PotionType {

    public PotionTypeBase(@Nullable String baseName, PotionEffect... effects) {
        super(baseName, effects);

        ProxyRegistry.register(this);
    }
}
