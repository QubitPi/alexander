package com.qubitpi.alexdander.analysis;

import com.qubitpi.alexdander.graph.EntityFlowGraph;
import com.qubitpi.alexdander.graph.GraphClient;
import com.qubitpi.alexdander.graph.Vertex;

import jakarta.validation.constraints.NotNull;

public interface EntityFlowAnalysis extends Runnable {

    @Override
    default void run() {
        analyze(getInitialEntityIdForSession());
    }

    Long getInitialEntityIdForSession();

    Vertex getTracedEntity(@NotNull final Long entityId);

    NextRound getNextRound(Vertex tracedEntity);

    /**
     * The implementation must be idempotent, i.e. invoking this method must return the same instance
     * @return
     */
    GraphClient getGraphClient();

    default void analyze(@NotNull final Long entityId) {
        NextRound nextRound = getNextRound(getTracedEntity(entityId));
        startNextRound(nextRound);
    }

    default void updateGraph(@NotNull final NextRound nextRound, @NotNull final String sessionId) {
        synchronized (this) {
            EntityFlowGraph existingGraph = getGraphClient()
                    .getGraphBySessionId(sessionId)
                    .orElseThrow(() -> new IllegalStateException(
                            String.format("No graph found for session '%s'", sessionId))
                    );

            nextRound.getAllVertices().forEach(existingGraph::addOneVertex);
            nextRound.getAllEdges().forEach(existingGraph::addOneEdge);

            getGraphClient().saveOrUpdate(existingGraph, sessionId);
        }
    }

    default void startNextRound(NextRound nextRound) {
        nextRound.getAllVertices().stream().map(Vertex::getId).forEach(this::analyze);
    }
}
