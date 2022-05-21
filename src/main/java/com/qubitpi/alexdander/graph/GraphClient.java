package com.qubitpi.alexdander.graph;

import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface GraphClient {

    @NotNull
    Optional<EntityFlowGraph> getGraphBySessionId(@NotNull String sessionId);

    void saveOrUpdate(@NotNull EntityFlowGraph graph, @NotNull String sessionId);
}
