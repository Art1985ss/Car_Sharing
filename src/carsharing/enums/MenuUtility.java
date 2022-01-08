package carsharing.enums;

import java.util.List;

/**
 * Util class to be used on Enums that represents different menus
 */
public class MenuUtility {

    /**
     * @param values enum values
     * @return formatted {@link String string} output that can be used to display menu to the user
     */
    public static <T> String getFormatted(final List<T> values) {
        final StringBuilder sb = new StringBuilder();
        for (T value : values) {
            sb.append(value).append("\n");
        }
        return sb.toString();
    }
}
