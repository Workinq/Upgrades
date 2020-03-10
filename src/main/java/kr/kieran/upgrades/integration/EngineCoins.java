package kr.kieran.upgrades.integration;

import com.massivecraft.massivecore.Engine;
import kr.kieran.convictcoins.api.CoinsAPI;
import org.bukkit.entity.Player;

public class EngineCoins extends Engine {

    private static EngineCoins i = new EngineCoins();

    public static EngineCoins get()
    {
        return i;
    }

    public boolean canAfford(Player player, double amount)
    {
        return CoinsAPI.canAffordPlayer(player, amount);
    }

    public void withdraw(Player player, double amount)
    {
        CoinsAPI.removePlayer(player, amount);
    }

    public void add(Player player, double amount)
    {
        CoinsAPI.addPlayer(player, amount);
    }

    public double getBalance(Player player)
    {
        return CoinsAPI.getPlayerBalance(player);
    }

    @Override
    public void setActive(boolean active)
    {
        super.setActive(active);
    }

}
