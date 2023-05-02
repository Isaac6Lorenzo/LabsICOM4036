import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Image {

	public static void main(String[] args) {
		Image first = new Image("src//photos//frog.png");

		first.show(first.getPath());

		System.out.println("alto: " + first.getHeight());
		System.out.println("ancho: " + first.getWidth());


		BufferedImage image = first.getInput();
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = image.getRGB(x, y);
				Color color = new Color(pixel, true);

				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();
				System.out.println("X,Y: " + x + ", " + y + " RGB:" + red + ", " + green + ", " + blue);
				//				System.out.println("X,Y: " + x + ", " + y + " COLOR:" + pixel);
				for (int i = 0; i < 100; i++) {

				}
			}
		}
	}

	private int height;
	private int width;
	private BufferedImage input = null;
	private BufferedImage[] output;
	private String path;
	private int areaPixels;
	private int[] pixels;

	public Image(String path) {
		this.reader(path);
		this.height = input.getHeight();
		this.width = input.getWidth();
		this.path = path;
		this.areaPixels = this.getHeight() * this.getWidth();

		//		output = new BufferedImage[2];
	}



	public void reader(String path) {
		try {
			File file = new File(path);
			input = ImageIO.read(file);
			System.out.println("Reading Complete");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error msg: " + e);
		}
	}

	public void writer(String path, String name) {
		try {
			File file = new File(path);
			ImageIO.write(output[0], name+".jpg", file);
			System.out.println("Writing	 Complete");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error msg: " + e);
		}
	}


	public int getPixels(int x, int y) {
		return this.pixels[x + (y * this.getWidth())];
	}

	private void updateImagePixelAt(int x, int y){
		this.getInput().setRGB(x, y, pixels[x+ (y * this.getWidth())]);
	}

	public void show(String path) {
		JFrame frame = new JFrame();
		ImageIcon icon = new ImageIcon(path);
		JLabel label = new JLabel(icon);
		frame.add(label);
		frame.setLayout(null);
		label.setLocation(0,0);
		label.setSize(this.getInput().getWidth(), this.getInput().getHeight());
		label.setVisible(true);

		frame.setVisible(true);
		frame.setSize(this.getInput().getWidth(), this.getInput().getHeight() +30);
		//		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	//	public void draw(Graphics g, Image image, int h, int w) {
	//		//check if the image exist first time
	//		
	//		g.drawImage(image, h, w, null);
	//	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public BufferedImage getInput() {
		return input;
	}

	public void setInput(BufferedImage input) {
		this.input = input;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


	public BufferedImage[] getOutput() {
		return output;
	}

	public void setOutput(BufferedImage[] output) {
		this.output = output;
	}

	public int getAreaPixels() {
		return areaPixels;
	}

	public void setAreaPixels(int areaPixels) {
		this.areaPixels = areaPixels;
	}

	public int[] getPixels() {
		return pixels;
	}



	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}


}
