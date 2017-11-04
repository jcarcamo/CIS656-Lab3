package edu.gvsu.cis.cis656.clock;

import java.util.Hashtable;
import java.util.Map;

import org.json.JSONObject;

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
        boolean hasHappenedBefore = true;
        for(String key:other.getClock().keySet()){
    		int pid = Integer.parseInt(key);
    		if(other.getTime(pid) < this.getTime(pid) ){
    			hasHappenedBefore = false;
    		}
    	}
    	return hasHappenedBefore;
    }

    public String toString() {
    	JSONObject obj = new JSONObject(clock);
        return obj.toString();
    }

    @Override
    public void setClockFromString(String clock) {
    	//"{\"0\":2,\"1\":0,\"2\":0 }",
    	String prevClock = toString();
    	boolean isValidClock = true;
    	
    	this.clock.clear();
    	JSONObject obj = new JSONObject(clock);
    	for(String key:obj.keySet()){
    		if(obj.get(key) instanceof Integer){
    			addProcess(Integer.parseInt(key), obj.getInt(key));
    		}else{
    			isValidClock = false;
    			break;
    		}
    	}
    	if(!isValidClock){
    		setClockFromString(prevClock);
    	}
    }

    @Override
    public int getTime(int p) {
        String key = String.valueOf(p);
        return clock.getOrDefault(key, 0);
    }

    @Override
    public void addProcess(int p, int c) {
    	String key = String.valueOf(p);
    	clock.put(key, c);
    	
    }
}
