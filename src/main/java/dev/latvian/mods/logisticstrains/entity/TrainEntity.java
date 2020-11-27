package dev.latvian.mods.logisticstrains.entity;

import dev.latvian.mods.logisticstrains.item.LTItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.TeleportationRepositioner;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

/**
 * @author LatvianModder
 */
public class TrainEntity extends Entity
{
	public TrainEntity(World world)
	{
		super(LTEntities.TRAIN.get(), world);
	}

	public TrainEntity(World world, double x, double y, double z)
	{
		this(world);
		setPosition(x, y, z);
		setMotion(Vector3d.ZERO);
		prevPosX = x;
		prevPosY = y;
		prevPosZ = z;
	}

	@Override
	protected void registerData()
	{
	}

	@Override
	protected void readAdditional(CompoundNBT compound)
	{
	}

	@Override
	protected void writeAdditional(CompoundNBT compound)
	{
	}

	@Override
	public IPacket<?> createSpawnPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected float getEyeHeight(Pose poseIn, EntitySize sizeIn)
	{
		return sizeIn.height;
	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	public boolean canCollide(Entity entity)
	{
		return (entity.func_241845_aY() || entity.canBePushed()) && !isRidingSameEntity(entity);
	}

	@Override
	public boolean func_241845_aY()
	{
		return true;
	}

	@Override
	public boolean canBePushed()
	{
		return false;
	}

	@Override
	protected Vector3d func_241839_a(Direction.Axis axis, TeleportationRepositioner.Result result)
	{
		return LivingEntity.func_242288_h(super.func_241839_a(axis, result));
	}

	@Override
	public double getMountedYOffset()
	{
		return -0.1D;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (isInvulnerableTo(source))
		{
			return false;
		}
		else if (!world.isRemote && !removed)
		{
			entityDropItem(new ItemStack(LTItems.TRAIN.get()));
			remove();
			return true;
		}
		else
		{
			return true;
		}
	}

	@Override
	public void applyEntityCollision(Entity entity)
	{
		if (entity instanceof TrainEntity)
		{
			if (entity.getBoundingBox().minY < getBoundingBox().maxY)
			{
				super.applyEntityCollision(entity);
			}
		}
		else if (entity.getBoundingBox().minY <= getBoundingBox().minY)
		{
			super.applyEntityCollision(entity);
		}
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return !removed;
	}

	@Override
	public Direction getAdjustedHorizontalFacing()
	{
		return getHorizontalFacing().rotateY();
	}

	@Override
	public void tick()
	{
		super.tick();
		doBlockCollisions();

		List<Entity> list = world.getEntitiesInAABBexcluding(this, getBoundingBox().grow(0.2F, -0.01F, 0.2F), EntityPredicates.pushableBy(this));
		if (!list.isEmpty())
		{
			boolean flag = !world.isRemote && !(getControllingPassenger() instanceof PlayerEntity);

			for (Entity entity : list)
			{
				if (!entity.isPassenger(this))
				{
					if (flag && getPassengers().size() < 2 && !entity.isPassenger() && entity.getWidth() < getWidth() && entity instanceof LivingEntity && !(entity instanceof WaterMobEntity) && !(entity instanceof PlayerEntity))
					{
						entity.startRiding(this);
					}
					else
					{
						applyEntityCollision(entity);
					}
				}
			}
		}
	}

	@Override
	public ActionResultType processInitialInteract(PlayerEntity player, Hand hand)
	{
		return ActionResultType.PASS;
	}
}
