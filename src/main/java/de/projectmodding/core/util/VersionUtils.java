package de.projectmodding.core.util;

import java.util.List;

public class VersionUtils {
    private VersionUtils(){}

    public static String getOldestVersion(List<String> versionList) {

        String oldestVersion = "";

        for(String version : versionList) {
            if (oldestVersion.isBlank())
                oldestVersion = version;
            else {
                VersionSegment oldestVersionSegment = getVersionSegment(oldestVersion);
                VersionSegment currentVersionSegment = getVersionSegment(version);

                if (oldestVersionSegment.major >= currentVersionSegment.major &&
                        oldestVersionSegment.minor >= currentVersionSegment.minor &&
                        oldestVersionSegment.patch > currentVersionSegment.patch) {
                    oldestVersion = currentVersionSegment.toString();
                }
            }
        }

        return oldestVersion;

    }

    private static VersionSegment getVersionSegment(String version){
        final int MAJOR_INDEX = 0;
        final int MINOR_INDEX = 1;
        final int PATCH_INDEX = 2;

        String[] versionSegment = version.split("\\.");
        VersionSegment versionSegmentResult = new VersionSegment();
        int major = 0;
        int minor = 0;
        int patch = 0;

        if (versionSegment.length > MAJOR_INDEX)
            major = MathUtils.tryParseInt(versionSegment[MAJOR_INDEX], 0);
        if (versionSegment.length > MINOR_INDEX)
            minor = MathUtils.tryParseInt(versionSegment[MINOR_INDEX], 0);
        if (versionSegment.length > PATCH_INDEX)
            patch = MathUtils.tryParseInt(versionSegment[PATCH_INDEX], 0);

        versionSegmentResult.major = major;
        versionSegmentResult.minor = minor;
        versionSegmentResult.patch = patch;
        return versionSegmentResult;
    }

    private static class VersionSegment {
        public int major;
        public int minor;
        public int patch;

        @Override
        public String toString() {
            return String.valueOf(major)
                    .concat(".")
                    .concat(String.valueOf(minor))
                    .concat(".")
                    .concat(String.valueOf(patch));
        }
    }
}
