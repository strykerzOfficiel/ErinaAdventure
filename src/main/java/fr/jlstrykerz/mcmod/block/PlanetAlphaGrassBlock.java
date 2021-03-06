
package fr.jlstrykerz.mcmod.block;

import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.common.IPlantable;

import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
import net.minecraft.loot.LootContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;

import java.util.List;
import java.util.Collections;

import fr.jlstrykerz.mcmod.itemgroup.PlanetAlphaTabItemGroup;
import fr.jlstrykerz.mcmod.MinecraftmodModElements;

@MinecraftmodModElements.ModElement.Tag
public class PlanetAlphaGrassBlock extends MinecraftmodModElements.ModElement {
	@ObjectHolder("minecraftmod:planet_alpha_grass")
	public static final Block block = null;
	public PlanetAlphaGrassBlock(MinecraftmodModElements instance) {
		super(instance, 78);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new CustomBlock());
		elements.items
				.add(() -> new BlockItem(block, new Item.Properties().group(PlanetAlphaTabItemGroup.tab)).setRegistryName(block.getRegistryName()));
	}
	public static class CustomBlock extends Block {
		public CustomBlock() {
			super(Block.Properties.create(Material.ORGANIC).sound(SoundType.GROUND).hardnessAndResistance(0.5f, 0.5f).setLightLevel(s -> 0));
			setRegistryName("planet_alpha_grass");
		}

		@Override
		public MaterialColor getMaterialColor() {
			return MaterialColor.TNT;
		}

		@Override
		public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction direction, IPlantable plantable) {
			return true;
		}

		@Override
		public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
			List<ItemStack> dropsOriginal = super.getDrops(state, builder);
			if (!dropsOriginal.isEmpty())
				return dropsOriginal;
			return Collections.singletonList(new ItemStack(PlanetAlphaDirtBlock.block, (int) (1)));
		}
	}
}
