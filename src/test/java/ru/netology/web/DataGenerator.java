package ru.netology.web;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    private static final Faker faker = new Faker(new Locale("ru"));
    public static String getRandomCity() {
        List<String> list = Arrays.asList("Краснодар", "Москва", "Санкт-Петербург", "Самара");
        Random rand = new Random();
        String randomElement = list.get(rand.nextInt(list.size()));
        return randomElement;
    }

    public static String getRelevantDate(int days) {
        String date = LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    public static String getIrrelevantDate() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return today;
    }

    public static String getFakerName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }
    static String dataNameWishLetterEBrief() {
        Random random = new Random();
        int rand = random.nextInt(7);
        String nameWishEBrief[] = {"Кизляр Ерёма", "Ёжиков Артур", "Егорова Фёкла", "Берёза Алексей",
                "Дрёма Василиса", "Кандратов Стёпа"};
        return nameWishEBrief[rand];
    }
    public static String getFakerPhone() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.phoneNumber().phoneNumber();
    }
}
