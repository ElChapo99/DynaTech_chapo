package me.profelements.dynatech.attributes;

import java.util.logging.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Preconditions;

import io.github.bakedlibs.dough.blocks.BlockPosition;
import io.github.thebusybiscuit.slimefun4.core.attributes.ItemAttribute;
import io.github.thebusybiscuit.slimefun4.utils.NumberUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.profelements.dynatech.DynaTech;

/**
 * Esta interfaz, cuando se adjunta a una clase que hereda de {@link SlimefunItem},
 * marca el objeto como un contenedor de líquidos.
 * Esto permite que el objeto interactúe con líquidos registrados.
 *
 */
public interface LiquidStorage extends ItemAttribute {
    
    int getLiquidCapacity();

    default boolean isFillable() {
        return getLiquidCapacity() > 0;
    }

    default int getLiquidAmount(@Nonnull BlockPosition l) {
        if (!isFillable()) {
            return 0;
        }

        return getLiquidAmount(l, BlockStorage.getLocationInfo(l.toLocation()));
    }

    default int getLiquidAmount(@Nonnull BlockPosition l, Config config) {
        Preconditions.checkNotNull(l, "La ubicación era nula");
        Preconditions.checkNotNull(config, "La configuración era nula");

        if (!isFillable()) {
            return 0;
        }

        String fluidAmount = config.getString("fluid-amount");

        if (fluidAmount != null) {
            return Integer.parseInt(fluidAmount);
        } else {
            return 0;
        }
    }

    default String getLiquid(@Nonnull BlockPosition l) {
        return getLiquid(l, BlockStorage.getLocationInfo(l.toLocation()));
    }

    default String getLiquid(@Nonnull BlockPosition l, @Nonnull Config config) {
        Preconditions.checkNotNull(l, "La ubicación era nula");
        Preconditions.checkNotNull(config, "La configuración era nula");

        String fluidName = config.getString("fluid-name");

        if (fluidName != null) {
            return fluidName;
        } else {
            return "NO_LIQUID";
        }
    }

    default void setLiquidAmount(@Nonnull BlockPosition l, int liquidAmount) {
        Preconditions.checkNotNull("La ubicación era nula");
        Preconditions.checkArgument(liquidAmount >= 0, "La cantidad de fluido debe ser 0 o mayor");

        try {
            int liquidCapacity = getLiquidCapacity();

            if (liquidCapacity > 0) {
                liquidAmount = NumberUtils.clamp(0, liquidAmount, liquidCapacity);

                if (liquidAmount != getLiquidAmount(l)) {
                    BlockStorage.addBlockInfo(l.toLocation(), "fluid-amount", String.valueOf(liquidAmount), false);
                }
            }
        } catch (Exception | LinkageError x) {
            DynaTech.getInstance().getLogger().log(
                Level.SEVERE,
                x,
                () -> "Excepción al intentar establecer la cantidad de fluido para \"" + getId() + "\" en " + l
            );
        }
    }
    
    default void addLiquidAmount(@Nonnull BlockPosition l, int liquidAmount) {
        Preconditions.checkNotNull("La ubicación era nula");
        Preconditions.checkArgument(liquidAmount > 0, "La cantidad de fluido debe ser mayor que 0");

        try {
            int liquidCapacity = getLiquidCapacity();

            if (liquidCapacity > 0) {
                int currentLiquidAmount = getLiquidAmount(l);

                if (currentLiquidAmount < liquidCapacity) {
                    int newLiquidAmount = Math.min(liquidCapacity, currentLiquidAmount + liquidAmount);
                    BlockStorage.addBlockInfo(l.toLocation(), "fluid-amount", String.valueOf(newLiquidAmount), false);
                }
            }
        } catch (Exception | LinkageError x) {
            DynaTech.getInstance().getLogger().log(
                Level.SEVERE,
                x,
                () -> "Excepción al intentar añadir una cantidad de fluido para \"" + getId() + "\" en " + l
            );
        }
    }

    default void removeLiquidAmount(@Nonnull BlockPosition l, int liquidAmount) {
        Preconditions.checkNotNull("La ubicación era nula");
        Preconditions.checkArgument(liquidAmount > 0, "La cantidad de fluido debe ser mayor que 0");
        
        try {
            int liquidCapacity = getLiquidCapacity();
            if (liquidCapacity > 0) {
                int currentLiquidAmount = getLiquidAmount(l);

                if (currentLiquidAmount > 0) {
                    int newLiquidAmount = Math.max(0, currentLiquidAmount - liquidAmount);
                    BlockStorage.addBlockInfo(l.toLocation(), "fluid-amount", String.valueOf(newLiquidAmount), false);
                }
            }

        } catch (Exception | LinkageError x) {
            DynaTech.getInstance().getLogger().log(
                Level.SEVERE,
                x,
                () -> "Excepción al intentar quitar una cantidad de fluido para \"" + getId() + "\" en " + l
            );
        }

    }

    default void setLiquid(@Nonnull BlockPosition l, @Nullable String fluidName) {
        Preconditions.checkNotNull(l, "La ubicación era nula");

        try {
            int liquidCapacity = getLiquidCapacity();
            
            // Cambiar fluidos solo debe ocurrir cuando no hay otro fluido en el bloque
            if (liquidCapacity == 0) {
                if (fluidName == null) {
                    BlockStorage.addBlockInfo(l.toLocation(), "fluid-name", "NO_LIQUID", false);
                } else {
                    BlockStorage.addBlockInfo(l.toLocation(), "fluid-name", fluidName, false);
                }
            }
        } catch (Exception | LinkageError x) {
            DynaTech.getInstance().getLogger().log(
                Level.SEVERE,
                x,
                () -> "Excepción al intentar establecer el nombre del fluido para \"" + getId() + "\" en " + l
            );
        }
    }
}
