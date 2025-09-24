package es.upm.etsisi.poo;

import java.util.Scanner;

public class Control {

    public static String echo(Scanner keyboard, String s) {
        System.out.print(s);
        return keyboard.nextLine();
    }

}
