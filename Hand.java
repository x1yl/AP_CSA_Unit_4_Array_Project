public class Hand {
    private final int[] cards;
    private int handType;
    private final int bid;
    private int rank;
    public Hand(int[] cards, int handType, int bid) {
        this.cards = cards;
        this.handType = handType;
        this.bid = bid;
    }

    public void setHandType(int handType) {
        this.handType = handType;
    }

    public int getBid() {
        return bid;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isStronger(Hand otherHand) {
        if(handType > otherHand.handType) {
            return true;
        } else if (handType < otherHand.handType) {
            return false;
        }
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] > otherHand.cards[i]) {
                return true;
            } else if (cards[i] < otherHand.cards[i]) {
                return false;
            }
        }
        return false;
    }

    public int wildJack() {
        int numberOfJacks = 0;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] == 11) {
                numberOfJacks++;
                cards[i] = 1;
            }
        }
        return numberOfJacks;
    }

    public void wildJackHandTypes(int numberOfJacks) {
        if (numberOfJacks == 1) {
            if (handType == 5) {
                setHandType(6);
            } else if (handType == 3) {
                setHandType(5);
            } else if (handType == 2) {
                setHandType(4);
            } else if (handType == 1) {
                setHandType(3);
            } else if (handType == 0) {
                setHandType(1);
            }
        } else if (numberOfJacks == 2) {
            if (handType == 4) {
                setHandType(6);
            } else if (handType == 2) {
                setHandType(5);
            } else if (handType == 1) {
                setHandType(3);
            }
        } else if (numberOfJacks == 3) {
            if (handType == 4) {
                setHandType(6);
            } else if (handType == 3) {
                setHandType(5);
            }
        } else if (numberOfJacks == 4) {
            if (handType == 5) {
                setHandType(6);
            }
        }
    }
}
