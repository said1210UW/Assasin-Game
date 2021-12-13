import java.util.*;
// Said Sheck
// TA: Andrew Wang
// 01.24.2020
// The AssassinManager class is a class
// which keeps track of whos being stalked or killed
// in the childhood game of assassin
public class playerManager {
    private playerNode killRing;
    private playerNode graveYard;


    /* Creates an AssassinManager instance, keeping track
     * of whos being stalked by stalked who with our new list of players
     * @throws an IllegalArgumentException if there are not a list of players
     * @param List<String> names - the list of players in our Assassin game
    */
   public playerManager(List<String> names) {
        if (names.isEmpty()) { //test if list is Empty
            throw new IllegalArgumentException();
        }

        // Explictyly Intialize our graveYard AssassinNode
        graveYard = null;

        //Front Case
        killRing = new playerNode(names.get(0));



       playerNode temp = killRing; //create a temp node to traverse

        for (int i = 1; i < names.size(); i++ ) {
            temp.next = new playerNode(names.get(i)); // create new node
            temp = temp.next;   // create pointer for next node
        }



    }

    /* The following method prints the "kill list" which keep track of who is stalking who
     * It should be noted if there is only one non assasinated person then they're stalking themselves
    */
    public void printKillRing() {
        playerNode temp = killRing;

        while (temp.next != null ) { //Middle Cases
            System.out.println("    " + temp.name + " is stalking " + temp.next.name);
            temp = temp.next;

        }
        //End Case with no Ref
        System.out.println("    " + temp.name + " is stalking " + killRing.name);


    }


    /* The following method prints out those who've been "assasinated" in our
     * assassin game as well the assassins who assailed the people in the graveYard
    */
    public void printGraveyard() {
        playerNode temp = graveYard;

        while (temp != null ){ //Middle Cases
           System.out.println("    "+ temp.name + " was killed by " + temp.killer);
           temp = temp.next;

        }

    }


    /* The following method determines if a name is among a linkList
     * It should be noted that this method is case insentive,
     * meaning someone named KeVIN is the same as kevin
     * @param String name - name you're looking for in list
     * @param AssassinNode groupList. the AssassinNode which
     * will be examined to look for name
     * @returns boolean, stating whether or not that name exist among list
    */
    private boolean xContains(String name, playerNode groupList) {
       playerNode temp = groupList;
        while (temp != null) {
            if(temp.name.equalsIgnoreCase(name)) {
                return true;
            }
            temp = temp.next;
        }

        return false;
    }


    /* The following method determines if a name is among those still alive
     * in the assassin game. It should be noted that this method is case insentive,
     * meaning  someone named KeVIN is the same as kevin
     * @param String name - name you're looking for in list
     * @returns boolean, stating whether or not that name exist among those alive
    */
    public boolean killRingContains(String name) {
        return xContains(name, killRing);
    }


    /* The following method determines if a name is among those who have been "assasinated"
     * in our game of assassin. It should be noted that this method is case insentive,
     * meaning someone named KeVIN is treated the same as someone name kevin
     * @param String name - name you're looking for in list
     * @returns boolean, stating whether or not that name exist among those "assasinated"
    */
    public boolean graveyardContains(String name) {
         return xContains(name, graveYard);
    }

    /* The following determines if the game of Assassin is over
     * @returns boolean, to determine if the game of Assassin is over
    */
    public boolean gameOver() {
       return (killRing.next == null);
    }


    /* The following method winner, gives the name of the winner of our game of Asssain
     * It should be noted that if there is no winner this method will return a null value
     * @returns String - which will represent the name of our winner
    */
    public String winner() {
        if (gameOver()) { // if gameOver was true then well return killRing remaining node name
            return killRing.name;
        }
        return null;


    }


    /* The following method, adds a person whos been "killed", into our
        graveYard which keeps track of who dies and lives
        @param AssassinNode killedNode - The person who died
    */
    private void addToGraveyard (playerNode killedNode) {
        if (graveYard == null) { //make that first node
            graveYard = killedNode;
            graveYard.next = null;
        } else {
            killedNode.next = graveYard;
            graveYard = killedNode;

        }

    }



    /* The followin method, help me find the last person in killRing
     * @returns AssassinNode temp - the last person in our  killRing
    */
    private playerNode findLastNode() {
        playerNode temp = killRing;

        while (temp.next != null) {
            temp = temp.next;
        }

        return temp;

    }







    /* The following method kill, kills a person given there name. Adding them to
     * the graveYard (those who have been assasinated)
     * @throws IllegalStateException if the game is over and name is not part of players
     * @throws IllegalArgumentException if the given name is not part of those still alive
    */
    public void kill(String name) {
      if (gameOver()) {
         throw new IllegalStateException();
      }


      if (!killRingContains(name)){
         throw new IllegalArgumentException();
      }

      playerNode temp = killRing;
      playerNode xtraTemp = null;

      while (temp != null) {

         if (temp.next != null && temp.next.name.equalsIgnoreCase(name)) { // when we find
               temp.next.killer = temp.name; //set the kill
               xtraTemp = temp.next;
               temp.next = temp.next.next;
               addToGraveyard(xtraTemp);


        } else if (temp.name.equalsIgnoreCase(name) && temp.next != null){
               temp.killer = findLastNode().name;
               xtraTemp = temp;
               killRing = temp.next;
               addToGraveyard(xtraTemp);

        }

         temp = temp.next;
      }




    }




}
