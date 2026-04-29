package com.coralstay.pathfinderspringbackend.common;

public final class PersistenceConstants {

    private PersistenceConstants() {}

    private static final String ROOT_PACKAGE = "com.coralstay.pathfinderspringbackend";

    // --- Baseline Module ---
    public static final String BASELINE_MODULE = "baseline";
    public static final String BASELINE_PACKAGE = ROOT_PACKAGE + "." + BASELINE_MODULE;
    public static final String BASELINE_DATASOURCE_PROPERTIES = "app.datasource." + BASELINE_MODULE;
    public static final String BASELINE_ENTITY_MANAGER_FACTORY = BASELINE_MODULE + "EntityManagerFactory";
    public static final String BASELINE_TRANSACTION_MANAGER = BASELINE_MODULE + "TransactionManager";
    public static final String BASELINE_PERSISTENCE_UNIT = BASELINE_MODULE;

    // --- Field Module ---
    public static final String FIELD_MODULE = "field";
    public static final String FIELD_PACKAGE = ROOT_PACKAGE + "." + FIELD_MODULE;
    public static final String FIELD_DATASOURCE_PROPERTIES = "app.datasource." + FIELD_MODULE;
    public static final String FIELD_ENTITY_MANAGER_FACTORY = FIELD_MODULE + "EntityManagerFactory";
    public static final String FIELD_TRANSACTION_MANAGER = FIELD_MODULE + "TransactionManager";
    public static final String FIELD_PERSISTENCE_UNIT = FIELD_MODULE;

    // --- Insights Module ---
    public static final String INSIGHTS_MODULE = "insights";
    public static final String INSIGHTS_PACKAGE = ROOT_PACKAGE + "." + INSIGHTS_MODULE;
    public static final String INSIGHTS_DATASOURCE_PROPERTIES = "app.datasource." + INSIGHTS_MODULE;
    public static final String INSIGHTS_ENTITY_MANAGER_FACTORY = INSIGHTS_MODULE + "EntityManagerFactory";
    public static final String INSIGHTS_TRANSACTION_MANAGER = INSIGHTS_MODULE + "TransactionManager";
    public static final String INSIGHTS_PERSISTENCE_UNIT = INSIGHTS_MODULE;

    // --- Progress Module ---
    public static final String PROGRESS_MODULE = "progress";
    public static final String PROGRESS_PACKAGE = ROOT_PACKAGE + "." + PROGRESS_MODULE;
    public static final String PROGRESS_DATASOURCE_PROPERTIES = "app.datasource." + PROGRESS_MODULE;
    public static final String PROGRESS_ENTITY_MANAGER_FACTORY = PROGRESS_MODULE + "EntityManagerFactory";
    public static final String PROGRESS_TRANSACTION_MANAGER = PROGRESS_MODULE + "TransactionManager";
    public static final String PROGRESS_PERSISTENCE_UNIT = PROGRESS_MODULE;

    // --- Valuation Module ---
    public static final String VALUATION_MODULE = "valuation";
    public static final String VALUATION_PACKAGE = ROOT_PACKAGE + "." + VALUATION_MODULE;
    public static final String VALUATION_DATASOURCE_PROPERTIES = "app.datasource." + VALUATION_MODULE;
    public static final String VALUATION_ENTITY_MANAGER_FACTORY = VALUATION_MODULE + "EntityManagerFactory";
    public static final String VALUATION_TRANSACTION_MANAGER = VALUATION_MODULE + "TransactionManager";
    public static final String VALUATION_PERSISTENCE_UNIT = VALUATION_MODULE;
}
