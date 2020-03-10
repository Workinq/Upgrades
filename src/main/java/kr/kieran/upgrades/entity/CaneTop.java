package kr.kieran.upgrades.entity;

import com.massivecraft.massivecore.collections.MassiveMap;
import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;

import java.util.UUID;

@EditorName("config")
public class CaneTop extends Entity<CaneTop> {

    protected static transient CaneTop i;
    private MassiveMap<UUID, Integer> players = new MassiveMap<>();

    public static CaneTop get()
    {
        return i;
    }

    public MassiveMap<UUID, Integer> getPlayers()
    {
        return players;
    }

    public void setPlayers(MassiveMap<UUID, Integer> players)
    {
        this.players = players;
    }

    public void increaseStats(UUID uuid, int amount)
    {
        players.putIfAbsent(uuid, 0);
        players.put(uuid, players.get(uuid) + amount);
        this.changed();
    }

    @Override
    public CaneTop load(CaneTop that)
    {
        super.load(that);
        setPlayers(that.players);
        return this;
    }

}
