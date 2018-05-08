/**
 * 求有向带权图G=(V,E)中某个顶点到另一个顶点的最短路径问题
 *
 * 从顶点j到k到最短路径，有两种选择，要么直接到达，要么借助n个节点到达
 * 假设只允许借助0号节点到达，那么可以更新掉借助0之后更短到路径数据
 * 关键是借助0、1到达，此时借助0的结果已经产生，借助0、1依赖于0
 *
 *
 * @author charpty
 * @since 2018/5/8
 */
public class Floyd {

	// 我们总是从0开始给顶点编号
	private static final int N = 9;
	private static final int[][] E = new int[N][N];

	private static int UNREACHABLE = Integer.MAX_VALUE / 3;

	static {
		// 初始化边集合
		initE();
	}

	public static void main(String[] args) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				for (int k = 0; k < N; k++) {
					if (E[j][k] > E[i][k] + E[j][i]) {
						E[j][k] = E[i][k] + E[j][i];
					}
				}
			}
		}
		System.out.println("最短路径 [1]->[0]: " + E[1][0]);
		System.out.println("最短路径 [0]->[2]: " + E[0][2]);
		System.out.println("最短路径 [0]->[5]: " + E[0][5]);
		System.out.println("最短路径 [1]->[6]: " + E[1][6]);
	}

	private static void initE() {
		// 设定顶点之间的关系
		for (int i = 0; i < E.length; i++) {
			for (int j = 0; j < E[i].length; j++) {
				E[i][j] = UNREACHABLE;
			}
		}
		E[0][1] = 1;
		E[0][2] = 5;
		E[1][3] = 2;
		E[1][2] = 1;
		E[2][4] = 4;
		E[3][4] = 4;
		E[4][5] = 2;
		E[3][6] = 1;
		E[3][5] = 1;
		E[6][4] = 1;
		E[6][7] = 3;
		E[7][8] = 2;
		E[8][0] = 6;
	}
}
