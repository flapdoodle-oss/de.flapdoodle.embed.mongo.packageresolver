package de.flapdoodle.embed.mongo.packageresolver.linux;

import de.flapdoodle.embed.mongo.packageresolver.Command;
import de.flapdoodle.os.linux.AlmaVersion;
import de.flapdoodle.os.linux.RedhatVersion;
import org.junit.jupiter.api.Test;

class AlmaPackageFinderTest extends AbstractVersionMappedPackageFinderTest<AlmaVersion, RedhatVersion> {

	public AlmaPackageFinderTest() {
		super(new AlmaPackageFinder(new RedhatFallbackToOlderVersionPackageFinder(new RedhatPackageFinder(Command.MongoD))));
	}

	@Test
	public void almaToRedHatMapping() {
		assertMappedVersion(AlmaVersion.Alma_8, RedhatVersion.Redhat_8);
		assertMappedVersion(AlmaVersion.Alma_9, RedhatVersion.Redhat_9);
		assertMappedVersion(AlmaVersion.Alma_10, RedhatVersion.Redhat_10);
	}
}
