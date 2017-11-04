package edu.gvsu.cis.cis656.clock;

import java.util.Comparator;


public class VectorClockComparator implements Comparator<VectorClock> {

    @Override
    public int compare(VectorClock lhs, VectorClock rhs) {
        int result = 0;
    	if(lhs.happenedBefore(rhs)) {
    		result = -1;
    	}else if(rhs.happenedBefore(lhs)) {
    		result = 1;
    	}
    	return result;
    }
}
