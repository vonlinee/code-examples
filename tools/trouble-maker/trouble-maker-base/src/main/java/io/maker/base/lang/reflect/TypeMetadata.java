package io.maker.base.lang.reflect;

public abstract class TypeMetadata {

	private final TypeMetadata holder;

	protected TypeMetadata(Object target) {
		this.holder = new TypeMetadataHolder(target);
	}
	
	private class TypeMetadataHolder extends TypeMetadata {

		private Class<?> typeClass;
		
		protected TypeMetadataHolder(Object target) {
			super(target);
		}
	}
}