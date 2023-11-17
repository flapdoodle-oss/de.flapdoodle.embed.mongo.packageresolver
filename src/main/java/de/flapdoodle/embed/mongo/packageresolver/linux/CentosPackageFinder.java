package de.flapdoodle.embed.mongo.packageresolver.linux;

import de.flapdoodle.os.linux.CentosVersion;
import de.flapdoodle.os.linux.OracleVersion;
import de.flapdoodle.os.linux.RedhatVersion;
import de.flapdoodle.types.Pair;

public class CentosPackageFinder extends AbstractVersionMappedPackageFinder<CentosVersion, RedhatVersion> {
	protected CentosPackageFinder(RedhatPackageFinder delegate) {
		super(delegate,
			Pair.of(CentosVersion.CentOS_6, RedhatVersion.Redhat_6),
			Pair.of(CentosVersion.CentOS_7, RedhatVersion.Redhat_7),
			Pair.of(CentosVersion.CentOS_8, RedhatVersion.Redhat_8),
			Pair.of(CentosVersion.CentOS_9, RedhatVersion.Redhat_9)
		);
	}
}
