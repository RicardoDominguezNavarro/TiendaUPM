package es.upm.etsisi.poo;

public class CashierCLIController {
    private final TicketControl ticketControl;

    public CashierCLIController(TicketControl ticketControl) {
        this.ticketControl = ticketControl;
    }

    public void handleCommand(String line, String[] split) {
        if (split.length < 2) return;
        String action = split[1];
        switch (action) {
            case "add":
                String name = CommandUtils.getName(line);
                if (name == null) {
                    System.out.println("The name can't be empty");
                    return;
                }
                String[] args = CommandUtils.getAfterName(line);
                if (args.length < 1) {
                    System.out.println("Error: missing email");
                    return;
                }
                String email = args[0];
                if (!validateEmail(email)) {
                    System.out.println("Invalid email format.");
                    return;
                }

                int firstQuote = line.indexOf('"');
                String beforeName = line.substring(0, firstQuote).trim();
                String[] partBeforeName = beforeName.split(" ");
                String id = null;
                if (partBeforeName.length >= 3) {
                    id = split[2];
                }
                ticketControl.addCashier(id, name, email);
                break;
            case "remove":
                if (split.length < 3) {
                    System.out.println("Invalid cash remove command. Usage: cash remove <id>");
                    return;
                }
                ticketControl.removeCashier(split[2]);
                break;
            case "list":
                ticketControl.listCashiers();
                break;
            case "tickets":
                if(split.length < 3) {
                    System.out.println("Invalid command. Usage: cash tickets <id>");
                    return;
                }
                ticketControl.listCashierTickets(split[2]);
                break;
            default:
                System.out.println("Unknown cash command");
                break;
        }
    }

    private boolean validateEmail(String email) {
        return email != null && email.contains("@") && email.indexOf("@") < email.length() - 1 && email.substring(email.indexOf("@") + 1).contains(".");
    }
}
