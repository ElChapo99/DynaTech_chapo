package me.profelements.dynatech.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu.MenuClickHandler;
import me.profelements.dynatech.registries.Registries;
import me.profelements.dynatech.utils.Recipe;
import me.profelements.dynatech.utils.RecipeRegistry;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;

public class UtilidadesMenuCofre {

    private static final ItemStack ITEM_FONDO = io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils.getBackground();
    private static final MenuClickHandler SIN_CLICK = io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils.getEmptyClickHandler();

    public static void abrirLibroRecetas(Player jugador) {
        ChestMenu menu = new ChestMenu("Libro de Recetas");
        menu.setEmptySlotsClickable(false);

        Set<Recipe> recetas = Registries.RECIPES.getEntries();

        Set<ItemStack> salidas = new HashSet<>();

        for (Recipe receta : recetas) {
            salidas.add(receta.getOutput()[0]);
        }

        int iter = 0;
        for (ItemStack salidaReceta : salidas) {
            if (iter == 54) break;

            menu.addItem(iter, salidaReceta, new MenuClickHandler() {
                @Override
                public boolean onClick(Player jugador, int slot, ItemStack item, ClickAction action) {
                    abrirRecetaConItem(jugador, menu.getItemInSlot(slot), 0);
                    return false;
                }
            });
            iter++;
        }

        menu.open(jugador);
    }

    private static void abrirRecetaConItem(Player jugador, ItemStack item, int indice) {
        ChestMenu menu = new ChestMenu("Receta");
        menu.setEmptySlotsClickable(false);

        menu.addItem(0, io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils.getBackButton(jugador, ""),
                new MenuClickHandler() {
                    @Override
                    public boolean onClick(Player jugador, int slot, ItemStack item, ClickAction action) {
                        abrirLibroRecetas(jugador);
                        return false;
                    }
                });

        for (int i = 1; i < 9; i++) {
            menu.addItem(i, ITEM_FONDO, SIN_CLICK);
        }

        List<Recipe> recetas = Registries.RECIPES.getEntries().stream()
                .filter((receta) -> receta.getOutput()[0].equals(item)).toList();

        Recipe receta = recetas.get(indice);

        int[] slotsReceta = new int[] {12, 13, 14, 21, 22, 23, 30, 31, 32};
        int iter = 0;

        for (ItemStack itemReceta : receta.getInput()) {
            if (RecipeRegistry.getInstance().getRecipesByOutput(itemReceta).toList().size() > 0) {
                menu.addItem(slotsReceta[iter], itemReceta, new MenuClickHandler() {
                    @Override
                    public boolean onClick(Player jugador, int slot, ItemStack itemS, ClickAction action) {
                        abrirRecetaConItem(jugador, itemReceta, 0);
                        return false;
                    }
                });
            } else {
                menu.addItem(slotsReceta[iter], itemReceta, SIN_CLICK);
            }
            iter++;
        }

        menu.addItem(19, receta.getRecipeType().toItem(), SIN_CLICK);
        menu.addItem(25, receta.getOutput()[0], SIN_CLICK);

        // Botones de navegación
        menu.addItem(36, io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils.getPreviousButton(jugador, indice + 1, recetas.size()),
                new MenuClickHandler() {
                    @Override
                    public boolean onClick(Player jugador, int slot, ItemStack itemS, ClickAction action) {
                        abrirRecetaConItem(jugador, item, Math.max(0, indice - 1));
                        return false;
                    }
                });

        menu.addItem(44, io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils.getNextButton(jugador, indice + 1, recetas.size()),
                new MenuClickHandler() {
                    @Override
                    public boolean onClick(Player jugador, int slot, ItemStack itemS, ClickAction action) {
                        abrirRecetaConItem(jugador, item, Math.min(recetas.size() - 1, indice + 1));
                        return false;
                    }
                });

        for (int i = 37; i < 44; i++) {
            menu.addItem(i, ITEM_FONDO, SIN_CLICK);
        }

        menu.open(jugador);
    }
}
