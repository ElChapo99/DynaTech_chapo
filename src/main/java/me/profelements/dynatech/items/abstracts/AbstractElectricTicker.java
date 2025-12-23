package me.profelements.dynatech.items.abstracts;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import com.google.common.base.Preconditions;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;

public abstract class AbstractElectricTicker extends AbstractTicker implements EnergyNetComponent {
    
    private int energyConsumedPerTick = -1;
    private int energyCapacity = -1;
    private int processingSpeed = -1; 
    
    protected AbstractElectricTicker(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe); 
    }

    @Override
    protected boolean checkTickPreconditions(Block b) {
        return takeCharge(b.getLocation()); 
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    public int getCapacity() {
        return energyCapacity; 
    }

    public int getEnergyConsumption() {
        return energyConsumedPerTick;
    }
    
    public int getSpeed() {
        return processingSpeed;
    }

    public final AbstractElectricTicker setCapacity(int capacity) {
        Preconditions.checkArgument(capacity > 0, "La capacidad debe ser mayor que 0"); 

        this.energyCapacity = capacity; 
        return this;
    }

    public final AbstractElectricTicker setConsumption(int consumption) {
        Preconditions.checkArgument(getCapacity() > 0, "La capacidad debe establecerse antes del consumo");
        Preconditions.checkArgument(consumption < getCapacity() && consumption != 0, "El consumo no puede ser mayor que la capacidad"); 
        
        this.energyConsumedPerTick = consumption;
        return this;
    }

    public final AbstractElectricTicker setProcessingSpeed(int speed) {
        Preconditions.checkArgument(speed > 0, "La velocidad debe ser mayor que cero"); 

        this.processingSpeed = speed; 
        return this; 
    }

    protected boolean takeCharge(Location l) {
        Preconditions.checkNotNull(l, "No se puede tomar energía de una ubicación nula"); 

        if (isChargeable()) {
            int charge = getCharge(l); 

             if (charge < getEnergyConsumption()) {
                return false;
             }

             setCharge(l, charge - getEnergyConsumption()); 
        }
        return true; 
    }
}
