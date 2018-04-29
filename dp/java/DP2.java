import java.util.Arrays;

/**
 * 求字符串A转换为字符串B的最少编辑次数，也就是编辑距离
 *
 * 首先断定A -> B一共可以有3种操作，修改，插入，删除
 * 首先考虑找到问题的边界
 * F(A,B) = 0; len(A) == len(B) == 0;
 * F(A,B) = len(A||B) ; len(A||B) == 0
 * 令: F_1(A,B) = F(A[1:],B[1:]); A[0] == B[0] ; 该位置字符正好相等，且认为不操作该位置是最优选择
 * 令: F_2(A,B) = F(A[1:],B[1:])+1; 修改该字符
 * 令: F_3(A,B) = F(A[1:],B)+1; 删除A字符，删除B同理
 * 令: F_4(A,B) = F(A,B[1:])+1; 在A上添加字符，在B添加字符同理
 * F(A,B) = min(F_1(A,B),F_2(A,B),F_3(A,B),F_4(A,B))
 *
 * 递归模式不再阐述，让我们从边界开始往上推导，我们始终以操作A字符串为基准
 *
 * F("","") = 0; 都是空串，自然为0
 * F("","a") = 1; A是空串，B有一个，那值就是1
 * F("","ab") = 2; A是空串，B有两个，值为2
 * ....A空串的情况类似
 * F("a","x") = min(F("","x")+删除a,F("","")+修改a,F("a","")+在A上插入);
 */
class DP2 {

	public static void main(String[] args) {

		String str1 = "abc";
		String str2 = "abcde";

		char[] A = str1.toCharArray();
		char[] B = str2.toCharArray();

		// dp[i][j]代表的是长度为i的字符串A和长度为j的字符串B的编辑距离
		int[][] dp = new int[A.length + 1][B.length + 1];
		// 初始化各类边界情况
		// A、B都为空串
		dp[0][0] = 0;
		// 当A为空串，编辑距离就是B的长度
		for (int i = 1; i < dp[0].length; i++) {
			dp[0][i] = i;
		}
		// 当B为空串，编辑距离就是A的长度
		for (int i = 1; i < dp.length; i++) {
			dp[i][0] = i;
		}

		// 辅助数组用于记录每次的选择（删除、修改、插入、不操作）
		String[][] choose = new String[A.length + 1][B.length + 1];

		// 边界的dp都求完了，开始计算中间行与中间列
		for (int i = 1; i < dp.length; i++) {
			for (int j = 1; j < dp[i].length; j++) {
				int insert = Math.min(dp[i - 1][j], dp[i][j - 1]) + 1;
				int modify = dp[i - 1][j - 1] + 1;
				int delete = Math.min(dp[i - 1][j], dp[i][j - 1]) + 1;
				int noChange = Integer.MAX_VALUE;
				// 注意下dp[1][1]代表的长度为1的A、B字符串的比较，也就是A[0]、B[0]
				if (A[i - 1] == B[j - 1]) {
					noChange = dp[i - 1][j - 1];
				}
				dp[i][j] = minAction(insert, modify, delete, noChange);
			}
		}
		print(dp);
		System.out.println("字符串编辑距离：" + dp[A.length][B.length]);
	}

	public static int minAction(int insert, int modify, int delete, int noChange) {
		int min = noChange;
		if (min > insert) {
			min = insert;
		}
		if (min > modify) {
			min = modify;
		}
		if (min > delete) {
			min = delete;
		}
		return min;
	}

	public static void print(int[][] dp) {
		for (int i = 0; i < dp.length; i++) {
			int[] arr = dp[i];
			for (int j = 0; j < arr.length; j++) {
				System.out.print(arr[j] + ",");
			}
		}
		System.out.println();
	}

}