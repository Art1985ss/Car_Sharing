package carsharing.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

import static carsharing.enums.MenuUtility.getFormatted;

/**
 * Main menu items used to create main application menu.
 *
 * @version 1.0
 */
public enum MainMenu {
    LOG_AS_MANAGER("Log in as a manager", 1),
    LOG_AS_CUSTOMER("Log in as a customer", 2),
    CREATE_CUSTOMER("Create a customer", 3),
    EXIT("Exit", 0);

    private final String text;
    private final int option;

    MainMenu(final String text, int option) {
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
    public static MainMenu getByNum(final int num) throws IllegalArgumentException {
        return Arrays.stream(values()).filter(menu -> menu.option == num).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Option number bigger then there are options"));
    }

    @Override
    public String toString() {
        return option + ". " + text;
    }
}
