package es.upm.etsisi.poo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;


public class Events extends Product{

    private LocalDateTime expirationDate;
    private final int maxParticipants = 100;
    private int maxPeopleAllowed;
    private int actualPeople;
    private EventType eventType;
    private double pricePerPerson;


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

    public Events(Events other, int peopleAmount) {
        super(other.getId_product(), other.getName(), other.pricePerPerson * peopleAmount, null);
        if (peopleAmount > other.maxPeopleAllowed) {
            throw new IllegalArgumentException("Error: Capacity " + other.maxPeopleAllowed + " exceeded by request of " + peopleAmount);
        }
        this.expirationDate = other.expirationDate;
        this.eventType = other.eventType;
        this.pricePerPerson = other.pricePerPerson;
        this.maxPeopleAllowed = other.maxPeopleAllowed;
        this.actualPeople = peopleAmount;
    }


    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }
    public int getMaxParticipants() {
        return maxParticipants;
    }
    public EventType getEventType() {
        return eventType;
    }


    public boolean validDate() {
        int minHours = getEventType().getMinPlanningHours();
        LocalDateTime actualTime = LocalDateTime.now();
        if (Duration.between(actualTime, expirationDate).toHours() >= minHours) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String dateStr = expirationDate.toLocalDate().toString(); // yyyy-MM-dd
        String typeStr;
        if (eventType == EventType.FOOD) {
            typeStr = "Food";
        } else {
            typeStr = "Meeting";
        }
        if (actualPeople == 0) {
            return String.format(Locale.US,
                    "{class:%s, id:%s, name:'%s', price:%.1f, date of Event:%s, max people allowed:%d}",
                    typeStr, getId_product(), getName(), getPrice(), dateStr, maxPeopleAllowed);
        } else {
            return String.format(Locale.US,
                    "{class:%s, id:%s, name:'%s', price:%.1f, date of Event:%s, max people allowed:%d, actual people in event:%d}",
                    typeStr, getId_product(), getName(), getPrice(), dateStr, maxPeopleAllowed, actualPeople);
        }
    }
}