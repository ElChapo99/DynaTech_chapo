package me.profelements.dynatech.tasks;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
import me.profelements.dynatech.DynaTech;
import me.profelements.dynatech.items.misc.ItemBand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TareaItemBand implements Runnable {

    // El valor, si no es nulo, será un SlIMEFUN_ID que representa un ítem

    public TareaItemBand() {}

    @Override
    public void run() {
        for (Player jugador : Bukkit.getOnlinePlayers()) {
            if (!jugador.isValid() || jugador.isDead()) {
                continue;
            }
            for (ItemStack item : jugador.getEquipment().getArmorContents()) {
                comprobarItemBand(jugador, item);
            }
            comprobarItemBand(jugador, jugador.getEquipment().getItemInMainHand());
        }
    }

    private static void comprobarItemBand(@Nonnull Player jugador, @Nullable ItemStack item) {
        if (item != null && item.getType() != Material.AIR && item.hasItemMeta()) {
            String id = PersistentDataAPI.getString(item.getItemMeta(), ItemBand.KEY);

            if (id != null) {
                SlimefunItem sfItem = SlimefunItem.getById(id);

                if (sfItem instanceof ItemBand) {
                    ItemBand itemBand = (ItemBand) sfItem;

                    DynaTech.runSync(() -> {
                        for (PotionEffect efecto : itemBand.getPotionEffects()) {
                            if (efecto.getType() == PotionEffectType.HEALTH_BOOST) {
                                double saludActual = jugador.getHealth();
                                jugador.addPotionEffect(efecto);
                                if (saludActual > jugador.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
                                    jugador.setHealth(jugador.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                                } else {
                                    jugador.setHealth(saludActual);
                                }
                            } else {
                                jugador.addPotionEffect(efecto);
                            }
                        }
                    });
                }
            }
        }
    }

}
