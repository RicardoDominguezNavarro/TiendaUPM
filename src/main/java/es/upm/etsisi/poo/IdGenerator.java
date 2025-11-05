package es.upm.etsisi.poo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public final class IdGenerator {
    private static final Random RANDOM = new Random();
    private static final DateTimeFormatter TICKET_DATE_FMT = DateTimeFormatter.ofPattern("yy-MM-dd-HH:mm");

    //falta lo del numero aleatorio
    public static String generateTicketIdOpen() {
        return TICKET_DATE_FMT.format(LocalDateTime.now());
    }



    //NOTA: solo crea un candidato CashService tiene q verificar si existe ya un id asi o no

    public static String generateCashIdCandidate() {
        int num = RANDOM.nextInt(10_000_000);
        return String.format("UW%07d", num);
    }

}
