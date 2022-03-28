package io.pocket.base.jdbc.db.meta.index;

/**
 * Index meta data.
 */
public final class IndexMetaData {

    private String name;

	public IndexMetaData(String name) {
		super();
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "IndexMetaData [name=" + name + "]";
	}
}
