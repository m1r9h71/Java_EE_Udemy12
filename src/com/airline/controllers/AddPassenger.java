package com.airline.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.airline.models.Gender;
import com.airline.models.Passenger;

/**
 * Servlet implementation class AddPassenger
 */
@WebServlet("/AddPassenger")
public class AddPassenger extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPassenger() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//the first time the info boxes are displayed make the first name blank not null
		request.setAttribute("first_name", "");
		request.setAttribute("last_name", "");
		request.setAttribute("dob", "");

		
		
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/add_passenger.jsp");
		
		view.forward(request,  response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		request.setAttribute("errors", false);
		
		Passenger p = new Passenger();
		
		String firstName = request.getParameter("first-name");
		System.out.println("first Name:" + firstName);
		
		if(firstName.length() == 0) {
			System.out.println("Empty first name error");
			
			request.setAttribute("errors", true);
			request.setAttribute("firstname_error", true);
			//Initially set the first name box to blank not null
			request.setAttribute("first_name", "");
					
		}else {
			p.setFirstName(firstName);
			request.setAttribute("first_name", firstName);
		}
		
		String lastName = request.getParameter("last-name");
		System.out.println("last Name:" + lastName);
		
		if(lastName.length() == 0) {
			System.out.println("Empty first name error");
			
			request.setAttribute("errors", true);
			request.setAttribute("lastname_error", true);
			request.setAttribute("last_name", "");
					
		}else {
			p.setLastName(lastName);
			request.setAttribute("last_name", lastName);
		}

		String dob_raw = request.getParameter("dob");
		//format dob regard / as part of the date format
		String dobArray[] = dob_raw.split("\\/");
		//dob pattern
		String pattern = "^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$";
		Pattern r = Pattern.compile(pattern);
		
		Matcher m = r.matcher(dob_raw);
		
		if(m.find()) {
			//put day month and year in an array
			String month = dobArray[0];
			String day = dobArray[1];
			String year = dobArray[2];
			
			Calendar cal = Calendar.getInstance();
			
			cal.set(Calendar.YEAR, Integer.parseInt(year));
			cal.set(Calendar.MONTH, Integer.parseInt(month));
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
			
			Date dob = cal.getTime();
			System.out.println(dob);
			
			p.setDob(dob);
			request.setAttribute("dob", dob_raw);
		}
		else {
			//if user types invalid dob
			System.out.println("Invalid date of birth");
			//set errors variable to true
			request.setAttribute("errors", true);
			
			request.setAttribute("date_format_error", true);
			request.setAttribute("dob", dob_raw);
			//if user leaves dob empty make it an empty string not null
			if(dob_raw.length() == 0) {
				request.setAttribute("dob", "");
			}
		}
		String gender = request.getParameter("gender");
		System.out.println("Gender: " + gender);
		p.setGender(Gender.valueOf(gender));
		
		if((boolean)request.getAttribute("errors")){
				
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/add_passenger.jsp");
			
			view.forward(request,  response);
		
		}
		else {
			
			ServletContext sc = this.getServletContext();
			
			synchronized (this) {
			
			ArrayList<Passenger> pList =  (ArrayList<Passenger>) sc.getAttribute("passengers");
			
			pList.add(p);
			
			sc.setAttribute("passengers", pList);
			}
			
			response.sendRedirect("");
			
		}
		
		
		
	}

}
