package kr.kieran.upgrades.cmd;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanTrue;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.util.Txt;
import kr.kieran.upgrades.Perm;
import kr.kieran.upgrades.entity.MConf;
import kr.kieran.upgrades.entity.Tools;
import kr.kieran.upgrades.enums.ToolType;
import kr.kieran.upgrades.util.ItemBuilder;
import kr.kieran.upgrades.util.LevelUtil;
import kr.kieran.upgrades.cmd.type.TypeTool;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CmdTools extends MassiveCommand {

    private static CmdTools i = new CmdTools();

    public CmdTools() {
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypeTool.get(), "tool");
        this.addParameter(TypeInteger.get(), "level", "0");
        this.addParameter(TypeInteger.get(), "amount", "1");
        this.addParameter(TypeBooleanTrue.get(), "silent", "false");
        this.addRequirements(RequirementHasPerm.get(Perm.TOOLS));
    }

    public static CmdTools get() {
        return i;
    }

    @Override
    public List<String> getAliases() {
        return MConf.get().toolsAliases;
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArgAt(0);
        ToolType type = this.readArgAt(1);
        int level = this.readArgAt(2, 0);
        int amount = this.readArgAt(3, 1);
        boolean silent = this.readArgAt(4, false);
        if (type == null) {
            MixinMessage.get().msgOne(sender, MConf.get().invalidTool);
            return;
        }
        ItemStack item;
        switch (type) {
            case CRAFT_WAND:
                item = new ItemBuilder(Tools.get().craftWandMaterial).name(Txt.parse(Tools.get().craftWandName)).setLore(Txt.parse(Tools.get().craftWandLore)).amount(amount).unbreakable(true);
                break;
            case FISHING_ROD:
                item = new ItemBuilder(Tools.get().fishingRodMaterial).name(Txt.parse(Tools.get().fishingRodName)).setLore(Txt.parse(Tools.get().fishingRodLore)).amount(amount).unbreakable(true);
                LevelUtil.increaseFishingRod(item, level);
                break;
            case HARVESTER_HOE:
                item = new ItemBuilder(Tools.get().harvesterHoeMaterial).name(Txt.parse(Tools.get().harvesterHoeName)).setLore(Txt.parse(Tools.get().harvesterHoeLore)).amount(amount).unbreakable(true);
                LevelUtil.increaseHarvesterHoe(item, level);
                break;
            case LIGHTNING_WAND:
                item = new ItemBuilder(Tools.get().lightningWandMaterial).name(Txt.parse(Tools.get().lightningWandName)).setLore(Txt.parse(Tools.get().lightningWandLore)).amount(amount).unbreakable(true);
                break;
            case SAND_WAND:
                item = new ItemBuilder(Tools.get().sandWandMaterial).name(Txt.parse(Tools.get().sandWandName)).setLore(Txt.parse(Tools.get().sandWandLore)).amount(amount).unbreakable(true);
                break;
            case SELL_WAND:
                item = new ItemBuilder(Tools.get().sellWandMaterial).name(Txt.parse(Tools.get().sellWandName)).setLore(Txt.parse(Tools.get().sellWandLore)).amount(amount).unbreakable(true);
                LevelUtil.increaseSellWand(item, level);
                break;
            case TRAY_PICKAXE:
                item = new ItemBuilder(Tools.get().trayPickaxeMaterial).name(Txt.parse(Tools.get().trayPickaxeName)).setLore(Txt.parse(Tools.get().trayPickaxeLore)).amount(amount).unbreakable(true);
                break;
            case TRENCH_PICKAXE:
            default:
                item = null;
                break;
        }
        if (item == null) {
            MixinMessage.get().msgOne(sender, MConf.get().invalidTool);
            return;
        }
        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation(), item);
        } else {
            player.getInventory().addItem(item);
        }
        if (!silent) MixinMessage.get().msgOne(player, MConf.get().receivedTool, amount, type.getName());
        MixinMessage.get().msgOne(sender, MConf.get().gaveTool, player.getName(), amount, type.getName());
    }

}
