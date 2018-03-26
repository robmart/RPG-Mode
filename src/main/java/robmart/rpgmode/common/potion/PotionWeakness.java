package robmart.rpgmode.common.potion;

import robmart.rpgmode.common.helper.PotionHelper;

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
public class PotionWeakness extends PotionBase {
    public static final String TAG_NAME = "rpgmode - weakness";
    public static PotionWeakness instance = null;

    public PotionWeakness() {
        super(true, 61, 171, 234, "weakness");
        this.registerPotionAttributeModifier(PotionHelper.STRENGTH, "2f6f5f5f-172b-4f95-97e3-d976823ed50e", -2, 0);
        instance = this;
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}