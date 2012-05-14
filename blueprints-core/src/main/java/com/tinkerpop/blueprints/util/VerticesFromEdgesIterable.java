package com.tinkerpop.blueprints.util;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

import java.util.Iterator;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class VerticesFromEdgesIterable implements Iterable<Vertex> {

    private final Iterable<Edge> iterable;
    private final Direction direction;
    private final Vertex vertex;

    public VerticesFromEdgesIterable(final Vertex vertex, final Direction direction, final String... labels) {
        this.direction = direction;
        this.vertex = vertex;
        this.iterable = vertex.getEdges(direction, labels);
    }

    public Iterator<Vertex> iterator() {
        return new Iterator<Vertex>() {
            final Iterator<Edge> itty = iterable.iterator();

            public void remove() {
                this.itty.remove();
            }

            public boolean hasNext() {
                return this.itty.hasNext();
            }

            public Vertex next() {
                if (direction.equals(Direction.OUT)) {
                    return this.itty.next().getInVertex();
                } else if (direction.equals(Direction.IN)) {
                    return this.itty.next().getOutVertex();
                } else {
                    final Edge edge = this.itty.next();
                    if (edge.getInVertex().equals(vertex))
                        return edge.getOutVertex();
                    else
                        return edge.getInVertex();
                }
            }
        };
    }
}
