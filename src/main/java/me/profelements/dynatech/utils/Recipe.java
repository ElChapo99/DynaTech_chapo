package me.profelements.dynatech.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import com.google.common.base.Preconditions;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.profelements.dynatech.registries.Registries;
import me.profelements.dynatech.registries.TypedKey;

public class Recipe {
    // Clave única de la receta
    private NamespacedKey CLAVE;
    
    // Tipo de receta (Smeltery, Crafting, etc)
    private RecipeType TIPO;
    
    // Ítems de entrada
    private ItemStack[] ENTRADA;
    
    // Ítems de salida
    private ItemStack[] SALIDA;

    protected Recipe() {
    }

    // Inicializa la receta
    public static Recipe init() {
        return new Recipe();
    }

    public Recipe setKey(NamespacedKey key) {
        Preconditions.checkNotNull(key, "La clave namespaced de la receta no puede ser nula");
        this.CLAVE = key;
        return this;
    }

    public final NamespacedKey getKey() {
        return this.CLAVE;
    }

    public Recipe setRecipeType(RecipeType type) {
        Preconditions.checkNotNull(type, "El tipo de receta no puede ser nulo");
        this.TIPO = type;
        return this;
    }

    public final RecipeType getRecipeType() {
        return this.TIPO;
    }

    public Recipe setInput(ItemStack input) {
        Preconditions.checkNotNull(input, "Los ítems de entrada de la receta no pueden ser nulos");
        this.ENTRADA = new ItemStack[] { input };
        return this;
    }

    public Recipe setInput(ItemStack[] input) {
        Preconditions.checkNotNull(input, "Los ítems de entrada de la receta no pueden ser nulos");
        this.ENTRADA = input;
        return this;
    }

    public final ItemStack[] getInput() {
        return this.ENTRADA;
    }

    public Recipe setOutput(ItemStack output) {
        Preconditions.checkNotNull(output, "El ítem de salida de la receta no puede ser nulo");
        this.SALIDA = new ItemStack[] { output };
        return this;
    }

    public Recipe setOutput(ItemStack output, int amount) {
        Preconditions.checkNotNull(output, "El ítem de salida de la receta no puede ser nulo");
        var actualOutput = output.clone();
        actualOutput.setAmount(amount);
        this.SALIDA = new ItemStack[] { actualOutput };
        return this;
    }

    public Recipe setOutput(ItemStack[] output) {
        Preconditions.checkNotNull(output, "Los ítems de salida de la receta no pueden ser nulos");
        this.SALIDA = output;
        return this;
    }

    public final ItemStack[] getOutput() {
        return this.SALIDA;
    }

    // Valida que todos los campos estén correctamente inicializados
    public void validate() {
        Preconditions.checkNotNull(this.CLAVE, "La clave namespaced de la receta no puede ser nula");
        Preconditions.checkNotNull(this.TIPO, "El tipo de receta no puede ser nulo");
        Preconditions.checkNotNull(this.ENTRADA, "Los ítems de entrada de la receta no pueden ser nulos");
        Preconditions.checkNotNull(this.SALIDA, "Los ítems de salida de la receta no pueden ser nulos");
    }

    // Construye la receta y valida sus campos
    public Recipe build() {
        this.validate();
        return this;
    }

    // Registra la receta en el sistema
    public Recipe register() {
        Recipe res = this.build();
        res.getRecipeType().register(res.getInput(), res.getOutput()[0]);
        Registries.RECIPES.register(TypedKey.create(res.getKey()), res);
        return res;
    }
}
