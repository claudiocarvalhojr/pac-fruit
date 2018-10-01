package main;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import main.resources.Fruit;
import main.resources.Game;
import main.resources.Player;
import main.resources.Rain;


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
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		initialize();
	}

	private static void initialize()
	{

		final String fileProperties = "main/resources/properties/config.properties";

		String output = null;

		final Properties props = Utils.loadProperties(fileProperties);
		if (props != null)
		{
			output = props.getProperty("output");
		}

		final File init = new File(output + "\\" + "init.txt");
		if (!init.exists())
		{

			final String[] options =
			{ "instalar", "jogar", "cancelar" };
			final String question = "Você deseja " + options[0] + " ou " + options[1] + " pac-fruit?";
			final int input = JOptionPane.showOptionDialog(null, question, "Pac-fruit", JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE, new ImageIcon(Main.class.getResource("/main/resources/img/pac-fruit.png")), options,
					options[0]);
			if (input == 0)
			{

				try
				{
					Installer.initialize(fileProperties);
				}
				catch (final IOException e)
				{
					JOptionPane.showMessageDialog(null, e.getMessage());
				}

			}
			else if (input == 1)
			{

				buildGame();

			}
			else if (input == 2)
			{
				System.exit(0);
			}
		}
		else
		{

			buildGame();

		}


	}

	private static void buildGame()
	{

		frame = new JFrame();
		frame.setSize(405, 302);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(getJPanel());
		frame.setVisible(true);
		start();
		motion(game.getPlayer().getWidth(), game.getPlayer().getHeight());

	}

	private static JPanel getJPanel()
	{
		if (panel == null)
		{
			panel = new JPanel();
			panel = new JPanel();
			panel.setBackground(Color.BLACK);
			panel.setLayout(null);
			panel.setVisible(true);
		}
		return panel;
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
		getJPanel().removeAll();
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
		game.setListFruits(null);
		game.setActive(false);
		refreshPanel();
		getJPanel().removeAll();
	}

	private static void refreshPanel()
	{
		getJPanel().revalidate();
		getJPanel().repaint();
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
					getJPanel().remove(game.getListFruits().get(count));
					game.setCountFruit(game.getCountFruit() + 1);
					game.setCountTotalFruit(game.getCountTotalFruit() + 1);
					refreshInfo();
					if (game.getCountFruit() == game.getQtyFruit())
					{
						stop();
						final int input = JOptionPane.showConfirmDialog(panel,
								"Você ganhou!\n\nColetou: " + game.getCountTotalFruit() + " frutas\nNível: " + game.getLevel()
										+ "\n\n Jogar o próximo nível?",
								"Pac-fruit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
			getJPanel().remove(rain);
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
			game.getPlayer().setIcon(new ImageIcon(Main.class.getResource("/main/resources/img/player.gif")));
			game.getPlayer().setVisible(true);
		}
		game.getPlayer().setBounds(1, 256, game.getPlayer().getWidth(), game.getPlayer().getHeight());
		refreshPanel();
		getJPanel().add(game.getPlayer());
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
			fruit.setIcon(new ImageIcon(Main.class.getResource("/main/resources/img/fruit.png")));
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
			getJPanel().add(fruit);
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
							getJPanel().add(rain);
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
									rain.setIcon(new ImageIcon(getClass().getResource("/main/resources/img/rain.png")));
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
							getJPanel().remove(rain);
							rain = null;
						}
						catch (final Exception e)
						{
							JOptionPane.showMessageDialog(null, e.getMessage());
						}
					}
				}
			}.start();
		}
	}

}
