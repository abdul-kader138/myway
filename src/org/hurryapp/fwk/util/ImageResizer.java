package org.hurryapp.fwk.util;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


public class ImageResizer {

	private String imagePath;
	private Image originalImage;
	private BufferedImage resizedImage;
	
	public ImageResizer(String imagePath) throws Exception {
		this.imagePath = imagePath;
		//originalImage = ImageIO.read(new File(imagePath));
		originalImage = Toolkit.getDefaultToolkit().getImage(imagePath);
		MediaTracker mediaTracker = new MediaTracker(new Container());
		mediaTracker.addImage(originalImage, 0);
		mediaTracker.waitForID(0);
	}

	/** 
	 * Resize an Image with width and height
	 * @param width
	 * @param height
	 * @param antialias
	 */
	public void resize(int width, int height, boolean antialias) {
		//int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = resizedImage.createGraphics();
		//graphics2D.setComposite(AlphaComposite.Src);
		
		if (antialias) {
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		
		graphics2D.drawImage(originalImage, 0, 0, width, height, null);
		graphics2D.dispose();
	}

	/**
	 * Resize an Image with width and height
	 * @param width
	 * @param height
	 */
	public void resize(int width, int height) {
		resize(width, height, true);
	}
	
	/**
	 * Resize an Image with a given height and keep ratio
	 * @param height
	 * @param antialias
	 */
	public void resizeByHeight(int height, boolean antialias) {
		int originalHeight = originalImage.getHeight(null);
		int originalWidth = originalImage.getWidth(null);
		double imageRatio = (double)originalHeight / (double)originalWidth;
		int thumbHeight = height;
		int thumbWidth = (int)(thumbHeight / imageRatio);

		resize(thumbWidth, thumbHeight, antialias);
	}

	/**
	 * Resize an Image with a given height and keep ratio
	 * @param height
	 * @param antialias
	 */
	public void resizeByHeight(int height) {
		resizeByHeight(height, true);
	}

	/**
	 * Resize an Image with a given width and keep ratio
	 * @param height
	 * @param antialias
	 */
	public void resizeByWidth(int width, boolean antialias) {
		int originalHeight = originalImage.getHeight(null);
		int originalWidth = originalImage.getWidth(null);
		double imageRatio = (double)originalWidth / (double)originalHeight;
		int thumbWidth = width;
		int thumbHeight = (int)(thumbWidth / imageRatio);

		resize(thumbWidth, thumbHeight, antialias);
	}

	/**
	 * Resize an Image with a given width and keep ratio
	 * @param height
	 * @param antialias
	 */
	public void resizeByWidth(int width) {
		resizeByWidth(width, true);
	}

	/**
	 * Write image into a file with a given jpg quality
	 * @param imagePath path where write file
	 * @param quality jpg encoding quality
	 * @throws ImageFormatException
	 * @throws IOException
	 */
	public String write(String imagePath, int quality) throws ImageFormatException, IOException {
		String newImagePath = null;
		if (resizedImage != null) {
			newImagePath = imagePath.substring(0, imagePath.lastIndexOf('.')) + ".png";

			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(newImagePath));
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(resizedImage);
			quality = Math.max(0, Math.min(quality, 100));
			param.setQuality((float)quality / 100.0f, false);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(resizedImage);
			out.close();
		}
		return newImagePath;
	}
	
	/**
	 * Write image into a file with a given jpg quality
	 * @param imagePath path where write file
	 * @throws ImageFormatException
	 * @throws IOException
	 */
	public String write(String imagePath) throws ImageFormatException, IOException {
		String newImagePath = null;
		if (resizedImage != null) {
			newImagePath = imagePath.substring(0, imagePath.lastIndexOf('.')) + ".png";
			ImageIO.write(resizedImage, newImagePath.substring(newImagePath.lastIndexOf('.')+1), new File(newImagePath));
		}
		return newImagePath;
	}

	/**
	 * Return original image width
	 * @return
	 */
	public int getOriginalImageWidth() {
		return originalImage.getWidth(null);
	}
	
	/**
	 * Return original image height
	 * @return
	 */
	public int getOriginalImageHeight() {
		return originalImage.getHeight(null);
	}
	
	/**
	 * Return original image weight
	 * @return
	 */
	public double getOriginalImageWeight() {
		return new File(imagePath).length() / 1024.0 / 1000.0;
	}

	/**
	 * Return image weight
	 * @return
	 */
	public static double getWeight(String imagePath) {
		return new File(imagePath).length() / 1024.0 / 1000.0;
	}

	/**
	 * Resize and rewrite an image
	 * @param imagePath
	 * @param width
	 * @param height
	 * @param nbPixels
	 * @return
	 * @throws Exception
	 */
	public static String resize(String imagePath, Integer width, Integer height, Integer nbPixels) throws Exception {
		ImageResizer imageResizer = new ImageResizer(imagePath);
		
		if (imageResizer.getOriginalImageWeight() > 10.0) { // Si image > 10 Mo --> exception
			throw new ResizeException("Image's filesize must be less than 10Mb");
		}
		
		int orginalImageWidth = imageResizer.getOriginalImageWidth();
		int orginalImageHeight = imageResizer.getOriginalImageHeight();
		
		if (nbPixels != null && orginalImageWidth*orginalImageHeight > nbPixels) {
			double ratio = Math.sqrt((double)nbPixels / (double)(orginalImageWidth*orginalImageHeight));
			width = (int) (orginalImageWidth * ratio);
			height = (int) (orginalImageHeight * ratio);
		}

		if (orginalImageWidth > width || orginalImageHeight > height) {
			if (orginalImageHeight > height) {
				imageResizer.resizeByHeight(height);
			}
			else if (orginalImageWidth > width) {
				imageResizer.resizeByWidth(width);
			}
			
			new File(imagePath).delete();
			imagePath = imageResizer.write(imagePath);
		}
		
		return imagePath;
	}
	
	public static void main(String[] args) {
		try {
			//ImageResizer imageResizer = new ImageResizer("c:\\image\\1.jpg");
			//imageResizer.resizeByHeight(100, true);
			//imageResizer.write("c:\\image\\1-resized.png");

			//System.out.println("Width :" + imageResizer.getOriginalImageWidth());
			//System.out.println("Height :" + imageResizer.getOriginalImageHeight());
			
			System.out.println(new File("c:\\image\\hs-2006-10-a-hires_jpg.jpg").length()/1024.0/1000.0);
			resize("c:\\image\\messier_104_sombrero.jpg", 8191, 8191, 16500000);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
