package by.anastacia.dijkstra;

import java.util.Comparator;

public class TempMarkComparator implements Comparator<Vertex> {
    @Override
    public int compare(Vertex o1, Vertex o2) {
        return o1.getTempMark() - o2.getTempMark();
    }
}
