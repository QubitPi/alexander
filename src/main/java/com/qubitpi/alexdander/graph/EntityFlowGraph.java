package com.qubitpi.alexdander.graph;

import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.Set;

public class EntityFlowGraph {

    private final Set<Vertex> vertices;
    private final Set<Edge> edges;

    public EntityFlowGraph(@NotNull final Set<Vertex> vertices, @NotNull final Set<Edge> edges) {
        this.vertices = Objects.requireNonNull(vertices, "vertices");
        this.edges = Objects.requireNonNull(edges, "edges");
    }

    public EntityFlowGraph addOneVertex(@NotNull final Vertex vertex) {
        getVertices().add(Objects.requireNonNull(vertex, "vertex"));
        return this;
    }

    public EntityFlowGraph addOneEdge(@NotNull final Edge edge) {
        getEdges().add(Objects.requireNonNull(edge, "edge"));
        return this;
    }

    private Set<Vertex> getVertices() {
        return vertices;
    }

    private Set<Edge> getEdges() {
        return edges;
    }
}
