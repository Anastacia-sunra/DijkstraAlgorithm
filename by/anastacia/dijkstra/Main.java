package by.anastacia.dijkstra;

import java.util.List;

import static by.anastacia.dijkstra.Vertex.MAX;

public class Main {

  public static void main(String[] args) {

    /**
     *  Создается матрица, показывающая длины дуг от каждой вершины до любой другой, включая ее саму (в этом случае длина 0)
     *  Если вершина не имеет непосредственной связи с другой, расстояние устанавливается в значение MAX
     */
    int[][] arcs = {
      {0, 4, 1, MAX, 2, MAX, MAX},
      {MAX, 0, MAX, 3, MAX, MAX, 2},
      {MAX, 6, 0, 5, 3, MAX, MAX},
      {MAX, 2, MAX, 0, MAX, 5, 4},
      {MAX, MAX, 4, MAX, 0, 4, MAX},
      {MAX, MAX, MAX, MAX, MAX, 0, 6},
      {MAX, MAX, MAX, MAX, MAX, MAX, 0}
    };

    /**
     * Инициализируется граф с заданными длинами дуг
     */
    DijkstraGraph dijkstraGraph = new DijkstraGraph(arcs);

    /**
     *  Присваивает постоянные метки
     */
    dijkstraGraph.setMarks(0);

    /**
     *  Находится путь от начальной до установленной конечной точки
     */
    List<Vertex> path = dijkstraGraph.getPathForward(6);

    /**
     *  Отображается длина пути и промежуточные вершины
     */
    showPath(path);
  }

  /**
   * @param path
   * Метод отображает решение в консоли
   */
  private static void showPath(List<Vertex> path) {
    int fullDistance = path.get(path.size() - 1).getConstantMark();
    String distance = fullDistance == MAX ? "Unreachable Point" : String.valueOf(fullDistance);
    System.out.println(
        "Full Distance from Vertex No."
            + path.get(0).getId()
            + " to Vertex No."
            + path.get(path.size() - 1).getId()
            + " = "
            + distance);
    System.out.print("Path: ");
    if (fullDistance == MAX) {
      System.out.println("No way");
    } else {
      for (Vertex vertex : path) {
        System.out.print("Vertex No." + vertex.getId() + "  ");
      }
    }
  }
}
