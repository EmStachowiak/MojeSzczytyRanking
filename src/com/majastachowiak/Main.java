package com.majastachowiak;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner= new Scanner(System.in);

        System.out.println("\n \t \t \t \t \t \t \t  *** Witaj w aplikacji MOJE SZCZYTY RANKING! ***");
        System.out.println("*********************************************************************************************************");

        boolean shouldContinue= true;
        Rankings rankings= new Rankings();

        try {
            while (shouldContinue) {
                System.out.println("Wybierz opcję: 1.Dodaj wycieczkę 2. Wyświetl wszystkie wycieczki 3. Wyświetl rankingi 4. Wyjdź z programu" + "\n");

                int userChoice = scanner.nextInt();
                switch (userChoice) {
                    case 1: {
                        rankings.addTrip();
                        break;
                    }
                    case 2: {
                        rankings.displayAllTrips();
                        break;
                    }
                    case 3: {
                        rankings.displayRankings();
                        break;
                    }
                    case 4: {
                        shouldContinue=false;
                        break;
                    }
                    default: {
                        System.out.println("Spróbuj ponownie: ");
                        break;
                    }
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: InputMismatchException. Koniec działania programu, uruchom go ponownie i zastosuj się do instrukcji.");
        } catch (Exception e) {
            System.out.println("Error: other. Koniec działania programu, uruchom go ponownie i zastosuj się do instrukcji.");
            }
        }
    }
