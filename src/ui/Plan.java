import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Plan {

    String course;
    String courseName;
    int credits;
    String teacherName;
    String classroom;
    String[] activityNames = new String[5];
    LocalDate[] activityDates = new LocalDate[5];
    double[] activityPercentages = new double[5];
    int activityCount = 0;
    double[] activityGrades = new double[5];
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Plan run = new Plan();
        run.showMenu();
    }

    public void init() {
        registerCourse();
        registerActivities();
        loadGrades();
        displayActivities();
        calculateCourseAverage();
        scanner.close();
    }

    public void displayMenuOptions() {
    System.out.println("\n=== MENÚ PRINCIPAL ===");
    System.out.println("1. Registrar curso");
    System.out.println("2. Registrar actividades");
    System.out.println("3. Cargar notas");
    System.out.println("4. Mostrar actividades");
    System.out.println("5. Calcular promedio del curso");
    System.out.println("6. Salir");
    System.out.print("Seleccione una opción: ");
}
public void showMenu() {
    int option;

    do {
        displayMenuOptions();
        option = validateMenuOption();

        switch (option) {
            case 1 -> registerCourse();
            case 2 -> registerActivities();
            case 3 -> loadGrades();
            case 4 -> displayActivities();
            case 5 -> calculateCourseAverage();
            case 6 -> System.out.println("Gracias por usar el sistema. ¡Hasta pronto!");
            default -> System.out.println("Opción inválida. Intente de nuevo.");
        }
    } while (option != 6);
}
    
    public void displayGoodbye() {
        System.out.println("\n¡Gracias por usar PLANEO!");
        System.out.println("¡Hasta luego!");
    }
    public int validateMenuOption() {
    while (true) {
        try {
            int option = Integer.parseInt(scanner.nextLine());
            if (option >= 1 && option <= 6) {
                return option;
            } else {
                System.out.print("Ingrese un número entre 1 y 6: ");
            }
        } catch (NumberFormatException e) {
            System.out.print("Entrada inválida. Ingrese un número: ");
        }
    }
}

    public void registerCourse() {
        System.out.print("Ingrese el código del curso: ");
        course = scanner.nextLine();
        System.out.print("Ingrese el nombre del curso: ");
        courseName = scanner.nextLine();
        System.out.print("Ingrese el número de créditos: ");
        credits = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese el nombre del profesor: ");
        teacherName = scanner.nextLine();
        System.out.print("Ingrese el salón: ");
        classroom = scanner.nextLine();
        System.out.println("\nCurso registrado exitosamente!");
    }

    public void registerActivities() {
        System.out.println("\nPuedes registrar maximo 5 actividades para este curso");
        int numActivities = validateActivityCount();
        double totalPercentage = collectActivitiesData(numActivities);
        validateTotalPercentage(totalPercentage);
    }

    public double collectActivitiesData(int numActivities) {
        double totalPercentage = 0;
        int i = 0;
        while (i < numActivities) {
            System.out.println("\nActividad " + (i + 1));
            double percentage = registerActivity(i);

            if (totalPercentage + percentage > 100) {
                System.out.println("Error: El porcentaje excede el 100%. Intente de nuevo.");
                continue;
            }
            activityPercentages[i] = percentage;
            totalPercentage += percentage;
            activityCount++;
            i++;
            System.out.println("Porcentaje acumulado: " + totalPercentage + "%");
        }
        return totalPercentage;
    }

    public double registerActivity(int index) {
        System.out.print("Nombre de la actividad: ");
        activityNames[index] = scanner.nextLine();
        System.out.print("Fecha de entrega (dd/MM/yyyy): ");
        activityDates[index] = validateDate();
        System.out.print("Porcentaje de la actividad: ");
        return validatePercentage();
    }

    public void validateTotalPercentage(double totalPercentage) {
        if (totalPercentage != 100.0) {
            System.out.println("\nEl porcentaje total es " + totalPercentage + "% y debe ser exactamente 100%");
            System.out.println("Las actividades no han sido guardadas.");
            activityCount = 0;
        } else {
        }
    }

    public int validateActivityCount() {
        while (true) {
            try {
                int count = Integer.parseInt(scanner.nextLine());
                if (count >= 1 && count <= 5) {
                    return count;
                } else {
                    System.out.print("Debe registrar entre 1 y 5 actividades: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }

    public LocalDate validateDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            try {
                String dateInput = scanner.nextLine();
                return LocalDate.parse(dateInput, formatter);
            } catch (DateTimeParseException e) {
                System.out.print("Formato de fecha inválido. Use dd/MM/yyyy: ");
            }
        }
    }

    public double validatePercentage() {
        while (true) {
            try {
                double percentage = Double.parseDouble(scanner.nextLine());
                if (percentage > 0 && percentage <= 100) {
                    return percentage;
                } else {
                    System.out.print("El porcentaje debe estar entre 0 y 100: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }
        
    public void loadGrades() {
        if (activityCount == 0) {
            System.out.println("No hay actividades registradas para cargar notas.");
            return;
        }
        System.out.println("\nIngrese las notas para las " + activityCount + " actividades:");

        for (int i = 0; i < activityCount; i++) {
            System.out.print("\nNota para '" + activityNames[i] + "' (0.0 - 5.0): ");
            activityGrades[i] = validateGrade();
            System.out.println("Nota registrada: " + activityGrades[i]);
        }
    }

    public double validateGrade() {
        while (true) {
            try {
                double grade = Double.parseDouble(scanner.nextLine());
                if (grade >= 0.0 && grade <= 5.0) {
                    return grade;
                } else {
                    System.out.print("La nota debe estar entre 0.0 y 5.0: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }
public void displayActivities() {
        if (activityCount == 0) {
            System.out.println("No hay actividades registradas para mostrar.");
            return;
        }

        System.out.println("\nCurso: " + courseName + " (" + course + ")" + " Dictado por: " + teacherName);
        System.out.println("Lista de actividades:");

        for (int i = 0; i < activityCount; i++) {
            displayActivity(i);
        }
    }

    public void displayActivity(int index) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = activityDates[index].format(formatter);
        System.out.println("\n" + (index + 1) + ". " + activityNames[index]);
        System.out.println("   Fecha de entrega: " + formattedDate);
        System.out.println("   Nota: " + activityGrades[index]);
        System.out.println("   Porcentaje: " + activityPercentages[index] + "%");
    }

    public void calculateCourseAverage() {
        if (activityCount == 0) {
            System.out.println("No hay actividades registradas para calcular promedio.");
            return;
        }
    
        System.out.println("\n=== PROMEDIO DEL CURSO ===");
        System.out.println("Curso: " + courseName + " (" + course + ")");
        System.out.println("Profesor: " + teacherName);
        System.out.println("\nDetalle de actividades:");
        displayActivitiesForAverage();
        double average = computeWeightedAverage();
        displayFinalResult(average);
    }
    
    public void displayActivitiesForAverage() {
        System.out.println("Actividad               | Peso    | Nota | Contribución");
        for (int i = 0; i < activityCount; i++) {
            double contribution = activityGrades[i] * (activityPercentages[i] / 100);
            System.out.printf("%-23s | %6.1f%% | %4.1f | %8.2f%n", 
                activityNames[i], 
                activityPercentages[i], 
                activityGrades[i],
                contribution);
        }
    }
    
    public double computeWeightedAverage() {
        double weightedSum = 0;
        for (int i = 0; i < activityCount; i++) {
            double contribution = activityGrades[i] * (activityPercentages[i] / 100);
            weightedSum += contribution;
        }
        return weightedSum;
    }
    
    public void displayFinalResult(double average) {
        System.out.println("------------------------|---------|------|-------------");
        System.out.printf("PROMEDIO PONDERADO:                        %8.2f%n", average);
        System.out.println("\n" + "=".repeat(50));
        if (average >= 3.0) {
            System.out.println("ESTADO DEL CURSO: APROBADO");
        } else {
            System.out.println("ESTADO DEL CURSO: REPROBADO");
        }
    }

}
