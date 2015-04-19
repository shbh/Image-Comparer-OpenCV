package com.gmail.egan.s.joseph.opencv.imageprocessing;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

public class ImageComparer
{
	private Mat baseImage;
	private HashMap<Mat, ImageComparisonMap> searches;
	
	private boolean saveSearches = true;
	
	public ImageComparer(Mat baseImage)
	{
		this.baseImage = baseImage.clone();
		
		this.searches = new HashMap<Mat, ImageComparisonMap>();
	}
	
	public ImageComparer(Mat baseImage, boolean saveSearches)
	{
		this.baseImage = baseImage.clone();
		
		this.searches = new HashMap<Mat, ImageComparisonMap>();
		
		this.saveSearches = saveSearches;
		
		if (this.saveSearches)
		{
			this.searches = new HashMap<Mat, ImageComparisonMap>();
		}
	}
	
	public ImageComparisonMap getImageComparisonMap(Mat imageToFind)
	{
		if(this.saveSearches && this.searches.containsKey(imageToFind))
		{
			this.searches.get(imageToFind);
		}
		
		ImageComparisonMap result = new ImageComparisonMap(this.baseImage, imageToFind);
		
		if(this.saveSearches)
		{
			this.searches.put(imageToFind, result);
		}
		
		return result;
	}
	
	public static void main(String[] args)
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat baseImage = Highgui.imread("Base.png", Highgui.CV_LOAD_IMAGE_COLOR);
		Mat imageToFind = Highgui.imread("ToFind.png", Highgui.CV_LOAD_IMAGE_COLOR);
		
		ImageComparer imageComparer = new ImageComparer(baseImage);
		
		ImageComparisonMap imageComparisonMap = imageComparer.getImageComparisonMap(imageToFind);
		
		Highgui.imwrite("visualisation.png", imageComparisonMap.getVisualization());
	}
}
