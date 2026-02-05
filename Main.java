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
        int totalBidValue = 0;
        int totalBidValueWithWildJack = 0;

        for (int i = 0; i < hands.length; i++) {
            String[] cards = hands[i].split(",");
            int[] cardsAsInt = new int[cards.length];
            int bid = 0;
            for (int j = 0; j < cards.length; j++) {
                String card = cards[j];
                int barIndex = card.indexOf("|");
                if (card.contains("|")) {
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

            int[] countOfEachType = new int[14];
            for (int card : cardsAsInt) {
                countOfEachType[card - 1]++;
            }
            String countOfEachTypeAsString = Arrays.toString(countOfEachType);
            if (countOfEachTypeAsString.contains("5")) {
                fiveOfAKind++;
                handObjects[i] = new Hand(cardsAsInt, 6, bid);
            } else if (countOfEachTypeAsString.contains("4")) {
                fourOfAKind++;
                handObjects[i] = new Hand(cardsAsInt, 5, bid);
            } else if (countOfEachTypeAsString.contains("3") && countOfEachTypeAsString.contains("2")) {
                fullHouse++;
                handObjects[i] = new Hand(cardsAsInt, 4, bid);
            } else if (countOfEachTypeAsString.contains("3")) {
                threeOfAKind++;
                handObjects[i] = new Hand(cardsAsInt, 3, bid);
            } else if (countOfEachTypeAsString.contains("2") && countOfEachTypeAsString.indexOf("2") != countOfEachTypeAsString.lastIndexOf("2")) {
                twoPair++;
                handObjects[i] = new Hand(cardsAsInt, 2, bid);
            } else if (countOfEachTypeAsString.contains("2")) {
                onePair++;
                handObjects[i] = new Hand(cardsAsInt, 1, bid);
            } else {
                highCard++;
                handObjects[i] = new Hand(cardsAsInt, 0, bid);
            }
        }
        totalBidValue = getTotal(handObjects, totalBidValue);

        for (Hand hand : handObjects) {
            int[] cards = hand.getCards();
            int[] wildJackCards = new int[cards.length];
            int numberOfJacks = 0;
            int getHandType = hand.getHandType();
            for (int j = 0; j < cards.length; j++) {
                if (cards[j] == 11) {
                    numberOfJacks++;
                    wildJackCards[j] = 1;
                } else {
                    wildJackCards[j] = cards[j];
                }
            }
            hand.setCards(wildJackCards);
            if (numberOfJacks == 1) {
                if (getHandType == 5) {
                    hand.setHandType(6);
                } else if (getHandType == 3) {
                    hand.setHandType(5);
                } else if (getHandType == 2) {
                    hand.setHandType(4);
                } else if (getHandType == 1) {
                    hand.setHandType(3);
                } else if (getHandType == 0) {
                    hand.setHandType(1);
                }
            } else if (numberOfJacks == 2) {
                if (getHandType == 4) {
                    hand.setHandType(6);
                } else if (getHandType == 2) {
                    hand.setHandType(5);
                }
            } else if (numberOfJacks == 3) {
                if (getHandType == 4) {
                    hand.setHandType(6);
                }
            }
        }
        totalBidValueWithWildJack = getTotal(handObjects, totalBidValueWithWildJack);

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

    private static int getTotal(Hand[] handObjects, int total) {
        for (Hand hand : handObjects) {
            int numberOfWeakerHands = 0;
            for (Hand handObject : handObjects) {
                if (hand.isStronger(handObject)) {
                    numberOfWeakerHands++;
                }
            }

            hand.setRank(numberOfWeakerHands + 1);
            total += hand.getRank() * hand.getBid();
        }
        return total;
    }
}
