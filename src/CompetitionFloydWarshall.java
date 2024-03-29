import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * A Contest to Meet (ACM) is a reality TV contest that sets three contestants at three random
 * city intersections. In order to win, the three contestants need all to meet at any intersection
 * of the city as fast as possible.
 * It should be clear that the contestants may arrive at the intersections at different times, in
 * which case, the first to arrive can wait until the others arrive.
 * From an estimated walking speed for each one of the three contestants, ACM wants to determine the
 * minimum time that a live TV broadcast should last to cover their journey regardless of the contestants
 * initial positions and the intersection they finally meet. You are hired to help ACM answer this question.
 * You may assume the following:
 *     Each contestant walks at a given estimated speed.
 *     The city is a collection of intersections in which some pairs are connected by one-way
 *	   streets that the contestants can use to traverse the city.
 *
 * This class implements the competition using Floyd-Warshall algorithm
 */

public class CompetitionFloydWarshall {

	double[][] distTo;
	int speedA, speedB, speedC;
	
    /**
     * @param filename: A filename containing the details of the city road network
     * @param sA, sB, sC: speeds for 3 contestants
     */
    CompetitionFloydWarshall (String filename, int sA, int sB, int sC)
    {
        speedA = sA;
        speedB = sB;
        speedC = sC;
        try 
        {
        	File inputFile = new File(filename);
        	Scanner fileScanner = new Scanner(inputFile);
        	int numberOfVertices = fileScanner.nextInt();
        	distTo = new double[numberOfVertices][numberOfVertices] ;
        	fileScanner.nextLine();
        	fileScanner.nextLine();
        	for(int i = 0 ; i < numberOfVertices ; i++)
        	{
        		for(int j = 0 ; j < numberOfVertices; j++)
        		{
        			if(j == i)
        			{
        				distTo[i][j] = 0;
        			} 
        			else distTo[i][j] = Double.MAX_VALUE;
        		}
        	}
        	while(fileScanner.hasNextLine())
        	{
        		distTo[fileScanner.nextInt()][fileScanner.nextInt()] = fileScanner.nextDouble();
        		if(fileScanner.hasNextLine()) fileScanner.nextLine();
        	}
        fileScanner.close();
        } catch (FileNotFoundException e) {} catch (NullPointerException e) {}
    }


    /**
     * @return int: minimum minutes that will pass before the three contestants can meet
     */
    public int timeRequiredforCompetition()
    {
    	if(speedA < 50 || speedB < 50 || speedC < 50 || speedA > 100 || speedB > 100 || speedC > 100 || distTo.length == 0)
    	{
    		return -1;
    	}
    	double minimumMinutesToMeeting = -1;
    	double longestShortestDistance = -1;
    	int slowestSpeed = Math.min(speedA, Math.min(speedB, speedC));
    	runFloydWarshall();
    	for(int i = 0 ; i < distTo.length ; i++)
    	{
    		for(int j = 0 ; j < distTo[i].length ; j++)
    		{
    			if(distTo[i][j] == Double.MAX_VALUE)
    			{
    				return -1;
    			}
    			longestShortestDistance = Math.max(longestShortestDistance, distTo[i][j]);
    		}
    	}
    	minimumMinutesToMeeting = longestShortestDistance*1000/slowestSpeed;
    	return (int)Math.ceil(minimumMinutesToMeeting);
    }


	private void runFloydWarshall() {
		for(int k = 0 ; k < distTo.length ; k++)
		{
			for(int i = 0 ; i < distTo.length ; i++)
			{
				for(int j = 0 ; j < distTo[i].length ; j++)
				{
					if(distTo[i][k] + distTo[k][j] < distTo[i][j])
					{
						distTo[i][j] = distTo[i][k] + distTo[k][j];
					}
				}
			}
		}
		
	}

}