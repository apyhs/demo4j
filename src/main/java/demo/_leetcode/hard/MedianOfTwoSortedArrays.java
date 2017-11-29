package demo._leetcode.hard;

import org.junit.Test;

import java.util.Arrays;

/**
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
 *
 * Example 1:
 * nums1 = [1, 3]
 * nums2 = [2]
 * The median is 2.0
 *
 * Example 2:
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 * The median is (2 + 3)/2 = 2.5
 */
public class MedianOfTwoSortedArrays {

    @Test
    public void test() {
        int[] nums1 = {1, 3};
        int[] nums2 = {2};
        System.out.println("nums1 = " + Arrays.toString(nums1));
        System.out.println("nums2 = " + Arrays.toString(nums2));
        System.out.println("Median is " + findMedianSortedArrays(nums1, nums2));
    }

    @Test
    public void test1() {
        int[] nums1 = {1, 2};
        int[] nums2 = {3, 4};
        System.out.println("nums1 = " + Arrays.toString(nums1));
        System.out.println("nums2 = " + Arrays.toString(nums2));
        System.out.println("Median is " + findMedianSortedArrays(nums1, nums2));
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // [1,2,3,4,5] [3,4,5,6,7]

        return 0d;
    }

}
