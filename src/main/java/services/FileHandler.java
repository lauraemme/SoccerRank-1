package services;

import utils.Game;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class FileHandler {
    public static  Set<Game> list = new HashSet<>();
    public static  ArrayList<Game> sortedList = new ArrayList<>();

    public static void readFile() {
        final String GAME_PATH = "src/main/resources/soccerlist.csv";
        String line;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(GAME_PATH));
            line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] teamLine = line.split(";");

                Game games = new Game(
                        teamLine[0],
                        teamLine[1],
                        (Integer.parseInt(teamLine[2])),
                        (Integer.parseInt(teamLine[3])),
                         LocalDate.parse(teamLine[4],dateTimeFormatter));
                list.add(games);
                //                   System.out.println(games);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
     public static void sortedList(){
        sortedList.addAll(list);
        sortedList.sort(Comparator.comparing(Game::getDateAndTime)
                .thenComparing(Game::getHomeTeamName).
                thenComparing(Game::getVisitorTeamName));
    }
    
    public static void separateByTeams() {
        list.stream()
                .collect(Collectors.groupingBy(Game::getHomeTeamName))
                .forEach((team,game) -> {
                    try (PrintWriter printWriter = new PrintWriter(
                            new FileOutputStream("src/main/resources/"+team+".csv"))){
                        printWriter.println(
                                "Time: " + team
                        );
                        printWriter.println(
                                "Jogos: " + game
                        );
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }

}
