
package fr.jlstrykerz.mcmod.block;

import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.loot.LootContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import net.minecraft.fluid.FluidState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Collections;

import fr.jlstrykerz.mcmod.procedures.BloodFlowerLayerFinalBlockDestroyedByPlayerProcedure;
import fr.jlstrykerz.mcmod.item.BloodSeedsItem;
import fr.jlstrykerz.mcmod.MinecraftmodModElements;

@MinecraftmodModElements.ModElement.Tag
public class BloodFlowerLayerFinalBlock extends MinecraftmodModElements.ModElement {
	@ObjectHolder("minecraftmod:blood_flower_layer_final")
	public static final Block block = null;
	public BloodFlowerLayerFinalBlock(MinecraftmodModElements instance) {
		super(instance, 11);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new CustomBlock());
		elements.items.add(() -> new BlockItem(block, new Item.Properties().group(null)).setRegistryName(block.getRegistryName()));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void clientLoad(FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
	}
	public static class CustomBlock extends Block {
		public CustomBlock() {
			super(Block.Properties.create(Material.PLANTS).sound(SoundType.PLANT).hardnessAndResistance(0f, 10f).setLightLevel(s -> 0)
					.doesNotBlockMovement().notSolid().setOpaque((bs, br, bp) -> false));
			setRegistryName("blood_flower_layer_final");
		}

		@Override
		public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
			return true;
		}

		@Override
		public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
			Vector3d offset = state.getOffset(world, pos);
			return VoxelShapes.create(0.062D, 0D, 0.062D, 0.937D, 0.812D, 0.937D).withOffset(offset.x, offset.y, offset.z);
		}

		@Override
		public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
			return new ItemStack(BloodSeedsItem.block, (int) (1));
		}

		@Override
		public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
			List<ItemStack> dropsOriginal = super.getDrops(state, builder);
			if (!dropsOriginal.isEmpty())
				return dropsOriginal;
			return Collections.singletonList(new ItemStack(BloodSeedsItem.block, (int) (1)));
		}

		@Override
		public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity entity, boolean willHarvest, FluidState fluid) {
			boolean retval = super.removedByPlayer(state, world, pos, entity, willHarvest, fluid);
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				BloodFlowerLayerFinalBlockDestroyedByPlayerProcedure.executeProcedure($_dependencies);
			}
			return retval;
		}
	}
}
