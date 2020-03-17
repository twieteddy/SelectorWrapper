package io.github.twieteddy.selectorwrapper.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SelectorWrapperCommand implements CommandExecutor {

  public static final int CHUNK_WIDTH = 16;

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (!(sender instanceof BlockCommandSender)) {
      return true;
    }

    BlockCommandSender commandBlock = (BlockCommandSender) sender;
    Player closestPlayer = getClosestPlayer(commandBlock.getBlock().getLocation());

    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("@p")) {
        if (closestPlayer == null) {
          commandBlock.sendMessage("Close player not found");
          return true;
        }
        args[i] = closestPlayer.getName();
      }
    }

    String passedCommand = String.join(" ", args);
    commandBlock.sendMessage(passedCommand);
    Bukkit.dispatchCommand(sender, passedCommand);
    return true;
  }

  private Player getClosestPlayer(Location location) {
    World world = location.getWorld();
    if (world == null) {
      return null;
    }

    int viewDistance = Bukkit.getViewDistance() * CHUNK_WIDTH;
    Collection<Entity> playerEntities =
        world.getNearbyEntities(
            location, viewDistance, viewDistance, viewDistance, e -> e instanceof Player);

    Entity closest =
        Collections.min(
            playerEntities, Comparator.comparing(e -> e.getLocation().distanceSquared(location)));

    return (Player) closest;
  }
}
