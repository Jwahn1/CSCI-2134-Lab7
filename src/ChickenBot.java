/**
   File: ChickenBot.java
   Author: Alex Brodsky
   Date: September 21, 2015
   Purpose: CSCI 1110, Assignment 4 Solution

   Description: This class specifies the ChickenBot class.
*/


/**
   This is the ChickenBot class for representing ChickenBots and their 
   behaviours on the planet DohNat.  
*/
public class ChickenBot extends TimBot {
  /** 
     This is the only constructor for this class.  This constructor 
     initializes the Tibot and sets its ID and the initial amount of
     energy that it has.

     parameter: id    : ID of the TimBot
                jolts : Initial amount of energy
   */
  public ChickenBot( int id, int jolts ) {
    super( id, jolts );
    personality = 'C';
  }

  /** 
     This method is called during the Move phase of each round and
     requires the TimBot to decide whether or not to move to another
     district.  If the TimBot wishes to move, it returns, District.NORTH,
     District.EAST, District.SOUTH, or District.WEST, indicating which 
     direction it wishes to move.  If it does not wish to move, it 
     returns District.CURRENT.

     returns: the one of District.NORTH, District.EAST, District.SOUTH, 
              District.WEST, or District.CURRENT
   */
  public int getNextMove() {
    // Allocate a scores array to score moves.  Lower score is better
    // score = 2000 * (bot sensed in district) + 1000 * (adjacent bots) + 
    //         spresso cont
    // We initially assume no robots are adjacent and initial move is to stay.
    int [] scores = new int[botsSensed.length];
    int adj = 0;
    int move = District.CURRENT;

    // If we have energy, consider moving
    if( energyLevel > 0 ) {
      // Loop through all possibilities and compute the scores.
      adj = getScores(scores, adj);
      // Only the current district can have an adjacency score.
      scores[District.CURRENT] += adj;

      // Choose the move with the minimum score
      move = minimumScore(scores, move);

      //if bot moves, then use energy
      useEnergyToMove(move);
    }
    return move;
  }
  // Loop through all possibilities and compute the scores.
  private int getScores(int[] scores, int adj) {
    for(int i = 0; i < scores.length; i++ ) {
      scores[i] = spressoSensed[i];
      if( ( i != District.CURRENT ) && botsSensed[i] ) {
        scores[i] += 2000;
        adj = 1000;
      }
    }
    return adj;
  }
}
