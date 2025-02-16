import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Plant {
    private String name;
    private String notes;
    private LocalDate planted;
    private LocalDate watering;
    private int wateringFrequency;

    public Plant(String name, String notes, LocalDate planted, LocalDate watering, int wateringFrequency) {
        this.name = name;
        this.notes = notes;
        this.planted = planted;
        this.watering = watering;
        this.wateringFrequency = wateringFrequency;
        validateWateringFrequency();
        validateWateringDate();

    }

    public Plant(String name) {
        this(name, "", LocalDate.now(), LocalDate.now(), 7);
    }

    public Plant(String name, int wateringFrequency) {
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

    public void setWateringFrequency(int wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
        validateWateringFrequency();
    }

    public void setWatering(LocalDate watering) {
        this.watering = watering;
        validateWateringDate();
    }

    public String getWateringInfo() {
        LocalDate nextWatering = watering.plusDays(wateringFrequency);
        return String.format("Názov: %s, Posledná zálievka: %s, Odporúčaná ďalšia zálievka: %s", name, watering, nextWatering);
    }

    public void doWateringNow() {
        this.watering = LocalDate.now();
    }


    private void validateWateringFrequency() {
        if (wateringFrequency <= 0)
            throw new PlantException("Frekvencia zálievky musí byť kladné číslo");
    }

    private void validateWateringDate() {
        if (watering.isBefore(planted)) {
            throw new PlantException("Dátum poslednej zálievky nemôže byť starší ako dátum zasadenia");
        }
    }

    public long daysSinceLastWatering() {
        return ChronoUnit.DAYS.between(watering, LocalDate.now());
    }


}
