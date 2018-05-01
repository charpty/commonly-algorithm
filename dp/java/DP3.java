/**
 * 0-1背包问题
 *
 * 设有个容量为W的背包，现有1..i个商品，每个商品的价值为V[i],容量为P[i]
 * 每个商品只有一个，不可拆分，求所能转入的物品的最大总价值
 *
 * f(i,j) = f(i-1,j) ; P[i] > 剩余容量
 * f(i,j) = max(f(i-1,j),f(i-1,j-P[i])+V[i])
 */
public class DP3 {

	private static int[] V = new int[] { 6, 3, 5, 4, 6 };
	private static int[] P = new int[] { 2, 5, 4, 2, 3 };
	private static int W = 10;

	public static void main(String[] args) {

		// 前i个物品放入容量为j的购物车的最大价值
		int[][] dp = new int[V.length + 1][W + 1];

		// 容量为0，物品数为0，总价值当然为0
		for (int i = 0; i < dp.length; i++) {
			dp[i][0] = 0;
		}
		for (int i = 0; i < dp[0].length; i++) {
			dp[0][i] = 0;
		}

		int[][] choose = new int[V.length + 1][W + 1];

		for (int i = 1; i < dp.length; i++) {
			for (int j = 1; j < dp[i].length; j++) {
				if (P[i - 1] > j) {
					dp[i][j] = dp[i - 1][j];
				} else {
					// 装与不装的最大值
					int in = dp[i - 1][j - P[i - 1]] + V[i - 1];
					int out = dp[i - 1][j];
					if (in > out) {
						dp[i][j] = in;
						choose[i][j] = 1;
					} else {
						dp[i][j] = out;
						choose[i][j] = 0;
					}
				}
			}
		}
		printAction(choose, 5, 10);
		System.out.println("最大价值为: " + dp[5][10]);
	}

	private static void printAction(int[][] choose, int i, int j) {
		String[] actions = new String[i > j ? i : j];
		int c = 0;

		for (; i >= 0 && j >= 0; ) {
			if (choose[i][j] == 0) {
				--i;
			} else {
				actions[c++] = "装入" + i;
				j -= P[i-- - 1];
			}
		}

		for (i = (actions.length - 1); i >= 0; i--) {
			if (actions[i] == null) {
				continue;
			}
			System.out.print(actions[i]);
			if (i != 0) {
				System.out.print(" -> ");
			}
		}
		System.out.println();
	}
}
