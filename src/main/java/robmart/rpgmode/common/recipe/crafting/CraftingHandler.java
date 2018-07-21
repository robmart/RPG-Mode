/*
 * This is not under any license
 * The original source is viewable at https://gist.github.com/williewillus/a1a899ce5b0f0ba099078d46ae3dae6e
 */

package robmart.rpgmode.common.recipe.crafting;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.commons.io.FileUtils;
import robmart.rpgmode.common.reference.Reference;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author williewillus
 */
public class CraftingHandler {
    private static final Gson        GSON           = new GsonBuilder().setPrettyPrinting().create();
    private static final Set<String> USED_OD_NAMES  = new TreeSet<>();
    private static       File        RECIPE_DIR     = null;
    private static       File        RECIPE_OUT_DIR = null;

    private static File getMcDir() {
        if (FMLCommonHandler.instance().getMinecraftServerInstance() != null &&
            FMLCommonHandler.instance().getMinecraftServerInstance().isDedicatedServer()) {
            return new File(".");
        }
        return Minecraft.getMinecraft().mcDataDir;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void setupDir() {
        if (RECIPE_DIR == null) {
            RECIPE_DIR = new File(
                    getMcDir(), String.format("../src/main/resources/assets/%s/recipes/", Reference.MOD_ID));

            if (RECIPE_DIR.exists()) {
                String[] entries = RECIPE_DIR.list();
                if (entries != null)
                    for (String s : entries) {
                        File currentFile = new File(RECIPE_DIR.getPath(), s);
                        currentFile.delete();
                    }
                RECIPE_DIR.delete();
            }
        }

        if (!RECIPE_DIR.exists()) {
            RECIPE_DIR.mkdir();
        }
    }

    public static void generateConstants() {
        System.out.println("Generate constants");
        setupOutDir();

        List<Map<String, Object>> json = new ArrayList<>();
        for (String s : USED_OD_NAMES) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("name", s.toUpperCase(Locale.ROOT));
            entry.put("ingredient", ImmutableMap.of("type", "forge:ore_dict", "ore", s));
            json.add(entry);
        }

        try (FileWriter w = new FileWriter(new File(RECIPE_DIR, "_constants.json"))) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();

        }
        try {
            FileUtils.copyDirectory(RECIPE_DIR, RECIPE_OUT_DIR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addShapedRecipe(ItemStack result, Object... components) {
        setupDir();

        // GameRegistry.addShapedRecipe(result, components);

        Map<String, Object> json = new HashMap<>();

        List<String> pattern = new ArrayList<>();
        int i = 0;
        while (i < components.length && components[i] instanceof String) {
            pattern.add((String) components[i]);
            i++;
        }
        json.put("pattern", pattern);

        boolean isOreDict = false;
        Map<String, Map<String, Object>> key = new HashMap<>();
        Character curKey = null;
        for (; i < components.length; i++) {
            Object o = components[i];
            if (o instanceof Character) {
                if (curKey != null)
                    throw new IllegalArgumentException("Provided two char keys in a row");
                curKey = (Character) o;
            }
            else {
                if (curKey == null)
                    throw new IllegalArgumentException("Providing object without a char key");
                if (o instanceof String)
                    isOreDict = true;
                key.put(Character.toString(curKey), serializeItem(o));
                curKey = null;
            }
        }
        json.put("key", key);
        json.put("type", isOreDict ? "forge:ore_shaped" : "minecraft:crafting_shaped");
        json.put("result", serializeItem(result));

        // names the json the same name as the output's registry name
        // repeatedly adds _alt if a file already exists
        // janky I know but it works
        String suffix = result.getItem().getHasSubtypes() ? "_" + result.getItemDamage() : "";
        File f = new File(RECIPE_DIR, result.getItem().getRegistryName().getResourcePath() + suffix + ".json");

        while (f.exists()) {
            suffix += "_alt";
            f = new File(RECIPE_DIR, result.getItem().getRegistryName().getResourcePath() + suffix + ".json");
        }

        try (FileWriter w = new FileWriter(f)) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addShapelessRecipe(ItemStack result, Object... components) {
        setupDir();

        // addShapelessRecipe(result, components);

        Map<String, Object> json = new HashMap<>();

        boolean isOreDict = false;
        List<Map<String, Object>> ingredients = new ArrayList<>();
        for (Object o : components) {
            if (o instanceof String)
                isOreDict = true;
            ingredients.add(serializeItem(o));
        }
        json.put("ingredients", ingredients);
        json.put("type", isOreDict ? "forge:ore_shapeless" : "minecraft:crafting_shapeless");
        json.put("result", serializeItem(result));

        // names the json the same name as the output's registry name
        // repeatedly adds _alt if a file already exists
        // janky I know but it works
        String suffix = result.getItem().getHasSubtypes() ? "_" + result.getItemDamage() : "";
        File f = new File(RECIPE_DIR, result.getItem().getRegistryName().getResourcePath() + suffix + ".json");

        while (f.exists()) {
            suffix += "_alt";
            f = new File(RECIPE_DIR, result.getItem().getRegistryName().getResourcePath() + suffix + ".json");
        }


        try (FileWriter w = new FileWriter(f)) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Object> serializeItem(Object thing) {
        if (thing instanceof Item) {
            return serializeItem(new ItemStack((Item) thing));
        }
        if (thing instanceof Block) {
            return serializeItem(new ItemStack((Block) thing));
        }
        if (thing instanceof ItemStack) {
            ItemStack stack = (ItemStack) thing;
            Map<String, Object> ret = new HashMap<>();
            ret.put("item", stack.getItem().getRegistryName().toString());
            if (stack.getItem().getHasSubtypes() || stack.getItemDamage() != 0) {
                ret.put("data", stack.getItemDamage());
            }
            if (stack.getCount() > 1) {
                ret.put("count", stack.getCount());
            }

            if (stack.hasTagCompound()) {
                ret.put("type", "minecraft:item_nbt");
                ret.put("nbt", stack.getTagCompound().toString());
            }

            return ret;
        }
        if (thing instanceof String) {
            Map<String, Object> ret = new HashMap<>();
            USED_OD_NAMES.add((String) thing);
            ret.put("item", "#" + ((String) thing).toUpperCase(Locale.ROOT));
            return ret;
        }

        throw new IllegalArgumentException("Not a block, item, stack, or od name");
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void setupOutDir() {
        if (RECIPE_OUT_DIR == null) {
            RECIPE_OUT_DIR = new File(
                    getMcDir(), String.format("../out/production/RPG-Mode_main/assets/%s/recipes/", Reference.MOD_ID));

            if (RECIPE_OUT_DIR.exists()) {
                String[] entries = RECIPE_OUT_DIR.list();
                if (entries != null)
                    for (String s : entries) {
                        File currentFile = new File(RECIPE_OUT_DIR.getPath(), s);
                        currentFile.delete();
                    }
                RECIPE_OUT_DIR.delete();
            }
        }

        if (!RECIPE_OUT_DIR.exists()) {
            RECIPE_OUT_DIR.mkdir();
        }
    }
}
