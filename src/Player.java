
public class Player {

	int chipCount;
	
	public Player(int chipNum)
	{
		this.chipCount = chipNum;
	}
	
	public void setChipCount(int chipNum)
	{
		this.chipCount = chipNum;
	}
	
	public int getChipCount()
	{
		return this.chipCount;
	}
	
	public void addChipCount(int chipNum)
	{
		 this.chipCount += chipNum;
		 
		 if (chipCount < 0)
		 {
			 chipCount = 0;
		 }
	}

}
