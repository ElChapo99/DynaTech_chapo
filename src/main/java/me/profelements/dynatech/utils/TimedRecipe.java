package me.profelements.dynatech.utils;

import com.google.common.base.Preconditions;

import me.profelements.dynatech.registries.Registries;
import me.profelements.dynatech.registries.TypedKey;

public class TimedRecipe extends Recipe {
    private int TIME_IN_TICKS;

    protected TimedRecipe() {
        super();
    }

    public static TimedRecipe init() {
        return new TimedRecipe();
    }

    public final int getTimeInTicks() {
        return this.TIME_IN_TICKS;
    }

    public TimedRecipe setTimeInTicks(int ticks) {
        Preconditions.checkNotNull(ticks, "El tiempo en ticks de la receta no debe ser nulo");
        this.TIME_IN_TICKS = ticks;
        return this;
    }

    @Override
    public TimedRecipe build() {
        Preconditions.checkNotNull(this.TIME_IN_TICKS, "El tiempo en ticks de la receta no debe ser nulo");
        super.validate();
        return this;
    }

    @Override
    public Recipe register() {
        TimedRecipe res = this.build();
        res.getRecipeType().register(res.getInput(), res.getOutput()[0]);
        Registries.RECIPES.register(TypedKey.create(this.getKey()), res);
        return res;
    }
}

