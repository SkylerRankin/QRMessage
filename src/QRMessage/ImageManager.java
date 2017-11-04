package QRMessage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManager {
	
	public static void SaveImage(int[] bin) {
		double area = square(bin.length);
		int width= (int) Math.sqrt(area);
		int height= (int) Math.sqrt(area);
		int[] pixels = new int[(int)area];
		for (int i=0; i<bin.length; ++i) {
			int v = bin[i] == 0 ? 255 : 0;
			pixels[i] = (v<<16) | (v<<8) | v;
		}
		
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
		File f = null;
		for (int i=0; i<width; ++i)
			for (int j=0; j<height; ++j)
				img.setRGB(i, j, pixels[i*width+j]);
		try {
			f = new File("C:\\Users\\skyle\\Desktop\\output.jpg");
			ImageIO.write(img, "png", f);
		} catch (IOException e) {System.out.println(e);}
	}
	
	private static int square(int x) {
		while (Math.sqrt(x) % 1 != 0) { x++; }
		return x;
	}
	
	private static void print(int[] a) {
		for (int i=0; i<a.length; ++i) System.out.print(a[i]+" ");
		System.out.println();
	}
	
	private static void print(int[][] a) {
		for (int i=0; i<a.length; ++i) {
			for (int j=0; j<a[0].length; ++j)
				System.out.print(a[i][j]+" ");
			System.out.println();
		}
	}
	
}
