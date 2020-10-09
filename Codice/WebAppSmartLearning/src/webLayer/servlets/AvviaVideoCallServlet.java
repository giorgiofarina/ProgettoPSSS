package webLayer.servlets;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataLayer.lezione.entities.FasciaOraria;
import dataLayer.utilities.StateResult;

import serviceLayer.videoroom.implementation.ImplVideoRoom;

/**
 * Servlet implementation class AvviaVideoCallServlet
 */
@WebServlet("/AvviaVideoCallServlet")
public class AvviaVideoCallServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AvviaVideoCallServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("AVVIA VIDEO CHIAMATA");
		ImplVideoRoom implVideoRoom = new ImplVideoRoom();
		String[] tokenDocente = new String[1]; ;
		Vector<String> tokens = new Vector<String>();
		
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String idFasciaOraria = request.getParameter("idprog");
		String idDocente = request.getParameter("requesterId");
		FasciaOraria fascia = new FasciaOraria();
		fascia.setId(new dataLayer.utilities.idFasciaOraria(Integer.parseInt(idFasciaOraria)));
		
		StringBuffer xmlReply = new StringBuffer();
		
		if (implVideoRoom.verifyDocenteHasFasciaOraria(idDocente, fascia)==StateResult.VALID && implVideoRoom.verifyFasciaOrariaIsInProgress(fascia) == StateResult.VALID) {
			String nomeRoom = implVideoRoom.genNomeRoom(idFasciaOraria, idDocente);
			
			
			if (implVideoRoom.startVideoRoom(idFasciaOraria,nomeRoom, tokenDocente, tokens)==StateResult.CREATED) {
				xmlReply.append("<risposta><risultato>VideoCallCreata</risultato><nomeRoom>"+nomeRoom+"</nomeRoom><tokenDocente>"+tokenDocente[0]+"</tokenDocente><tokenUtente>");
				for (int i=0; i<tokens.size();i++) {
					xmlReply.append("<token>"+tokens.get(i)+"</token>");
				}
				xmlReply.append("</tokenUtente></risposta>");
				response.setContentType("text/xml");
				System.out.println("XML: "+ xmlReply.toString());
				response.getWriter().write(xmlReply.toString());
			}else {
				xmlReply.append("<risposta><risultato>VideoCallNonCreata</risultato></risposta>");
				response.setContentType("text/xml");
				response.getWriter().write(xmlReply.toString());
				System.out.println("XML: "+ xmlReply.toString());
			}
		}else {
			xmlReply.append("<risposta><risultato>VideoCallNonCreata</risultato></risposta>");
			response.setContentType("text/xml");
			response.getWriter().write(xmlReply.toString());
			System.out.println("XML: "+ xmlReply.toString());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}