/*
 * Correlation.java
 *
 * Created on July 1, 2002, 10:13 AM
 */

package xal.tools.correlator;

import xal.tools.statistics.UnivariateStatistics;

import java.util.*;

/**
 * Correlation is a generic container of correlated records.
 *
 * @author  tap
 */
public class Correlation {
    private Map<String,Object> recordTable;
    private UnivariateStatistics timeStatistics;
    
	
    /** Creates new Correlation */
    public Correlation( final Map<String,Object> newRecordTable, final UnivariateStatistics newTimeStatistics ) {
        recordTable = Collections.<String,Object>unmodifiableMap( new HashMap<String,Object>( newRecordTable ) );
        timeStatistics = new UnivariateStatistics( newTimeStatistics );
    }
    
    
    /** 
	 * Check if the named source is among the correlated.
	 * @param sourceName A name uniquely identifying a source (and record) 
	 * @return true if the sourceName identifies a record in this correlation and false otherwise.
	 */
    public boolean isCorrelated( final String sourceName ) {
        return names().contains( sourceName );
    }
    
    
    /** 
	 * Get the number of records correlated.
	 * @return the number of records correlated.
	 */
    public int numRecords() {
        return recordTable.size();
    }
    
    
    /** 
	 * Get the collection of names each of which identifies a record.
	 * @return The collection of names for correlated records.
	 */
    public Collection<String> names() {
        return recordTable.keySet();
    }
    
    
    /** 
     * Get a collection of the records in the correlation. 
     * @return Collection of records in the correlation.
     * @see #getRecord
     */
    final public Collection<Object> getRecords() {
        return recordTable.values();
    }
    
    
    /** 
     * Get the record identified by the source name (same one registered with the correlator).  
     * In general this record can be any class that the specific implementation chooses to make.  
     * The record holds the value and time stamp of the specific measurement (e.g. PV timestamp and value).
     * For a ChannelCorrelator, this method returns a ChannelTimeRecord.
	 * @param name The name that identifies the desired record.
	 * @return The record corresponding to the specified name.
     */
    final public Object getRecord( final String name ) {
        return recordTable.get( name );
    }
	
	
	/**
	 * Check whether this correlation contains all of the records of a specified correlation.
	 * @param correlation The correlation to test for being contained within <code>this</code>
	 * @return true if this correlation contains all of the records of the specified correlation and false otherwise.
	 */
	public boolean contains( final Correlation correlation ) {
		return getRecords().containsAll( correlation.getRecords() );
	}
	
    
    /** 
	 * Average time of the time stamps from the records in seconds since the Java epoch
	 * @return The mean time in seconds.
	 */
    public double meanTimeInSeconds() {
        return timeStatistics.mean();
    }
    
    
    /** 
	 * Convenience method to get a Java date for a given time stamp by averaging the dates of the records.
	 * @return The mean date of this correlation.
	 */
    public Date meanDate() {
        double seconds = meanTimeInSeconds();
        long milliseconds = (long) (1000 * seconds);
        
        return new Date(milliseconds);
    }
    
    
    /** 
	 * String representation of the correlation useful for printing
	 * @return The string representation of this correlation.
	 */
    public String toString() {
        final StringBuffer buffer = new StringBuffer();
        final Set<Map.Entry<String,Object>> recordEntries = recordTable.entrySet();
        
        for ( final Map.Entry<String,Object> entry : recordEntries ) {
            buffer.append( "name: " + entry.getKey() + ", " );
            buffer.append( entry.getValue() );
            buffer.append( "\n" );
        }
        
        return buffer.toString();
    }
}
