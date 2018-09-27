package game.main;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import game.source.Fruit;
import game.source.Game;
import game.source.Player;
import game.source.Rain;


/**
 *
 */
public class Main
{

	private static JFrame frame;
	private static JPanel panel;
	private static Game game;

	/**
	 * @param args
	 */
	public static void main(final String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		initialize();
	}

	private static void initialize()
	{
		frame = new JFrame();
		frame.setSize(405, 302);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setSize(405, 302);
		panel.setLayout(null);
		panel.setVisible(true);
		frame.getContentPane().add(panel);
		start();
		motion(game.getPlayer().getWidth(), game.getPlayer().getHeight());
	}

	private static void start()
	{
		if (game == null)
		{
			game = new Game();
			game.setLevel(1);
			game.setHigherSpeed(5);
			game.setLowerSpeed(10);
			game.setQtyRain(10);
			game.setQtyFruit(3);
			game.setCountTotalFruit(0);
		}
		game.setActive(true);
		game.setCountFruit(0);
		game.setListFruits(new ArrayList<Fruit>());
		refreshPanel();
		panel.removeAll();
		makeFruit(game.getQtyFruit());
		makePlayer();
		makeRain(game.getQtyRain());
		refreshInfo();
	}

	private static void restart()
	{
		game = null;
		start();
	}

	private static void nextLevel()
	{
		if (game.getPlayer() != null)
		{
			game.getPlayer().setLifes(game.getPlayer().getLifes() + 1);
			game.setLevel(game.getLevel() + 1);
			game.setQtyRain(game.getQtyRain() + 1);
			start();
		}
		else
		{
			restart();
		}
	}

	private static void stop()
	{
		//		game.getListFruits().clear();
		game.setListFruits(null);
		game.setActive(false);
		refreshPanel();
		panel.removeAll();
	}

	private static void refreshPanel()
	{
		panel.revalidate();
		panel.repaint();
	}

	private static void refreshInfo()
	{
		frame.setTitle("Nível: " + game.getLevel() + " | Vidas: " + game.getPlayer().getLifes() + " | Frutas: "
				+ game.getCountTotalFruit() + " | Atual: " + game.getCountFruit());
	}

	private static void motion(final int moveRL, final int moveUD)
	{
		frame.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(final KeyEvent event)
			{
				if (game.getPlayer() != null && game.getListFruits() != null && !game.getListFruits().isEmpty())
				{
					if (event.getKeyCode() == 37 && (game.getPlayer().getLocation().x - moveRL) > 0)
					{
						game.getPlayer().setLocation(game.getPlayer().getLocation().x - moveRL, game.getPlayer().getLocation().y);
						collisionFruit(game.getListFruits().size());
					}
					else if (event.getKeyCode() == 38 && (game.getPlayer().getLocation().y - moveUD) > 0)
					{
						game.getPlayer().setLocation(game.getPlayer().getLocation().x, game.getPlayer().getLocation().y - moveUD);
						collisionFruit(game.getListFruits().size());
					}
					else if (event.getKeyCode() == 39 && (game.getPlayer().getLocation().x + moveRL) <= 385)
					{
						game.getPlayer().setLocation(game.getPlayer().getLocation().x + moveRL, game.getPlayer().getLocation().y);
						collisionFruit(game.getListFruits().size());
					}
					else if (event.getKeyCode() == 40 && (game.getPlayer().getLocation().y + moveUD) <= 256)
					{
						game.getPlayer().setLocation(game.getPlayer().getLocation().x, game.getPlayer().getLocation().y + moveUD);
						collisionFruit(game.getListFruits().size());
					}
				}
				if (event.getKeyCode() == 27)
				{
					final int input = JOptionPane.showConfirmDialog(panel, "Deseja sair?", "Pac-fruit", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (input == 0)
					{
						System.exit(0);
					}
				}
			}
		});
	}

	private static void collisionFruit(final int listFruitSize)
	{
		if (game.getListFruits() != null && game.getPlayer() != null && !game.getListFruits().isEmpty())
		{
			for (int count = 0; count < listFruitSize; count++)
			{
				if (game.getPlayer().getLocation().x == game.getListFruits().get(count).getLocation().x
						&& game.getPlayer().getLocation().y == game.getListFruits().get(count).getLocation().y
						&& game.getCountFruit() < 3)
				{
					refreshPanel();
					panel.remove(game.getListFruits().get(count));
					game.setCountFruit(game.getCountFruit() + 1);
					game.setCountTotalFruit(game.getCountTotalFruit() + 1);
					refreshInfo();
					if (game.getCountFruit() == game.getQtyFruit())
					{
						stop();
						final int input = JOptionPane.showConfirmDialog(panel, "Você ganhou!\n\nColetou: " + game.getCountTotalFruit() 
								+ " frutas\nNível: " + game.getLevel() + "\n\n Jogar o próximo nível?", "Pac-fruit",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (input == 0)
						{

							nextLevel();
						}
						else
						{
							System.exit(0);
						}
					}
				}
			}
		}
	}

	private static boolean collisionRain(final Rain rain)
	{
		if (rain != null && game.getPlayer() != null && rain.getLocation().x == game.getPlayer().getLocation().x
				&& rain.getLocation().y == game.getPlayer().getLocation().y && game.getPlayer().getLifes() > 0)
		{
			game.getPlayer().setLifes(game.getPlayer().getLifes() - 1);
			refreshPanel();
			panel.remove(rain);
			refreshInfo();
			if (game.getPlayer().getLifes() == 0)
			{
				stop();
				final int input = JOptionPane.showConfirmDialog(panel, "Você perdeu!\n\nColetou: " + game.getCountTotalFruit()
						+ " frutas\nNível: " + game.getLevel() + "\n\nDeseja jogar novamente?", "Pac-fruit", JOptionPane.YES_NO_OPTION,
						JOptionPane.ERROR_MESSAGE);
				if (input == 0)
				{
					restart();
				}
				else
				{
					System.exit(0);
				}
			}
			return true;
		}
		return false;
	}

	private static void makePlayer()
	{
		if (game.getPlayer() == null)
		{
			game.setPlayer(new Player());
			game.getPlayer().setLifes(5);
			game.getPlayer().setWidth(12);
			game.getPlayer().setHeight(15);
			game.getPlayer().setIcon(new ImageIcon(Main.class.getResource("/game/img/player.gif")));
			game.getPlayer().setVisible(true);
		}
		game.getPlayer().setBounds(1, 256, game.getPlayer().getWidth(), game.getPlayer().getHeight());
		refreshPanel();
		panel.add(game.getPlayer());
	}

	private static void makeFruit(final int qtyFruit)
	{
		final Random random = new Random();
		int column = 1;
		int row = 1;
		for (int count = 0; count < qtyFruit; count++)
		{
			final Fruit fruit = new Fruit();
			fruit.setWidth(12);
			fruit.setHeight(15);
			fruit.setIcon(new ImageIcon(Main.class.getResource("/game/img/fruit.png")));
			do
			{
				column = random.nextInt(385) + 1;
			}
			while (column > 0 && ((column - 1) % fruit.getWidth() != 0));
			do
			{
				row = random.nextInt(256) + 1;
			}
			while (row > 0 && ((row - 1) % fruit.getHeight() != 0));
			if (column == 1 && row == 256)
			{
				column += 12;
			}
			fruit.setBounds(column, row, fruit.getWidth(), fruit.getHeight());
			fruit.setVisible(true);
			panel.add(fruit);
			game.getListFruits().add(fruit);
		}
	}

	private static void makeRain(final int qtyRain)
	{
		for (int i = 0; i < qtyRain; i++)
		{
			new Thread()
			{
				@Override
				public void run()
				{
					Rain rain = null;
					final Random random = new Random();
					int speed = 0;
					int column = 0;
					while (game.isActive())
					{
						try
						{
							if (rain == null)
							{
								rain = new Rain();
								rain.setWidth(12);
								rain.setHeight(15);
							}
							panel.add(rain);
							do
							{
								column = random.nextInt(385) + 1;
							}
							while (column > 1 && ((column - 1) % rain.getWidth() != 0));
							speed = random.nextInt(2);
							for (int row = 1; row <= 257; row++)
							{
								if (game.isActive())
								{
									rain.setIcon(new ImageIcon(getClass().getResource("/game/img/rain.png")));
									rain.setBounds(column, row, rain.getWidth(), rain.getHeight());
									rain.setVisible(true);
									if (speed == 0)
									{
										Thread.sleep(game.getHigherSpeed());
									}
									else if (speed == 1)
									{
										Thread.sleep(game.getLowerSpeed());
									}
									if (collisionRain(rain))
									{
										rain = null;
										return;
									}
								}
							}
							refreshPanel();
							panel.remove(rain);
							rain = null;
						}
						catch (final Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			}.start();
		}
	}

}
