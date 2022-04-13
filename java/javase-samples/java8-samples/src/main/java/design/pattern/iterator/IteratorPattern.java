package design.pattern.iterator;

/**
 * https://www.runoob.com/design-pattern/iterator-pattern.html
 */
public class IteratorPattern {
    public static void main(String[] args) {
        NameRepository namesRepository = new NameRepository();
        for (Iterator iter = namesRepository.getIterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            System.out.println("Name : " + name);
        }
    }
}
