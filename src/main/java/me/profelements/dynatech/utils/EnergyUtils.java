package me.profelements.dynatech.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.bakedlibs.dough.blocks.BlockPosition;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

public class UtilidadesEnergia {

    private UtilidadesEnergia() {
    }

    public static final int moverEnergiaDeAHacia(BlockPosition origen, BlockPosition destino, int tasaEnergiaOrigen,
            int energiaMaxDestino) {
        Location locOrigen = origen.toLocation();
        Location locDestino = destino.toLocation();
        String claveEnergia = "energy-charge";

        String energiaOrigenStr = BlockStorage.getLocationInfo(locOrigen, claveEnergia);
        String energiaDestinoStr = BlockStorage.getLocationInfo(locDestino, claveEnergia);
        if (energiaOrigenStr == null || energiaDestinoStr == null) {
            return 0;
        }

        int energiaOrigen = Integer.parseInt(energiaOrigenStr);
        int energiaDestino = Integer.parseInt(energiaDestinoStr);

        int energiaAtrasladar = Math.min(tasaEnergiaOrigen, Math.min(energiaMaxDestino - energiaDestino, energiaOrigen));

        int nuevaEnergiaOrigen = energiaOrigen - energiaAtrasladar;
        int nuevaEnergiaDestino = energiaDestino + energiaAtrasladar;

        BlockStorage.addBlockInfo(locOrigen, claveEnergia, String.valueOf(nuevaEnergiaOrigen));
        BlockStorage.addBlockInfo(locDestino, claveEnergia, String.valueOf(nuevaEnergiaDestino));

        return energiaAtrasladar;
    }

    public static final void moverInventarioDeAHacia(BlockPosition origen, BlockPosition destino, int[] slotsOrigen, int[] slotsDestino) {
        BlockMenu menuOrigen = BlockStorage.getInventory(origen.toLocation());
        BlockMenu menuDestino = BlockStorage.getInventory(destino.toLocation());
        if (menuOrigen == null || menuDestino == null) {
            return;
        }

        Map<Integer, ItemStack> itemsAtrasladar = new HashMap<>(slotsOrigen.length);
        for (int slot : slotsOrigen) {
            ItemStack item = menuOrigen.getItemInSlot(slot);

            if (item == null) {
                continue;
            }

            itemsAtrasladar.put(slot, item);
        }

        if (itemsAtrasladar.isEmpty()) {
            return;
        }

        List<Integer> slotsVacios = new ArrayList<>(slotsDestino.length);
        for (int slot : slotsDestino) {
            ItemStack item = menuDestino.getItemInSlot(slot);
            // Ignora slots ocupados
            if (item != null) {
                continue;
            }

            slotsVacios.add(slot);
        }

        Iterator<Map.Entry<Integer, ItemStack>> iter = itemsAtrasladar.entrySet().iterator();
        for (int slot : slotsVacios) {
            if (!iter.hasNext()) {
                return;
            }

            Map.Entry<Integer, ItemStack> entry = iter.next();
            int slotOrigen = entry.getKey();
            ItemStack item = entry.getValue();

            menuDestino.toInventory().setItem(slot, item);
            menuOrigen.replaceExistingItem(slotOrigen, new ItemStack(Material.AIR, 0));
        }

        menuOrigen.markDirty();
        menuDestino.markDirty();
    }
}
