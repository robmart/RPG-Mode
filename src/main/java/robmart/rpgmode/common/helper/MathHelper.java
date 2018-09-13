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

package robmart.rpgmode.common.helper;

/**
 * @author Robmart
 * Created on 9/13/2018
 */
public class MathHelper {

    public static float diminishingReturns(float val, float scale) {
        if (val < 0)
            return -diminishingReturns(-val, scale);
        float mult = val / scale;
        double trinum = (Math.sqrt(8.0 * mult + 1.0) - 1.0) / 2.0;
        return (float) trinum * scale;
    }
}
