package graph.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 邻接表
 */
class AdjacencyList extends ArrayList<List<Link>> {

    void addLink(Link link)
    {
        int src = link.src;
        List<Link> links = get(src);
        if (links == null) {
            links = new ArrayList<>();
            set(src, links);
        }
        links.add(link);
    }

}