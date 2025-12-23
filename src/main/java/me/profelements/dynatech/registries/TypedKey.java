package me.profelements.dynatech.registries;

import java.util.Locale;

import javax.annotation.Nonnull;

import org.bukkit.NamespacedKey;

public record TypedKey<T>(@Nonnull NamespacedKey clave) {
    public static <T> TypedKey<T> crear(NamespacedKey clave) {
        return new TypedKey<>(clave);
    }

    public static <T> TypedKey<T> crear(String espacioDeNombres, String clave) {
        return new TypedKey<>(new NamespacedKey(espacioDeNombres, clave));
    }

    // ESTO ES TEMPORAL HASTA QUE SLIMEFUN USE NamespacedKey
    public String comoIdSlimefun() {
        return this.clave().toString().replace(':', '_').toUpperCase(Locale.ROOT);
    }
}
