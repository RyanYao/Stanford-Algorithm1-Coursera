package algorithemsHW1;
import java.io.*;
import java.util.Arrays;

public class CountingInversions {
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		int len = 100000;
		String fileName = "C:\\Users\\yaoyun\\workspace\\algorithemsHW1\\src\\algorithemsHW1\\IntegerArray.txt";
		Integer[] nums = openFile(fileName,len);

		long  totalInver = countInversion(nums);
		System.out.println(totalInver);

		long endTime = System.nanoTime();
		System.out.println("Took "+(endTime - startTime)/1000000.0 + " ms"); 
	}
	
	public static Integer[] openFile(String fileName, int len){
		Integer[] ret = new Integer[len];
		Arrays.fill(ret, -1);
		try {
			 BufferedReader textFile = new BufferedReader(new FileReader(fileName));
			 String str="";
			 int lineNum = 0;
			 while (lineNum< len && (str = textFile.readLine()) != null) {
				 //System.out.println(str);
				 ret[lineNum] = Integer.parseInt(str);
				 lineNum++;
			 }
			 textFile.close();
		}
		catch (IOException e) {
		}
		return ret;
	}
	
	public static long countInversion(Integer[] nums){
		int len = nums.length;
		return divideConquerCountInversion(nums, 0, len-1);
	}
	
	public static long divideConquerCountInversion(Integer[] nums, int start, int end){
		//System.out.println("start"+Integer.toString(start)+"end"+Integer.toString(end));
		if(start==end)
			return 0;
		long  left = divideConquerCountInversion(nums, start, start+(end-start)/2);
		long  right  = divideConquerCountInversion(nums, start+(end-start)/2+1, end);
		long  total = 0;
		Integer[] temp = new Integer[end-start+1];
		Arrays.fill(temp,-1);
		int k = 0;
		int i = start;
		int j = start+(end-start)/2+1;
		while(k<end-start+1){
			if(i>start+(end-start)/2){
				temp[k] = nums[j];
				k++;
				j++;
			}
			else if(j>end){
				temp[k] = nums[i];
				k++;
				i++;
			}
			else if (nums[i]<nums[j]){
				temp[k] = nums[i];
				k++;
				i++;
			}
			else{
				total += start+(end-start)/2 - i + 1;
				temp[k] = nums[j];
				k++;
				j++;
			}
		}
		for(k=0; k<end-start+1; k++){
			nums[start+k] = temp[k];
		}
		total = total+left+right;
		//System.out.println(total);
		return total;
	}
}
