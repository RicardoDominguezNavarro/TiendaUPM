package es.upm.etsisi.poo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class ProductCLIController {

    private final Catalog catalog;

    public ProductCLIController(Catalog catalog) {
        this.catalog = catalog;
    }

    public void handleCommand(String line, String[] split) {
        if(split.length < 2) {
            System.out.println("Invalid prod command");
            return;
        }
        String action = split[1];
        switch (action) {
            case "list":
                catalog.prodList();
                break;
            case "remove":
                if (split.length < 3) {
                    System.out.println("Invalid prod remove command. Usage: prod remove <id>");
                    break;
                }
                catalog.prodRemove(split[2]);
                break;
            case "update":
                if (split.length < 5) {
                    System.out.println("Invalid prod update command. Usage: prod update <id> <field> <value>");
                    return;
                }
                String idToUpdate = split[2];
                String field = split[3];
                String value = String.join(" ", Arrays.copyOfRange(split, 4, split.length));
                catalog.updateProd(idToUpdate, field, value);
                break;
            case "add":
                if (split.length >= 3 && split[2].matches("\\d{4}-\\d{2}-\\d{2}")) {
                    handleAddService(split);
                } else {
                    handleAddStandardProduct(line);
                }
                break;
            case "addFood":
                handleAddEventProduct(line, action);
                break;
            case "addMeeting":
                handleAddEventProduct(line, action);
                break;
            default:
                System.out.println("Unknown prod command");
                break;
        }

    }

    private void handleAddService(String[] split) {
            try {
                if (split.length < 4){
                    System.out.println("Usage: prod add <yyyy-MM-dd> <CATEGORY>");
                    return;
                }
            String dateStr = split[2];
            String categoryStr = split[3].toUpperCase();
            LocalDate expDate = LocalDate.parse(dateStr);
            Service.Category category;
            try {
                category = Service.Category.valueOf(categoryStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: '" + categoryStr + "' is not a valid SERVICE category.");
                System.out.println("Valid service categories are: TRANSPORT, SHOWS, INSURANCE");
                return;
            }
            Service newService = new Service(category,expDate);
            catalog.addProd(newService);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format for Service expected yyyy-MM-dd.");
        } catch (Exception e) {
            System.out.println("Error adding service: " + e.getMessage());
        }
    }

    private void handleAddStandardProduct(String line) {
        String name = CommandUtils.getName(line);
        if (name == null || name.isEmpty()) {
            System.out.println("The name can't be empty and must be between quotation marks ");
            return;
        }
        try {
            String[] args = CommandUtils.getAfterName(line);
            int firstQuote = line.indexOf('"');
            String beforeName = line.substring(0, firstQuote).trim();
            String[] partBeforeName = beforeName.split(" ");

            if (args.length >= 2) {
                String id;
                if (partBeforeName.length < 3) {
                    id = String.valueOf(catalog.generateId());
                } else {
                    id = partBeforeName[2];
                }
                Category category = Category.valueOf(args[0].toUpperCase());
                double price = Double.parseDouble(args[1]);
                if (args.length > 2) { // es personalizado
                    int maxPers = Integer.parseInt(args[2]);
                    PersonalizedProduct productPersonalize = new PersonalizedProduct(id, name, price, category, maxPers);
                    catalog.addProd(productPersonalize);
                } else {
                    StandardProduct product = new StandardProduct(id, name, price, category);
                    catalog.addProd(product);
                }
            } else {
                System.out.println("The category or price is missing.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric value for id or price.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid category or parameters.");
        }
    }

    private void handleAddEventProduct(String line, String action) {
        String name = CommandUtils.getName(line);
        if (name == null) {
            System.out.println("The name can't be empty");
            return;
        }
        String[] args = CommandUtils.getAfterName(line);
        int firstQuote = line.indexOf('"');
        String beforeName = line.substring(0, firstQuote).trim();
        String[] partBeforeName = beforeName.split(" ");
        try {
            if (args.length == 3) {
                String id;
                if (partBeforeName.length < 3) {
                    id = String.valueOf(catalog.generateId());
                } else {
                    id = partBeforeName[2];
                }
                double price = Double.parseDouble(args[0]);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate expirationDate = LocalDate.parse(args[1], formatter);
                LocalDateTime expirationDateTime = expirationDate.atStartOfDay();
                int maxPeopleAllowed = Integer.parseInt(args[2]);
                if (action.equals("addFood")) {
                    Events foodEvent = new Events(id, name, price, expirationDateTime, Events.EventType.FOOD, maxPeopleAllowed);
                    catalog.addProd(foodEvent);
                } else if (action.equals("addMeeting")) {
                    Events meetingEvent = new Events(id, name, price, expirationDateTime, Events.EventType.MEETING, maxPeopleAllowed);
                    catalog.addProd(meetingEvent);
                }
            } else {
                System.out.println("The category, price or expiration date are missing.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric value for id or price.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error processing ->prod " + action + " ->" + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Expected format: yyyy-MM-dd");
        }
    }
}
