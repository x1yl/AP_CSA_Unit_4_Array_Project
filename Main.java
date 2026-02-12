import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fileData = "";
        try {
            File f = new File("src/data");
            Scanner s = new Scanner(f);

            while (s.hasNextLine()) {
                String line = s.nextLine();
                fileData += line + "\n";
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        String[] hands = fileData.split("\n");

        int fiveOfAKind = 0;
        int fourOfAKind = 0;
        int fullHouse = 0;
        int threeOfAKind = 0;
        int twoPair = 0;
        int onePair = 0;
        int highCard = 0;

        Hand[] handObjects = new Hand[hands.length];

        for (int i = 0; i < hands.length; i++) {
            String[] cards = hands[i].split(",");
            int[] cardsAsInt = new int[cards.length];
            int bid = 0;
            for (int j = 0; j < cards.length; j++) {
                String card = cards[j];
                int barIndex = card.indexOf("|");
                if (barIndex != -1) {
                    bid = Integer.parseInt(card.substring(barIndex + 1));
                    cards[j] = card.substring(0, barIndex);
                }
                if (cards[j].equals("Jack")) {
                    cardsAsInt[j] = 11;
                } else if (cards[j].equals("Queen")) {
                    cardsAsInt[j] = 12;
                } else if (cards[j].equals("King")) {
                    cardsAsInt[j] = 13;
                } else if (cards[j].equals("Ace")) {
                    cardsAsInt[j] = 14;
                } else {
                    cardsAsInt[j] = Integer.parseInt(cards[j]);
                }
            }

            int[] countOfEachCard = new int[14];
            for (int card : cardsAsInt) {
                countOfEachCard[card - 1]++;
            }
            String countOfEachCardAsString = Arrays.toString(countOfEachCard);
            if (countOfEachCardAsString.contains("5")) {
                fiveOfAKind++;
                handObjects[i] = new Hand(cardsAsInt, 6, bid);
            } else if (countOfEachCardAsString.contains("4")) {
                fourOfAKind++;
                handObjects[i] = new Hand(cardsAsInt, 5, bid);
            } else if (countOfEachCardAsString.contains("3") && countOfEachCardAsString.contains("2")) {
                fullHouse++;
                handObjects[i] = new Hand(cardsAsInt, 4, bid);
            } else if (countOfEachCardAsString.contains("3")) {
                threeOfAKind++;
                handObjects[i] = new Hand(cardsAsInt, 3, bid);
            } else if (countOfEachCardAsString.contains("2") && countOfEachCardAsString.indexOf("2") != countOfEachCardAsString.lastIndexOf("2")) {
                twoPair++;
                handObjects[i] = new Hand(cardsAsInt, 2, bid);
            } else if (countOfEachCardAsString.contains("2")) {
                onePair++;
                handObjects[i] = new Hand(cardsAsInt, 1, bid);
            } else {
                highCard++;
                handObjects[i] = new Hand(cardsAsInt, 0, bid);
            }
        }
        int totalBidValue = getTotal(handObjects);

        for (Hand handObject : handObjects) {
            int numberOfJacks = handObject.wildJack();
            handObject.wildJackHandTypes(numberOfJacks);
        }

        int totalBidValueWithWildJack = getTotal(handObjects);

        System.out.println("Number of five of a kind hands: " + fiveOfAKind);
        System.out.println("Number of full house hands: " + fullHouse);
        System.out.println("Number of four of a kind hands: " + fourOfAKind);
        System.out.println("Number of three of a kind hands: " + threeOfAKind);
        System.out.println("Number of two pair hands: " + twoPair);
        System.out.println("Number of one pair hands: " + onePair);
        System.out.println("Number of high card hands: " + highCard);
        System.out.println("Total Bid Value: " + totalBidValue);
        System.out.println("Total Bid Value With Jacks Wild: " + totalBidValueWithWildJack);
    }

    private static int getTotal(Hand[] handObjects) {
        int total = 0;
        for (Hand handObject : handObjects) {
            int numberOfWeakerHands = 0;
            for (Hand object : handObjects) {
                if (handObject.isStronger(object)) {
                    numberOfWeakerHands++;
                }
            }

            handObject.setRank(numberOfWeakerHands + 1);
            total += handObject.getRank() * handObject.getBid();
        }
        return total;
    }
}
