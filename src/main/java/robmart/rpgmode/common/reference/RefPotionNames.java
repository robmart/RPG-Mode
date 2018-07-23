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

package robmart.rpgmode.common.reference;

/**
 * @author Robmart
 */
public class RefPotionNames {
    public static final String LAVA          = getName("lava");
    public static final String STRENGTH      = getName("strength");
    public static final String WEAKNESS      = getName("weakness");
    public static final String DEXTEROUSNESS = getName("dexterousness");
    public static final String CLUMSINESS    = getName("clumsiness");
    public static final String FORTITUDE     = getName("fortitude");
    public static final String LETHARGY      = getName("lethargy");
    public static final String INTELLIGENCE  = getName("intelligence");
    public static final String STUPIDITY     = getName("stupidity");
    public static final String WISDOM        = getName("wisdom");
    public static final String FOOLISHNESS   = getName("foolishness");

    private RefPotionNames() {
    }

    private static String getName(String name) {
        return String.format("%s:%s", Reference.MOD_ID, name);
    }
}
