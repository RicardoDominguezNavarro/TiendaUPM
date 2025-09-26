package es.upm.etsisi.poo;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

    private void menuPrincipal(Scanner scanner) {
        // Muestra el menú principal y gestiona la entrada del usuario para dirigirlo a la opción seleccionada
        scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\n--- Commands ---");
            System.out.println("1. prod add <id> \"<name>\" <category> <price>\n" +
                    " 2. prod list\n" +
                    " 3. prod update <id> NAME|CATEGORY|PRICE <value>\n" +
                    " 4. prod remove <id>\n" +
                    " 5. ticket new\n" +
                    " 6. ticket add <prodId> <quantity>\n" +
                    " 7. ticket remove <prodId>\n" +
                    " 8. ticket print\n" +
                    " 9. help\n" +
                    " 10. exit");
            System.out.println(" ");
            int option = Control.readNumber(scanner, "Select an option", 1, 10);
            switch (option) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
                case 7:

                    break;
                case 8:

                    break;
                case 9:

                    break;
                case 10:

                    break;
                default:
                    System.out.println("Please select a valid option");
            }
        }

    }
}
