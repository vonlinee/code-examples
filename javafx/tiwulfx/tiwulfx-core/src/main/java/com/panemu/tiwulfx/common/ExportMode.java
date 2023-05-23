/*
 * Copyright (C) 2018 Panemu.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
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
