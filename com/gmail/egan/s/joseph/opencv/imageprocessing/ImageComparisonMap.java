package com.gmail.egan.s.joseph.opencv.imageprocessing;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

public class ImageComparisonMap
{
	private final Mat result;
	private final Point maxPoint;
	private final Point minPoint;
	
	private Mat visualization = null;
	
	public ImageComparisonMap(Mat baseImage, Mat imageToFind)
	{
		this.result = new Mat(baseImage.rows() - imageToFind.rows() + 1, baseImage.cols() - imageToFind.cols() + 1, CvType.CV_32FC1);
		Imgproc.matchTemplate(baseImage, imageToFind, this.result, Imgproc.TM_CCOEFF);
		Core.normalize(this.result, this.result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
		
		this.maxPoint = Core.minMaxLoc(this.result).maxLoc;
		this.minPoint = Core.minMaxLoc(this.result).minLoc;
	}
	
	public double get(int x, int y)
	{
		return this.result.get(y, x)[0];
	}
	
	public int getWidth()
	{
		return this.result.cols();
	}
	
	public int getHeight()
	{
		return this.result.rows();
	}
	
	public Point getMaxPoint()
	{
		return this.maxPoint;
	}
	
	public Point getMinPoint()
	{
		return this.minPoint;
	}
	
	public double getMaxValue()
	{
		return this.result.get((int)this.maxPoint.y, (int)this.maxPoint.x)[0];
	}
	
	public double getMinValue()
	{
		return this.result.get((int)this.minPoint.y, (int)this.minPoint.x)[0];
	}
	
	public Mat getVisualization()
	{
		if(this.visualization != null)
		{
			return this.visualization.clone();
		}
		
		this.visualization = new Mat(this.result.rows(), this.result.cols(), CvType.CV_8UC3);
		
		for(int i = 0, iMax = this.result.cols(); i < iMax; i++)
		{
			for(int j = 0, jMax = this.result.rows(); j < jMax; j++)
			{
				byte[] shade = {(byte) (255 * this.result.get(j, i)[0]), (byte) (255 * this.result.get(j, i)[0]), (byte) (255 * this.result.get(j, i)[0])};
				this.visualization.put(j, i, shade);
			}
		}
		
		return this.visualization.clone();
	}
}
