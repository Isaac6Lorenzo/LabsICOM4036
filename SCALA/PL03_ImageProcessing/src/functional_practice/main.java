package functional_practice;

public class main {

	public static String path = "src//photos//noise.jpg";
	public static String output = "src//photos//noise02.jpg";
	
	
	public static void main(String[] args) {

		MyImage image = new MyImage();
		image.readImage(path);
		
		MyImage copy = new MyImage(image);
		
		//median filter
		long start = System.currentTimeMillis();
		FilterImage filter = new FilterImage();
		filter.medianFilter(copy, 3);
		long end = System.currentTimeMillis();
		long time = end-start;
		System.out.println("Time execute: " + time + " mseg");
		//guatdar imagen output
		copy.writeImage(output);
	}

}
