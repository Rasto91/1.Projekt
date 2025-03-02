import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class PlantList {
    private List<Plant> plants = new ArrayList<>();

    public void addPlant(Plant plant) {
        this.plants.add(plant);
    }

    public Plant getPlant(int index) {
        return plants.get(index);
    }

    public void removePlant(int index) {
        if (index < 0 || index >= plants.size()) {
            throw new IndexOutOfBoundsException("Neplatný index: " + index + "Zoznam má veľkosť: " + plants.size() + ".");
        }
        plants.remove(index);
    }

    public List<Plant> getPlants() {
        return new ArrayList<>(plants); // Vráti kópiu zoznamu
    }

    public List<Plant> getPlantsToWater() {
        return plants.stream()
                .filter(plant -> plant.daysSinceLastWatering() >= plant.getWateringFrequency())
                .collect(Collectors.toList());
    }

    public void sortByName() {
        Collections.sort(plants);
    }

    public void sortByWateringDate() {
        Collections.sort(plants, Comparator.comparing(Plant::getWatering));
    }

    public void loadFromFile(String flowers) throws IOException, PlantException {
        try (BufferedReader br = new BufferedReader(new FileReader(flowers))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] parts = line.split("\t");
                if (parts.length == 5) {
                    try {
                        String name = parts[0];
                        String notes = parts[1];
                        LocalDate planted = LocalDate.parse(parts[2]);
                        LocalDate watering = LocalDate.parse(parts[3]);
                        int frequency = Integer.parseInt(parts[4]);
                        plants.add(new Plant(name, notes, planted, watering, frequency));
                    } catch (DateTimeParseException ex) {
                        System.err.println("Riadok " + lineNumber + ": Chyba pri načítaní dátumu: " + ex.getMessage());
                    } catch (NumberFormatException ex) {
                        System.err.println("Riadok " + lineNumber + ": Chyba pri načítaní čísla: " + ex.getMessage());
                    } catch (PlantException ex) {
                        System.err.println("Riadok " + lineNumber + "Chyba pri validácii dát: " + ex.getMessage());
                        // V prípade chyby pokračujeme s ďalším riadkom
                    }
                } else {
                    System.err.println("Riadok " + lineNumber + "Neplatný formát riadku " + line);
                }
            }
        }
    }


    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Plant plant : plants) {
                bw.write(String.format("%s\t%s\t%s\t%s\t%d%n",
                        plant.getName(), plant.getNotes(), plant.getPlanted(),
                        plant.getWatering(), plant.getWateringFrequency()));
            }
        }
    }
}
