import java.time.Year;

/**
 * 求有向带权图G=(V,E)中某个顶点到另一个顶点的最短路径问题
 *
 * 设定u={源点x}，已知当前x到各个节点的距离
 * 选出离x最近的点加入到集合u中，并由于新节点的加入，可以借其通道更新x到关联节点到距离
 * 重复此操作直到u包含所有节点
 *
 * 每次选择距离最近的点加入，保证了最短计算路径总是优先计算
 *
 * @author charpty
 * @since 2018/5/5
 */
public class Dijkstra {

	// 我们总是从0开始给顶点编号
	private static final int N = 9;
	private static final int[][] E = new int[N][N];

	private static int UNREACHABLE = Integer.MAX_VALUE;

	static {
		// 初始化边集合
		initE();
	}

	public static void main(String[] args) {
		// 求顶点x到顶点y的距离
		int x = 1, y = 0;

		int dist[] = new int[E[x].length];
		for (int i = 0; i < E[x].length; i++) {
			dist[i] = E[x][i];
		}

		int[] inUSet = new int[N];
		inUSet[x] = 1;
		int[] from = new int[N];
		for (int i = 0; i < from.length; i++) {
			from[i] = x;
		}
		for (int c = 1; c < N; c++) {
			int min = UNREACHABLE;
			int index = -1;
			for (int i = 0; i < dist.length; i++) {
				if (dist[i] < min && inUSet[i] == 0) {
					min = dist[i];
					index = i;
				}
			}
			if (index < 0) {
				// 存在节点是不可达的
				break;
			}
			inUSet[index] = 1;
			// 新加入的节点总会引起新的路径变化
			for (int i = 0; i < E[index].length; i++) {
				if (E[index][i] < UNREACHABLE) {
					int newPath = dist[index] + E[index][i];
					if (newPath < dist[i]) {
						dist[i] = newPath;
						from[i] = index;
					}
				}
			}
		}
		printPath(from, x, y);
		System.out.println("最短路径大小：" + dist[y]);
	}

	private static void printPath(int[] from, int x, int y) {
		String[] actions = new String[N];
		int i = 0;
		for (; y != x; ) {
			actions[i++] = "选择[" + y + "]";
			y = from[y];
		}
		actions[i] = "从[" + x + "]号节点出发";

		for (int c = actions.length - 1; c >= 0; c--) {
			if (actions[c] != null) {
				System.out.print(actions[c]);
				if (c != 0) {
					System.out.print(" -> ");
				}
			}
		}
		System.out.println();
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
