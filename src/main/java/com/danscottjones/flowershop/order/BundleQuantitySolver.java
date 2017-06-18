package com.danscottjones.flowershop.order;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This problem is logically identical to the "Coin-change problem", whereby our bundle sizes can be
 * treated as coin denominations, and our total number of flowers is the monetary value those coins
 * need to sum up to.
 * <p>
 * A dynamic-programming approach is used to efficiently calculate the least number of bundles
 * required to meet the total. There are more efficient algorithms (see: probabilistic convolution
 * tree) but for the typical requirements of this problem, our solution is suitable.
 * <p>
 * <i>N.B. We assume that bundles of any given size are infinitely available.</i>
 * <p>
 * The way this solution works is a [N][M] matrix is constructed where N is the number of
 * denominations + 1, and M is the value of total + 1. The value of each entry {@code [i][j]} is
 * the minimum number of "bundles" required to reach a total of {@code i}, using denominations
 * {@code 0..j}. Whilst counting these minimum quantities of bundles, we also track which bundles
 * were used to get there in another `solutions` matrix, which is a 3D array again representing
 * [N][M][:]; the final dimension is a list of bundle sizes used reach this value.
 * <p>
 * Once we've finished processing and have fully populated matrices, the minimum number of
 * denominations can be found at [-1][-1] (i.e. the last element of the last array), and
 * {@code solutions[-1][-1]} will return an array which is a list of bundle sizes. The sum of
 * this array should equal the provided {@code total}, if it doesn't, then it's impossible for
 * the provided denominations to reach the required total.
 */
public class BundleQuantitySolver {

	/**
	 * See {@link BundleQuantitySolver} javadoc for an explanation of what this method is
	 * achieving.
	 * @param bundleSizes a list of available bundle sizes to be used to reach the required total
	 * @param total the number of items we want the bundles to sum to
	 * @return a Map where the key is the bundleSize and value is the quantity of that bundle
	 */
	public static Map<Integer, Integer> solve(int[] bundleSizes, int total) {

		int[][] matrix = trackingMatrix(bundleSizes, total);
		int[][][] solution = new int[bundleSizes.length + 1][total + 1][total];

		for (int i = 1; i <= bundleSizes.length; i++) {
			for (int j = 1; j <= total; j++) {
				if (bundleSizes[i - 1] == j) {
					// we can reach the value `j` using just one bundle
					matrix[i][j] = 1;
					// new solution, hence inserting at [i][j][0]
					solution[i][j][0] = bundleSizes[i - 1];
				} else if (bundleSizes[i - 1] > j) {
					// this denomination is too big to reach `j` so the minimum number of moves is
					// the same as for the previous denomination at the same `j`
					matrix[i][j] = matrix[i - 1][j];
					solution[i][j] = solution[i - 1][j];
				} else {
					matrix[i][j] = Math.min(
							matrix[i - 1][j],
							1 + matrix[i][j - bundleSizes[i - 1]]
					);

					// NOTE: Here we have a branch if there are multiple optimum solutions. In
					// the future it would be better to track these, and then use the cheapest in
					// our order, however this over-complicates the problem so we take the solution
					// using the most recent denomination. You can switch this logic to take the
					// oldest solution by changing the condition in this if statement from `<` to
					// `<=`.
					if (matrix[i - 1][j] < 1 + matrix[i][j - bundleSizes[i - 1]]) {
						// the most efficient solution is to not use this denomination, copy the
						// solution from the previous denomination.
						solution[i][j] = solution[i - 1][j].clone();
					} else {
						// use of this denomination gives us the optimal solution so-far. Copy
						// the previous solution and append the current denomination value to the it
						solution[i][j] = solution[i][j - bundleSizes[i - 1]].clone();
						int k = 0;
						// the solution matrix is filled with 0's, so we need to find the next
						// available index to insert our next denomination into
						for (; k < solution[i][j].length && solution[i][j][k] != 0; k++) {
						}
						solution[i][j][k] = bundleSizes[i - 1];
					}
				}
			}
		}

		// extract the solution list, convert to Integer, filter the 0's, reverse largest first
		// and return a list
		List<Integer> solutionList = Arrays.stream(solution[bundleSizes.length][total])
				.boxed()
				.filter(i -> i != 0)
				.sorted(Comparator.reverseOrder())
				.collect(Collectors.toList());

		// key: bundle size, value: quantity of bundle
		// LinkedHashMap to preserve order
		Map<Integer, Integer> solutionMap = new LinkedHashMap<>();
		for (Integer bundle : solutionList) {
			if (solutionMap.containsKey(bundle)) {
				solutionMap.put(bundle, solutionMap.get(bundle) + 1);
			} else {
				solutionMap.put(bundle, 1);
			}
		}

		return solutionMap;
	}


	/**
	 * Generate the initial matrix to track minimum number of denominations
	 *
	 * @param bundleSizes see {@link #solve(int[], int)}
	 * @param total see {@link #solve(int[], int)}
	 * @return an {@code [N][M]} matrix denoting number of denominations and target value
	 */
	private static int[][] trackingMatrix(int[] bundleSizes, int total) {
		int[][] matrix = new int[bundleSizes.length + 1][total + 1];

		int initValue = Integer.MAX_VALUE - 1;
		for (int i = 0; i <= total; i++) {
			matrix[0][i] = initValue;
		}

		return matrix;
	}
}
