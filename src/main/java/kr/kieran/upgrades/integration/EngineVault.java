package kr.kieran.upgrades.integration;

import com.massivecraft.massivecore.Engine;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EngineVault extends Engine {

    private static EngineVault i = new EngineVault();
    private Economy economy;

    private EngineVault() {
        setupEconomy();
    }

    public static EngineVault get() {
        return i;
    }

    public Economy getEconomy() {
        return economy;
    }

    private void setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return;
        economy = rsp.getProvider();
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
    }

}
