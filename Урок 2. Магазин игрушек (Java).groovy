import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Random;

public class Toy {
    private int id;
    private String name;
    private int weight; // Вес игрушки (частота выпадения)

    public Toy(int id, String name, int weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public static void main(String[] args) {
        PriorityQueue<Toy> toyQueue = new PriorityQueue<>((t1, t2) -> t2.getWeight() - t1.getWeight());

        // Заполнение очереди игрушками
        toyQueue.add(new Toy(1, "Игрушка 1", 10)); // Пример частоты выпадения: 10
        toyQueue.add(new Toy(2, "Игрушка 2", 5));  // Пример частоты выпадения: 5
        toyQueue.add(new Toy(3, "Игрушка 3", 3));  // Пример частоты выпадения: 3

        int numDraws = 10; // Количество розыгрышей
        try {
            FileWriter fileWriter = new FileWriter("results.txt");
            for (int i = 0; i < numDraws; i++) {
                Toy drawnToy = drawToy(toyQueue);
                System.out.println("Выпала игрушка: " + drawnToy.getName());
                fileWriter.write("Выпала игрушка: " + drawnToy.getName() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Toy drawToy(PriorityQueue<Toy> toyQueue) {
        Random random = new Random();
        int totalWeight = toyQueue.stream().mapToInt(Toy::getWeight).sum();
        int randomNumber = random.nextInt(totalWeight);

        int currentWeightSum = 0;
        for (Toy toy : toyQueue) {
            currentWeightSum += toy.getWeight();
            if (randomNumber < currentWeightSum) {
                return toy;
            }
        }

        // Если что-то пошло не так, вернуть первую игрушку
        return toyQueue.peek();
    }
}
