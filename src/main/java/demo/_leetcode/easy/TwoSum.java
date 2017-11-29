package demo._leetcode.easy;

import org.junit.Test;

import java.util.Arrays;

/**
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 *
 * Example:
 * Given nums = [2, 7, 11, 15], target = 9,
 *
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 */
public class TwoSum {

    @Test
    public void test() {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        System.out.println(Arrays.toString(twoSum1(nums, target)));
    }

    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                System.out.println(i + ">>" + j);
                if (nums[i] + nums[j] == target) {
                    return new int[] {i, j};
                }
            }
        }
        return null;
    }

    public int[] twoSum1(int[] nums, int target) {
        int length = nums.length;
        int index = 0;
        int index1 = index + 1;
        while (true) {
            if (nums[index] + nums[index1] == target) {
                return new int[] {index, index1};
            }
            else {
                if (index1 == length -1) { index++; index1 = index + 1; }
                else index1 ++;
                if (index == length -1) break;
            }
        }
        return null;
    }

}
