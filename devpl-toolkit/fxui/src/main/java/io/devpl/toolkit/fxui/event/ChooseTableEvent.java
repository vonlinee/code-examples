package io.devpl.toolkit.fxui.event;

import io.devpl.toolkit.fxui.model.props.ConnectionConfig;
import lombok.Data;

@Data
public class ChooseTableEvent {

	private String databaseName;
	private String tableName;
	
	private ConnectionConfig connectionInfo;

	public ChooseTableEvent(String databaseName, String tableName) {
		super();
		this.databaseName = databaseName;
		this.tableName = tableName;
	}
}
