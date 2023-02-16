package io.devpl.tookit.fxui.event;

import io.devpl.tookit.fxui.model.props.ConnectionInfo;
import lombok.Data;

@Data
public class ChooseTableEvent {

	private String databaseName;
	private String tableName;
	
	private ConnectionInfo connectionInfo;

	public ChooseTableEvent(String databaseName, String tableName) {
		super();
		this.databaseName = databaseName;
		this.tableName = tableName;
	}
}
