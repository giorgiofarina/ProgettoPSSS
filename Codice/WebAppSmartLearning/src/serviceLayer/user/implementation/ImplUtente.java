package serviceLayer.user.implementation;

import java.util.Vector;

import dataLayer.lezione.controller.ControllerLezioneDB;
import dataLayer.lezione.entities.LezioneDB;
import dataLayer.topic.controller.ControllerTopicDB;
import dataLayer.topic.entities.TopicDB;
import dataLayer.user.controller.ControllerUtenteDB;
import dataLayer.user.entities.UtenteDB;
import dataLayer.utilities.StateResult;
import dataLayer.utilities.idUser;
import serviceLayer.user.interfaces.IUtente;

public class ImplUtente implements IUtente {

	@Override
	public StateResult getLessonsByCognome(Vector<String> str, Vector<LezioneDB> lezioni) {
		// TODO Auto-generated method stub
		
		ControllerUtenteDB controller = new ControllerUtenteDB();
		ControllerTopicDB controllertopic = new ControllerTopicDB();
		
		
		StateResult result = controller.getLessonsByCognome(str.get(0), lezioni);
		
		if(lezioni.size() > 0) {
		UtenteDB utente = new UtenteDB();
		controller.retrieveUser(lezioni.get(0).getIdUtente(), utente);
		str.add(utente.getNome());
		
		for (int i = 0; i<lezioni.size(); i++) {
			TopicDB topicvar = new TopicDB(lezioni.get(i).getIdTopic());
			controllertopic.getTopicName(topicvar);
	
			str.add(topicvar.getNome());
		}
		
		}
		
		
		//System.out.println("[IMPLUTENTE] nome: "+ str.get(1)+" topic: "+str.get(2));
		return result;
	}

	@Override
	public StateResult getUserDataById(UtenteDB utente) {
		// TODO Auto-generated method stub
		
		ControllerUtenteDB controller = new ControllerUtenteDB();
		
		StateResult result = controller.retrieveUser(utente.getId(), utente);
		
		return result;
	}

	@Override
	public StateResult upgradeDocente(UtenteDB utente) {
		// TODO Auto-generated method stub
		
		ControllerUtenteDB controller = new ControllerUtenteDB();
		
		StateResult result = controller.createDocente(utente);
		
		return result;
	}
	
	@Override
	public StateResult validateUser(idUser id) {
		// TODO Auto-generated method stub
		
		ControllerUtenteDB controller = new ControllerUtenteDB();
		
		StateResult result = controller.validateUser(id);
		
		return result;
	}
	
	@Override
	public StateResult validateDocente(idUser id) {
		// TODO Auto-generated method stub
		
		ControllerUtenteDB controller = new ControllerUtenteDB();
		
		StateResult result = controller.validateDocente(id);
		
		return result;
	}
	
	@Override
	public StateResult getLessonsById(idUser myid, Vector<String> str, Vector<LezioneDB> lezioni) {
		// TODO Auto-generated method stub
		
		ControllerLezioneDB controller = new ControllerLezioneDB();
		ControllerTopicDB controllertopic = new ControllerTopicDB();
		
		StateResult result = controller.getLessonsByDocente(myid, lezioni);
		
		for (int i = 0; i<lezioni.size(); i++) {
			TopicDB topicvar = new TopicDB(lezioni.get(i).getIdTopic());
			controllertopic.getTopicName(topicvar);
		
			str.add(topicvar.getNome());
		}
		
		return result;
	}
	

}