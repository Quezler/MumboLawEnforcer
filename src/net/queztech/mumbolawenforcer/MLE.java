package net.queztech.mumbolawenforcer;

import org.bukkit.plugin.java.JavaPlugin;

public class MLE extends JavaPlugin {

    @Override
    public void onEnable(){
        getLogger().info("Boes and hoes <3");

        getServer().getPluginManager().registerEvents(new Events(this), this);

    }

}
