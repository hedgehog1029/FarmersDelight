package vectorwing.farmersdelight.data;

import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.blocks.*;
import vectorwing.farmersdelight.registry.ModBlocks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class BlockStates extends BlockStateProvider
{
	private static final int DEFAULT_ANGLE_OFFSET = 180;

	public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, FarmersDelight.MODID, exFileHelper);
	}

	private String blockName(Block block) {
		return block.getRegistryName().getPath();
	}

	public ResourceLocation resourceBlock(String path) {
		return new ResourceLocation(FarmersDelight.MODID, "block/" + path);
	}

	public ModelFile existingModel(Block block) {
		return new ModelFile.ExistingModelFile(resourceBlock(blockName(block)), models().existingFileHelper);
	}

	public ModelFile existingModel(String path) {
		return new ModelFile.ExistingModelFile(resourceBlock(path), models().existingFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		this.simpleBlock(ModBlocks.RICH_SOIL.get(), cubeRandomRotation(ModBlocks.RICH_SOIL.get(), ""));
		this.simpleBlock(ModBlocks.SAFETY_NET.get(), existingModel(ModBlocks.SAFETY_NET.get()));

		this.simpleBlock(ModBlocks.STRAW_THATCH.get(), existingModel(ModBlocks.STRAW_THATCH.get()));
		this.slabBlock((SlabBlock) ModBlocks.STRAW_THATCH_SLAB.get(), existingModel(ModBlocks.STRAW_THATCH_SLAB.get()), existingModel("straw_thatch_slab_top"), existingModel(ModBlocks.STRAW_THATCH.get()));
//		this.stairsBlock((StairsBlock) ModBlocks.STRAW_THATCH_STAIRS.get(), existingModel(ModBlocks.STRAW_THATCH_STAIRS.get()), existingModel("straw_thatch_stairs_inner"), existingModel("straw_thatch_stairs_outer"));
		this.stairsThatchBlock((StairsBlock) ModBlocks.STRAW_THATCH_STAIRS.get());

		String riceBag = blockName(ModBlocks.RICE_BAG.get());
		this.simpleBlock(ModBlocks.RICE_BAG.get(), models().withExistingParent(riceBag, "cube")
				.texture("particle", resourceBlock(riceBag + "_top"))
				.texture("down", resourceBlock(riceBag + "_bottom"))
				.texture("up", resourceBlock(riceBag + "_top"))
				.texture("north", resourceBlock(riceBag + "_side_tied"))
				.texture("south", resourceBlock(riceBag + "_side_tied"))
				.texture("east", resourceBlock(riceBag + "_side"))
				.texture("west", resourceBlock(riceBag + "_side"))
		);

		customDirectionalBlock(ModBlocks.BASKET.get(),
				$ -> existingModel(ModBlocks.BASKET.get()), BasketBlock.ENABLED, BasketBlock.WATERLOGGED);
		customDirectionalBlock(ModBlocks.RICE_BALE.get(),
				$ -> existingModel(ModBlocks.RICE_BALE.get()));
		customHorizontalBlock(ModBlocks.CUTTING_BOARD.get(),
				$ -> existingModel(ModBlocks.CUTTING_BOARD.get()), BasketBlock.WATERLOGGED);

		this.horizontalBlock(ModBlocks.HALF_TATAMI_MAT.get(), existingModel("tatami_mat_half"));
		this.horizontalBlock(ModBlocks.STOVE.get(), state -> {
			String name = blockName(ModBlocks.STOVE.get());
			String suffix = state.get(StoveBlock.LIT) ? "_on" : "";

			return models().orientableWithBottom(name + suffix,
					resourceBlock(name + "_side"),
					resourceBlock(name + "_front" + suffix),
					resourceBlock(name + "_bottom"),
					resourceBlock(name + "_top" + suffix));
		});

		this.stageBlock(ModBlocks.BROWN_MUSHROOM_COLONY.get(), MushroomColonyBlock.COLONY_AGE);
		this.stageBlock(ModBlocks.RED_MUSHROOM_COLONY.get(), MushroomColonyBlock.COLONY_AGE);
		this.stageBlock(ModBlocks.RICE_UPPER_CROP.get(), RiceUpperCropBlock.RICE_AGE);
		this.customStageBlock(ModBlocks.CABBAGE_CROP.get(), resourceBlock("crop_cross"), "cross", CabbagesBlock.AGE, new ArrayList<>());
		this.customStageBlock(ModBlocks.TOMATO_CROP.get(), resourceBlock("crop_cross"), "cross", TomatoesBlock.AGE, Arrays.asList(0, 0, 1, 1, 2, 2, 3, 4));
		this.customStageBlock(ModBlocks.ONION_CROP.get(), mcLoc("crop"), "crop", OnionsBlock.AGE, Arrays.asList(0, 0, 1, 1, 2, 2, 2, 3));

		this.crateBlock(ModBlocks.CARROT_CRATE.get(), "carrot");
		this.crateBlock(ModBlocks.POTATO_CRATE.get(), "potato");
		this.crateBlock(ModBlocks.BEETROOT_CRATE.get(), "beetroot");
		this.crateBlock(ModBlocks.CABBAGE_CRATE.get(), "cabbage");
		this.crateBlock(ModBlocks.TOMATO_CRATE.get(), "tomato");
		this.crateBlock(ModBlocks.ONION_CRATE.get(), "onion");

		this.axisBlock((RotatedPillarBlock) ModBlocks.STRAW_BALE.get());

		this.pantryBlock(ModBlocks.OAK_PANTRY.get(), "oak");
		this.pantryBlock(ModBlocks.BIRCH_PANTRY.get(), "birch");
		this.pantryBlock(ModBlocks.SPRUCE_PANTRY.get(), "spruce");
		this.pantryBlock(ModBlocks.JUNGLE_PANTRY.get(), "jungle");
		this.pantryBlock(ModBlocks.ACACIA_PANTRY.get(), "acacia");
		this.pantryBlock(ModBlocks.DARK_OAK_PANTRY.get(), "dark_oak");
		this.pantryBlock(ModBlocks.CRIMSON_PANTRY.get(), "crimson");
		this.pantryBlock(ModBlocks.WARPED_PANTRY.get(), "warped");

		this.pieBlock(ModBlocks.APPLE_PIE.get());
		this.pieBlock(ModBlocks.CHOCOLATE_PIE.get());
		this.pieBlock(ModBlocks.SWEET_BERRY_CHEESECAKE.get());

		this.feastBlock((FeastBlock) ModBlocks.STUFFED_PUMPKIN_BLOCK.get());
		this.feastBlock((FeastBlock) ModBlocks.ROAST_CHICKEN_BLOCK.get());
		this.feastBlock((FeastBlock) ModBlocks.HONEY_GLAZED_HAM_BLOCK.get());
		this.feastBlock((FeastBlock) ModBlocks.SHEPHERDS_PIE_BLOCK.get());

		this.wildCropBlock(ModBlocks.WILD_BEETROOTS.get(), false);
		this.wildCropBlock(ModBlocks.WILD_CABBAGES.get(), false);
		this.wildCropBlock(ModBlocks.WILD_POTATOES.get(), false);
		this.wildCropBlock(ModBlocks.WILD_TOMATOES.get(), false);
		this.wildCropBlock(ModBlocks.WILD_CARROTS.get(), true);
		this.wildCropBlock(ModBlocks.WILD_ONIONS.get(), true);
		this.doublePlantBlock(ModBlocks.WILD_RICE.get());
	}

	public ConfiguredModel[] cubeRandomRotation(Block block, String suffix) {
		String formattedName = blockName(block) + (suffix.isEmpty() ? "" : "_" + suffix);
		return ConfiguredModel.allYRotations(models().cubeAll(formattedName, resourceBlock(formattedName)), 0, false);
	}

	public void customDirectionalBlock(Block block, Function<BlockState, ModelFile> modelFunc, Property<?>... ignored) {
		getVariantBuilder(block)
				.forAllStatesExcept(state -> {
					Direction dir = state.get(BlockStateProperties.FACING);
					return ConfiguredModel.builder()
							.modelFile(modelFunc.apply(state))
							.rotationX(dir == Direction.DOWN ? 180 : dir.getAxis().isHorizontal() ? 90 : 0)
							.rotationY(dir.getAxis().isVertical() ? 0 : ((int) dir.getHorizontalAngle() + DEFAULT_ANGLE_OFFSET) % 360)
							.build();
				}, ignored);
	}

	public void customHorizontalBlock(Block block, Function<BlockState, ModelFile> modelFunc, Property<?>... ignored) {
		getVariantBuilder(block)
				.forAllStatesExcept(state -> ConfiguredModel.builder()
						.modelFile(modelFunc.apply(state))
						.rotationY(((int) state.get(BlockStateProperties.HORIZONTAL_FACING).getHorizontalAngle() + DEFAULT_ANGLE_OFFSET) % 360)
						.build(), ignored);
	}

	public void stageBlock(Block block, IntegerProperty ageProperty, Property<?>... ignored) {
		getVariantBuilder(block)
				.forAllStatesExcept(state -> {
					int ageSuffix = state.get(ageProperty);
					String stageName = blockName(block) + "_stage" + ageSuffix;
					return ConfiguredModel.builder()
							.modelFile(models().cross(stageName, resourceBlock(stageName))).build();
				}, ignored);
	}

	// I am not proud of this method... But hey, it's runData. Only I shall have to deal with it.
	public void customStageBlock(Block block, @Nullable ResourceLocation parent, String textureKey, IntegerProperty ageProperty, List<Integer> suffixes, Property<?>... ignored) {
		getVariantBuilder(block)
				.forAllStatesExcept(state -> {
					int ageSuffix = state.get(ageProperty);
					String stageName = blockName(block) + "_stage";
					stageName += suffixes.isEmpty() ? ageSuffix : suffixes.get(Math.min(suffixes.size(), ageSuffix));
					if (parent == null) {
						return ConfiguredModel.builder()
								.modelFile(models().cross(stageName, resourceBlock(stageName))).build();
					}
					return ConfiguredModel.builder()
							.modelFile(models().singleTexture(stageName, parent, textureKey, resourceBlock(stageName))).build();
				}, ignored);
	}

	public void wildCropBlock(Block block, boolean isBushCrop) {
		if (isBushCrop) {
			this.simpleBlock(block, models().singleTexture(blockName(block), resourceBlock("bush_crop"), "crop", resourceBlock(blockName(block))));
		} else {
			this.simpleBlock(block, models().cross(blockName(block), resourceBlock(blockName(block))));
		}
	}

	public void stairsThatchBlock(StairsBlock block) {
		ModelFile stairsBottom = existingModel(block);
		ModelFile stairsTop = existingModel(blockName(block) + "_top");
		ModelFile stairsInnerBottom = existingModel(blockName(block) + "_inner");
		ModelFile stairsInnerTop = existingModel(blockName(block) + "_inner_top");
		ModelFile stairsOuterBottom = existingModel(blockName(block) + "_outer");
		ModelFile stairsOuterTop = existingModel(blockName(block) + "_outer_top");

		getVariantBuilder(block)
				.forAllStatesExcept(state -> {
					Direction facing = state.get(StairsBlock.FACING);
					Half half = state.get(StairsBlock.HALF);
					StairsShape shape = state.get(StairsBlock.SHAPE);
					int xRot = 0;
					int yRot = (int) facing.rotateY().getHorizontalAngle(); // Stairs model is rotated 90 degrees clockwise for some reason
					if (shape == StairsShape.INNER_LEFT || shape == StairsShape.OUTER_LEFT) {
						yRot += 270; // Left facing stairs are rotated 90 degrees clockwise
					}
					if (shape != StairsShape.STRAIGHT && half == Half.TOP) {
						xRot += 180; // Top bent stairs need a 180 rotation
						yRot += 90; // Top stairs are rotated 90 degrees clockwise
					}
					if (shape == StairsShape.STRAIGHT && half == Half.TOP) {
						yRot += 180;
					}
					yRot %= 360;
					boolean uvlock = yRot != 0 || half == Half.TOP; // Don't set uvlock for states that have no rotation

					ModelFile stairs = half == Half.TOP ? stairsTop : stairsBottom;
					ModelFile stairsInner = half == Half.TOP ? stairsInnerTop : stairsInnerBottom;
					ModelFile stairsOuter = half == Half.TOP ? stairsOuterTop : stairsOuterBottom;

					return ConfiguredModel.builder()
							.modelFile(shape == StairsShape.STRAIGHT ? stairs : shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? stairsInner : stairsOuter)
							.rotationX(xRot)
							.rotationY(yRot)
							.uvLock(uvlock)
							.build();
				}, StairsBlock.WATERLOGGED);
	}

	public void crateBlock(Block block, String cropName) {
		this.simpleBlock(block,
				models().cubeBottomTop(blockName(block), resourceBlock(cropName + "_crate_side"), resourceBlock("crate_bottom"), resourceBlock(cropName + "_crate_top")));
	}

	public void pantryBlock(Block block, String woodType) {
		this.horizontalBlock(block, state -> {
			String suffix = state.get(PantryBlock.OPEN) ? "_open" : "";
			return models().orientable(blockName(block) + suffix,
					resourceBlock(woodType + "_pantry_side"),
					resourceBlock(woodType + "_pantry_front" + suffix),
					resourceBlock(woodType + "_pantry_top"));
		});
	}

	public void feastBlock(FeastBlock block) {
		getVariantBuilder(block)
				.forAllStates(state -> {
					int servings = state.get(FeastBlock.SERVINGS);

					String suffix = "_stage" + (block.getMaxServings() - servings);

					if (servings == 0) {
						suffix = block.hasLeftovers ? "_leftover" : "_stage3";
					}

					return ConfiguredModel.builder()
							.modelFile(existingModel(blockName(block) + suffix))
							.rotationY(((int) state.get(FeastBlock.FACING).getHorizontalAngle() + DEFAULT_ANGLE_OFFSET) % 360)
							.build();
				});
	}

	public void doublePlantBlock(Block block) {
		getVariantBuilder(block)
				.partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
				.modelForState().modelFile(models().cross(blockName(block) + "_bottom", resourceBlock(blockName(block) + "_bottom"))).addModel()
				.partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)
				.modelForState().modelFile(models().cross(blockName(block) + "_top", resourceBlock(blockName(block) + "_top"))).addModel();
	}

	public void pieBlock(Block block) {
		getVariantBuilder(block)
				.forAllStates(state -> {
							int bites = state.get(PieBlock.BITES);
							String suffix = bites > 0 ? "_slice" + bites : "";
							return ConfiguredModel.builder()
									.modelFile(existingModel(blockName(block) + suffix))
									.rotationY(((int) state.get(PieBlock.FACING).getHorizontalAngle() + DEFAULT_ANGLE_OFFSET) % 360)
									.build();
						}
				);
	}
}
