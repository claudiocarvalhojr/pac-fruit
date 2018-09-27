package main.resources;

import javax.swing.JLabel;


/**
 *
 */
public class Fruit extends JLabel
{

	private static final long serialVersionUID = 1L;
	private int width;
	private int height;

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
