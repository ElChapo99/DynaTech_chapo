package me.profelements.dynatech.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.NamespacedKey;

public class LiquidRegistry {

    // Lista de todos los líquidos registrados
    private static final ArrayList<Liquid> LIQUIDOS = new ArrayList<>();

    // Instancia singleton del registro
    private static LiquidRegistry INSTANCIA;

    private LiquidRegistry() {
        INSTANCIA = this;
    }

    // Devuelve la instancia actual del registro
    public static LiquidRegistry getInstance() {
        return INSTANCIA;
    }

    // Inicializa el registro (crea la instancia singleton)
    public static LiquidRegistry init() {
        return new LiquidRegistry();
    }

    // Agrega un líquido al registro
    public LiquidRegistry addLiquid(Liquid liquid) {
        LIQUIDOS.add(liquid);
        return this;
    }

    // Obtiene la lista de todos los líquidos registrados
    public List<Liquid> getLiquids() {
        return LIQUIDOS;
    }

    // Obtiene un líquido a partir de su clave namespaced
    public final Liquid getByKey(NamespacedKey key) {
        return getLiquids().stream()
                .filter(r -> r.getKey().equals(key))
                .toList()
                .get(0);
    }
}
