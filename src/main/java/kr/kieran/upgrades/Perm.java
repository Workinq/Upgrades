package kr.kieran.upgrades;

import com.massivecraft.massivecore.Identified;
import com.massivecraft.massivecore.util.PermissionUtil;
import org.bukkit.permissions.Permissible;

public enum Perm implements Identified {

    BASECOMMAND,
    UPGRADES,
    TOOLS,
    TRENCH_PICKAXE;

    private final String id;

    @Override
    public String getId()
    {
        return this.id;
    }

    Perm()
    {
        this.id = PermissionUtil.createPermissionId(UpgradesPlugin.get(), this);
    }

    public boolean has(Permissible permissible, boolean verboose)
    {
        return PermissionUtil.hasPermission(permissible, this, verboose);
    }

    public boolean has(Permissible permissible)
    {
        return PermissionUtil.hasPermission(permissible, this);
    }

}
