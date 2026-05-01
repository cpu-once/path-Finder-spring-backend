package com.coralstay.core.math.graph;

/**
 * 범용 그래프의 노드(정점)를 나타내는 인터페이스입니다.
 * Spring 의존성 없이 순수 Java로만 구성됩니다.
 *
 * @param <T> 노드가 담고 있는 데이터의 타입 (식별자 역할)
 */
public interface Node<T> {

    /**
     * 노드의 고유 식별자 또는 데이터를 반환합니다.
     */
    T getId();
}
