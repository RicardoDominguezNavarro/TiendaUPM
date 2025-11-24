package es.upm.etsisi.poo;

import java.time.Duration;
import java.time.LocalDateTime;

public class Events extends Product{
    private LocalDateTime expirationDate;
    private final int maxParticipants = 100;
    private EventType eventType;

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


    public Events (int id, String name, double price, LocalDateTime expirationDate, EventType eventType){
        super(id, name, price, null);
        this.expirationDate = expirationDate;
        this.eventType = eventType;
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

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public double pricePerPerson(int numParticipants) {
        double price = getPrice() * numParticipants;
        return price;
    }

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

    @Override
    public String toString() {
        return "{class:" + eventType.name() +
                ", id:" + getId_product() +
                ", name:'" + getName() +
                "', price:" + getPrice() +
                ", date of Event:" + expirationDate.toLocalDate() +
                ", max people allowed:" + maxParticipants +
                ", actual people in event:"+
                "}";
    }

//metodo toString, validación de fechas y calcular precio por persona

}