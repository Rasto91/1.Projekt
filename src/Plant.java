import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Plant implements Comparable<Plant> {
    private String name;
    private String notes;
    private LocalDate planted;
    private LocalDate watering;
    private int wateringFrequency;

    public Plant(String name, String notes, LocalDate planted, LocalDate watering, int wateringFrequency) throws PlantException {
        this.name = name;
        this.notes = notes;
        this.planted = planted;
        this.watering = watering;
        this.wateringFrequency = wateringFrequency;
        validateWateringFrequency();
        validateWateringDate();

    }

    public Plant(String name) throws PlantException {
        this(name, "", LocalDate.now(), LocalDate.now(), 7);
    }

    public Plant(String name, int wateringFrequency) throws PlantException {
        this(name, "", LocalDate.now(), LocalDate.now(), wateringFrequency);
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDate getPlanted() {
        return planted;
    }

    public LocalDate getWatering() {
        return watering;
    }

    public int getWateringFrequency() {
        return wateringFrequency;
    }

    public void setWateringFrequency(int wateringFrequency) throws PlantException {
        if (wateringFrequency <= 0) {
            throw new PlantException("Frekvencia zálievky musí byť kladné číslo.");
        }
        this.wateringFrequency = wateringFrequency;
    }

    public void setWatering(LocalDate watering) throws PlantException {
        if (watering.isBefore(planted)) {
            throw new PlantException("Dátum poslednej zálievky nemôže byť starší ako dátum zasadenia.");
        }
        this.watering = watering;
    }

    public String getWateringInfo() {
        LocalDate nextWatering = watering.plusDays(wateringFrequency);
        return String.format("Názov: %s, Posledná zálievka: %s, Odporúčaná ďalšia zálievka: %s", name, watering, nextWatering);
    }

    public void doWateringNow() {
        this.watering = LocalDate.now();
    }

    private void validateWateringFrequency() throws PlantException {
        if (wateringFrequency <= 0) {
            throw new PlantException("Frekvencia zálievky musí byť kladné číslo.");
        }
    }

    private void validateWateringDate() throws PlantException {
        if (watering.isBefore(planted)) {
            throw new PlantException("Dátum poslednej zálievky nemôže byť starší ako dátum zasadenia.");
        }
    }

    public long daysSinceLastWatering() {
        return ChronoUnit.DAYS.between(watering, LocalDate.now());
    }

    @Override
    public int compareTo(Plant other) {
        return this.name.compareTo(other.name);
    }

}
