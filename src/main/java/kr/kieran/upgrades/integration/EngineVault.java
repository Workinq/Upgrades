package kr.kieran.upgrades.integration;

import com.massivecraft.massivecore.Engine;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EngineVault extends Engine {

    private static EngineVault i = new EngineVault();
    private Economy economy;

    public static EngineVault get()
    {
        return i;
    }

    public Economy getEconomy()
    {
        return economy;
    }

    private void setupEconomy()
    {
        RegisteredServiceProvider<Economy> provider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (provider == null)
        {
            return;
        }
        economy = provider.getProvider();
    }

    @Override
    public void setActive(boolean active)
    {
        super.setActive(active);
        setupEconomy();
    }

}
