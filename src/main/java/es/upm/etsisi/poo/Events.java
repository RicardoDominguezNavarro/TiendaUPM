package es.upm.etsisi.poo;

import java.time.LocalDateTime;

public class Events extends Product{
    private LocalDateTime expirationDate;
    private final int maxParticipants = 100;
    private int minPlanningHours;   //12 para reunión, 72 (3días) para comidas
    private EventType eventType;

    public enum EventType {
        COMIDA, REUNION
    }


    public Events (int id, String name, double price, LocalDateTime expirationDate, int maxParticipants,
                   int minPlanningHours, EventType eventType){
        super(id, name, price, null);
        this.expirationDate = expirationDate;
        this.minPlanningHours = minPlanningHours;
        this.eventType = eventType;
    }
    public int getMinPlanningHours() {
        return minPlanningHours;
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

    public void setMinPlanningHours(int minPlanningHours) {
        this.minPlanningHours = minPlanningHours;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    //metodo toString, validación de fechas y calcular precio por persona

}
