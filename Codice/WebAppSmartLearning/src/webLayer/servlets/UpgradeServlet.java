package webLayer.servlets;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataLayer.lezione.entities.LezioneDB;
import dataLayer.user.entities.UtenteDB;
import dataLayer.utilities.StateResult;
import dataLayer.utilities.idUser;
import serviceLayer.registration.implementation.ImplRegistrazione;
import serviceLayer.user.implementation.ImplUtente;


public class UpgradeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpgradeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		

		
		
		UtenteDB utente = new UtenteDB();
		ImplUtente iutente = new ImplUtente();
		utente.setId(new idUser(Integer.parseInt(request.getParameter("requesterId"))));
		utente.setContoPaypal(request.getParameter("email"));
		utente.setCurriculum(request.getParameter("curriculum"));

		StateResult result = iutente.upgradeDocente(utente);
		
		StringBuffer xmlReply = new StringBuffer();
		
		System.out.println(result.toString());
		
		if(result == StateResult.CREATED) {
			
			xmlReply.append("<risposta>utenteAggiornato</risposta>");
			response.setContentType("text/xml");
			response.getWriter().write(xmlReply.toString());
			
		}else if (result == StateResult.NOCHANGES) {
			
			xmlReply.append("<risposta>utenteNonAggiornato</risposta>");
			response.setContentType("text/xml");
			response.getWriter().write(xmlReply.toString());
			
		}else {
			
			xmlReply.append("<risposta>errore</risposta>");
			response.setContentType("text/xml");
			response.getWriter().write(xmlReply.toString());
		}

	}

}
