// Clase estática de operaciones matemáticas
public class cMatematicas {
	
	public static int menor(int[] nums){
		int menor=nums[0];
		for(int i=1;i<nums.length-1;i++){
			if(nums[i]<menor)menor=nums[i];
		}
		return menor;
	}
	public static int mayor(int[] nums){
		int mayor=nums[0];
		for(int i=1;i<nums.length-1;i++){
			if(nums[i]>mayor)mayor=nums[i];
		}
		return mayor;
	}
	public static int menor(int x, int y){
		if(x>y)return y;
		else return x;
	}
	public static int mayor(int x, int y){
		if(x>y)return x;
		else return y;
	}
	
}
