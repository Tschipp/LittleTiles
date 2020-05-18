package com.creativemd.littletiles.common.tile.place;

import java.util.Collections;
import java.util.List;

import com.creativemd.littletiles.client.render.tile.LittleRenderingCube;
import com.creativemd.littletiles.common.structure.LittleStructure;
import com.creativemd.littletiles.common.structure.directional.StructureDirectionalField;
import com.creativemd.littletiles.common.structure.relative.StructureRelative;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.creativemd.littletiles.common.tile.math.vec.LittleAbsoluteVec;
import com.creativemd.littletiles.common.tileentity.TileEntityLittleTiles;
import com.creativemd.littletiles.common.tileentity.TileList;
import com.creativemd.littletiles.common.util.grid.LittleGridContext;
import com.creativemd.littletiles.common.util.place.PlacementMode;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class PlacePreviewRelative extends PlacePreview {
	
	public StructureDirectionalField relativeType;
	public StructureRelative relative;
	
	public PlacePreviewRelative(LittleBox box, StructureRelative relative, StructureDirectionalField relativeType) {
		super(box.copy(), null);
		this.relative = relative;
		this.relativeType = relativeType;
	}
	
	@Override
	public boolean needsCollisionTest() {
		return false;
	}
	
	@Override
	public boolean requiresSplit() {
		return false;
	}
	
	@Override
	public PlacePreview copy() {
		return new PlacePreviewRelative(box.copy(), relative, relativeType);
	}
	
	@Override
	public List<LittleRenderingCube> getPreviews(LittleGridContext context) {
		List<LittleRenderingCube> cubes = super.getPreviews(context);
		for (LittleRenderingCube cube : cubes)
			cube.color = relativeType.annotation.color();
		return cubes;
	}
	
	@Override
	public List<LittleTile> placeTile(EntityPlayer player, BlockPos pos, LittleGridContext context, TileEntityLittleTiles teLT, TileList list, List<LittleTile> unplaceableTiles, List<LittleTile> removedTiles, PlacementMode mode, EnumFacing facing, boolean requiresCollisionTest, LittleStructure structure) {
		if (structure.getMainTile() == null && structure.selectMainTile())
			throw new RuntimeException("Invalid structure. Missing main tile!");
		
		relative.setBox(BlockPos.ORIGIN, box.copy(), context);
		relative.add(new LittleAbsoluteVec(pos, context).getRelative(structure.getMainTile().getAbsolutePos()));
		relativeType.set(structure, relative);
		return Collections.EMPTY_LIST;
	}
	
}
