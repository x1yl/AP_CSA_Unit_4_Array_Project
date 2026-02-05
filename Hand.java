public class Hand {
    private int[] cards;
    private int handType;
    private final int bid;
    private int rank;
    public Hand(int[] cards, int handType, int bid) {
        this.cards = cards;
        this.handType = handType;
        this.bid = bid;
    }

    public int[] getCards() {
        return cards;
    }

    public void setCards(int[] cards) {
        this.cards = cards;
    }

    public int getHandType() {
        return handType;
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


}
