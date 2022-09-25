package com.majastachowiak;

import java.util.*;
import java.util.stream.Collectors;

public class Rankings {

    Scanner scanner = new Scanner(System.in);
    private List<MountainPeak> listOfTrips = new ArrayList<>();
    int sumOfTrips;

    public void addTrip() {

        System.out.print("Wpisz nazwę szczytu: ");
        String name = (scanner.next()) + scanner.nextLine();
        System.out.print("Wpisz wysokość szczytu w metrach: ");
        int height = scanner.nextInt();
        System.out.print("Wpisz przewyższenie w metrach: ");
        int verticalGain = scanner.nextInt();
        System.out.print("Wpisz przebytą odległość km,m (*użyj przecinka oddzielając kilometry od metrów): ");
        float distance = scanner.nextFloat();
        System.out.println();

        MountainPeak mountainPeak = new MountainPeak(name, height, verticalGain, distance);
        listOfTrips.add(mountainPeak);
        sumOfTrips++;
    }

    public void displayAllTrips() {

        if (listOfTrips.isEmpty()) {
            System.out.println("Lista póki co jest pusta :( Śmigaj w góry!\n");
        } else {
            for (MountainPeak s : listOfTrips) {
                s.displayTrip();
            }
        }

    }  public void displayRankings() {

        System.out.println("Ilość zdobytych szczytów: " + sumOfTrips);
        int sumVerticalGain= listOfTrips.stream().collect(Collectors.summingInt(s->s.getVerticalGain()));
        System.out.println("Suma przebytych przewyższeń: " + sumVerticalGain + " metrów");
        double sumDistance= listOfTrips.stream().collect(Collectors.summingDouble(s->s.getDistance()));
        System.out.println("Suma przebytych kilometrów: " + (String.format("%.2f", sumDistance)) + " kilometrów");
        System.out.println();

        if (listOfTrips.isEmpty()) {
            System.out.println("Uuuuu ale bida... Czas ruszyć dupeczkę!\n");
        } else {

        List<MountainPeak> listHighestPeaks= listOfTrips.stream()
                .sorted(Comparator.comparingInt(MountainPeak::getHeight)
                .reversed())
                .limit(3)
                .collect(Collectors.toList());

        System.out.println("Lista trzech najwyższych szczytów: ");
        for (MountainPeak s : listHighestPeaks) {
            s.displayHighestRanking();

        }
        System.out.println();

        List<MountainPeak> listGreatestVerticalGains= listOfTrips.stream()
                .sorted(Comparator.comparingInt(MountainPeak::getVerticalGain)
                .reversed())
                .limit(3)
                .collect(Collectors.toList());

        System.out.println("Lista trzech tras z największym przebytym przewyższeniem: ");
        for (MountainPeak s : listGreatestVerticalGains) {
            s.displayVerticalGainRanking();

        }
        System.out.println();

        List<MountainPeak> listGreatestDistances= listOfTrips.stream()
                .sorted(Comparator.comparingDouble(MountainPeak::getDistance)
                .reversed())
                .limit(3)
                .collect(Collectors.toList());


        System.out.println("Lista trzech najdłuższych przebytych tras: ");
        for (MountainPeak s: listGreatestDistances) {
            s.displayDistanceRanking();

        }
        System.out.println();

            List<MountainPeak> mostDifficultTrip= listOfTrips.stream()
                    .sorted(Comparator.comparingInt(MountainPeak::getVerticalGain)
                    .thenComparingDouble(MountainPeak::getDistance)
                    .thenComparingInt(MountainPeak::getHeight)
                    .reversed())
                    .limit(1)
                    .collect(Collectors.toList());

            System.out.println();

            System.out.println("Najtrudniejszy szczyt: ");

            for (MountainPeak s: mostDifficultTrip) {
               s.mostDifficultTrip();

            }
            System.out.println();

        } }
}

