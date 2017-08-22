package chapter01;

public class SortNumbers {
    public static void sort(float[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int min = i;
            for (int j = i; j < nums.length; j++) {
                if (nums[j] < nums[min]) min = j;
            }
            float tmp = nums[i];
            nums[i] = nums[min];
            nums[min] = tmp;
        }
    }
}