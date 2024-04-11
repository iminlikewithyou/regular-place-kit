package games.omg.command.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import games.omg.Main;
import games.omg.channeling.Channel;
import games.omg.channeling.TeleportTools;
import games.omg.channeling.states.ChannelStartedResult;
import games.omg.chat.SystemMessage;
import games.omg.command.CommandMessage;
import games.omg.command.RegularCommand;
import games.omg.utils.PlayerUtils;
import games.omg.utils.StringUtils;
import games.omg.utils.TaskManager;
import net.md_5.bungee.api.ChatColor;

public class TeleportCommand extends RegularCommand implements Listener {

  final static HashMap<Player, Player> teleportRequests = new HashMap<>();

  public CommandMessage execute(CommandSender sender, String label, String[] args) {
    Player player = (Player) sender;

    if (args.length < 1) {
      // The player hasn't entered a player name
      return CommandMessage.from("You need to enter a player name. (/" + label.toLowerCase() + " <name>)");
    }
    if (args.length > 1) {
      // The player entered too many arguments
      return CommandMessage.from("You need to enter only a player name. (/" + label.toLowerCase() + " " + args[0] + ")");
    }

    // Search for the player that was requested
    final Player searchedPlayer = PlayerUtils.getSearchedPlayer(args[0]);
    if (searchedPlayer == null) {
      return CommandMessage.from("That is not a valid player.");
    }

    if (label.equalsIgnoreCase("teleport") || label.equalsIgnoreCase("tp") || label.equalsIgnoreCase("tpa")) {
      // This is the teleport command

      // Don't allow requests to yourself
      if (searchedPlayer.equals(player)) {
        return CommandMessage.from("You can't teleport to yourself.");
      }

      // Check if we already have a pending request to teleport to someone
      if (teleportRequests.containsKey(player)) {
        // Check if we're already asking to teleport to the same player we asked before
        if (teleportRequests.get(player).equals(searchedPlayer)) {
          return CommandMessage.from("A request is already pending for that player.");
        }
        
        // Tell the players that we cancelled the prior request
        SystemMessage.from("Teleport", ChatColor.RESET + player.getName() + ChatColor.GRAY + " cancelled the teleport.").sendTo(teleportRequests.get(player));
        SystemMessage.from("Teleport", "You cancelled the teleport to " + ChatColor.RESET + teleportRequests.get(player).getName() + ChatColor.GRAY + ".").sendTo(player);
      }

      // Add the player to the teleportAsk map
      teleportRequests.put(player, searchedPlayer);

      // Create a task to remove the player from the teleportAsk map after 60 seconds
      TaskManager.startTask(player, "teleport", new Runnable() {
        public void run() {
          teleportRequests.remove(player);
        }
      }, 20 * 60);

      SystemMessage.from("Teleport", ChatColor.RESET + player.getName() + ChatColor.GRAY + " wants to teleport to you.\nAccept with (/tpaccept " + player.getName() + ")").sendTo(searchedPlayer);
      return CommandMessage.from("You asked to teleport to " + ChatColor.RESET + searchedPlayer.getName() + ChatColor.GRAY + ".");
    } else {
      // This is the /tpaccept command

      // Check if the player has a request to teleport from the the player they entered
      if (!(teleportRequests.containsKey(searchedPlayer) && teleportRequests.get(searchedPlayer).equals(player))) {
        return CommandMessage.from("That player hasn't asked to teleport to you.");
      }

      // Remove the teleport request
      teleportRequests.remove(searchedPlayer);

      // Start the channel
      UUID uuid = player.getUniqueId();
      Channel channel = new Channel(TeleportTools.getChannelingTimesBetweenPlayers(player, searchedPlayer),
          new Runnable() {
            @Override
            public void run() {
              if (!searchedPlayer.isOnline())
                return;
              Player teleportPlayer = Bukkit.getPlayer(uuid);
              if (teleportPlayer == null || !teleportPlayer.isOnline()) {
                SystemMessage.from("Teleport", "That player is gone.").sendTo(searchedPlayer);
                return;
              }
              SystemMessage
                  .from("Teleport", "Teleported to " + ChatColor.RESET + teleportPlayer.getName() + ChatColor.GRAY + ".")
                  .sendTo(searchedPlayer);
              SystemMessage.from("Teleport",
                  ChatColor.RESET + searchedPlayer.getName() + ChatColor.GRAY + " has arrived.").sendTo(teleportPlayer);
              searchedPlayer.teleport(teleportPlayer);
            }
          }, new Runnable() {
            @Override
            public void run() {
              Player teleportPlayer = Bukkit.getPlayer(uuid);
              if (teleportPlayer == null)
                return;
              SystemMessage.from("Teleport",
                  ChatColor.RESET + searchedPlayer.getName() + ChatColor.GRAY + " stopped channeling.").sendTo(teleportPlayer);
            }
          });
      ChannelStartedResult result = TeleportTools.channelTeleport(searchedPlayer, channel);
      if (result == ChannelStartedResult.ALREADY_CHANNELING) {
        SystemMessage.from("Teleport",
            ChatColor.RESET + player.getName() + ChatColor.GRAY
                + " accepted your teleport request, but you are already channeling somewhere!")
            .sendTo(searchedPlayer);
        return CommandMessage.from("That player is already teleporting somewhere.");
      }
      SystemMessage.from("Teleport",
          ChatColor.RESET + player.getName() + ChatColor.GRAY + " accepted your teleport request.").sendTo(searchedPlayer);
      return CommandMessage
          .from("Accepted " + ChatColor.RESET + searchedPlayer.getName() + ChatColor.GRAY + "'s teleport request."
              + (result == ChannelStartedResult.INSTANT_CHANNEL ? ""
                  : " They will arrive in about " + ChatColor.RESET + StringUtils.getTextTime(channel.getStartingTime())
                      + ChatColor.GRAY + "."));
    }
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent e) {
    teleportRequests.remove(e.getPlayer());
  }

  @Override
  public List<String> getAliases() {
    return Arrays.asList("tp", "tpa", "teleport", "tpaccept", "teleportaccept", "tpaaccept");
  }

  @Override
  public boolean canUse(CommandSender sender) {
    return true;
  }

  @Override
  public String getDescription(CommandSender sender) {
    return "Teleport to a player.";
  }

  @Override
  public String getDisplayName() {
    return "Teleport";
  }
}