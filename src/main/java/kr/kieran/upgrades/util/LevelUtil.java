package kr.kieran.upgrades.util;

import com.massivecraft.massivecore.util.Txt;
import kr.kieran.upgrades.entity.Levels;
import kr.kieran.upgrades.enums.ToolType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class LevelUtil {

    public static int calculateCost(int level, int nextLevel, ToolType type) {
        int total = 0;
        int newLevel = level + nextLevel;
        if (newLevel > Levels.get().getMaxLevel(type)) newLevel = Levels.get().getMaxLevel(type);
        for (int i = level; i <= newLevel; i++) total += Levels.get().getPriceByLevel(i, type);
        return total;
    }

    private static int roundDown(double number) {
        double result = number / (double) 10;
        result = Math.floor(result);
        result *= 10;
        return (int) result;
    }

    public static void increaseSellWand(ItemStack item, int newLevel) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = item.getItemMeta().getLore();
        lore.set(7, Txt.parse("&7Sell wand level: &6" + newLevel));
        meta.setLore(lore);
        item.setItemMeta(meta);
        switch (LevelUtil.roundDown(newLevel)) {
            case 10:
                increaseSellMultiplier(item, 80);
                break;
            case 20:
                increaseSellMultiplier(item, 85);
                break;
            case 30:
                increaseSellMultiplier(item, 90);
                break;
            case 40:
                increaseSellMultiplier(item, 95);
                break;
            case 50:
                increaseSellMultiplier(item, 100);
                break;
            case 0:
            default:
                break;
        }
    }

    public static void increaseHarvesterHoe(ItemStack item, int newLevel) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = item.getItemMeta().getLore();
        lore.set(7, Txt.parse("&7Harvester hoe level: &6" + newLevel));
        meta.setLore(lore);
        item.setItemMeta(meta);
        switch (LevelUtil.roundDown(newLevel)) {
            case 10:
                increaseTokenChance(item, 80);
                break;
            case 20:
                increaseTokenChance(item, 85);
                break;
            case 30:
                increaseTokenChance(item, 85);
                increaseExplosive(item, 1);
                break;
            case 40:
                increaseTokenChance(item, 90);
                break;
            case 50:
                increaseTokenChance(item, 95);
                break;
            case 60:
                increaseTokenChance(item, 100);
                break;
            case 70:
                increaseTokenChance(item, 100);
                increaseExplosive(item, 2);
                break;
            case 0:
            default:
                break;
        }
    }

    public static void increaseFishingRod(ItemStack item, int newLevel) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = item.getItemMeta().getLore();
        lore.set(7, Txt.parse("&7Fishing rod level: &6" + newLevel));
        meta.setLore(lore);
        item.setItemMeta(meta);
        switch (LevelUtil.roundDown(newLevel)) {
            case 10:
                increaseTokenChance(item, 80);
                break;
            case 20:
                increaseTokenChance(item, 85);
                break;
            case 30:
                increaseTokenChance(item, 85);
                increaseFishingRewards(item, 1);
                break;
            case 40:
                increaseTokenChance(item, 90);
                break;
            case 50:
                increaseTokenChance(item, 95);
                break;
            case 60:
                increaseTokenChance(item, 100);
                break;
            case 70:
                increaseTokenChance(item, 100);
                increaseFishingRewards(item, 2);
                break;
            case 0:
            default:
                break;
        }
    }

    private static void increaseTokenChance(ItemStack item, int newChance) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = item.getItemMeta().getLore();
        lore.set(6, Txt.parse("&7Token chance: &6" + newChance + "%"));
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    private static void increaseExplosive(ItemStack item, int newLevel) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = item.getItemMeta().getLore();
        lore.set(5, Txt.parse("&7Explosive level: &6" + newLevel));
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    private static void increaseSellMultiplier(ItemStack item, int newMultiplier) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = item.getItemMeta().getLore();
        lore.set(6, Txt.parse("&7Sell multiplier: &6" + newMultiplier + "%"));
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    private static void increaseFishingRewards(ItemStack item, int newReward) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = item.getItemMeta().getLore();
        lore.set(5, Txt.parse("&7Fishing reward level: &6" + newReward));
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

}
