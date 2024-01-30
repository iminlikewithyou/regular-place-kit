package games.omg.channeling;

import org.bukkit.Material;

public enum ChannelType {
  TRAVEL_TIME("Travel Time", "The time it would take to travel by foot.", Material.IRON_BOOTS),
  WORLD_WARP("World Warp", "The time it would take to switch between worlds.", Material.ENDER_PEARL),
  EYE_OF_ENDER("Eye of Ender", "This might take a while.", Material.ENDER_EYE),
  IN_COMBAT("In Combat", "An extended time which occurs during combat.", Material.SKELETON_SKULL);

  private final String name;
  private final String description;
  private final Material displayMaterial;

  private ChannelType(String name, String description, Material displayMaterial) {
    this.name = name;
    this.description = description;
    this.displayMaterial = displayMaterial;
  }

  public ChannelTime create(int time) {
    return new ChannelTime(this, time);
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Material getDisplayMaterial() {
    return displayMaterial;
  }
}
