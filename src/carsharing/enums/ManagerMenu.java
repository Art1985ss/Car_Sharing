package carsharing.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

import static carsharing.enums.MenuUtility.getFormatted;

/**
 * Menu items used to create manager menu.
 *
 * @version 1.0
 */
public enum ManagerMenu {
    COMPANY_LIST("Company list", 1),
    CREATE_COMPANY("Create a company", 2),
    BACK("Back", 0);

    private final String text;
    private final int option;

    ManagerMenu(final String text, int option) {
        this.text = text;
        this.option = option;
    }

    public static String asMenu() {
        return getFormatted(Arrays.stream(values()).collect(Collectors.toList()));
    }

    /**
     * @param num user selected menu number
     * @return enum corresponding to user number
     * @throws IllegalArgumentException if user selected number bigger then number of enum values
     */
    public static ManagerMenu getByNum(final int num) throws IllegalArgumentException {
        return Arrays.stream(values()).filter(menu -> menu.option == num).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Option number bigger then there are options"));
    }

    @Override
    public String toString() {
        return option + ". " + text;
    }
}
