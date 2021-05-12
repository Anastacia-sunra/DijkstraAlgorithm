package by.anastacia.dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DijkstraGraph {
    /**
     *  Матрица, показывающая длины дуг от каждой вершины до любой другой, включая ее саму (в этом случае длина 0)
     *  Если вершина не имеет непосредственной связи с другой, расстояние устанавливается в значение MAX
     */
    private final int[][] distanceMatrix;
    /**
     * Массив вершин. Инициализируется в конструкторе
     */
    private Vertex[] vertices;
    /**
     * Номер начальной вершины маршрута
     */
    private int start;

    /**
     * @param distanceMatrix
     * Конструктор, инициальзирующий массив вершин
     */
    public DijkstraGraph(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
        vertices = new Vertex[distanceMatrix.length];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vertex(i);
        }
        start = -1;
    }

    /**
     * @param start - Номер начальной вершины
     *  Метод устанавливает конечные постоянные метки для всех вершин графа, кроме недостижимых, если они имеются
     */
    public void fixStart(int start) {
        vertices = new Vertex[distanceMatrix.length];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vertex(i);
        }
        this.start = start;
        vertices[start].setConstantMark(0);
        Vertex current = vertices[start];
        buildPaths(current);
    }

    /**
     * Рекурсивный метод, строящий маршрут от текущей вершины
     * @param current - текущая вершина
     */
    private void buildPaths(Vertex current) {
        int[] distances = distanceMatrix[current.getId()];
        for (int i = 0; i < distances.length; i++) {
            if (i != current.getId() && distances[i] != Vertex.MAX) {
                vertices[i].checkPredecessor(current, distances[i]);
            }
        }
        Vertex newCurrent = findMin();
        if (newCurrent == null) {
            return;
        }

        newCurrent.setConstantMark(newCurrent.getTempMark());
        newCurrent.setBestPredecessor(newCurrent.getTempPredecessor());
        buildPaths(newCurrent);
    }

    /**
     * @return Возвращает вершину с минимальной временной меткой. Возвращает null, если у всех вершин установлены
     * конечные постоянные метки, кроме недостижимых вершин, если они имеются
     */
    private Vertex findMin() {
        int minMark = Vertex.MAX;
        int index = -1;
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i].getConstantMark() == Vertex.MAX && vertices[i].getTempMark() < minMark) {
                minMark = vertices[i].getTempMark();
                index = i;
            }
        }
        if (index != -1) {
            return vertices[index];
        } else {
            return null;
        }
    }

    /**
     * @param end - Номер последней вершины маршрута
     * @return Список вершин кратчайшего маршрута в обратном порядке
     *
     */
    public List<Vertex> getPathBack(int end) {
        List<Vertex> path = new ArrayList<>();
        path.add(vertices[end]);
        if (start != -1 && start != end) {
            int current = end;
            while (vertices[current].getBestPredecessor() != null) {
                current = vertices[current].getBestPredecessor().getId();
                path.add(vertices[current]);
            }
        }
        return path;
    }

    /**
     * @param end - Номер последней вершины маршрута
     * @return Список вершин кратчайшего маршрута в прямом порядке
     *
     */
    public List<Vertex> getPathForward(int end) {
        List<Vertex> list = getPathBack(end);
        Collections.reverse(list);
        return list;
    }
}
