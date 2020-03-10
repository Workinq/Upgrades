package kr.kieran.upgrades.cmd;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.mson.Mson;
import com.massivecraft.massivecore.pager.Msonifier;
import com.massivecraft.massivecore.pager.Pager;
import com.massivecraft.massivecore.util.Txt;
import kr.kieran.upgrades.entity.CaneTop;
import kr.kieran.upgrades.entity.MConf;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.*;
import java.util.stream.Collectors;

public class CmdCaneTop extends MassiveCommand {

    private static CmdCaneTop i = new CmdCaneTop();

    public CmdCaneTop()
    {
        this.addParameter(TypeInteger.get(), "page", "1");
    }

    public static CmdCaneTop get()
    {
        return i;
    }

    @Override
    public List<String> getAliases()
    {
        return MConf.get().caneTopAliases;
    }

    @Override
    public void perform() throws MassiveException
    {
        int page = this.readArgAt(0, 1);
        if (CaneTop.get().getPlayers().isEmpty())
        {
            MixinMessage.get().msgOne(sender, MConf.get().noCaneTopPlayers);
            return;
        }
        Map<UUID, Integer> sorted = CaneTop.get().getPlayers().entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        List<Mson> caneTopList = new ArrayList<>();
        int counter = 0;
        for (Map.Entry<UUID, Integer> entry : sorted.entrySet())
        {
            OfflinePlayer player = Bukkit.getOfflinePlayer(entry.getKey());
            Mson mson = mson(Txt.parse(MConf.get().caneTopFormat, counter + 1, player.getName().equals(sender.getName()) ? Txt.parse("<g>" + player.getName()) : Txt.parse("<n>" + player.getName()), entry.getValue()));
            caneTopList.add(mson); counter += 1;
        }
        Pager<Mson> pager = new Pager<>(this, "Cane Top", page, caneTopList, (Msonifier<Mson>) (mson, i) -> caneTopList.get(i));
        pager.message();
    }

}
