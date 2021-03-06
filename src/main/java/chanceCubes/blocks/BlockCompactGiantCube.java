package chanceCubes.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import chanceCubes.CCubesCore;
import chanceCubes.tileentities.TileGiantCube;

public class BlockCompactGiantCube extends BaseChanceBlock
{
	public static final String blockName = "Compact_Giant_Chance_Cube";
	
	public BlockCompactGiantCube()
	{
		super("Compact_Giant_Chance_Cube");
		this.setHardness(0.5f);
		this.setBlockTextureName("chancecubes:chanceCube");
	}

	public void onPostBlockPlaced(World world, int x, int y, int z, int p_149714_5_)
	{
		super.onPostBlockPlaced(world, x, y, z, p_149714_5_);
		if(world.isRemote)
			return;
		for(int xx = x - 1; xx <= x + 1; xx++)
		{
			for(int zz = z - 1; zz <= z + 1; zz++)
			{
				for(int yy = y; yy <= y + 2; yy++)
				{
					world.setBlock(xx, yy, zz, CCubesBlocks.chanceGiantCube);
					TileEntity tile = world.getTileEntity(xx, yy, zz);
					boolean master = (xx == x && yy == y + 1 && z == zz);
					if(tile != null && (tile instanceof TileGiantCube))
					{
						((TileGiantCube) tile).setMasterCoords(x, y + 1, z);
						((TileGiantCube) tile).setHasMaster(true);
						((TileGiantCube) tile).setIsMaster(master);
					}
				}
			}
		}
		world.playSoundEffect(x, y, z, CCubesCore.MODID + ":giant_Cube_Spawn", 1, 1);
	}
}