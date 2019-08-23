package levelpointsplaceholders.levelpp.placeholderapi.lp.expansions;


import levelpoints.lp.LP;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
        return "1.0.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {

        int playerlevel = plugin.getPlayersConfig().getInt(player.getName() + ".level");
        int expamount = plugin.getPlayersConfig().getInt(player.getName() + ".EXP.Amount");
        if (plugin.LevelConfig.getBoolean("CustomLeveling")) {
            needep = plugin.LevelConfig.getInt("Level-" + nlevel);
        } else {
            needep = nlevel * LEXP;
        }
        float percentage = expamount * 100;
        prestige = plugin.getPlayersConfig().getInt(player.getName() + ".Prestige");


        int prestigelevel = plugin.getPlayersConfig().getInt(player.getName() + ".Prestige");

        String playerLevels = Integer.toString(playerlevel);
        if (identifier.equals("player_level")) {
            return String.valueOf(playerLevels);
        }
        if (identifier.equals("exp_amount")) {
            return String.valueOf(expamount);
        }
        if (identifier.equals("exp_required")) {
            return String.valueOf(needep);
        }
        if (identifier.equals("exp_progress")) {
            return String.valueOf(Math.round(expamount / needep));
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