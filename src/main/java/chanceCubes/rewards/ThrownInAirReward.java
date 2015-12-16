package chanceCubes.rewards;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import chanceCubes.CCubesCore;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;

public class ThrownInAirReward implements IChanceCubeReward
{

	@Override
	public void trigger(World world, BlockPos pos, final EntityPlayer player)
	{
		int px = (int) Math.floor(player.posX);
		int py = (int) Math.floor(player.posY) + 1;
		int pz = (int) Math.floor(player.posZ);

		for(int y = 0; y < 40; y++)
			for(int x = -1; x < 2; x++)
				for(int z = -1; z < 2; z++)
					world.setBlockToAir(pos.add(px + x, py + y, pz + z));

		Task task = new Task("Item_Of_Destiny_Reward", 5)
		{
			@Override
			public void callback()
			{
				player.isAirBorne = true;
				player.motionY = 20;
				((EntityPlayerMP) player).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(player.getEntityId(), player.motionX, player.motionY, player.motionZ));
			}
		};
		Scheduler.scheduleTask(task);
	}

	@Override
	public int getChanceValue()
	{
		return -30;
	}

	@Override
	public String getName()
	{
		return CCubesCore.MODID + ":Thrown_In_Air";
	}

}