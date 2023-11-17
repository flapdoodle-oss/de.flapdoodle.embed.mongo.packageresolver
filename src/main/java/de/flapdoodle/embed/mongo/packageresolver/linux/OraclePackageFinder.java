package de.flapdoodle.embed.mongo.packageresolver.linux;

import de.flapdoodle.os.linux.OracleVersion;
import de.flapdoodle.os.linux.RedhatVersion;
import de.flapdoodle.types.Pair;

public class OraclePackageFinder extends AbstractVersionMappedPackageFinder<OracleVersion, RedhatVersion> {
	protected OraclePackageFinder(RedhatPackageFinder delegate) {
		super(delegate,
			Pair.of(OracleVersion.Oracle_6, RedhatVersion.Redhat_6),
			Pair.of(OracleVersion.Oracle_7, RedhatVersion.Redhat_7),
			Pair.of(OracleVersion.Oracle_8, RedhatVersion.Redhat_8),
			Pair.of(OracleVersion.Oracle_9, RedhatVersion.Redhat_9)
		);
	}
}
