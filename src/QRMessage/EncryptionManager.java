package QRMessage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.paint.Color;

public class EncryptionManager {
	
	private static int[][] readImage(String path) {
		File f = new File(path);
		BufferedImage img = null;
		try {
			img = ImageIO.read(f);
		} catch (IOException e) {
			System.out.println(e);
		}
		if (img == null) { System.out.println("No image found at "+path);return null; }
		
		int[][] pixels = new int[img.getWidth()][img.getHeight()];
		for (int i=0; i<img.getWidth(); ++i)
			for (int j=0; j<img.getHeight(); ++j) {
					pixels[j][i] = Math.abs(img.getRGB(j, i)) < 1000 ? 0 : 1;
			}
		return pixels;
	}
	
	public static Event getText(String path) {
		if (!new File(path).exists()) return new Event("File Not Found", Color.RED);
		int[][] pixels = readImage(path);
		String binary = collapse(pixels);
		StringBuilder s = new StringBuilder();
		int i=0;
		while (i<binary.length()-6) {
			s.append(ascii(binary.substring(i, i+7)));
			i+=7;
		}
		Event e = new Event("File Successfully Read", Color.GREEN);
		e.setData(s.toString());
		return e;
	}
	
	private int[] trim(int[] a, int m) {
		int[] b = a;
		while (a.length % m != 0) {
			b = new int[a.length-1];
			for (int i=0; i<b.length; ++i) b[i] = a[i];
			a = b;
		}
		return b;
	}
	
	private static String collapse(int[][] a) {
		int[] b = new int[a.length*a[0].length];
		for (int i=0; i<a.length; ++i)
			for (int j=0; j<a[0].length; ++j)
				b[i*a.length + j] = a[i][j];
		String s = "";
		for (int i : b) s=s+i;
		return s;
	}

	private void print(int[] a) {
		for (int i=0; i<a.length; ++i) System.out.print(a[i]+" ");
		System.out.println();
	}
	
	private void print(int[][] a) {
		for (int i=0; i<a.length; ++i) {
			for (int j=0; j<a[0].length; ++j)
				System.out.print(a[i][j]+" ");
			System.out.println();
		}
	}
	
	private static int[] toBinaryArray(String s) {
		int[] binary = new int[s.length()*7];
		for (int i=0; i<s.length(); ++i) {
			int dec = (int) s.charAt(i);
			String bin = bin(dec, 7);
			for (int j=0; j<7; ++j)
				binary[i*7+j] = Integer.parseInt(bin.substring(j, j+1));
		}
		//print(binary);
		return binary;
	}
	
	public static String ascii(String s) {
		return Character.toString((char) Integer.parseInt(s, 2));
	}
	
	private static String bin(int x, int size) {
		String r = Integer.toBinaryString(x);
		while (r.length() < size) r = "0"+r;
		return r;
	}

	public static Event SaveImage(String text, String path) {
		
		if (text.length() < 1) return new Event("No Text Entered", Color.ORANGE);
		File f = new File(path);
		if (!new File(f.getParent()).exists()) return new Event("Save location does not exist", Color.RED);
		
		String[] image_extensions = {"jpg", "jpeg", "png"};
		
		int dot = path.lastIndexOf(".");
		if (dot == -1) return new Event("No File Extension", Color.RED);
		String ext = path.substring(dot+1, path.length());
		System.out.println(ext);
		boolean valid = false;
		for (String s : image_extensions) 
			if (s.equals(ext)) valid = true;
		if (!valid) return new Event("No Image File Extension", Color.RED);
		
		int[] bin = toBinaryArray(text);
		double area = square(bin.length);
		int width= (int) Math.sqrt(area);
		int height= (int) Math.sqrt(area);
		int[] pixels = new int[(int)area];
		for (int i=0; i<bin.length; ++i) {
			int v = bin[i] == 0 ? 255 : 0;
			pixels[i] = (v<<16) | (v<<8) | v;
		}
		
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
		
		for (int i=0; i<width; ++i)
			for (int j=0; j<height; ++j)
				img.setRGB(i, j, pixels[i*width+j]);
		
		try {
			ImageIO.write(img, "png", f);
		} catch (Exception e) {System.out.println(e); return new Event("Failed to create image: "+e.getMessage(), Color.RED);}
		return new Event("Image created at "+path, Color.GREEN);
	}
	
	private static int square(int x) {
		while (Math.sqrt(x) % 1 != 0) { x++; }
		return x;
	}
	
}
