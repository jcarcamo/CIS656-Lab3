package edu.gvsu.cis.cis656.clock;

import java.util.Comparator;


public class VectorClockComparator implements Comparator<VectorClock> {

    @Override
    public int compare(VectorClock lhs, VectorClock rhs) {
        int result = -1;
    	for(String key:rhs.getClock().keySet()){
        	int pid = Integer.parseInt(key);
    		if(rhs.getTime(pid) < lhs.getTime(pid)){
    			result = 1;
    		}
        }
    	return result;
    }
}
