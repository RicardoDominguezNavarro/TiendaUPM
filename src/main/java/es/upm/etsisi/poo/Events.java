package es.upm.etsisi.poo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;


/**
 * Represents a product that is time-bound and based on participant capacity,
 * such as Food events or Meetings.
 * It extends the base {@code Product} class but calculates its price per person.
 */
public class Events extends Product{
    /**
     * The date and time when the event product expires or takes place.
     */
    private LocalDateTime expirationDate;
    /**
     * The maximum capacity allowed for any event of this type (fixed at 100).
     */
    private final int maxParticipants = 100;
    /**
     * The specific maximum number of people allowed for this concrete event instance.
     */
    private int maxPeopleAllowed;
    /**
     * The number of people/participants currently associated with this event in a ticket.
     */
    private int actualPeople;
    /**
     * The specific type of the event (FOOD or MEETING).
     */
    private EventType eventType;
    /**
     * The base price charged per participant.
     */
    private double pricePerPerson;


    /**
     * Defines the types of events and their minimum required planning time.
     */
    public enum EventType {
        FOOD(72), MEETING(12);
        final private int minPlanningHours;
        EventType (int hours){
            minPlanningHours = hours;
        }

        public int getMinPlanningHours() {
            return minPlanningHours;
        }
    }


    /**
     * Constructs a new Events product for the catalog.
     * The base price is set to 0.0 as the cost is calculated per person.
     *
     * @param id The unique identifier of the product.
     * @param name The name of the event.
     * @param pricePerPerson The base price per individual participant.
     * @param expirationDate The date and time the event expires/occurs.
     * @param eventType The type of the event (FOOD or MEETING).
     * @param maxPeopleAllowed The maximum number of participants allowed for this instance.
     * @throws IllegalArgumentException if {@code maxPeopleAllowed} exceeds {@code maxParticipants}.
     */
    public Events (String id, String name, double pricePerPerson, LocalDateTime expirationDate, EventType eventType, int maxPeopleAllowed) {
        super(id, name, 0.0, null); //precio base es 0, luego se calculará por persona
        if(maxPeopleAllowed > maxParticipants){
            throw new IllegalArgumentException("Error adding product: Capacity " + maxPeopleAllowed + " exceeds limit of  " + maxParticipants);
        }
        this.expirationDate = expirationDate;
        this.eventType = eventType;
        this.pricePerPerson = pricePerPerson;
        this.maxPeopleAllowed = maxPeopleAllowed;
        this.actualPeople = 0;
    }

    /**
     * Copy constructor used when adding the event to a ticket.
     * It calculates the total price based on the number of participants.
     *
     * @param other The existing {@code Events} product from the catalog.
     * @param peopleAmount The number of participants for this specific ticket item.
     */
    public Events(Events other, int peopleAmount) {
        super(other.getId_product(), other.getName(), other.pricePerPerson * peopleAmount, null);
        this.expirationDate = other.expirationDate;
        this.eventType = other.eventType;
        this.pricePerPerson = other.pricePerPerson;
        this.maxPeopleAllowed = other.maxPeopleAllowed;
        this.actualPeople = other.actualPeople;
    }

    /**
     * Gets the expiration date and time of the event.
     * @return The event's {@code LocalDateTime} expiration date.
     */
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    /**
     * Gets the absolute maximum number of participants for any event.
     * @return The maximum capacity (100).
     */
    public int getMaxParticipants() {
        return maxParticipants;
    }

    /**
     * Gets the specific type of the event (FOOD or MEETING).
     * @return The {@code EventType}.
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Sets a new expiration date for the event.
     * @param expirationDate The new {@code LocalDateTime} expiration date.
     */
    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Sets a new event type.
     * @param eventType The new {@code EventType}.
     */
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    /**
     * Calculates the total price for a given number of participants based on the price per person.
     * @param numParticipants The number of participants.
     * @return The total price for all participants.
     */
    public double pricePerPerson(int numParticipants) {
        double price = getPrice() * numParticipants;
        return price;
    }

    /**
     * Checks if the event's expiration date allows for the minimum required planning time
     * as defined by its {@code EventType}.
     *
     * @return {@code true} if the event date is valid (far enough in the future), {@code false} otherwise.
     */
    public boolean validDate() {
        boolean validDate = false;
        int minHours = getEventType().getMinPlanningHours();
        LocalDateTime actualTime = LocalDateTime.now();
        LocalDateTime expirationDate = getExpirationDate();
        if (Duration.between(actualTime, expirationDate).toHours() >= minHours) {
            validDate = true;
        }
        return validDate;
    }

    /**
     * Returns a string representation of the event product.
     * The format differs based on whether it is a catalog item (0 actual people) or a ticket item (actual people > 0).
     *
     * @return A formatted string with event details.
     */
    @Override
    public String toString() {
        String dateStr = expirationDate.toLocalDate().toString();
        if (actualPeople == 0) { //Formato de catalogo
            return String.format(Locale.US,
                    "{class:%s, id:%d, name:'%s', price:%.1f, date of Event:%s, max people allowed:%d}",
                    eventType == EventType.FOOD ? "Food" : "Meeting",
                    getId_product(), getName(), getPrice(), dateStr, maxPeopleAllowed);
        }else {// Formato Ticket (con 'actual people')
            return String.format(Locale.US,
                    "{class:%s, id:%d, name:'%s', price:%.1f, date of Event:%s, max people allowed:%d, actual people in event:%d}",
                    eventType == EventType.FOOD ? "Food" : "Meeting",
                    getId_product(), getName(), getPrice(), dateStr, maxPeopleAllowed, actualPeople);
        }
    }
}