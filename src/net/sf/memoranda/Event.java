/**
 * Event.java
 * Created on 08.03.2003, 12:21:40 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 *-----------------------------------------------------
 */
package net.sf.memoranda;
import java.util.Date;

import net.sf.memoranda.date.CalendarDate;

/**
 * 
 */
/*$Id: Event.java,v 1.4 2004/07/21 17:51:25 ivanrise Exp $*/
public interface Event {
    
    String getId();
    
    //CalendarDate getDate();
    
    /**
     * 
     * @return The integer representation of the hour when this event occurs.
     */
    int getHour();
    
    /**
     * 
     * @return The integer representation of the minute when this event occurs.
     */
    int getMinute();
    
    /**
     * 
     * @return The integer representation of the hour of the duration of this event.
     */
    int getDHour();
    
    /**
     * 
     * @return The integer representation of the minutes of the duration of this event.
     */
    int getDMinute();
    
    //Date getTime();
    
    /**
     * 
     * @return The details of this event as a String.
     */
    String getText();
    
    /**
     * 
     * @return The location of this event as a String.
     */
    String getLocation();
    
    /**
     * 
     * @return The participants of this event as a String.
     */
    String getParticipants();
    
    /**
     * 
     * @return The element that contains this event.
     */
    nu.xom.Element getContent();
    
    /**
     * 
     * @return The value associated with the key "repeat type"; an int from 0-5 that indicates the repeat frequency as defined in EventsManager.
     */
    int getRepeat();
    
    
    CalendarDate getStartDate();
    CalendarDate getEndDate();
    int getPeriod();
    boolean isRepeatable();
    
    Date getTime();
    Date getDuration();
    String getTimeString();
    
	boolean getWorkingDays();
    
}
