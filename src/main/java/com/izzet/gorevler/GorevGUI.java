package com.izzet.gorevler;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GorevGUI implements Listener {

    public static final String BASLIK = "§8§lGorevler Menusu";
    private static final int[] KONUMLAR = {10, 12, 14, 16};

    private final GorevlerPlugin plugin;

    public GorevGUI(GorevlerPlugin plugin) {
        this.plugin = plugin;
    }

    public void ac(Player oyuncu) {
        Inventory envanter = Bukkit.createInventory(null, 27, BASLIK);

        ItemStack cam = camDoldur();
        for (int i = 0; i < 27; i++) {
            envanter.setItem(i, cam);
        }

        GorevTuru[] turler = GorevTuru.values();
        for (int i = 0; i < turler.length && i < KONUMLAR.length; i++) {
            envanter.setItem(KONUMLAR[i], gorevEsyasi(oyuncu, turler[i]));
        }

        oyuncu.openInventory(envanter);
    }

    private ItemStack camDoldur() {
        ItemStack cam = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = cam.getItemMeta();
        meta.setDisplayName(" ");
        cam.setItemMeta(meta);
        return cam;
    }

    private ItemStack gorevEsyasi(Player oyuncu, GorevTuru tur) {
        OyuncuGorevVerisi veri = plugin.getGorevYoneticisi().getVeri(oyuncu);
        boolean aktif = veri.aktifMi(tur);
        int ilerleme = veri.getIlerleme(tur);

        ItemStack esya = new ItemStack(tur.getIkon());
        ItemMeta meta = esya.getItemMeta();
        meta.setDisplayName(tur.getRenk() + "§l" + tur.getIsim());

        List<String> lore = new ArrayList<>();
        lore.add("§7Gorev: §f" + tur.getAciklama());
        lore.add("§7Hedef: §f" + tur.getHedef());
        lore.add("§7Odul: §e" + (int) tur.getOdul() + " para");
        lore.add("");
        if (aktif) {
            lore.add("§aAktif §7- Ilerleme: §f" + ilerleme + "/" + tur.getHedef());
            lore.add("§7Bu gorev su an calisiyor.");
        } else {
            lore.add("§cAktif degil");
            lore.add("§eAlmak icin tikla!");
        }
        meta.setLore(lore);
        esya.setItemMeta(meta);
        return esya;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(BASLIK)) return;
        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player oyuncu)) return;
        ItemStack tiklanan = event.getCurrentItem();
        if (tiklanan == null || !tiklanan.hasItemMeta()) return;

        for (GorevTuru tur : GorevTuru.values()) {
            if (tiklanan.getType() == tur.getIkon()
                    && tiklanan.getItemMeta().hasDisplayName()
                    && tiklanan.getItemMeta().getDisplayName().equals(tur.getRenk() + "§l" + tur.getIsim())) {
                plugin.getGorevYoneticisi().gorevAl(oyuncu, tur);
                break;
            }
        }
    }
}
