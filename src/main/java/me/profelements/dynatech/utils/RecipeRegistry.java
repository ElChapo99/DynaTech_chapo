package me.profelements.dynatech.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

public class RecipeRegistry {
    // Lista de todas las recetas registradas
    private static final ArrayList<Recipe> RECIPES = new ArrayList<>();
    
    // Instancia singleton de RecipeRegistry
    private static RecipeRegistry INSTANCE;

    private RecipeRegistry() {
        INSTANCE = this;
    }

    // Obtiene la instancia singleton
    public static RecipeRegistry getInstance() {
        return INSTANCE;
    }

    // Inicializa la instancia singleton
    public static RecipeRegistry init() {
        return new RecipeRegistry();
    }

    // Añade una receta al registro
    public RecipeRegistry addRecipe(Recipe recipe) {
        RECIPES.add(recipe);
        return this;
    }

    // Devuelve todas las recetas
    public final ArrayList<Recipe> getRecipes() {
        return RECIPES;
    }

    // Obtiene una receta por su clave
    public final Recipe getRecipeByKey(NamespacedKey key) {
        return getRecipes().stream()
                .filter(r -> r.getKey().equals(key))
                .toList()
                .get(0);
    }

    // Devuelve un stream de recetas según el tipo de receta
    public final Stream<Recipe> getRecipesByRecipeType(RecipeType type) {
        return getRecipes().stream()
                .filter(r -> r.getRecipeType().equals(type));
    }

    // Devuelve un stream de recetas que coinciden con los ítems de entrada
    public final Stream<Recipe> getRecipesByInput(ItemStack[] input) {
        return getRecipes().stream()
                .filter(r -> r.getInput().equals(input));
    }

    // Devuelve un stream de recetas que coinciden con un ítem de salida
    public final Stream<Recipe> getRecipesByOutput(ItemStack output) {
        return getRecipes().stream()
                .filter(r -> r.getOutput()[0].equals(output));
    }

    // Devuelve un stream de recetas que coinciden con múltiples ítems de salida
    public final Stream<Recipe> getRecipeByOutputs(ItemStack[] outputs) {
        return getRecipes().stream()
                .filter(r -> r.getOutput().equals(outputs));
    }

    // Comprueba si existe una receta que coincida con tipo, entrada y salida
    public final boolean isMatching(RecipeType type, ItemStack[] input, ItemStack output) {
        List<Recipe> recipes = getRecipesByRecipeType(type)
                .filter(r -> r.getOutput().equals(output))
                .filter(r -> r.getInput().equals(input))
                .toList();
        return !recipes.isEmpty();
    }
}
