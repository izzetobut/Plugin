package com.izzet.gorevler;

import java.util.EnumMap;
import java.util.Map;

public class OyuncuGorevVerisi {

    private final Map<GorevTuru, Boolean> aktifGorevler = new EnumMap<>(GorevTuru.class);
    private final Map<GorevTuru, Integer> ilerleme = new EnumMap<>(GorevTuru.class);

    public boolean aktifMi(GorevTuru tur) {
        return aktifGorevler.getOrDefault(tur, false);
    }

    public void gorevAl(GorevTuru tur) {
        aktifGorevler.put(tur, true);
        ilerleme.put(tur, 0);
    }

    public void goreviBitir(GorevTuru tur) {
        aktifGorevler.put(tur, false);
        ilerleme.put(tur, 0);
    }

    public int getIlerleme(GorevTuru tur) {
        return ilerleme.getOrDefault(tur, 0);
    }

    public void ilerlemeArtir(GorevTuru tur, int miktar) {
        ilerleme.put(tur, getIlerleme(tur) + miktar);
    }
}
