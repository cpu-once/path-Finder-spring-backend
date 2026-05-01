package com.coralstay.core.math.graph;

import java.util.Collection;

/**
 * 범용 그래프 자료구조를 나타내는 인터페이스입니다.
 *
 * @param <N> 그래프를 구성하는 노드 타입
 * @param <E> 그래프를 구성하는 간선 타입
 */
public interface Graph<N extends Node<?>, E extends Edge<N, ?>> {

    /**
     * 그래프에 포함된 모든 노드를 반환합니다.
     */
    Collection<N> getNodes();

    /**
     * 그래프에 포함된 모든 간선을 반환합니다.
     */
    Collection<E> getEdges();

    /**
     * 특정 노드의 모든 인접(연결된) 간선을 반환합니다.
     */
    Collection<E> getAdjacentEdges(N node);
}
