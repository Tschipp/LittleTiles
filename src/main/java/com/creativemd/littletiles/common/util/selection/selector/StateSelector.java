package com.creativemd.littletiles.common.util.selection.selector;

import com.creativemd.creativecore.common.utils.mc.BlockUtils;
import com.creativemd.littletiles.common.tile.LittleTile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;

public class StateSelector extends TileSelectorBlock {
	
	public int meta;
	
	public StateSelector(IBlockState state) {
		super(state.getBlock());
		this.meta = block.getMetaFromState(state);
	}
	
	public StateSelector() {
		
	}
	
	@Override
	protected void saveNBT(NBTTagCompound nbt) {
		super.saveNBT(nbt);
		nbt.setInteger("meta", meta);
	}
	
	@Override
	protected void loadNBT(NBTTagCompound nbt) {
		meta = nbt.getInteger("meta");
	}
	
	@Override
	public boolean is(LittleTile tile) {
		if (super.is(tile))
			return tile.getMeta() == meta;
		return false;
	}
	
	@Override
	public IBlockState getState() {
		return BlockUtils.getState(block, meta);
	}
	
}
