package de.flapdoodle.embed.mongo.packageresolver.parser.st;

import org.stringtemplate.v4.AttributeRenderer;

import java.util.Locale;
import java.util.function.BiFunction;

public class TypedAttributeRenderer<T> implements AttributeRenderer {
	private final Class<T> type;
	private final TypedRenderer<T> renderer;

	public TypedAttributeRenderer(Class<T> type, TypedRenderer<T> renderer) {
		this.type = type;
		this.renderer = renderer;
	}

	@Override
	public String toString(Object o, String format, Locale locale) {
		return renderer.toString(type.cast(o), format, locale);
	}

	interface TypedRenderer<T> {
		String toString(T value, String format, Locale locale);
	}
}
