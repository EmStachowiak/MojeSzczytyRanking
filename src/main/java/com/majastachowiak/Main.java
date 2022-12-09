package com.majastachowiak;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        StandardServiceRegistry ssr= new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();

        Metadata metadata = new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Scanner scanner= new Scanner(System.in);boolean shouldContinue= true;
        Rankings rankings= new Rankings();

        System.out.println("\n \t \t \t \t \t \t \t  *** Witaj w aplikacji MOJE SZCZYTY RANKING! ***");
        System.out.println("*********************************************************************************************************");

        try {
            while (shouldContinue) {
                System.out.println("Wybierz opcję: 1.Dodaj wycieczkę 2. Wyświetl wszystkie wycieczki 3. Wyświetl rankingi 4. Skasuj wycieczkę lub całą listę 5. Wyjdź z programu");

                int userChoice1 = scanner.nextInt();
                System.out.println();
                switch (userChoice1) {
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
                        System.out.println("1. Skasuj jedną wycieczkę 2. resetuj listę wycieczek");
                        int userChoice2 = scanner.nextInt();
                        if (userChoice2 == 1) {
                            System.out.print("Wpisz ID wycieczki którą chcesz skasować: ");
                            int id = scanner.nextInt();
                            rankings.deleteOneTrip(id);
                        } else if (userChoice2 ==2){
                            rankings.clearList();
                        } else {
                            System.out.println("Niepoprawny wybór, spróbuj ponownie.");
                        }
                        break;
                    }
                    case 5: {
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
            if(transaction != null) transaction.rollback();
        } catch (Exception e) {
            System.out.println("Error: other. Koniec działania programu, uruchom go ponownie i zastosuj się do instrukcji.");
            if(transaction != null) transaction.rollback();
        } finally {
            sessionFactory.close();
            session.close();
            scanner.close();
        }

        }
    }
