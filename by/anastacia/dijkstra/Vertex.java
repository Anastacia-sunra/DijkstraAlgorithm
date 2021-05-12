package by.anastacia.dijkstra;

public class Vertex {
    /**
     * Условная бесконечность
     */
    public static int MAX = Integer.MAX_VALUE;

    private final int id;
    private int constantMark;
    private int tempMark;
    private Vertex bestPredecessor;
    private Vertex tempPredecessor;

    public Vertex(int id) {
        this.id = id;
        constantMark = MAX;
        tempMark = MAX;
    }

    public int getId() {
        return id;
    }

    public int getConstantMark() {
        return constantMark;
    }

    public void setConstantMark(int constantMark) {
        this.constantMark = constantMark;
    }

    public int getTempMark() {
        return tempMark;
    }

    public Vertex getBestPredecessor() {
        return bestPredecessor;
    }

    public void setBestPredecessor(Vertex bestPredecessor) {
        this.bestPredecessor = bestPredecessor;
    }

    public Vertex getTempPredecessor() {
        return tempPredecessor;
    }
    /**
     * @param vertex
     * @param distance
     * Метод устанавивает новую временную метку и нового предшественника, если значение новой временной метки меньше предыдущего
     */
    public void checkPredecessor(Vertex vertex, int distance) {
        if (vertex.getConstantMark() + distance < tempMark && constantMark == MAX) {
            tempPredecessor = vertex;
            tempMark = vertex.getConstantMark() + distance;
        }
    }
}
