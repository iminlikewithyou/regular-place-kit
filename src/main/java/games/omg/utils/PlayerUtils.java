package games.omg.utils;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import games.omg.resources.ServerColors;
import games.omg.resources.ServerStrings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.Style.Merge;

public class PlayerUtils {

  // We don't color the nametag here because we want to be able to color it differently for different teams
  // Or various different colors for various different reasons
  public static Component getNametag(OfflinePlayer player) {
    String name = PlayerUtils.getPlayerName(player);

    // Component prefix = Component.text(ServerStrings.USERNAME_PREFIX)
    //                             .color(ServerColors.OWNER_ROLE_COLOR)
    //                             .mergeStyle(Style.empty(), Style.Merge.Strategy.IF_ABSENT_ON_TARGET);

    // Component username = Component.text(name);

    // JoinConfiguration config = JoinConfiguration.builder()
    //                                             .parentStyle(null)

    // // use Joining to append the prefix and username together
    // return Component.join();

    Component bulletPoint = Component.text(ServerStrings.USERNAME_PREFIX).color(ServerColors.OWNER_ROLE_COLOR); // Red bullet point
    Component username = Component.text(name); // Username without color

    JoinConfiguration joinConfig = JoinConfiguration.noSeparators();
    Component combined = Component.join(joinConfig, bulletPoint, username);

    // Later, apply fallback style
    // combined = combined.applyFallbackStyle(Style.style(NamedTextColor.YELLOW)); // Example fallback color

    return combined;
  }

  public static String getPlayerName(Player player) {
    return getPlayerName((OfflinePlayer) player);
  }

  public static String getPlayerName(OfflinePlayer player) {
    // TODO: possibly nicknames in the future

    return player.getName();
  }

  public static String getPlayerName(CommandSender sender)  {
    if (sender instanceof OfflinePlayer) {
      return getPlayerName((OfflinePlayer) sender);
    }
    return sender.getName();
  }

  // for commands where you can enter partial usernames and search for online players
  // eventually, this should also be added to the proxy server to also search for players in other servers and possibly teleport to them
  public static Player getSearchedPlayer(String query) {
    Collection<? extends Player> players = Bukkit.getOnlinePlayers();
    query = query.toLowerCase();

    // exact matches will always be returned immediately,
    // otherwise we store the next best matches and return them if no exact match is found
    Player partialMatch = null;
    Player displayExactMatch = null;
    Player displayPartialMatch = null;
    
    // look for an exact match first
    for (Player player : players) {
      String name = player.getName().toLowerCase();
      String displayName = getPlayerName(player).toLowerCase();

      if (name.equals(query)) {
        // an exact match was hit for the username - return it
        return player;
      } else if (name.startsWith(query)) {
        partialMatch = player;
      }

      if (displayName.equals(query)) {
        displayExactMatch = player;
      } else if (displayName.startsWith(query)) {
        displayPartialMatch = player;
      }
    }

    // if no exact match was found, return the next best matches
    if (partialMatch != null) return partialMatch;
    if (displayExactMatch != null) return displayExactMatch;
    if (displayPartialMatch != null) return displayPartialMatch;
    return null;
  }
}
