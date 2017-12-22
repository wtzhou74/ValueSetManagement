package valueset.model.modelView;

public class RxnormGraph {

	private Vertex vertexList[]; // list of vertices  
    private int adjMat[][]; // adjacency matrix  
  
    private int nVerts;  
    private static int MAX_VERTS = 7; // n个点  
  
    int i = 0;  
    int j = 0;  
  
    public Vertex[] getVertexList() {  
        return vertexList;  
    }  
  
    public int[][] getAdjMat() {  
        return adjMat;  
    }  
  
    public int getN() {  
        return MAX_VERTS;  
    }  
  
    public RxnormGraph(int index) {  
        adjMat = new int[MAX_VERTS][MAX_VERTS]; // 邻接矩阵  
        vertexList = new Vertex[MAX_VERTS]; // 顶点数组  
        nVerts = 0;  
  
        for (i = 0; i < MAX_VERTS; i++) {  
            for (j = 0; j < MAX_VERTS; j++) {  
                adjMat[i][j] = 0;  
            }  
        }  
  
        addVertex('A');  
        addVertex('B');  
        addVertex('C');  
        addVertex('D');  
        addVertex('E');  
        addVertex('F');  
        addVertex('G');  
  
        addEdge(0, 1);  
        addEdge(0, 2);  
        addEdge(1, 4);  
        addEdge(2, 0);  
        addEdge(2, 5);  
        addEdge(3, 0);  
        addEdge(3, 2);  
        addEdge(3, 3);  
        addEdge(4, 1);  
        addEdge(4, 2);  
        addEdge(5, 6);  
        addEdge(6, 3);  
  
        switch (index) {  
        case 0:  
            break;  
        case 1:  
            delEdge(4, 2);  
            break;  
        default:  
            break;  
        }  
    }  
  
    private void delEdge(int start, int end) {  
        adjMat[start][end] = 0;  
    }  
  
    private void addEdge(int start, int end) {// 有向图，添加边  
        adjMat[start][end] = 1;  
        // adjMat[end][start] = 1;  
    }  
  
    public void addVertex(char lab) {  
        vertexList[nVerts++] = new Vertex(lab);// 添加点  
    }  
  
    public char displayVertex(int i) {  
        return vertexList[i].getLabel();  
    }  
  
    public boolean displayVertexVisited(int i) {  
        return vertexList[i].WasVisited();  
    }  
  
    public void printGraph() {  
        for (i = 0; i < MAX_VERTS; i++) {  
            System.out.print("第" + displayVertex(i) + "个节点:" + " ");  
  
            for (j = 0; j < MAX_VERTS; j++) {  
                System.out.print(displayVertex(i) + "-" + displayVertex(j)  
                        + "：" + adjMat[i][j] + " ");  
            }  
            System.out.println();  
        }  
  
    }  
  
}  

