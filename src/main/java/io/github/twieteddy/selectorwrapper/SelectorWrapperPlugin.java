package io.github.twieteddy.selectorwrapper;

import io.github.twieteddy.selectorwrapper.commands.SelectorWrapperCommand;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class SelectorWrapperPlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    PluginCommand selectorWrapper = getCommand("selectorwrapper");
    assert selectorWrapper != null;
    selectorWrapper.setExecutor(new SelectorWrapperCommand());
  }
}
