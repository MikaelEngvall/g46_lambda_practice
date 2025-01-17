package se.lexicon.data;


import se.lexicon.model.Person;
import se.lexicon.util.PersonGenerator;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * Create implementations for all methods. I have already provided an implementation for the first method *
 */
public class DataStorageImpl implements DataStorage {

    private static final DataStorage INSTANCE;

    static {
        INSTANCE = new DataStorageImpl();
    }

    private final List<Person> personList;

    private DataStorageImpl() {
        personList = PersonGenerator.getInstance().generate(1000);
    }

    static DataStorage getInstance() {
        return INSTANCE;
    }


    @Override
    public List<Person> findMany(Predicate<Person> filter) {

        return personList.stream()
                .filter(filter)
                .toList();
    }

    @Override
    public Person findOne(Predicate<Person> filter) {

        return personList.stream()
                .filter(filter).findFirst()
                .orElse(new Person(null,null,null,null));
    }

    @Override
    public String findOneAndMapToString(Predicate<Person> filter, Function<Person, String> personToString) {
//        Person person = findOne(filter);
        return personList.stream()
                .filter(filter)
                .findFirst()
                .map(personToString)
                .orElse("Person not found");
    }

//    @Override //todo: remove this once we don't need it anymore
//    public List<Person> findAll() {
//        return personList.stream()
//                .toList();
//    }

    @Override
    public List<String> findManyAndMapEachToString(Predicate<Person> filter, Function<Person, String> personToString) {
        //todo: implement the method
        return null;
    }

    @Override
    public void findAndDo(Predicate<Person> filter, Consumer<Person> consumer) {
        //todo: implement the method
    }

    @Override
    public List<Person> findAndSort(Comparator<Person> comparator) {
        //todo: implement the method
        return null;
    }

    @Override
    public List<Person> findAndSort(Predicate<Person> filter, Comparator<Person> comparator) {
        //todo: implement the method
        return null;
    }
}
