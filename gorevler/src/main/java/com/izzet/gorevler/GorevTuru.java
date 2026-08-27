package com.izzet.gorevler;

import org.bukkit.Material;

public enum GorevTuru {

    ODUNCU("Oduncu", "§6", Material.OAK_LOG, 64, 100.0, "Odun kir (tum agac turleri)"),
    BALIKCI("Balikci", "§b", Material.FISHING_ROD, 10, 100.0, "Balik tut"),
    MADENCI("Madenci", "§7", Material.STONE, 100, 10.0, "Tas kir"),
    AVCI("Avci", "§2", Material.ROTTEN_FLESH, 10, 10.0, "Zombi oldur");

    private final String isim;
    private final String renk;
    private final Material ikon;
    private final int hedef;
    private final double odul;
    private final String aciklama;

    GorevTuru(String isim, String renk, Material ikon, int hedef, double odul, String aciklama) {
        this.isim = isim;
        this.renk = renk;
        this.ikon = ikon;
        this.hedef = hedef;
        this.odul = odul;
        this.aciklama = aciklama;
    }

    public String getIsim() {
        return isim;
    }

    public String getRenk() {
        return renk;
    }

    public Material getIkon() {
        return ikon;
    }

    public int getHedef() {
        return hedef;
    }

    public double getOdul() {
        return odul;
    }

    public String getAciklama() {
        return aciklama;
    }
}
