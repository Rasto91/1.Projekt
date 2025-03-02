import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        PlantList plantList = new PlantList();

        // Načítanie zo súboru
        try {
            plantList.loadFromFile("plantList/flowers-bad-frequency.txt");
        } catch (IOException e) {
            System.err.println("Chyba pri načítaní zo súboru: " + e.getMessage());
        } catch (PlantException e) {
            System.err.println("Chyba pri validácii dát: " + e.getMessage());
        }

        // Výpis informácií o zálievke
        for (Plant plant : plantList.getPlants()) {
            System.out.println(plant.getWateringInfo());
        }

        // Pridanie novej kvetiny
        try {
            plantList.addPlant(new Plant("Fialka", "Kvitne na jar", LocalDate.now(), LocalDate.now(), 7));
        } catch (PlantException e) {
            System.err.println("Chyba pri vytváraní kvetiny: " + e.getMessage());
        }

        // Pridanie 10 tulipánov
        for (int i = 1; i <= 10; i++) {
            try {
                plantList.addPlant(new Plant("Tulipán na predaj " + i, " ", LocalDate.now(), LocalDate.now(), 14));
            } catch (PlantException e) {
                System.err.println("Chyba pri vytváraní tulipánu: " + e.getMessage());
            }
        }

        plantList.removePlant(3);

        // Uloženie do výstupného súboru
        try {
            plantList.saveToFile("plantList/kvetiny_aktualizovane.txt");
        } catch (IOException e) {
            System.err.println("Chyba pri ukladaní do súboru: " + e.getMessage());
        }

        // Načítanie z výstupného súboru
        PlantList plantList2 = new PlantList();
        try {
            plantList2.loadFromFile("plantList/kvetiny_aktualizovane.txt");
        } catch (IOException e) {
            System.err.println("Chyba pri načítaní zo súboru: " + e.getMessage());
        } catch (PlantException e) {
            System.err.println("Chyba pri validácii dát: " + e.getMessage());
        }

        // Overenie, či sú dáta správne načítané
        System.out.println("\nOverenie načítania z kvetiny_aktualizovane.txt:");
        for (Plant plant : plantList2.getPlants()) {
            System.out.println(plant.getName());
        }

        plantList.sortByName();
        System.out.println("\nZoznam kvetín zoradený podľa názvu:");
        for (Plant plant : plantList.getPlants()) {
            System.out.println(plant.getName());
        }

        Collections.sort(plantList.getPlants(), Comparator.comparing(Plant::getWatering));
        System.out.println("\nZoznam kvetín zoradený podľa dátumu poslednej zálievky:");
        for (Plant plant : plantList.getPlants()) {
            System.out.println(plant.getWatering());
        }
    }
}