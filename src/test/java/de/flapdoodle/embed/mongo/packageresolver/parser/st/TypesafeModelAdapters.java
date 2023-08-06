package de.flapdoodle.embed.mongo.packageresolver.parser.st;

import org.stringtemplate.v4.STGroup;

import java.util.Optional;

public class TypesafeModelAdapters {
	public static TypesafeModelAdapter<Optional> optionals() {
		return TypesafeModelAdapter.of(Optional.class, (o, name) -> {
			if (name.equals("is")) {
				return o.isPresent();
			}
			if (name.equals("get")) {
				return o.get();
			}
			throw new IllegalArgumentException("unknown property: '"+name+"' - use 'is' or 'get'");
		});
	}

	public static void registerDefaults(STGroup group) {
		optionals().register(group);
	}
}
