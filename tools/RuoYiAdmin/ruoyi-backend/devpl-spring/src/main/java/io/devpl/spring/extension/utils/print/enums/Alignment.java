package io.devpl.spring.extension.utils.print.enums;

public enum Alignment {

    LEFT, RIGHT,

    /**
     * Indicates the elements should be aligned to the origin. For the horizontal
     * axis with a left to right orientation this means aligned to the left edge.
     * For the vertical axis leading means aligned to the top edge.
     * @see #createParallelGroup(Alignment)
     */
    LEADING,

    /**
     * Indicates the elements should be aligned to the end of the region. For the
     * horizontal axis with a left to right orientation this means aligned to the
     * right edge. For the vertical axis trailing means aligned to the bottom edge.
     * @see #createParallelGroup(Alignment)
     */
    TRAILING,

    /**
     * Indicates the elements should be centered in the region.
     * @see #createParallelGroup(Alignment)
     */
    CENTER,

    /**
     * Indicates the elements should be aligned along their baseline.
     * @see #createParallelGroup(Alignment)
     * @see #createBaselineGroup(boolean, boolean)
     */
    BASELINE
}
