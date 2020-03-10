package kr.kieran.upgrades.entity;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.Material;

import java.util.List;

@EditorName("config")
public class MConf extends Entity<MConf> {

    protected static transient MConf i;
    public List<String> cmdAliases = MUtil.list("upgrade", "upgrades");
    public List<String> toolsAliases = MUtil.list("tool", "tools");
    public List<String> trenchPickAliases = MUtil.list("trenchpickaxe", "trenchpick");
    public List<String> caneTopAliases = MUtil.list("canetop");
    public String cannotAfford = "<b>You cannot afford this upgrade, you need <i>%,d token(s)<b>.";
    public String maximumLevel = "<b>This tool is already at its maximum level.";
    public String toolUpgraded = "<g>You've upgraded your tool by <i>%d level(s) <g>at a cost of <i>%,d token(s)<g>.";
    public String mustHoldItem = "<b>You must be holding a valid tool to open the upgrade GUI.";
    public String emptyChest = "<b>This chest is empty.";
    public String craftedItems = "<i>%,d item(s) <g>were converted into <i>%,d block(s)<g>.";
    public String notAllowedHere = "<b>You can only use this tool in your own faction territory.";
    public String lightningWandCooldown = "<b>You cannot use another lightning wand for another <i>%s second(s)<b>.";
    public String sandWandCharged = "<g>Your were charged <i>$%,d <g>for using a sand wand.";
    public String noItemsToSell = "<b>There are no sellable items in this chest.";
    public String soldItems = "<g>You sold <i>%,d item(s) <g>for <i>$%,.2f<g>.";
    public String invalidHarvesterHoe = "<b>You must be holding a harvester hoe to open the upgrade gui.";
    public String invalidSellWand = "<b>You must be holding a sell wand to open the upgrade gui.";
    public String invalidFishingRod = "<b>You must be holding a fishing rod to open the upgrade gui.";
    public String receivedTool = "<g>You received <i>%d %s(s)<g>.";
    public String gaveTool = "<g>You gave <i>%s %d %s(s)<g>.";
    public String invalidTool = "<b>The tool you specified is invalid or does not exist.";
    public String warzoneFishing = "<b>You can only fish in the warzone.";
    public String noCaneTopPlayers = "<b>There are no players that have broken sugar cane yet.";
    public String tooManyItems = "<b>You can only be holding 1 of this item to open the upgrades GUI.";
    public String caneTopFormat = "<i>%d. %s <k>%,d";
    public int lightningWandCooldownTime = 10;
    public int sandWandCost = 1000;
    public String openingUpgradeGui = "<g>Opening upgrades gui...";
    public String inventoryName = "&8(&6!&8) &7Upgrade";
    public String simpleHarvesterHoe = "&6&lHarvester Hoe";
    public String simpleSellWand = "&6&lSell Wand";
    public String simpleFishingRod = "&6&lFishing Rod";
    public String upgradeByOne = "&7Increase level by &61";
    public String upgradeByTen = "&7Increase level by &610";
    public List<String> upgradeLore = MUtil.list("", "&7Level up your %tool%", "&7by %levels% levels, costing: &6%tokens% tokens&7.");
    public Material fillerMaterial = Material.STAINED_GLASS_PANE;
    public byte fillerData = (byte) 7;
    public String fillerName = " ";
    public String notEnoughMoney = "<b>You do not have enough money to use this. You need $%.1f more.";

    public static MConf get()
    {
        return MConf.i;
    }

    @Override
    public MConf load(MConf that)
    {
        super.load(that);
        return this;
    }

}
