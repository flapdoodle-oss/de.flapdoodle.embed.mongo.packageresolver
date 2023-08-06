/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Martin Jöhren <m.joehren@googlemail.com>
 *
 * with contributions from
 * 	konstantin-ba@github,Archimedes Trajano	(trajano@github)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.embed.mongo.packageresolver.parser.st;

import org.stringtemplate.v4.AttributeRenderer;

import java.util.Locale;

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
