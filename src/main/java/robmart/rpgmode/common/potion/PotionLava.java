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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import robmart.rpgmode.api.reference.RefPotionNames;

import javax.annotation.Nullable;

/**
 * @author Robmart
 */
public class PotionLava extends PotionBase {

    public PotionLava() {
        super(true, -2860526, RefPotionNames.LAVA, false);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return false;
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    @Override
    public void affectEntity(
            @Nullable Entity source, @Nullable Entity indirectSource, EntityLivingBase entityLivingBaseIn,
            int amplifier, double health) {
        entityLivingBaseIn.setFire(10);
    }
}
