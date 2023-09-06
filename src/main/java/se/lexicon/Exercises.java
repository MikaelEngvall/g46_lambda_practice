package se.lexicon;

import se.lexicon.data.DataStorage;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Exercises {

    private final static DataStorage storage = DataStorage.INSTANCE;

    /*
         1.	Find everyone that has firstName: “Erik” using findMany().

    */
    public static void exercise1(String message) {
        System.out.println(message);
        //Write your code here
        Predicate<Person> erikPredicate = person -> "Erik".equals(person.getFirstName());
        List<Person> foundPeople = storage.findMany(erikPredicate);
        foundPeople.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
          2.	Find all females in the collection using findMany().
     */
    public static void exercise2(String message) {
        System.out.println(message);
        //Write your code here
        Predicate<Person> females = person -> Gender.FEMALE.equals(person.getGender());
        List<Person> foundPeople = storage.findMany(females);
        foundPeople.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
          3.	Find all who were born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message) {
        System.out.println(message);
        //Write your code here
        LocalDate date = LocalDate.of(2000, 1, 1).minusDays(1);
        Predicate<Person> afterDate = person -> date.isBefore(person.getBirthDate());
        List<Person> foundPeople = storage.findMany(afterDate);
        foundPeople.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
         4.	Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message) {
        System.out.println(message);
        //Write your code here
        System.out.println(storage.findOne((person -> person.getId() == 123)));
//        Predicate<Person> personById = person -> "123".equals(String.valueOf(person.getId()));
//        System.out.println(storage.findOne(personById));
        System.out.println("----------------------");

    }

    /*
          5.	Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message) {
        System.out.println(message);
        // Write your code here
        Predicate<Person> personById = person -> person.getId() == 456;

        // Define the custom format function
        Function<Person, String> customFormat = person -> {
            String name = person.getFirstName() + " " + person.getLastName();
            String birthDate = person.getBirthDate().toString();
            return "Name: " + name + " born " + birthDate;
        };

        String formatted = storage.findOneAndMapToString(personById, customFormat);
        System.out.println(formatted);
        System.out.println("----------------------");
    }

    /*
         Implement method that finds all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message) {
        System.out.println(message);
        // Write your code here
        Predicate<Person> maleNamesStartingWithE = person ->
                person.getGender() == Gender.MALE && person.getFirstName().startsWith("E");

        List<String> maleNamesStartingWithEStrings = storage.findManyAndMapEachToString(maleNamesStartingWithE, person -> {
            String name = person.getFirstName() + " " + person.getLastName(); //Custom format
            String birthDate = person.getBirthDate().toString();//Custom format
            return "Name: " + name + " born " + birthDate;//Custom format
        });

        maleNamesStartingWithEStrings.forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
         7.	Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message) {
        System.out.println(message);
        //Write your code here
        Predicate<Person> belowAgeOf10 = person -> {
            int age = LocalDate.now().getYear() - person.getBirthDate().getYear();
            return age < 10;
        };

        List<String> belowAgeOf10Strings = storage.findManyAndMapEachToString(belowAgeOf10, person -> {
            int age = LocalDate.now().getYear() - person.getBirthDate().getYear();//Custom format
            return "Name: " + person.getFirstName() + " " + person.getLastName() + " " + age + " years";//Custom format
        });

        belowAgeOf10Strings.forEach(System.out::println);
    }

    /*
         8.	Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message) {
        System.out.println(message);
        //Write your code here
        storage.findAndDo(
                person -> person.getFirstName().equals("Ulf"),
                System.out::println
        );

        System.out.println("----------------------");
    }

    /*
         9.	Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message) {
        System.out.println(message);
        //Write your code here
        storage.findAndDo(
                person -> person.getLastName().toLowerCase().contains(person.getFirstName().toLowerCase()),
                System.out::println
        );

        System.out.println("----------------------");
    }

    /*
         10.	Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message) {
        System.out.println(message);
        //Write your code here
        storage.findAndDo(
                person -> person.getFirstName().equalsIgnoreCase(new StringBuilder(person.getFirstName()).reverse().toString()),
                person -> System.out.println(person.getFirstName() + " " + person.getLastName())
        ); // reverse() does the trick here when it comes to palindromes

        System.out.println("----------------------");
    }

    /*
         11.	Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message) {
        System.out.println(message);
        //Write your code here
        storage.findAndSort(
                person -> person.getFirstName().startsWith("A"),
                Comparator.comparing(Person::getBirthDate)
        ).forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
         12.	Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message) {
        System.out.println(message);
        //Write your code here
        storage.findAndSort(
                person -> person.getBirthDate().getYear() < 1950,
                Comparator.comparing(Person::getBirthDate).reversed()
        ).forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
         13.	Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message) {
        System.out.println(message);
        //Write your code here
        //With Anonymous inner Class
        Comparator<Person> compareLastName = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getLastName().compareTo(o2.getLastName());
            }
        };

        //With Lambda
        Comparator<Person> compareFirstName = (Person o1, Person o2) -> o1.getFirstName().compareTo(o2.getFirstName());

        //With Method Reference
        Comparator<Person> compareBirthDate = Comparator.comparing(Person::getBirthDate);

        //Stack
        Comparator<Person> all = compareLastName.thenComparing(compareFirstName).thenComparing(compareBirthDate);

        storage.findAndSort(all).forEach(System.out::println);
        /*
        storage.findAndSort(
                Comparator.comparing(Person::getLastName).thenComparing(Person::getFirstName).thenComparing(Person::getBirthDate)
        ).forEach(System.out::println);
        */

        System.out.println("----------------------");
    }
}
