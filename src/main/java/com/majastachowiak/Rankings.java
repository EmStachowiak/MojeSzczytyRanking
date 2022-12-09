package com.majastachowiak;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class Rankings {

    private Scanner scanner = new Scanner(System.in);
    private List<MountainPeak> listOfTrips = new ArrayList<>();
    int sumOfTrips;

    StandardServiceRegistry ssr= new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
    Metadata metadata = new MetadataSources(ssr).getMetadataBuilder().build();
    SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

    public void addTrip() {
        Session session =null;
        Transaction transaction = null;

        try {
            System.out.print("Wpisz nazwę szczytu: ");
            String name = scanner.nextLine();
            System.out.print("Wpisz wysokość szczytu w metrach: ");
            int height = scanner.nextInt();
            System.out.print("Wpisz przewyższenie w metrach: ");
            int verticalGain = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Wpisz przebytą odległość km,m (*użyj przecinka oddzielając kilometry od metrów): ");
            float distance = scanner.nextFloat();
            scanner.nextLine();
            System.out.println();

            MountainPeak mountainPeak = new MountainPeak(name, height, verticalGain, distance);
            listOfTrips.add(mountainPeak);
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(mountainPeak);
            transaction.commit();
            sumOfTrips++;


        } catch (Exception e) {
            e.printStackTrace();
            if(transaction != null) transaction.rollback();
        }
        session.close();
    }

    public void displayAllTrips() {
        Session session =null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            String hql = "FROM MountainPeak";
            Query query = session.createQuery(hql);
            List results = query.getResultList();
            sumOfTrips = results.size();

            if (results.isEmpty()) {
                System.out.println("Uuuuu ale słabo... Czas iść w góry!\n");
            } else {
                System.out.println("Byłeś na: " + sumOfTrips + " wycieczkach!\n");
                Iterator<MountainPeak> iterator = results.iterator();
                while (iterator.hasNext()) {
                    MountainPeak mp = iterator.next();
                    mp.displayTrip();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(transaction != null) transaction.rollback();
        }
        session.close();


    }  public void displayRankings() {
        Session session =null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            String hql = "FROM MountainPeak";
            Query query = session.createQuery(hql);
            List results = query.getResultList();
            listOfTrips = results;
            sumOfTrips = results.size();

            System.out.println("Ilość zdobytych szczytów: " + sumOfTrips);
            int sumVerticalGain = listOfTrips.stream().collect(Collectors.summingInt(MountainPeak::getVerticalGain));
            System.out.println("Suma przebytych przewyższeń: " + sumVerticalGain + " metrów");
            double sumDistance = listOfTrips.stream().collect(Collectors.summingDouble(s -> s.getDistance()));
            System.out.println("Suma przebytych kilometrów: " + (String.format("%.2f", sumDistance)) + " kilometrów");
            System.out.println();

            if (listOfTrips.isEmpty()) {
                System.out.println("Uuuuu ale słabo... Czas iść w góry!\n");
            } else {

                List<MountainPeak> listHighestPeaks = listOfTrips.stream()
                        .sorted(Comparator.comparingInt(MountainPeak::getHeight)
                                .reversed())
                        .limit(3)
                        .collect(Collectors.toList());

                System.out.println("Lista trzech najwyższych szczytów: ");
                for (MountainPeak s : listHighestPeaks) {
                    s.displayHighestRanking();
                }
                System.out.println();

                List<MountainPeak> listGreatestVerticalGains = listOfTrips.stream()
                        .sorted(Comparator.comparingInt(MountainPeak::getVerticalGain)
                                .reversed())
                        .limit(3)
                        .collect(Collectors.toList());

                System.out.println("Lista trzech tras z największym przebytym przewyższeniem: ");
                for (MountainPeak s : listGreatestVerticalGains) {
                    s.displayVerticalGainRanking();
                }
                System.out.println();

                List<MountainPeak> listGreatestDistances = listOfTrips.stream()
                        .sorted(Comparator.comparingDouble(MountainPeak::getDistance)
                                .reversed())
                        .limit(3)
                        .collect(Collectors.toList());


                System.out.println("Lista trzech najdłuższych przebytych tras: ");
                for (MountainPeak s : listGreatestDistances) {
                    s.displayDistanceRanking();

                }
                System.out.println();

                List<MountainPeak> mostDifficultTrip = listOfTrips.stream()
                        .sorted(Comparator.comparingInt(MountainPeak::getVerticalGain)
                                .thenComparingDouble(MountainPeak::getDistance)
                                .thenComparingInt(MountainPeak::getHeight)
                                .reversed())
                        .limit(1)
                        .collect(Collectors.toList());

                System.out.println("Najtrudniejszy szczyt: ");

                for (MountainPeak s : mostDifficultTrip) {
                    s.mostDifficultTrip();

                }
                System.out.println();

            }
        } catch (Exception e) {
            e.printStackTrace();
            if(transaction != null) transaction.rollback();
        }
        session.close();
    }

    public void deleteOneTrip(Integer id) {
        Session session =null;
        Transaction transaction = null;

        try {
            if (!listOfTrips.contains(id)) {
                session = sessionFactory.openSession();
                transaction = session.beginTransaction();
                MountainPeak mountainPeak = (MountainPeak) session.get(MountainPeak.class, id);
                session.delete(mountainPeak);
                transaction.commit();
                System.out.println("Wycieczka na szczyt: " + mountainPeak.getMountainPeak() + "- została skasowana.\n");
                sumOfTrips--;
            }  else {
                System.out.println("Spróbuj ponownie.");
            }
            } catch (IllegalArgumentException e) {
            System.out.println("Wycieczka o podanym ID Nie istnieje na liście, spróbuj ponownie.\n");
        }
            catch(Exception e){
                e.printStackTrace();
                if (transaction != null) transaction.rollback();
            }


    }
    public void clearList() {
        Session session =null;
        Transaction transaction = null;

        try {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        Query query = session.createQuery("DELETE FROM MountainPeak");
        query.executeUpdate();
        transaction.commit();
        listOfTrips.clear();
        System.out.println("Lista została skasowana.\n");
    } catch (Exception e) {
            e.printStackTrace();
            if(transaction != null) transaction.rollback();
        }
    }
}

