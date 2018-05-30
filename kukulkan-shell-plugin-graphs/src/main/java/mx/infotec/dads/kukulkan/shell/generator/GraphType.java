package mx.infotec.dads.kukulkan.shell.generator;

/**
 * Graphs
 * 
 * @author Clara Fragoso
 *
 */
public enum GraphType {

    /**
     * Box plot Chart.
     */
    BOX_PLOT("BOX_PLOT"),

    /**
     * Bullet Chart
     */
    BULLET("BULLET"),

    /**
     * Candlestick Bar Chart
     */
    CANDLESTICK("CANDLESTICK"),

    /**
     * Cumulative Line Chart
     */
    CUMULATIVE_LINE("CUMULATIVE_LINE"),

    /**
     * Discrete bar Chart
     */
    DISCRETE_BAR("DISCRETE_BAR"),

    /**
     * Donut Chart (Pie)
     */
    DONUT_CHART("DONUT_CHART"),

    /**
     * Force Directed Chart
     */
    FORCE_DIRECTED("FORCE_DIRECTED"),

    /**
     * Historical Bar Chart
     */
    HISTORICAL_BAR("HISTORICAL_BAR"),

    /**
     * Line Chart
     */
    LINE_CHART("LINE_CHART"),

    /**
     * Line with Focus Chart
     */
    LINE_FOCUS("LINE_FOCUS"),

    /**
     * Multi Bar Horizontal Chart
     */
    MULTIBAR_HORIZONTAL("MULTIBAR_HORIZONTAL"),

    /**
     * Multi Bar Chart
     */
    MULTIBAR("MULTIBAR"),

    /**
     * Multi Chart
     */
    MULTI_CHART("MULTI_CHART"),

    /**
     * OHCL Chart
     */
    OHCL("OHCL"),

    /**
     * Paralell Chart
     */
    PARALELL("PARALELL"),

    /**
     * Pie Chart
     */
    PIE_CHART("PIE_CHART"),

    /**
     * Scatter Line Chart
     */
    SCATTER_LINE("SCATTER_LINE"),

    /**
     * Scatter Chart
     */
    SCATTER("SCATTER"),

    /**
     * Spark Line Chart
     */
    SPARK_LINE("SPARK_LINE"),

    /**
     * Stacked Area Chart
     */
    STACKED_AREA("STACKED_AREA"),

    /**
     * Sunburst Chart
     */
    SUNBURST("SUNBURST"),

    /**
     * All Charts
     */
    ALL("ALL");


    public final String brandname;
    GraphType(String brand) {
        this.brandname = brand;
    }

    @Override
    public String toString(){
        return brandname;
    }

}

