package me.profelements.dynatech.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import com.google.common.base.Preconditions;

public class Liquido {
    private NamespacedKey CLAVE;
    private String NOMBRE;
    private Color COLOR_LIQUIDO;
    private Material MATERIAL_LIQUIDO;
    private Material MATERIAL_ALMACENAMIENTO;

    private Liquido() {
    }

    public static Liquido init() {
        return new Liquido();
    }

    public Liquido setClave(NamespacedKey clave) {
        Preconditions.checkNotNull(clave, "La clave del líquido no puede ser nula!");
        this.CLAVE = clave;
        return this;
    }

    public final NamespacedKey getClave() {
        return this.CLAVE;
    }

    public Liquido setNombre(String nombre) {
        Preconditions.checkNotNull(nombre, "El nombre del líquido no puede ser nulo!");
        this.NOMBRE = nombre;
        return this;
    }

    public final String getNombre() {
        return this.NOMBRE;
    }

    public Liquido setColor(Color color) {
        Preconditions.checkNotNull(color, "El color del líquido no puede ser nulo!");
        this.COLOR_LIQUIDO = color;
        return this;
    }

    public final Color getColor() {
        return this.COLOR_LIQUIDO;
    }

    public Liquido setMaterialLiquido(Material mat) {
        Preconditions.checkArgument(mat == Material.LAVA || mat == Material.WATER,
                "El material del líquido debe ser un líquido (agua o lava)!");
        this.MATERIAL_LIQUIDO = mat;
        return this;
    }

    public final Material getMaterialLiquido() {
        return this.MATERIAL_LIQUIDO;
    }

    public Liquido setMaterialAlmacenamiento(Material mat) {
        Preconditions.checkNotNull(mat, "El material de almacenamiento del líquido no puede ser nulo!");
        this.MATERIAL_ALMACENAMIENTO = mat;
        return this;
    }

    public final Material getMaterialAlmacenamiento() {
        return this.MATERIAL_ALMACENAMIENTO;
    }

    public final Liquido build() {
        Preconditions.checkNotNull(this.CLAVE, "La clave del líquido no puede ser nula!");
        Preconditions.checkNotNull(this.NOMBRE, "El nombre del líquido no puede ser nulo!");
        Preconditions.checkNotNull(this.COLOR_LIQUIDO, "El color del líquido no puede ser nulo!");
        Preconditions.checkNotNull(this.MATERIAL_LIQUIDO, "El material del líquido no puede ser nulo!");
        Preconditions.checkNotNull(this.MATERIAL_ALMACENAMIENTO, "El material de almacenamiento no puede ser nulo!");
        return this;
    }

    public void registrar(LiquidRegistry registro) {
        Liquido liquido = this.build();
        registro.addLiquid(liquido);
    }
}
