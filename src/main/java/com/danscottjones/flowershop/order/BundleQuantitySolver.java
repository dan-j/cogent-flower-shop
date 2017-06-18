package com.danscottjones.flowershop.order;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BundleQuantitySolver {

	public static Map<Integer, Integer> solve(int[] bundleSizes, int total) {

		int[][] matrix = trackingMatrix(bundleSizes, total);
		int[][][] solution = new int[bundleSizes.length + 1][total + 1][total];

		for (int i = 1; i <= bundleSizes.length; i++) {
			for (int j = 1; j <= total; j++) {
				if (bundleSizes[i - 1] == j) {
					matrix[i][j] = 1;
					solution[i][j][0] = bundleSizes[i - 1];
				} else if (bundleSizes[i - 1] > j) {
					matrix[i][j] = matrix[i - 1][j];
					solution[i][j] = solution[i - 1][j];
				} else {
					matrix[i][j] = Math.min(
							matrix[i - 1][j],
							1 + matrix[i][j - bundleSizes[i - 1]]
					);

					if (matrix[i - 1][j] < 1 + matrix[i][j - bundleSizes[i - 1]]) {
						solution[i][j] = solution[i - 1][j].clone();
					} else {
						solution[i][j] = solution[i][j - bundleSizes[i - 1]].clone();
						int k = 0;
						for (; k < solution[i][j].length && solution[i][j][k] != 0; k++) {
						}
						solution[i][j][k] = bundleSizes[i - 1];
					}
				}
			}
		}

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

	private static int[][] trackingMatrix(int[] bundleSizes, int total) {
		int[][] matrix = new int[bundleSizes.length + 1][total + 1];

		int initValue = Integer.MAX_VALUE - 1;
		for (int i = 0; i <= total; i++) {
			matrix[0][i] = initValue;
		}

		return matrix;
	}
}
