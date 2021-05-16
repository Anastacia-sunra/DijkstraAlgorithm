package by.anastacia.dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

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

    /*
     * Основной метод, котрый без рекурсии(!) выполняет всю работу, расставляя метки
     */
    public void setMarks(int start) {
        this.start = start;
        vertices = new Vertex[distanceMatrix.length];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vertex(i);
        }
        vertices[start].setConstantMark(0);
        int currentMark = 0;
        Vertex current = vertices[start];
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<>(new TempMarkComparator());
        do {
            int[] distances = distanceMatrix[current.getId()];
            for (int i = 0; i < distances.length; i++) {
                if (i != current.getId() && distances[i] != Vertex.MAX && vertices[i].getConstantMark() == Vertex.MAX) {
                    vertexQueue.remove(vertices[i]);
                    vertices[i].checkPredecessor(current, distances[i]);
                    vertexQueue.add(vertices[i]);
                }
            }
            current = vertexQueue.poll();
            if (current != null) {
                currentMark = current.getTempMark();
                current.setConstantMark(currentMark);
                current.setBestPredecessor(current.getTempPredecessor());
            }
        } while (current != null);
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