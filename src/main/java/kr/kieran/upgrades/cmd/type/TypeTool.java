package kr.kieran.upgrades.cmd.type;

import com.massivecraft.massivecore.command.type.TypeAbstract;
import kr.kieran.upgrades.enums.ToolType;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collection;

public class TypeTool extends TypeAbstract<ToolType> {

    private static TypeTool i = new TypeTool();

    public TypeTool()
    {
        super(ToolType.class);
    }

    public static TypeTool get()
    {
        return i;
    }

    @Override
    public String getName()
    {
        return "tool";
    }

    @Override
    public ToolType read(String arg, CommandSender sender)
    {
        return ToolType.getByAlias(arg);
    }

    @Override
    public Collection<String> getTabList(CommandSender commandSender, String arg)
    {
        Collection<String> collection = new ArrayList<>();
        for (ToolType type : ToolType.values())
        {
            if (type == ToolType.TRENCH_PICKAXE)
            {
                continue;
            }
            collection.add(type.getAlias());
        }
        return collection;
    }

}
