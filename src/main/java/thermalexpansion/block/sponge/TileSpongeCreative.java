package thermalexpansion.block.sponge;

import cofh.render.IconRegistry;
import cofh.util.ServerHelper;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

import thermalexpansion.block.TEBlocks;

public class TileSpongeCreative extends TileSponge {

	public static void initialize() {

		GameRegistry.registerTileEntity(TileSpongeCreative.class, "thermalexpansion.SpongeCreative");
	}

	public TileSpongeCreative() {

	}

	public TileSpongeCreative(int metadata) {

		super(metadata);
	}

	@Override
	public int getType() {

		return BlockSponge.Types.CREATIVE.ordinal();
	}

	@Override
	public void placeAir() {

		if (ServerHelper.isClientWorld(worldObj)) {
			return;
		}
		if (fullOnPlace) {
			return;
		}
		Block query;
		int queryMeta;
		Fluid queryFluid;
		int bucketCounter = 0;
		for (int i = xCoord - 1; i <= xCoord + 1; i++) {
			for (int j = yCoord - 1; j <= yCoord + 1; j++) {
				for (int k = zCoord - 1; k <= zCoord + 1; k++) {
					query = worldObj.getBlock(i, j, k);
					if (query.isAir(worldObj, i, j, k) || query.getMaterial().isLiquid()) {
						worldObj.setBlock(i, j, k, TEBlocks.blockAirBarrier, 0, 3);
					}
				}
			}
		}
	}

	/* ISidedTexture */
	@Override
	public IIcon getTexture(int side, int pass) {

		return IconRegistry.getIcon("Sponge", getType());
	}

}