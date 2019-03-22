package utils;

public class MatrixOperations {
	public static double[] add (double[] data1, double[] data2){
		double[] newArray = new double[data1.length];
		for(int i = 0;i < data1.length;i++){
			newArray[i] = data1[i] + data2[i];
		}
		return newArray;
	}

	public static double[] sub (double[] data1, double[] data2){
		double[] newArray = new double[data1.length];
		for(int i = 0;i < data1.length;i++){
			newArray[i] = data1[i] - data2[i];
		}
		return newArray;
	}

	public static double[] mult (double[] data1, double[] data2){
		double[] newArray = new double[data1.length];
		for(int i = 0;i < data1.length;i++){
			newArray[i] = data2[i] * data1[i];
		}
		return newArray;
	}

	public static double[] divide (double[] data1, double[] data2){
		double[] newArray = new double[data1.length*data2.length];
		for(int i = 0;i < newArray.length;i++){
			newArray[i] = data1[i >= data1.length ? i - data1.length: i];
		}
		return newArray;
	}

	public static double[] divide (double[] data, double val){
		double[] newArray = new double[data.length];
		for(int i = 0;i < data.length;i++){
			newArray[i] = val / data[i];
		}
		return newArray;
	}
}
