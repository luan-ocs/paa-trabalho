package main.java.org.luaneric.backpack;

import main.java.org.luaneric.GenericCustomValue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BackpackProblemSolve {
    private static final String PI = "31415926535897932384626433832795028841971693993751";

    public void execute(ArrayList<GenericCustomValue> allWords) {
        List<GenericCustomValue> only50 = getFirst50(allWords);

        List<BackpackItem> items = createItems(only50);

        ArrayList<Double> totalSize = new ArrayList<Double>();
        for (int i = 50; i <= 80; i++) {
            Double total = solveBackpackProblem(items, i);
            totalSize.add(total);
        }

        String listString = totalSize.stream().map(Object::toString)
                .collect(Collectors.joining(",\n"));

        System.out.println(listString);

    }

    public Double solveBackpackProblem(List<BackpackItem> possibleItems, int maxCapacity) {
        possibleItems.sort((first, second) -> (int) (getItemValue(second) - getItemValue(first)));

        Double freeSpace = (double) maxCapacity;

        List<BackpackItem> backpack = new ArrayList<>();

        for (BackpackItem item : possibleItems) {

            if (item.getWeight() >= freeSpace) {
                double fraction = freeSpace / item.getWeight();
                BackpackItem fractionItem = new BackpackItem(item.getWord(), (int) fraction,
                        item.getValue() * fraction);
                backpack.add(fractionItem);
                freeSpace -= fraction;
                break;

            } else {

                backpack.add(item);
                freeSpace -= item.getWeight();
            }
        }

        return maxCapacity - freeSpace;
    }

    public double getItemValue(BackpackItem item) {
        return item.getWeight() / item.getWord().getFrequency();
    };

    public List<GenericCustomValue> getFirst50(ArrayList<GenericCustomValue> values) {
        return values.subList(0, 50);
    }

    public List<BackpackItem> createItems(List<GenericCustomValue> values) {
        ArrayList<BackpackItem> backpackItems = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            backpackItems.add(new BackpackItem(values.get(i), getWeight(i)));
        }

        return backpackItems;
    }

    private int getWeight(int index) {
        String[] piString = PI.split("");

        if (piString[index].equals("0")) {
            return 10;
        } else {
            return Integer.parseInt(piString[index]);
        }
    }
}
