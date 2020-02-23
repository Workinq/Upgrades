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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CmdTrenchPickaxe extends MassiveCommand {

    private static CmdTrenchPickaxe i = new CmdTrenchPickaxe();

    public CmdTrenchPickaxe() {
        this.addParameter(TypePlayer.get(), "player");
        this.addParameter(TypeInteger.get(), "radius");
        this.addParameter(TypeInteger.get(), "amount", "1");
        this.addParameter(TypeBooleanTrue.get(), "silent", "false");
        this.addRequirements(RequirementHasPerm.get(Perm.TRENCH_PICKAXE));
    }

    public static CmdTrenchPickaxe get() {
        return i;
    }

    @Override
    public List<String> getAliases() {
        return MConf.get().trenchPickAliases;
    }

    @Override
    public void perform() throws MassiveException {
        Player player = this.readArgAt(0);
        int radius = this.readArgAt(1);
        int amount = this.readArgAt(2, 1);
        boolean silent = this.readArgAt(3, false);
        List<String> lore = new ArrayList<>();
        for (String string : Tools.get().trenchPickaxeLore) {
            lore.add(Txt.parse(string.replace("%radius%", String.valueOf(radius))));
        }
        ItemStack trenchPickaxe = new ItemBuilder(Tools.get().trenchPickaxeMaterial).name(Txt.parse(Tools.get().trenchPickaxeName)).setLore(lore).amount(amount).unbreakable(true);
        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation(), trenchPickaxe);
        } else {
            player.getInventory().addItem(trenchPickaxe);
        }
        if (!silent) MixinMessage.get().msgOne(player, MConf.get().receivedTool, amount, ToolType.TRENCH_PICKAXE.getName());
        MixinMessage.get().msgOne(sender, MConf.get().gaveTool, player.getName(), amount, ToolType.TRENCH_PICKAXE.getName());
    }

}
