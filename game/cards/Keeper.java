package game.cards;

public class Keeper implements IntrigueCard{

    private String type;
    private int rollValue;

    public Keeper(String type){
        this.type = type;
    }

    public Keeper(String type, int rollValue){
        this.type = type;
        this.rollValue = rollValue;
    }

    public String getType(){
        return type;
    }

    public int getRoll(){
        return rollValue;
    }

    /**
     * Depending on the type of the card, this will print out the
     * full name of this card.
     */
    public String toString(){
        if(type.equals("Roll")){
            return "Add to Roll: " + rollValue;
        } else if(type.equals("Move")){
            return "Move to Any Location";
        } else if(type.equals("Start")){
            return "Send Player back to Start Square";
        } else if(type.equals("Avoid")){
            return "Avoid a Guess";
        } else if(type.equals("Make")){
            return "Make a Guess";
        } else if(type.equals("Turn")){
            return "Take an Extra Turn";
        } else if(type.equals("See")){
            return "See Another Player's Card";
        } else {
            System.out.println("Wrong keeper name");
            throw new Error();
        }
    }
}
