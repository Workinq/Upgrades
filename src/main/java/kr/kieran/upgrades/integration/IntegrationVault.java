package kr.kieran.upgrades.integration;

import com.massivecraft.massivecore.Integration;

public class IntegrationVault extends Integration {

    private static IntegrationVault i = new IntegrationVault();

    private IntegrationVault()
    {
        this.setPluginName("Vault");
    }

    public static IntegrationVault get()
    {
        return i;
    }

    @Override
    public EngineVault getEngine()
    {
        return EngineVault.get();
    }

}
