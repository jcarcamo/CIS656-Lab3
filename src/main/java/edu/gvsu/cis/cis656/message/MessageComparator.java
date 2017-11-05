package edu.gvsu.cis.cis656.message;

import java.util.Comparator;

import edu.gvsu.cis.cis656.clock.VectorClockComparator;

/**
 * Message comparator class. Use with PriorityQueue.
 */
public class MessageComparator implements Comparator<Message> {

    @Override
    public int compare(Message lhs, Message rhs) {
    	VectorClockComparator comparator = new VectorClockComparator();
        return comparator.compare(lhs.ts, rhs.ts);
    }

}
