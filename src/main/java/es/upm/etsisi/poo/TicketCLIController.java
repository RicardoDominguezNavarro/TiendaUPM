package es.upm.etsisi.poo;
import java.util.ArrayList;

public class TicketCLIController {
    private final TicketControl ticketControl;

    public TicketCLIController(TicketControl ticketControl) {
        this.ticketControl = ticketControl;
    }

    public void handleCommand(String line, String[] split) {
        if (split.length < 2){
            System.out.println("Invalid command");
            return;
        }
        String action = split[1];
        switch (action) {
            case "new":
                handleNewTicket(split);
                break;
            case "add":
                handleAddProduct(split);
                break;
            case "remove":
                handleRemoveProduct(split);
                break;
            case "print":
                if (split.length != 4) {
                    System.out.println("Invalid ticket print command. Usage: ticket print <ticketId> <cashId>");
                    return;
                }
                String ticketIdPrint = split[2];
                String cashIdPrint = split[3];
                User cashPrint = ticketControl.findUserById(cashIdPrint);
                if (!(cashPrint instanceof Cash)) {
                    System.out.println("Cashier with id: " + cashIdPrint + " doesn't exist.");
                    return;
                }
                ticketControl.printTicket(ticketIdPrint, cashIdPrint);
                break;

            case "list":
                if (split.length != 2) {
                    System.out.println("Invalid ticket list command. Usage: ticket list");
                    return;
                }
                System.out.println(ticketControl.listTicket());
                break;

            default:
                System.out.println("Unknown ticket command");
                break;
        }
    }

    private void handleNewTicket(String[] split) {
        String userId;
        String cashId;
        String ticketId = null;
        String type = "-p"; // Por defecto
        int effectiveLength = split.length;
        if (split.length > 0) {
            String lastArg = split[split.length - 1];
            if (lastArg.equals("-c") || lastArg.equals("-p") || lastArg.equals("-s")) {
                type = lastArg;
                effectiveLength--;
            }
        }
        if (effectiveLength == 4) { // ticket new <cashId> <userId>
            cashId = split[2];
            userId = split[3];
        } else if (effectiveLength == 5) { // ticket new <ticketId> <cashId> <userId>
            ticketId = split[2];
            cashId = split[3];
            userId = split[4];
        } else {
            System.out.println("Invalid ticket new command. Usage: ticket new [<ticketId>] <cashId> <userId> [-c/-p/-s]");
            return;
        }
        User userNew = ticketControl.findUserById(userId);
        User cashNew = ticketControl.findUserById(cashId);
        if (cashNew == null || !(cashNew instanceof Cash)) {
            System.out.println("Cashier with id: " + cashId + " doesn't exist.");
            return;
        }
        if (userNew == null) {
            System.out.println("User with id: " + userId + " doesn't exist.");
            return;
        }
        ticketControl.newTicket(userId, cashId, ticketId, type);
    }

    private void handleAddProduct(String[] split) {
        if (split.length < 5) {
            System.out.println("Invalid ticket add command. Usage: ticket add <ticketId><cashId> <prodId> <amount> [--p<txt>]");
            return;
        }
        String ticketIdAdd = split[2];
        String cashIdAdd = split[3];
        String prodIdStr = split[4];
        int amount = 1;
        int nextIndex = 5;
        if(split.length > 5){
            try {
                amount = Integer.parseInt(split[5]);
                nextIndex = 6;
            } catch (NumberFormatException e) {
                System.out.println("Amount must be a positive number.");
                return;
            }
        }

        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }
        User cashAdd = ticketControl.findUserById(cashIdAdd);
        if (!(cashAdd instanceof Cash)) {
            System.out.println("Cashier with id: " + cashIdAdd + " doesn't exist.");
            return;
        }
        ArrayList<String> personalizations = new ArrayList<>();
        for (int i = nextIndex; i < split.length; i++) {
            String param = split[i];
            if (param.startsWith("--p")) {
                String text = param.substring(3);
                personalizations.add(text);
            }
        }
        ticketControl.addProductToTicket(ticketIdAdd, cashIdAdd, prodIdStr, amount, personalizations);
    }

    private void handleRemoveProduct(String[] split) {
        if (split.length != 5) {
            System.out.println("Invalid ticket remove command. Usage: ticket remove <ticketId> <cashId> <prodId>");
            return;
        }
        String ticketIdRemove = split[2];
        String cashIdRemove = split[3];
        String prodIdRemove = split[4]; // Idem, mejor String

        User cashRemove = ticketControl.findUserById(cashIdRemove);
        if (!(cashRemove instanceof Cash)) {
            System.out.println("Cashier with id: " + cashIdRemove + " doesn't exist.");
            return;
        }

        ticketControl.removeProduct(ticketIdRemove, cashIdRemove, prodIdRemove);
    }
}
