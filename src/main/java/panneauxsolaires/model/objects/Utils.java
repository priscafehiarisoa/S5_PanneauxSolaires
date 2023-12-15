package panneauxsolaires.model.objects;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Utils {

    public static Date stringToDate(String dateString) throws ParseException {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        java.util.Date utilDate = sdf.parse(dateString);
        return new Date(utilDate.getTime());
    }
    public static LocalDate convertToLocalDate(String dateString) {
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(dateString, formatter);
    }
    public static LocalTime convertToTime(String timeString) {
        String pattern = "HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalTime DL;
        try{
           DL= LocalTime.parse(timeString, formatter);
        }catch (Exception e){
            DL= LocalTime.parse(timeString+":00", formatter);
        }
        return DL;
    }
    public static LocalTime addMinuteHour(LocalTime time, int minutes ){
        long totalSeconds = (long) (minutes * 60L);
        LocalTime finalTime = time.plus(totalSeconds, ChronoUnit.SECONDS);
        return finalTime;
    }

    public static LocalTime getMinutes(LocalTime localTime,double minutesAndSeconds){
        long minutes = (long) minutesAndSeconds;
        double remainingSeconds = (minutesAndSeconds - minutes) * 60;

        // Ajouter les minutes à l'heure initiale
        LocalTime finalTime = localTime.plus(minutes, ChronoUnit.MINUTES);

        // Ajouter les secondes à l'heure finale
        finalTime = finalTime.plus((long) remainingSeconds, ChronoUnit.SECONDS);
        return finalTime;
    }


//    public double dichotomie (int ){
//
//    }

    public static double dichotomie ( double nombreRecherche){
        double intervale1= 0 ;
        double intervalle2 = 200;
        double milieu=0;
        int i =0;

        while ((intervalle2-intervale1)> 0.00001){
            milieu= (intervale1+intervalle2)/2;
            System.out.println(milieu+" "+nombreRecherche);
            if(milieu>nombreRecherche){
                intervalle2=milieu;
            }
            else if(milieu<nombreRecherche){
                intervale1=milieu;
            }
            if(milieu==nombreRecherche){
                return nombreRecherche;
            }
            i++;
        }
        return 0;
    }

    public static long localTimeToSeconds(LocalTime localTime) {
        if (localTime == null) {
            throw new IllegalArgumentException("LocalTime cannot be null");
        }

        // Extraire les heures, minutes et secondes
        int hours = localTime.getHour();
        int minutes = localTime.getMinute();
        int seconds = localTime.getSecond();

        // Convertir en secondes
        return hours * 3600 + minutes * 60 + seconds;
    }



    public static void main(String[] args) throws ParseException {

//        LocalTime initialTime = LocalTime.of(12, 0, 0);
//
//        // Double représentant les minutes et les secondes
//        double minutesAndSeconds = 52.83;
//
//        // Extraire les minutes et les secondes
//        long minutes = (long) minutesAndSeconds;
//        double remainingSeconds = (minutesAndSeconds - minutes) * 60;
//
//        // Ajouter les minutes à l'heure initiale
//        LocalTime finalTime = initialTime.plus(minutes, ChronoUnit.MINUTES);
//
//        // Ajouter les secondes à l'heure finale
//        finalTime = finalTime.plus((long) remainingSeconds, ChronoUnit.SECONDS);
//
//        System.out.println(finalTime);
//

        String dateString = "2023-12-13";

        // Convertir la chaîne en objet LocalDate
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);

        // Obtenir le jour de la semaine
        DayOfWeek jourDeLaSemaine = date.getDayOfWeek();

        // Afficher le résultat
        System.out.println("La date " + dateString + " correspond à un " + jourDeLaSemaine);
    }
}
