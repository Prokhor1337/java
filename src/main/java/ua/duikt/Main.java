package ua.duikt;

import org.apache.commons.lang3.StringUtils;

/**
 * Практична робота № 1
 * Дисципліна: Конструювання програмного забезпечення Java
 * Тема: Платформа та екосистема Java. Створення та збірка Java-проєкту засобами Maven.
 * Студент: Чертілін Прохор, група ТЦР-31
 */
public class Main {
    public static void main(String[] args) {
        // Отримання інформації про версію JVM та постачальника
        String jvmVersion = System.getProperty("java.version");
        String jvmVendor = System.getProperty("java.vendor");
        System.out.println("JVM: " + jvmVersion + " (" + jvmVendor + ")");

        // Отримання інформації про операційну систему та архітектуру
        String osName = System.getProperty("os.name");
        String osArch = System.getProperty("os.arch");
        System.out.println("OS: " + osName + " (" + osArch + ")");

        // Застосування методу із підключеної залежності Apache Commons Lang
        String inputWord = "avaJ";
        String reversed = StringUtils.reverse(inputWord);
        System.out.println("Reversed (Commons Lang): " + reversed);

        // Виклик методу з рядком українською мовою
        printUkrainianGreeting();
    }

    /**
     * Окремий метод для виводу привітання українською мовою.
     * Байт-код цього методу досліджується через утиліту javap -c.
     */
    public static void printUkrainianGreeting() {
        System.out.println("Привіт зі збірки!");
    }
}
