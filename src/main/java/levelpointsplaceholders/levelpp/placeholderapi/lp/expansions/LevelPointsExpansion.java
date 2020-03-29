package levelpointsplaceholders.levelpp.placeholderapi.lp.expansions;


import levelpoints.levelpoints.LevelPoints;

import levelpoints.utils.utils.API;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class LevelPointsExpansion extends PlaceholderExpansion {

    private int posTop = 0;
    public int prestige;
    private LevelPoints plugin;

    @Override
    public boolean canRegister() {
        return Bukkit.getPluginManager().getPlugin("LevelPoints") != null;
    }

    @Override
    public boolean register() {

        // Make sure "SomePlugin" is on the server
        if (!canRegister()) {
            return false;
        }

        plugin = (LevelPoints) Bukkit.getPluginManager().getPlugin("LevelPoints");

        if (plugin == null) {
            return false;
        }

        /*
         * Since we override the register method, we need to call the super method to actually
         * register this hook
         */
        return super.register();
    }

    @Override
    public String getAuthor() {
        return "Zoon20X";
    }


    @Override
    public String getIdentifier() {
        return "LevelPoints";
    }


    @Override
    public String getRequiredPlugin() {
        return "LevelPoints";
    }

    @Override
    public String getVersion() {
        return "0.1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        File userdata = new File(plugin.userFolder, player.getUniqueId() + ".yml");
        FileConfiguration UsersConfig = YamlConfiguration.loadConfiguration(userdata);
        int playerlevel = plugin.uc.getCurrentLevel(player);
        int expamount = plugin.uc.getCurrentEXP(player);
        int needep = plugin.uc.getRequiredEXP(player);
        int Booster = UsersConfig.getInt("ActiveBooster");
        float percentage = expamount * 100;
        prestige = UsersConfig.getInt("Prestige");


        int prestigelevel = UsersConfig.getInt("Prestige");

        String playerLevels = Integer.toString(playerlevel);

        double required_progress = needep;
        double current_progress = expamount;
        double progress_percentage = current_progress / required_progress;
        StringBuilder sb = new StringBuilder();

        int bar_length = 20;
        String completed = API.format(plugin.uc.getLangConfig().getString("lpBarDesignCompleted"));
        String need = API.format(plugin.uc.getLangConfig().getString("lpBarDesignRequired"));
        for (int i = 0; i < bar_length; i++) {
            if (i < bar_length * progress_percentage) {
                sb.append(completed);
            } else {
                sb.append(need);
            }
        }
        if (identifier.equals("player_level")) {
            return String.valueOf(playerLevels);
        }
        if(identifier.contains("Top")){

            ArrayList<String> name = new ArrayList<>();
            ArrayList<Integer> level = new ArrayList<>();
            FileConfiguration con = plugin.uc.getTopListConfig();
            ConfigurationSection cf = con.getConfigurationSection("");
            cf.getValues(false)
                    .entrySet()
                    .stream()
                    .sorted((a1, a2) -> {
                        int points1 = ((MemorySection) a1.getValue()).getInt("Level");
                        int points2 = ((MemorySection) a2.getValue()).getInt("Level");
                        return points2 - points1;
                    }).limit(10).forEach(f -> {
                        posTop += 1;

                        int points = ((MemorySection) f.getValue()).getInt("Level");
                        String playername = ((MemorySection) f.getValue()).getString("Name");

                        name.add(playername);
                        level.add(points);


                    });

            if(identifier.contains("Top_Name")){

                String value = identifier.replace("Top_Name_", "");
                if(name.size() < Integer.parseInt(value)){
                    return "";
                }else {
                    return name.get(Integer.parseInt(value) - 1);
                }
            }
            if(identifier.contains("Top_Level")) {
                String value = identifier.replace("Top_Level_", "");
                if (level.size() < Integer.parseInt(value)) {
                    return "";
                } else {
                    return String.valueOf(level.get(Integer.parseInt(value) - 1));
                }
            }


        }
        if (identifier.equals("exp_amount")) {
            return String.valueOf(expamount);
        }
        if (identifier.equals("exp_required")) {
            return String.valueOf(needep);
        }
        if (identifier.equals("progress_bar")) {
            return sb.toString();
        }
        if (identifier.equals("exp_progress")) {

            return String.valueOf(Math.round(percentage / needep));
        }
        if (identifier.equals("booster_active")) {
            return String.valueOf(Booster);
        }

        if (player == null) {
            return "";
        }
        if (!(prestigelevel == 0)) {
            if (identifier.equals("prestige")) {
                return String.valueOf(prestige);
            }
            if (player == null) {
                return "";
            }
        } else {
            if (identifier.equals("prestige")) {
                return "";
            }

        }
        return null;
    }
}