package learning.graph;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Stream;

@Slf4j
public class GraphTest {

    @Test
    public void testSimpleGraph() {
        innerTestGraph(new SimpleGraph());
    }

    private void innerTestGraph(Graph graph) {
        Assert.assertEquals(0, graph.size());

        Stream.of("0", "1", "2", "3", "4", "5", "6")
                .map(SimpleGraph.SimpleVertex::new)
                .forEach(graph::addVertex);

        Assert.assertEquals(7, graph.size());

        /*
            0
         /.   .\
        1   -   2
        |.  .\  |.
        3   -   4
         */
        graph.addEdge(SimpleGraph.SimpleEdge.directed(0, 1));
        graph.addEdge(SimpleGraph.SimpleEdge.nonDirected(2, 1));
        graph.addEdge(SimpleGraph.SimpleEdge.directed(2, 0));
        graph.addEdge(SimpleGraph.SimpleEdge.directed(1, 3));
        graph.addEdge(SimpleGraph.SimpleEdge.directed(4, 1));
        graph.addEdge(SimpleGraph.SimpleEdge.nonDirected(3, 4));
        graph.addEdge(SimpleGraph.SimpleEdge.directed(2, 4));

        /*
          0
         /
        1 - 2
            |
        3 - 4
         */
        Graph mst = graph.mst();
        Assert.assertEquals(5, mst.size());
    }
}
