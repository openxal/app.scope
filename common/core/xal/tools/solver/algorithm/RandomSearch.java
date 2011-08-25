/*
 *  RandomSearch.java
 *
 *  Created Monday June 28 2004 12:23pm
 *
 *  Copyright 2003, Spallation Neutron Source
 *  Oak Ridge National Laboratory
 *  Oak Ridge, TN 37830
 */
package xal.tools.solver.algorithm;

import xal.tools.solver.*;
import xal.tools.solver.solutionjudge.*;

import java.util.*;

/**
 * RandomSearch returns a random trial point that lies within the bounds specified by the
 * variables.
 *
 * @author   ky6
 */
public class RandomSearch extends SearchAlgorithm {
	/** random number generator */
	protected Random _randomGenerator;


	/** Creates a new instance of RandomSearch */
	public RandomSearch() {
		_randomGenerator = new Random( 0 );
	}


	/**
	 * Generate a random variable between the upper and lower limits.
	 * @param variable  The variable whose limits we are using.
	 * @return          A double random variable.
	 */
	private double generateRandomValue( final Variable variable ) {
		double lowerLimit = variable.getLowerLimit();
		double upperLimit = variable.getUpperLimit();

		return lowerLimit + _randomGenerator.nextDouble() * ( upperLimit - lowerLimit );
	}


	/**
	 * Return the label for a search algorithm.
	 * @return   The trial point.
	 */
	public String getLabel() {
		return "Random Search";
	}
	
	
	/**
	 * Calculate the next few trial points.
	 * @param algorithmRun the algorithm run to perform the evaluation
	 */
	public void performRun( final AlgorithmRun algorithmRun ) {
		try {
			algorithmRun.evaluateTrialPoint( nextTrialPoint() );
		}
		catch ( RunTerminationException exception ) {}
	}
	

	/**
	 * Return the next trial point.
	 * @return   trialPoint
	 */
	public TrialPoint nextTrialPoint() {
		Map values = new HashMap();
		Iterator variableIter = _problem.getVariables().iterator();
		while ( variableIter.hasNext() ) {
			Variable variable = (Variable)variableIter.next();
			double value = generateRandomValue( variable );
			values.put( variable, new Double( value ) );
		}
		return new TrialPoint( values );
	}


	/**
	 * Returns the global rating which in an integer between 0 and 10.
	 * @return   The global rating for this algorithm.
	 */
	public int globalRating() {
		return 10;
	}


	/**
	 * Returns the local rating which is an integer between 0 and 10.
	 * @return   The local rating for this algorithm.
	 */
	public int localRating() {
		return 0;
	}


	/**
	 * Handle a message that an algorithm is available.
	 * @param source  The source of the available algorithm.
	 */
	public void algorithmAvailable( SearchAlgorithm source ) { }


	/**
	 * Handle a message that an algorithm is not available.
	 * @param source  The source of the available algorithm.
	 */
	public void algorithmUnavailable( SearchAlgorithm source ) { }


	/**
	 * Handle a message that a trial has been scored.
	 * @param trial              The trial that was scored.
	 * @param schedule           the schedule providing this event
	 */
	public void trialScored( AlgorithmSchedule schedule, Trial trial ) { }


	/**
	 * Handle a message that a trial has been vetoed.
	 * @param trial              The trial that was vetoed.
	 * @param schedule           the schedule providing this event
	 */
	public void trialVetoed( AlgorithmSchedule schedule, Trial trial ) { }


	/**
	 * Handle a message that a new optimal solution has been found.
	 * @param source     The source of the new optimal solution.
	 * @param solutions  The list of solutions.
	 * @param solution   The new optimal solution.
	 */
	public void foundNewOptimalSolution( SolutionJudge source, List<Trial> solutions, Trial solution ) { }
}

