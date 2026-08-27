package com.izzet.gorevler;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Set;

public class GorevListener implements Listener {

    private final GorevlerPlugin plugin;
    private final NamespacedKey yerlesimAnahtari;

    // "Tas kir" gorevi icin sayilan bloklar
    private static final Set<Material> TAS_BLOKLARI = Set.of(
            Material.STONE, Material.COBBLESTONE, Material.DEEPSLATE,
            Material.COBBLED_DEEPSLATE, Material.ANDESITE, Material.DIORITE,
            Material.GRANITE, Material.TUFF, Material.CALCITE
    );

    public GorevListener(GorevlerPlugin plugin) {
        this.plugin = plugin;
        this.yerlesimAnahtari = new NamespacedKey(plugin, "oyuncu-yerlestirdi");
    }

    // Oyuncunun kendi yerlestirdigi odun/tas bloklarini isaretle,
    // boylece koyup kirarak gorev sayimi sacmalatilamiyor.
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Material tur = event.getBlockPlaced().getType();
        if (Tag.LOGS.isTagged(tur) || TAS_BLOKLARI.contains(tur)) {
            event.getBlockPlaced().getPersistentDataContainer()
                    .set(yerlesimAnahtari, PersistentDataType.BOOLEAN, true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block blok = event.getBlock();
        Player oyuncu = event.getPlayer();

        if (blok.getPersistentDataContainer().has(yerlesimAnahtari, PersistentDataType.BOOLEAN)) {
            // Oyuncu tarafindan yerlestirilmis blok - gorev sayilmaz
            return;
        }

        Material tur = blok.getType();
        if (Tag.LOGS.isTagged(tur)) {
            plugin.getGorevYoneticisi().ilerlemeEkle(oyuncu, GorevTuru.ODUNCU, 1);
        } else if (TAS_BLOKLARI.contains(tur)) {
            plugin.getGorevYoneticisi().ilerlemeEkle(oyuncu, GorevTuru.MADENCI, 1);
        }
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            plugin.getGorevYoneticisi().ilerlemeEkle(event.getPlayer(), GorevTuru.BALIKCI, 1);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() == EntityType.ZOMBIE && event.getEntity().getKiller() != null) {
            plugin.getGorevYoneticisi().ilerlemeEkle(event.getEntity().getKiller(), GorevTuru.AVCI, 1);
        }
    }
}
