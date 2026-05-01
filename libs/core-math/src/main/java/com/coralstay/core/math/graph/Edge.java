package com.coralstay.core.math.graph;

/**
 * 범용 그래프의 간선(연결선)을 나타내는 인터페이스입니다.
 * 두 노드 사이의 관계성, 방향성, 가중치를 표현합니다.
 *
 * @param <N> 간선이 연결하는 노드의 타입
 * @param <W> 간선의 가중치 타입 (예: 기간, 비용 등)
 */
public interface Edge<N extends Node<?>, W> {

    /**
     * 출발 노드를 반환합니다.
     */
    N getSource();

    /**
     * 도착 노드를 반환합니다.
     */
    N getTarget();

    /**
     * 간선의 가중치나 메타데이터(소요시간, 비용, 관계 종류)를 반환합니다.
     */
    W getWeight();
}
