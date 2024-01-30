package games.omg.channeling;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import games.omg.menus.InventoryMenu;
import games.omg.resources.Decorations;
import games.omg.resources.ServerColors;
import games.omg.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class ChannelingInventoryTest implements Listener {
  
  @EventHandler
  public void onClick(PlayerDropItemEvent event) {
    new InventoryMenu(event.getPlayer(), "Channeling..", 9 * 6, "Channeling..", new InventoryMenu.InventoryClickHandler() {
      @Override
      public void create(Inventory inventory) {
        // Party sign
        ItemStack partySign = new ItemStack(Material.OAK_HANGING_SIGN);
        ItemMeta partySignMeta = partySign.getItemMeta();
        partySignMeta.displayName(
          Component.text("Party")
            .decoration(TextDecoration.ITALIC, false)
            .color(ServerColors.PRIMARY_COLOR)
        );
        partySignMeta.lore(Arrays.asList(
          Component.text("The players who are")
            .decoration(TextDecoration.ITALIC, false)
            .color(ServerColors.SECONDARY_COLOR),
          Component.text("warping.")
            .decoration(TextDecoration.ITALIC, false)
            .color(ServerColors.SECONDARY_COLOR)
        ));
        partySign.setItemMeta(partySignMeta);
        inventory.setItem(1, partySign);

        // Warping ender pearl
        ItemStack enderPearl = new ItemStack(Material.ENDER_PEARL);
        ItemMeta enderPearlMeta = enderPearl.getItemMeta();
        enderPearlMeta.displayName(
          Component.text("Warping")
            .decoration(TextDecoration.ITALIC, false)
            .color(ServerColors.PRIMARY_COLOR)
        );
        enderPearlMeta.lore(Arrays.asList(
          Component.text("The party is warping.")
            .decoration(TextDecoration.ITALIC, false)
            .color(ServerColors.SECONDARY_COLOR)
        ));
        enderPearl.setItemMeta(enderPearlMeta);
        inventory.setItem(10, enderPearl);

        // Headed to sign
        ItemStack toSign = partySign.clone();
        ItemMeta toSignMeta = toSign.getItemMeta();
        toSignMeta.displayName(
          Component.text("Headed To")
            .decoration(TextDecoration.ITALIC, false)
            .color(ServerColors.PRIMARY_COLOR)
        );
        toSignMeta.lore(Arrays.asList(
          Component.text("The location the party")
            .decoration(TextDecoration.ITALIC, false)
            .color(ServerColors.SECONDARY_COLOR),
          Component.text("is headed to.")
            .decoration(TextDecoration.ITALIC, false)
            .color(ServerColors.SECONDARY_COLOR)
        ));
        toSign.setItemMeta(toSignMeta);
        inventory.setItem(7, toSign);

        // Location compass
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compassMeta = compass.getItemMeta();
        compassMeta.displayName(
          Component.text("Location")
            .decoration(TextDecoration.ITALIC, false)
            .color(ServerColors.PRIMARY_COLOR)
        );
        compassMeta.lore(Arrays.asList(
          Component.text("The party is warping to")
            .decoration(TextDecoration.ITALIC, false)
            .color(ServerColors.SECONDARY_COLOR),
          Component.text("this location.")
            .decoration(TextDecoration.ITALIC, false)
            .color(ServerColors.SECONDARY_COLOR)
        ));
        compass.setItemMeta(compassMeta);
        inventory.setItem(16, compass);

        int maxPartyShown = 3;
        int partyListStartIndex = 11;

        ItemStack location = new ItemStack(Material.RED_BED);
        ItemMeta locationMeta = location.getItemMeta();
        locationMeta.displayName(
          Component.text("Home")
            .decoration(TextDecoration.ITALIC, false)
            .color(ServerColors.PRIMARY_COLOR)
        );
        locationMeta.lore(Arrays.asList(
          Component.text("Home sweet home.")
            .decoration(TextDecoration.ITALIC, false)
            .color(ServerColors.SECONDARY_COLOR)
        ));
        location.setItemMeta(locationMeta);
        inventory.setItem(15, location);

        // create a player head of the user
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta playerHeadMeta = (SkullMeta) playerHead.getItemMeta();
        playerHeadMeta.displayName(
          PlayerUtils.getNametag(event.getPlayer(), true, false)
            .decoration(TextDecoration.ITALIC, false)
            .applyFallbackStyle(ServerColors.SECONDARY_COLOR)
        );
        playerHeadMeta.lore(Arrays.asList(
          Component.text(Decorations.BALLOT_BOX_WITH_CHECK + " Present")
            .decoration(TextDecoration.ITALIC, false)
            .color(ServerColors.PRIMARY_COLOR)
        ));
        playerHeadMeta.setOwningPlayer(event.getPlayer());
        playerHead.setItemMeta(playerHeadMeta);
        inventory.setItem(11, playerHead);

        // Time remaining clock
        ItemStack clock = new ItemStack(Material.CLOCK);
        ItemMeta clockMeta = clock.getItemMeta();
        clockMeta.displayName(
          Component.text("Channeling..")
            .decoration(TextDecoration.ITALIC, false)
            .color(ServerColors.PRIMARY_COLOR)
        );
        clockMeta.lore(Arrays.asList(
          Component.text("Warping in 1:34..")
            .decoration(TextDecoration.ITALIC, false)
            .color(ServerColors.SECONDARY_COLOR)
        ));
        clock.setAmount(64);
        clock.setItemMeta(clockMeta);
        inventory.setItem(49, clock);
      }

      @Override
      public void inventoryInteractEvent(InventoryInteractEvent e) {
        e.setCancelled(true);
      }

      @Override
      public void close(Inventory i) {

      }
    });
  }
}
