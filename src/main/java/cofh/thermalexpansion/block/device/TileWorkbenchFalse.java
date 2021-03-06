package cofh.thermalexpansion.block.device;

import static cofh.thermalexpansion.block.workbench.TileWorkbench.*;

import cofh.thermalexpansion.block.TEBlocks;
import cofh.thermalexpansion.block.TileInventory;
import cofh.thermalexpansion.block.workbench.TileWorkbench;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileWorkbenchFalse extends TileInventory {

	public static void initialize() {

		GameRegistry.registerTileEntity(TileWorkbenchFalse.class, "thermalexpansion.Workbench");
	}

	public int selectedSchematic = 0;
	public ItemStack[] craftingGrid = new ItemStack[9];

	// Conversion Code
	@Override
	public void cofh_validate() {

		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		tag.setByte("Type", (byte) 1);
		invalidate();

		inventory = new ItemStack[SCHEMATICS[1] + INVENTORY[1]];

		worldObj.setBlock(xCoord, yCoord, zCoord, TEBlocks.blockWorkbench, 1, 3);
		TileWorkbench tile = (TileWorkbench) worldObj.getTileEntity(xCoord, yCoord, zCoord);
		tile.readFromNBT(tag);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public TileWorkbenchFalse() {

	}

	@Override
	public String getName() {

		return "tile.invalid";
	}

	@Override
	public int getType() {

		return 0;
	}

	/* NBT METHODS */
	@Override
	public void readFromNBT(NBTTagCompound nbt) {

		selectedSchematic = nbt.getByte("Mode");
		readCraftingFromNBT(nbt);

		inventory = new ItemStack[SCHEMATICS[1] + INVENTORY[1]];

		super.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {

		super.writeToNBT(nbt);

		nbt.setByte("Mode", (byte) selectedSchematic);
		writeCraftingToNBT(nbt);
	}

	public void readCraftingFromNBT(NBTTagCompound nbt) {

		NBTTagList list = nbt.getTagList("Crafting", 10);
		craftingGrid = new ItemStack[9];
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = list.getCompoundTagAt(i);
			int slot = tag.getInteger("Slot");

			if (slot >= 0 && slot < craftingGrid.length) {
				craftingGrid[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
	}

	public void writeCraftingToNBT(NBTTagCompound nbt) {

		NBTTagList list = new NBTTagList();
		for (int i = 0; i < craftingGrid.length; i++) {
			if (craftingGrid[i] != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger("Slot", i);
				craftingGrid[i].writeToNBT(tag);
				list.appendTag(tag);
			}
		}
		nbt.setTag("Crafting", list);
	}

}
