import aleman.census.Education;
import aleman.census.Person;
import aleman.census.Sex;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<Person>();

        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        // поиск несовершеннолетних
        long countMinors = persons.stream()
                .filter(v -> v.getAge() < 18)
                .count();
        System.out.printf("Кол-во несовершеннолетних - %d\n", countMinors);

        // список призывников
        List<String> conscripts = persons.stream()
                .filter(v -> v.getAge() >= 18 && v.getAge() <= 27)
                .map(v -> v.getFamily())
                .collect(Collectors.toList());
        System.out.printf("Кол-во призывников - %d\n", conscripts.size());

        // отсортированный по фамилии списка потенциально работоспособных людей с высшим образованием
        List<String> people = persons.stream()
                .filter(v ->
                        ((v.getSex() == Sex.MAN) && (v.getAge() >= 18 && v.getAge() <= 65))  ||
                        ((v.getSex() == Sex.WOMAN) && (v.getAge() >= 18 && v.getAge() <= 60))
                )
                .filter(v -> v.getEducation() == Education.HIGHER)
                .sorted(Comparator.comparing(Person::getFamily))
                .map(v -> v.getFamily())
                .collect(Collectors.toList());
        System.out.printf("Кол-во потенциально работоспособных людей с высшим образованием - %d\n", people.size());
    }
}
