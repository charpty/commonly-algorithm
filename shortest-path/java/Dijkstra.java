/**
 * 求有向带权图G=(V,E)中某个顶点到另一个顶点的最短路径问题
 *
 * @author charpty
 * @since 2018/5/5
 */
public class Dijkstra {

	// 我们总是从0开始给顶点编号
	private static final int N = 5;
	private static final int[][] E = new int[N][N];

	private static int UNREACHABLE = Integer.MAX_VALUE;

	static {
		// 初始化边集合
		initE();
	}

	public static void main(String[] args) {
		// 求顶点x到顶点y的距离
		int x = 0, y = 4;

		int dist[] = new int[E[x].length];
		for (int i = 0; i < E[x].length; i++) {
			dist[i] = E[x][i];
		}

		int[] inUSet = new int[N];
		inUSet[0] = 1;

		for (int c = 1; c < N; c++) {
			int min = UNREACHABLE;
			int index = -1;
			for (int i = 0; i < dist.length; i++) {
				if (dist[i] < min && inUSet[i] == 0) {
					min = dist[i];
					index = i;
				}
			}
			inUSet[index] = 1;
			// 新加入的节点总会引起新的路径变化
			for (int i = 0; i < E[index].length; i++) {
				if (E[index][i] < UNREACHABLE) {
					int newPath = dist[index] + E[index][i];
					if (newPath < dist[i]) {
						dist[i] = newPath;
					}
				}
			}
		}
		System.out.println("最短路径大小：" + dist[y]);
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
		E[2][4] = 2;
		E[3][4] = 3;
		E[1][2] = 1;
	}
}
