package kr.kieran.upgrades.integration;

import com.massivecraft.massivecore.Integration;

public class IntegrationCoins extends Integration {

    private static IntegrationCoins i = new IntegrationCoins();

    private IntegrationCoins()
    {
        this.setPluginName("Coins");
    }

    public static IntegrationCoins get()
    {
        return i;
    }

    @Override
    public EngineCoins getEngine()
    {
        return EngineCoins.get();
    }

}
