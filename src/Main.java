
public class Main {
    public static void main(String[] args) {
        for (int dayNumber = 1; dayNumber <= 25; dayNumber++) {
            for (String part : new String[] { "a", "b"}) {
                try {
                    Class<?> dayClass = Class.forName("Day" + dayNumber + part);
                    Day day = (Day) dayClass.getConstructor().newInstance();
                    if (!day.verify()) {
                        return;
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("Next up is " + dayNumber + part);
                    return;
                } catch (Exception e) {
                    System.out.println("Can't instantiate day " + dayNumber + part + ": " + e + " " + e.getMessage());
                }
            }
        }
    }
}