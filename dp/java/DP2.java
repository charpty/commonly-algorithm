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

	private static final String ACTION_NO_CHANGE = "不修改";
	private static final String ACTION_INSERT_A = "在A中插入";
	private static final String ACTION_INSERT_B = "在B中插入";
	private static final String ACTION_MODIFY = "修改";
	private static final String ACTION_DELETE_A = "删除当前A";
	private static final String ACTION_DELETE_B = "删除当前B";

	public static void main(String[] args) {

		String str1 = "abc";
		String str2 = "aoooo";

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

				// 用了许多变量为了说明逻辑语义
				int insertA = dp[i][j - 1] + 1;
				int insertB = dp[i - 1][j] + 1;
				int modify = dp[i - 1][j - 1] + 1;
				int deleteA = dp[i - 1][j] + 1;
				int deleteB = dp[i][j - 1] + 1;
				int noChange = Integer.MAX_VALUE;
				// 注意下dp[1][1]代表的长度为1的A、B字符串的比较，也就是A[0]、B[0]
				if (A[i - 1] == B[j - 1]) {
					noChange = dp[i - 1][j - 1];
				}

				// 比较取出最小值并记录选择结果，比较简单且冗长就单独提出，考虑扩展性所以使用数组
				int[] values = new int[] { noChange, insertA, insertB, modify, deleteA, deleteB };
				String[] actions = new String[] { ACTION_NO_CHANGE, ACTION_INSERT_A, ACTION_INSERT_B, ACTION_MODIFY, ACTION_DELETE_A, ACTION_DELETE_B };
				Object[] r = minAction(values, actions);
				dp[i][j] = (int) r[0];
				choose[i][j] = (String) r[1];
			}
		}
		printAction(choose, A.length, B.length);
		System.out.println("字符串编辑距离：" + dp[A.length][B.length]);
	}

	//
	public static Object[] minAction(int[] values, String[] actions) {
		Object[] result = new Object[2];
		result[0] = values[0];
		result[1] = actions[0];

		for (int i = 1; i < values.length; i++) {
			if (values[i] < (int) result[0]) {
				result[0] = values[i];
				result[1] = actions[i];
			}
		}
		return result;
	}

	// 反向推导出选择路径，为了简单直接使用ACTION内容作为判断依据
	public static void printAction(String[][] dp, int lenA, int lenB) {
		String[] actions = new String[lenA > lenB ? lenA : lenB];
		actions[0] = dp[lenA][lenB];

		// 可以用个map规定下逻辑
		for (int i = 1; i < actions.length; i++) {
			if (lenA == 0 && lenB == 0) {
				break;
			}
			String prev = actions[i - 1];
			switch (prev) {
			case ACTION_NO_CHANGE:
			case ACTION_MODIFY:
				actions[i] = dp[--lenA][--lenB];
				break;
			case ACTION_INSERT_A:
			case ACTION_DELETE_B:
				actions[i] = dp[lenA][--lenB];
				break;
			case ACTION_INSERT_B:
			case ACTION_DELETE_A:
				actions[i] = dp[--lenA][lenB];
				break;
			}
		}

		for (int i = (actions.length - 1); i >= 0; i--) {
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