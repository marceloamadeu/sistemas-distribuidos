package br.edu.utfpr.util;

public class Util {

    // Define color constants
    public static final String TEXT_RESET = "\u001B[0m";
    public static final String TEXT_BLACK = "\u001B[30m";
    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_GREEN = "\u001B[32m";
    public static final String TEXT_YELLOW = "\u001B[33m";
    public static final String TEXT_BLUE = "\u001B[34m";
    public static final String TEXT_PURPLE = "\u001B[35m";
    public static final String TEXT_CYAN = "\u001B[36m";
    public static final String TEXT_WHITE = "\u001B[37m";

    public Util() {

    }

    /**
     * Client - Clean, refresh and print the console
     */
    public void cleanRefreshPrintClientHeader() {
        // Limpa o console
        System.out.print("\033[H\033[2J");
        System.out.flush();
        // Header
        System.out.println(TEXT_BLUE + "#################################################################");
        System.out.println(TEXT_BLUE + "####            Publisher / Subscriber - " + TEXT_YELLOW + "Cliente" + TEXT_BLUE + "            ####");
        System.out.println(TEXT_BLUE + "#################################################################");
        System.out.println(TEXT_RESET +" ");
    }

    /**
    * Server - Clean, refresh and print the console
    */
    public void cleanRefreshPrintServerHeader() {
        // Limpa o console
        System.out.print("\033[H\033[2J");
        System.out.flush();
        // Header
        System.out.println(TEXT_BLUE + "#################################################################");
        System.out.println(TEXT_BLUE + "####            Publisher / Subscriber - " + TEXT_RED + "Servidor" + TEXT_BLUE + "            ####");
        System.out.println(TEXT_BLUE + "#################################################################");
        System.out.println(TEXT_RESET +" ");
    }



}