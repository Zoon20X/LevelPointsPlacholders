package levelpointsplaceholders.levelpp.placeholderapi.lp.expansions;


import levelpoints.lp.LP;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class LevelPointsExpansion extends PlaceholderExpansion {


    private int exps;
    private int take;
    private int leftover;
    private int nlevel;
    public int needep;
    public int LEXP;
    public int prestige;
    private LP plugin;

    @Override
    public boolean canRegister() {
        return Bukkit.getPluginManager().getPlugin("LP") != null;
    }

    @Override
    public boolean register() {

        // Make sure "SomePlugin" is on the server
        if (!canRegister()) {
            return false;
        }

        plugin = (LP) Bukkit.getPluginManager().getPlugin("LP");

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
        return "0.0.3";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        File userdata = new File(plugin.userFolder, player.getUniqueId() + ".yml");
        FileConfiguration UsersConfig = YamlConfiguration.loadConfiguration(userdata);
        int playerlevel = UsersConfig.getInt(player.getName() + ".level");
        int expamount = UsersConfig.getInt(player.getName() + ".EXP.Amount");
        int LEXP = UsersConfig.getInt("LevelingEXP");
        int Booster = UsersConfig.getInt(player.getName() + ".EXP.Active");
        if (plugin.LevelConfig.getBoolean("CustomLeveling")) {
            needep = plugin.LevelConfig.getInt("Level-" + playerlevel);
        } else {
            needep = playerlevel * LEXP;
        }
        float percentage = expamount * 100;
        prestige = UsersConfig.getInt(player.getName() + ".Prestige");


        int prestigelevel = UsersConfig.getInt(player.getName() + ".Prestige");

        String playerLevels = Integer.toString(playerlevel);

        double required_progress = needep;
        double current_progress = expamount;
        double progress_percentage = current_progress / required_progress;
        StringBuilder sb = new StringBuilder();
        int bar_length = 20;
        for (int i = 0; i < bar_length; i++) {
            if (i < bar_length * progress_percentage) {
                sb.append(ChatColor.GREEN + "" + ChatColor.BOLD + "|"); //what to append if percentage is covered (e.g. GREEN '|'s)
            } else {
                sb.append(ChatColor.GRAY + "" + ChatColor.BOLD + "|"); //what to append if percentage is not covered (e.g. GRAY '|'s)
            }
        }
        if (identifier.equals("player_level")) {
            return String.valueOf(playerLevels);
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
            return String.valueOf(Math.round(expamount / needep));
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