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

                int userChoice = scanner.nextInt();
                System.out.println();
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
                        rankings.clearList();
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
        } catch (Exception e) {
            System.out.println("Error: other. Koniec działania programu, uruchom go ponownie i zastosuj się do instrukcji.");
            }
        finally {
            sessionFactory.close();
            session.close();
            scanner.close();
        }

        }
    }
