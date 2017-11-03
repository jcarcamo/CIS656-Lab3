package edu.gvsu.cis.cis656.clock;

import java.util.Hashtable;
import java.util.Map;

public class VectorClock implements Clock {

    // suggested data structure ...
    private Map<String,Integer> clock = new Hashtable<String,Integer>();


    @Override
    public void update(Clock other) {
    	for(String key:other.getClock().keySet()){
    		int pid = Integer.parseInt(key);
    		if(this.getTime(pid) < other.getTime(pid)){
    			clock.put(key, other.getTime(pid));
    		}
    	}
    }
    
    public Map<String,Integer> getClock(){
    	return this.clock;
    }

    @Override
    public void setClock(Clock other) {
    	this.clock = other.getClock();
    }

    @Override
    public void tick(Integer pid) {
    	String key = String.valueOf(pid);
    	clock.put(key,clock.get(key) + 1);
    }

    @Override
    public boolean happenedBefore(Clock other) {
        return false;
    }

    public String toString() {
        // TODO: you implement
        return null;
    }

    @Override
    public void setClockFromString(String clock) {

    }

    @Override
    public int getTime(int p) {
        String key = String.valueOf(p);
    	if(clock.containsKey(key)){
        	return clock.get(key);
        }
    	return 0;
    }

    @Override
    public void addProcess(int p, int c) {
    	String key = String.valueOf(p);
    	clock.put(key, c);
    	
    }
}
