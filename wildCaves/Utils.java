package wildCaves;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class Utils 
{
	
	// returns the order number of the probability that was chosen (1-6)
	// all parameters are probabilities
	// probabilities can be 0
	public static int weightedChoise(float par1, float par2, float par3, float par4, float par5, float par6)
	{
		float total = par1 + par2 + par3 + par4 + par5 + par6;
		float val = new Random().nextFloat();
		float previous=0.0f;
		
		par1=par1/total;
		par2=par2/total;
		par3=par3/total;
		par4=par4/total;
		par5=par5/total;
		//par6 is the remaining probability
		
		if(val < par1)
			return 1;
		else 
			previous = par1;
		
		if(val < par2+previous)
			return 2;
		else 
			previous += par2;
		
		if(val < par3+previous)
			return 3;
		else 
			previous += par3;
		
		if(val < par4+previous)
			return 4;
		else 
			previous += par4;
		
		if(val < par5+previous)
			return 5;
		else
			return 6;
	}
	
	// chooses one of the given ints at random
 	public static int randomChoise(int a, int b, int c, int d, int e) 
	{
 		Random random = new Random();
		int result = 5;
		switch (random.nextInt(5)) 
		{
			case 0:
				result = a;
				break;
			case 1:
				result = b;
				break;
			case 2:
				result = c;
				break;
			case 3:
				result = d;
				break;
			case 4:
				result = e;
				break;
		}
		return result;
	}
	
	// chooses one of the given ints at random
 	public static int randomChoise(int a, int b, int c, int d) 
	{
 		Random random = new Random();
		int result = 4;
		switch (random.nextInt(4)) 
		{
			case 0:
				result = a;
				break;
			case 1:
				result = b;
				break;
			case 2:
				result = c;
				break;
			case 3:
				result = d;
				break;
		}
		return result;
	}
 	
	// chooses one of the given ints at random
 	public static int randomChoise(int a, int b, int c) 
	{
 		Random random = new Random();
		int result = 3;
		switch (random.nextInt(3)) 
		{
			case 0:
				result = a;
				break;
			case 1:
				result = b;
				break;
			case 2:
				result = c;
				break;
		}
		return result;
	}
 	
	// chooses one of the given ints at random
 	public static int randomChoise(int a, int b) 
	{
 		Random random = new Random();
		int result = 2;
		switch (random.nextInt(2)) 
		{
			case 0:
				result = a;
				break;
			case 1:
				result = b;
				break;
		}
		return result;
	}
	
	// gets the number of empty blocks between the current one and the closest one bellow
	public static int getNumEmptyBlocks(World world, int x, int y, int z) 
	{
		int dist = 0;
		while (!world.isBlockNormalCube(x, y, z) && y > 5 && world.getBlockTileEntity(x, y, z)==null) 
		{
			y--;
			dist++;
		}
		return dist;
	}
	
	// transforms an area into snow and ice
	public static void convertToFrozenType(World world, Random random, int x, int y, int z)
	{
		int height = random.nextInt(5)+3;
		int length = random.nextInt(5)+3;
		int width = random.nextInt(5)+3;
		
		int newX = x - length/2;
		int newY = y + height/2;
		int newZ = z - width/2;
		
		int aux;
		
		for(int i=0; i<height; i++)
		{
			for(int j=0; j<length; j++)
			{
				for(int k=0; k<width; k++)
				{
					// basically transform or not
					if(Utils.weightedChoise(0.8f, 0.2f, 0, 0, 0, 0) == 1)
					{
						aux = world.getBlockId(newX+j, newY-i, newZ+k);
						if(aux==Block.stone.blockID || aux==Block.dirt.blockID || aux==Block.gravel.blockID || aux==Block.grass.blockID)// stone -> Ice
							world.setBlock(newX+j, newY-i, newZ+k, Block.ice.blockID);
					}
				}
			}
		}
	}
	
	//transform an area in to sand and sandstone
	public static void convertToSandType(World world, Random random, int x, int y, int z)
	{
		int height = random.nextInt(5)+3;
		int length = random.nextInt(5)+3;
		int width = random.nextInt(5)+3;
		
		int newX = x - length/2;
		int newY = y + height/2;
		int newZ = z - width/2;
		
		int aux;
		
		for(int i=0; i<height; i++)
		{
			for(int j=0; j<length; j++)
			{
				for(int k=0; k<width; k++)
				{
					// basically transform or not
					if(Utils.weightedChoise(0.7f, 0.3f, 0, 0, 0, 0) == 1)
					{
						aux = world.getBlockId(newX+j, newY-i, newZ+k);
						if(aux==Block.stone.blockID)// stone -> sandstone
							world.setBlock(newX+j, newY-i, newZ+k, Block.sandStone.blockID, 0, 2);
						else if(aux==Block.dirt.blockID || aux==Block.gravel.blockID) // dirt/gravel -> sand
							world.setBlock(newX+j, newY-i, newZ+k, Block.sand.blockID, 0, 2);
					}
				}
			}
		}
	}
	
	//checks if a given int is in the given array
	public static boolean arrayContainsInt(int[] array, int a)
	{
		boolean result = false;
		
		int i=0;
		while(i<array.length && result==false)
		{
			if(array[i]==a)
				result=true;
			i++;
		}
		return result;
	}
}
