package serviceLayer.lezione.implementation;

import java.util.Vector;

import dataLayer.lezione.controller.ControllerLezioneDB;
import dataLayer.lezione.entities.FasciaOraria;
import dataLayer.lezione.entities.LezioneDB;
import dataLayer.topic.controller.ControllerTopicDB;
import dataLayer.topic.entities.TopicDB;
import dataLayer.user.controller.ControllerUtenteDB;
import dataLayer.user.entities.UtenteDB;
import dataLayer.utilities.StateResult;
import dataLayer.utilities.idFasciaOraria;
import dataLayer.utilities.idLesson;
import dataLayer.utilities.idUser;
import serviceLayer.lezione.ILezione;

public class ImplLezione implements ILezione {

	public StateResult creaLezione(idUser iduser, String nome, String descrizione, String nomeTopic, int nmax) {
		// TODO Auto-generated method stub
		
		ControllerLezioneDB controller = new ControllerLezioneDB();
		LezioneDB lezione;
		
		if(descrizione == "") {
			lezione = new LezioneDB(iduser,nome,nmax);
		}else {
			lezione = new LezioneDB(iduser,nome, descrizione,nmax);
		}
		StateResult result = controller.createLesson(lezione, nomeTopic);
		
		return result;
	}

	@Override
	public StateResult getLessonsByTopic(Vector<String> str, Vector<LezioneDB> lezioni) {
		// TODO Auto-generated method stub
		ControllerTopicDB controllertopic = new ControllerTopicDB();
		ControllerUtenteDB controller = new ControllerUtenteDB();
		
		StateResult result = controllertopic.getLessonsByTopicName(str.get(0), lezioni);
		
		if(lezioni.size() > 0) {
			UtenteDB utente = new UtenteDB();
			controller.retrieveUser(lezioni.get(0).getIdUtente(), utente);
			str.add(utente.getCognome());
			str.add(utente.getNome());
		}
		
		return result;
	}
	
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
	

	@Override
	public StateResult addFasciaOraria(idUser iduser, idLesson idlesson, FasciaOraria fascia) {
		// TODO Auto-generated method stub
		
		ControllerLezioneDB controller = new ControllerLezioneDB();
		
		StateResult result = controller.addFasciaOraria( iduser, idlesson, fascia);
		
		return result;
	}

	@Override
	public StateResult getFasceOrarie(idLesson idlesson, Vector<FasciaOraria> fasce) {
		// TODO Auto-generated method stub
		ControllerLezioneDB controller = new ControllerLezioneDB();
		LezioneDB lezione = new LezioneDB(idlesson);
		Vector<LezioneDB> lezioneV = new Vector<LezioneDB>();
		lezioneV.add(lezione);
		
		StateResult result = controller.attachSlotsToLessonsDocente(lezioneV);
		
		if(result == StateResult.DBPROBLEM) {
			return StateResult.DBPROBLEM;
		}
		
		for(int i = 0; i < lezioneV.get(0).getSlots().size(); i++) {
			fasce.add(lezioneV.get(0).getSlots().get(i));
		}
		
		if(lezioneV.get(0).getSlots().size() > 0) {
			return StateResult.VALID;
		}else {
			return StateResult.NOVALID;
		}
	}
	

	@Override
	public StateResult removeFasciaById(idFasciaOraria id) {
		// TODO Auto-generated method stub
		ControllerLezioneDB controller = new ControllerLezioneDB();
		
		StateResult result = controller.removeFasciaById(id);
		
		
		return result;
	}

	@Override
	public StateResult getPayedLessons(idUser iduser, Vector<String> topics, Vector<LezioneDB> lezioni) {
		// TODO Auto-generated method stub
		
		ControllerLezioneDB controller = new ControllerLezioneDB();
		ControllerTopicDB controllertopic = new ControllerTopicDB();
		
		StateResult result = controller.getLessonsPayedStillUp(iduser, lezioni);
		
		for (int i = 0; i<lezioni.size(); i++) {
			TopicDB topicvar = new TopicDB(lezioni.get(i).getIdTopic());
			controllertopic.getTopicName(topicvar);
	
			topics.add(topicvar.getNome());
		}
		
		return result;
	}
	
	@Override
	public StateResult getPayedFasceByLesson(idUser iduser, idLesson idlez, Vector<FasciaOraria> fasce) {
		// TODO Auto-generated method stub
		
		ControllerLezioneDB controller = new ControllerLezioneDB();
		
		StateResult result = controller.getFascePayedStillUpByLesson(iduser, idlez, fasce);
		

		return result;
	}

}
