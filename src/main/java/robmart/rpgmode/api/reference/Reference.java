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

package robmart.rpgmode.api.reference;

/**
 * @author Robmart
 * Contains mod id, name, version and the like
 */
public class Reference {
    public static final String MOD_ID            = "rpgmode";
    public static final String MOD_NAME          = "Rob's RPG Mode";
    public static final String BUILD             = "GRADLE:BUILD";
    public static final String MOD_VERSION       = "GRADLE:VERSION";
    public static final String MINECRAFT_VERSION = "GRADLE:MCVERSION";

    public static final String CLIENT_PROXY = "robmart.rpgmode.client.ClientProxy";
    public static final String SERVER_PROXY = "robmart.rpgmode.server.ServerProxy";

    public static final String GUI_FACTORY = "robmart.rpgmode.client.gui.RPGGuiConfig";

    public static final String DEPENDENCIES = "required-after:autoreglib@[1.3-20,]";

    private Reference() {
    }
}
