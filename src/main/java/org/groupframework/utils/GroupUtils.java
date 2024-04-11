package org.groupframework.utils;

import org.groupframework.group.Group;

public final class GroupUtils {

    private GroupUtils() {
        throw new IllegalStateException("Utility class not to be instantiated");
    }

    public static boolean isEmpty(Group<?> group) {
        return group == null || group.isEmpty();
    }

    public static boolean isNotEmpty(Group<?> group) {
        return !isEmpty(group);
    }
}