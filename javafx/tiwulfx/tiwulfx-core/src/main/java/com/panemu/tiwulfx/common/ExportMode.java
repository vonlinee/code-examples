package com.panemu.tiwulfx.common;

/**
 * Options of {@link com.panemu.tiwulfx.table.TableControl TableControl} export mechanism.
 * @author amrul
 */
public enum ExportMode {
    /**
     * Default. Export only data currently displayed on {@link com.panemu.tiwulfx.table.TableControl TableControl}
     */
    CURRENT_PAGE,
    /**
     * Export data from all pages. It is dangerous as it could lead to Out of Memory error.
     */
    ALL_PAGES
}
