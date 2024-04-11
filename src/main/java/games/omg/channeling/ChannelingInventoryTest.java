package games.omg.channeling;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import games.omg.menus.InventoryMenu;
import games.omg.resources.DecoratedItem;
import games.omg.resources.Decorations;
import games.omg.resources.ServerColors;
import games.omg.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

public class ChannelingInventoryTest implements Listener {
  
  @EventHandler
  public void onClick(PlayerDropItemEvent event) {
    new InventoryMenu(event.getPlayer(), "Channeling..", 9 * 6, "Channeling..", new InventoryMenu.InventoryClickHandler() {
      @Override
      public void create(Inventory inventory) {
        // Party sign
        ItemStack partySign = DecoratedItem.create(Material.OAK_HANGING_SIGN, "Party", Arrays.asList(
          "The players who are",
          "warping."
        ));
        inventory.setItem(1, partySign);

        // Warping ender pearl
        ItemStack enderPearl = DecoratedItem.create(Material.ENDER_PEARL, "Warping", "The party is warping.");
        inventory.setItem(10, enderPearl);

        // Headed to sign
        ItemStack toSign = DecoratedItem.create(Material.OAK_HANGING_SIGN, "Headed To", Arrays.asList(
          "The location the party",
          "is headed to."
        ));
        inventory.setItem(7, toSign);

        // Location compass
        ItemStack compass = DecoratedItem.create(Material.COMPASS, "Location", Arrays.asList(
          "The party is warping to",
          "this location."
        ));
        inventory.setItem(16, compass);

        int maxPartyShown = 3;
        int partyListStartIndex = 11;

        ItemStack location = DecoratedItem.create(Material.RED_BED, "Home", "Home sweet home.");
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
        ItemStack clock = DecoratedItem.create(Material.CLOCK, "Channeling..", "Warping in 1:34..");
        clock.setAmount(64);
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
