package net.dalerd.aquaverse.datagen;

import net.dalerd.aquaverse.item.custom.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        // You don’t have custom blocks yet, so leave this empty for now
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // Register the Dunkleosteus spawn egg so datagen will create its JSON automatically
        itemModelGenerator.register(ModItems.DUNKLEOSTEUS_SPAWN_EGG,
                new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));
    }
}
