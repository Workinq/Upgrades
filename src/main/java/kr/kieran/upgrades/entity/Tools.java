package kr.kieran.upgrades.entity;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.Material;

import java.util.List;

@EditorName("config")
public class Tools extends Entity<Tools> {

    protected static transient Tools i;
    public String craftWandName = "&8&m&l*&7&m&l*&8&m&l*&8( &6&lCraft Wand &8)&8&m&l*&7&m&l*&8&m&l*";
    public List<String> craftWandLore = MUtil.list("&7Right click a chest to craft all ingots into blocks.");
    public Material craftWandMaterial = Material.DIAMOND_HOE;
    public String fishingRodName = "&8&m&l*&7&m&l*&8&m&l*&8( &6&lFishing Rod &8)&8&m&l*&7&m&l*&8&m&l*";
    public List<String> fishingRodLore = MUtil.list("&7This fishing rod gives you a chance to get tokens when fishing.", "", "&7Type &6/upgrade &7to open a menu where you", "&7can upgrade the chance to get tokens.", "", "&7Fishing reward level: &60", "&7Token chance: &675%", "&7Fishing rod level: &60");
    public Material fishingRodMaterial = Material.FISHING_ROD;
    public String harvesterHoeName = "&8&m&l*&7&m&l*&8&m&l*&8( &6&lHarvester Hoe &8)&8&m&l*&7&m&l*&8&m&l*";
    public List<String> harvesterHoeLore = MUtil.list("&7Break sugar cane with this hoe to have it automatically sell.", "", "&7Type &6/upgrade &7to open a menu where you", "&7can upgrade the explosive level and chance to get tokens.", "", "&7Explosive level: &60", "&7Token chance: &675%", "&7Harvester hoe level: &60");
    public Material harvesterHoeMaterial = Material.DIAMOND_HOE;
    public String lightningWandName = "&8&m&l*&7&m&l*&8&m&l*&8( &6&lLightning Wand &8)&8&m&l*&7&m&l*&8&m&l*";
    public List<String> lightningWandLore = MUtil.list("&7Strike lightning, every &610 seconds&7.");
    public Material lightningWandMaterial = Material.DIAMOND_HOE;
    public String sandWandName = "&8&m&l*&7&m&l*&8&m&l*&8( &6&lSand Wand &8)&8&m&l*&7&m&l*&8&m&l*";
    public List<String> sandWandLore = MUtil.list("&7Destroy sand pillars at a cost.");
    public Material sandWandMaterial = Material.DIAMOND_HOE;
    public String sellWandName = "&8&m&l*&7&m&l*&8&m&l*&8( &6&lSell Wand &8)&8&m&l*&7&m&l*&8&m&l*";
    public List<String> sellWandLore = MUtil.list("&7Right click a chest to sell all of its items.", "", "&7Type &6/upgrade &7to open a menu where you", "&7can upgrade the sell multiplier.", "", "&7Total value sold: &6$0", "&7Sell multiplier: &675%", "&7Sell wand level: &60");
    public Material sellWandMaterial = Material.BLAZE_ROD;
    public String trayPickaxeName = "&8&m&l*&7&m&l*&8&m&l*&8( &6&lTray Pickaxe &8)&8&m&l*&7&m&l*&8&m&l*";
    public List<String> trayPickaxeLore = MUtil.list("&7Easily mine blocks used to build trays around you.");
    public Material trayPickaxeMaterial = Material.DIAMOND_PICKAXE;
    public int trayPickaxeRadius = 10;
    public String trenchPickaxeName = "&8&m&l*&7&m&l*&8&m&l*&8( &6&lTrench Pickaxe &8)&8&m&l*&7&m&l*&8&m&l*";
    public List<String> trenchPickaxeLore = MUtil.list("&7Mine blocks around you in a &6%radius% &7block radius.");
    public Material trenchPickaxeMaterial = Material.DIAMOND_PICKAXE;
    public List<String> trenchBlacklistedBlocks = MUtil.list("BEDROCK", "MOB_SPAWNER", "BEACON", "CHEST", "TRAPPED_CHEST");

    public static Tools get() {
        return i;
    }

    @Override
    public Tools load(Tools that) {
        super.load(that);
        return this;
    }

}
