package com.ghogus.source;

import java.util.List;


/**
 *
 */
public class Game
{

	private boolean active;
	private int level;
	private int higherSpeed;
	private int lowerSpeed;
	private int qtyRain;
	private int qtyFruit;
	private int countFruit;
	private int countTotalFruit;
	private List<Fruit> listFruits;
	private Player player;

	/**
	 * @return the active
	 */
	public boolean isActive()
	{
		return active;
	}

	/**
	 * @param active
	 *           the active to set
	 */
	public void setActive(final boolean active)
	{
		this.active = active;
	}

	/**
	 * @return the level
	 */
	public int getLevel()
	{
		return level;
	}

	/**
	 * @param level
	 *           the level to set
	 */
	public void setLevel(final int level)
	{
		this.level = level;
	}

	/**
	 * @return the higherSpeed
	 */
	public int getHigherSpeed()
	{
		return higherSpeed;
	}

	/**
	 * @param higherSpeed
	 *           the higherSpeed to set
	 */
	public void setHigherSpeed(final int higherSpeed)
	{
		this.higherSpeed = higherSpeed;
	}

	/**
	 * @return the lowerSpeed
	 */
	public int getLowerSpeed()
	{
		return lowerSpeed;
	}

	/**
	 * @param lowerSpeed
	 *           the lowerSpeed to set
	 */
	public void setLowerSpeed(final int lowerSpeed)
	{
		this.lowerSpeed = lowerSpeed;
	}

	/**
	 * @return the qtyRain
	 */
	public int getQtyRain()
	{
		return qtyRain;
	}

	/**
	 * @param qtyRain
	 *           the qtyRain to set
	 */
	public void setQtyRain(final int qtyRain)
	{
		this.qtyRain = qtyRain;
	}

	/**
	 * @return the qtyFruit
	 */
	public int getQtyFruit()
	{
		return qtyFruit;
	}

	/**
	 * @param qtyFruit
	 *           the qtyFruit to set
	 */
	public void setQtyFruit(final int qtyFruit)
	{
		this.qtyFruit = qtyFruit;
	}

	/**
	 * @return the countFruit
	 */
	public int getCountFruit()
	{
		return countFruit;
	}

	/**
	 * @param countFruit
	 *           the countFruit to set
	 */
	public void setCountFruit(final int countFruit)
	{
		this.countFruit = countFruit;
	}

	/**
	 * @return the countTotalFruit
	 */
	public int getCountTotalFruit()
	{
		return countTotalFruit;
	}

	/**
	 * @param countTotalFruit
	 *           the countTotalFruit to set
	 */
	public void setCountTotalFruit(final int countTotalFruit)
	{
		this.countTotalFruit = countTotalFruit;
	}

	/**
	 * @return the listFruits
	 */
	public List<Fruit> getListFruits()
	{
		return listFruits;
	}

	/**
	 * @param listFruits
	 *           the listFruits to set
	 */
	public void setListFruits(final List<Fruit> listFruits)
	{
		this.listFruits = listFruits;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * @param player
	 *           the player to set
	 */
	public void setPlayer(final Player player)
	{
		this.player = player;
	}

}
