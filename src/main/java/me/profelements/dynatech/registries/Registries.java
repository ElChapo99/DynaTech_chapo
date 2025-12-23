package me.profelements.dynatech.registries;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.profelements.dynatech.utils.ItemWrapper;
import me.profelements.dynatech.utils.Recipe;

public class Registries {
    // Registros públicos para grupos de ítems, ítems, tipos de recetas y recetas
    public static Registry<ItemGroup> GRUPOS_DE_ITEMS = Registry.create(Keys.GRUPOS_DE_ITEMS);
    public static Registry<ItemWrapper> ITEMS = Registry.create(Keys.ITEMS);
    public static Registry<RecipeType> TIPOS_DE_RECETA = Registry.create(Keys.TIPOS_DE_RECETA);
    public static Registry<Recipe> RECETAS = Registry.create(Keys.RECETAS);

    // PARA QUIENES QUIERAN USAR ESTOS, escuchar el evento `RegistryFreezeEvent`
    //

    // Ejemplo de registros comentados
    // public static final Registry<Block> BLOQUES =
    // Registry<Block>.create(Keys.BLOQUES);
    // public static final Registry<Fluid> FLUIDOS =
    // Registry<Fluid>.create(Keys.FLUIDOS);

    public static final class Keys {
        public static final TypedKey<Registry<RecipeType>> TIPOS_DE_RECETA = TypedKey.create("dynatech", "recipe_types");
        public static final TypedKey<Registry<Recipe>> RECETAS = TypedKey.create("dynatech", "recipes");
        public static final TypedKey<Registry<ItemGroup>> GRUPOS_DE_ITEMS = TypedKey.create("dynatech", "item_groups");
        public static final TypedKey<Registry<ItemWrapper>> ITEMS = TypedKey.create("dynatech", "items");
        // public static final TypedKey<Block> BLOQUES = TypedKey.create("dynatech",
        // "block");
        // public static final TypedKey<Fluid> FLUIDOS = TypedKey.create("dynatech",
        // "fluid");
    }
}
