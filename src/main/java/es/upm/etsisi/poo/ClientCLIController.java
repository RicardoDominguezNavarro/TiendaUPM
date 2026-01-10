package es.upm.etsisi.poo;

public class ClientCLIController {
    private final TicketControl ticketControl;

    public ClientCLIController(TicketControl ticketControl) {
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
                if (args.length < 3) {
                    System.out.println("Invalid client add command. Usage: client add \"<nombre>\" <DNI/NIF> <email> <cashId>");
                    return;
                }
                String idDoc = args[0];
                String email = args[1];
                String cashId = args[2];
                if (!validateEmail(email)) {
                    System.out.println("Invalid email format.");
                    return;
                }

                ticketControl.addClient(name, idDoc, email, cashId);
                break;

            case "remove":
                if (split.length < 3) {
                    System.out.println("Invalid client remove command. Usage: client remove <DNI>");
                    return;
                }
                ticketControl.removeClient(split[2]);
                break;
            case "list":
                ticketControl.listClients();
                break;
            default:
                System.out.println("Unknown client command");
                break;
        }
    }

    private boolean validateEmail(String email) {
        return email != null && email.contains("@") && email.indexOf("@") < email.length() - 1 && email.substring(email.indexOf("@") + 1).contains(".");
    }

}


