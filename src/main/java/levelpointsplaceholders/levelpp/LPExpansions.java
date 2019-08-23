package levelpointsplaceholders.levelpp;

import levelpointsplaceholders.levelpp.placeholderapi.lp.expansions.LevelPointsExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class LPExpansions extends JavaPlugin {

    @Override
    public void onEnable(){
        // Small check to make sure that PlaceholderAPI is installed
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new LevelPointsExpansion().register();
        }
    }
}
