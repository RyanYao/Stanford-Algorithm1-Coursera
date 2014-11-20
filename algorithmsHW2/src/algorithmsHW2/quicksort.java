package algorithmsHW2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class quicksort {
	public static void main(String[] args) {
		int len = 1000;
		String fileName = "C:\\Users\\yaoyun\\workspace\\algorithmsHW2\\src\\algorithmsHW2\\1000.txt";
		int[] nums = openFile(fileName,len);
		long comp1 = quickSort1(nums, 0, len-1);
		nums = openFile(fileName,len);
		long comp2 = quickSort2(nums, 0, len-1);
		nums = openFile(fileName,len);
		//int[] num = {3, 2, 0, 4, 8, 5, 1, 9, 7, 6};
		long comp3 = quickSort3(nums, 0, nums.length-1);
		
		System.out.println(comp1);
		System.out.println(comp2);
		System.out.println(comp3);
	}
	
	public static int[] openFile(String fileName, int len){
		int[] ret = new int[len];
		Arrays.fill(ret, -1);
		try {
			 BufferedReader textFile = new BufferedReader(new FileReader(fileName));
			 String str="";
			 int lineNum = 0;
			 while (lineNum< len && (str = textFile.readLine()) != null) {
				 ret[lineNum] = Integer.parseInt(str);
				 lineNum++;
			 }
			 textFile.close();
		}
		catch (IOException e) {
		}
		return ret;
	}
	
	public static long quickSort1(int [] nums, int start, int end){//pivot is the first element
		long comp = 0;
		if(end<=start){
			return comp;
		}
		comp += end-start;
		
		int pivot = nums[start];
		int i = start+1;
		int j = start+1;
		for(; j<=end; j++){
			if(nums[j]>pivot)
				continue;
			int temp = nums[i];
			nums[i] = nums[j];
			nums[j] = temp;
			i++;
		}
		int temp = nums[i-1];
		nums[i-1] = nums[start];
		nums[start] = temp;
		
		comp += quickSort1(nums, start, i-2);
		comp += quickSort1(nums, i, end);
		
		return comp;
	}
	public static long quickSort2(int [] nums, int start, int end){//pivot is the last element
		long comp = 0;
		if(end<=start){
			return comp;
		}
		comp += end-start;
		
		int pivot = nums[end];
		int i = end-1;
		int j = end-1;
		for(; j>=start; j--){
			if(nums[j]<pivot)
				continue;
			int temp = nums[i];
			nums[i] = nums[j];
			nums[j] = temp;
			i--;
		}
		int temp = nums[i+1];
		nums[i+1] = nums[end];
		nums[end] = temp;
		
		comp += quickSort2(nums, start, i);
		comp += quickSort2(nums, i+2, end);
		
		return comp;
	}
	public static long quickSort3(int [] nums, int start, int end){//pivot is the median element
		long comp = 0;
		if(start>=end)
			return comp;
		
		comp += end-start;
		int pivot = getMedian(nums[start], nums[end], nums[(start+end)/2]);
		if(pivot==nums[(start+end)/2]){
			int temp=nums[start];
			nums[start] = nums[(start+end)/2];
			nums[(start+end)/2] = temp;
		}else if(pivot==nums[end]){
			int temp = nums[start];
			nums[start] = nums[end];
			nums[end] = temp;
		}
		int i=start+1;
		int j=start+1;
		for(; j<=end; j++){
			if(nums[j]>pivot)
				continue;
			int temp = nums[i];
			nums[i] = nums[j];
			nums[j] = temp;
			i++;
		}
		int temp = nums[i-1];
		nums[i-1] = nums[start];
		nums[start] = temp;
		
		comp += quickSort3(nums, start, i-2);
		comp += quickSort3(nums, i, end);
		return comp;
	}
	public static int getMedian(int i1, int i2, int i3){
		int[] arr = {i1, i2, i3};
		Arrays.sort(arr);
		return arr[1];
	}
}
