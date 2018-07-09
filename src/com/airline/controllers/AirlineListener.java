package com.airline.controllers;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.airline.models.Passenger;

/**
 * Application Lifecycle Listener implementation class AirlineListener
 *
 */
@WebListener
public class AirlineListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public AirlineListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  { 
         // TODO Auto-generated method stub
    	
    	ServletContext sc = event.getServletContext();
    	
    	ArrayList<Passenger> pList = (ArrayList<Passenger>) sc.getAttribute("passengers");
    	
    	if(pList == null) {
    		System.out.println("No passenger list created yet, let's creat the list here!");
    		
    		pList = new ArrayList<Passenger>();
    		
    		sc.setAttribute("passengers", pList);
    	}
    	
    }
	
}
