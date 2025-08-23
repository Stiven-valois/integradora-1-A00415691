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
        run.init();
    }

    public void init() {
        registerCourse();
        registerActivities();
        loadGrades();
        displayActivities();
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
        System.out.println("\nPuedes ingresar hasta 5 actividades para este curso");
        System.out.print("¿Cuántas actividades desea registrar? (máximo 5): ");
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
            System.out.println("Actividad registrada. Porcentaje acumulado: " + totalPercentage + "%");
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
            System.out.println("\nActividades registradas exitosamente!");
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
            System.out.print("Nota para '" + activityNames[i] + "' (0.0 - 5.0): ");
            activityGrades[i] = validateGrade();
            System.out.println("Nota registrada: " + activityGrades[i]);
        }

        System.out.println("\nTodas las notas han sido cargadas exitosamente!");
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
        System.out.println("\nLista de actividades:");

        for (int i = 0; i < activityCount; i++) {
            displayActivity(i);
        }
    }

    public void displayActivity(int index) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = activityDates[index].format(formatter);
        
        System.out.println("\n" + (index + 1) + ". " + activityNames[index]);
        System.out.println("   Fecha de entrega: " + formattedDate);
        System.out.println("Nota: " + activityGrades[index]);
        System.out.println("   Porcentaje: " + activityPercentages[index] + "%");
    }

}
