package com.izzet.gorevler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GorevlerKomutu implements CommandExecutor {

    private final GorevGUI gui;

    public GorevlerKomutu(GorevGUI gui) {
        this.gui = gui;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player oyuncu)) {
            sender.sendMessage("Bu komut sadece oyun icinde kullanilabilir.");
            return true;
        }
        gui.ac(oyuncu);
        return true;
    }
}
