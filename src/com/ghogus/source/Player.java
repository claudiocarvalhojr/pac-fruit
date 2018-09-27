package com.ghogus.source;

import javax.swing.JLabel;


/**
 *
 */
public class Player extends JLabel
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int lifes;
	private int width;
	private int height;

	/**
	 * @return the lifes
	 */
	public int getLifes()
	{
		return lifes;
	}

	/**
	 * @param lifes
	 *           the lifes to set
	 */
	public void setLifes(final int lifes)
	{
		this.lifes = lifes;
	}

	/**
	 * @return the width
	 */
	@Override
	public int getWidth()
	{
		return width;
	}

	/**
	 * @param width
	 *           the width to set
	 */
	public void setWidth(final int width)
	{
		this.width = width;
	}

	/**
	 * @return the height
	 */
	@Override
	public int getHeight()
	{
		return height;
	}

	/**
	 * @param height
	 *           the height to set
	 */
	public void setHeight(final int height)
	{
		this.height = height;
	}

}
