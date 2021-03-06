package serviceLayer.videoroom.implementation;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;

import dataLayer.lezione.controller.ControllerLezioneDB;
import dataLayer.lezione.entities.FasciaOraria;
import dataLayer.pagamento.controller.ControllerPagamentoDB;
import dataLayer.pagamento.entities.PagamentoDB;
import dataLayer.videoroom.controller.ControllerVideoRoomDB;
import dataLayer.videoroom.entities.VideoRoomDB;
import serviceLayer.lezione.implementation.ImplLezione;
import serviceLayer.videoroom.IVideoRoom;
import utilities.StateResult;
import utilities.idFasciaOraria;
import utilities.idUser;

public class ImplVideoRoom implements IVideoRoom{
	
	@Override
	public StateResult deleteVideoRoom(idFasciaOraria id) {
		ControllerVideoRoomDB controllerVideoRoom = new ControllerVideoRoomDB();
		if(controllerVideoRoom.removeRoom(id)==StateResult.REMOVED) {
			return StateResult.REMOVED;
		}
		return StateResult.NOREMOVED;
	}

	

	public StateResult startVideoRoom(String idFasciaOraria, String nomeRoom, String[] tokenDocente, Vector <String> tokens) {
		// TODO Auto-generated method stub
		ControllerVideoRoomDB contVideoRoom = new ControllerVideoRoomDB();
		ControllerPagamentoDB contPagamento = new ControllerPagamentoDB();
		 
		
		idFasciaOraria idFascia = new idFasciaOraria(Integer.parseInt(idFasciaOraria));
		
		VideoRoomDB  videoRoom = new VideoRoomDB();
		videoRoom.setNomeRoom(nomeRoom);
		
		
		if(contPagamento.thereAreUsersPayedLesson(idFascia)==StateResult.VALID && contVideoRoom.createNewRoom(idFascia, videoRoom)==StateResult.CREATED) {
			//System.out.println("start_1");
			//tokens.add(videoRoom.getPasswordRoom());
			tokenDocente[0] = videoRoom.getPasswordRoom();
			Vector<PagamentoDB> payments = new Vector<PagamentoDB>();
			if( contPagamento.genAndGetTokens(idFascia, payments) ==StateResult.UPDATED) {
				//System.out.println("start_2");
				for (int i=0; i<payments.size();i++) {
					tokens.add(payments.get(i).getToken());
				}
				
					return StateResult.CREATED;
				
			}else {
				//delete video room
				contVideoRoom.removeRoom(idFascia);
				System.out.println("Rollback:RooomEliminata");
			}
			
		}
		
		return StateResult.NOCHANGES;
	}
	
	
	
	public StateResult getJoinUserData(String idFasciaOraria, String idUtente, String[] tokenUtente, String[] nomeRoom) {
		// TODO Auto-generated method stub
		ControllerVideoRoomDB controllerV = new ControllerVideoRoomDB();
		VideoRoomDB room = new VideoRoomDB();
		ControllerPagamentoDB controllerP = new ControllerPagamentoDB();
		PagamentoDB pagam = new PagamentoDB(new idFasciaOraria(Integer.parseInt(idFasciaOraria)), new idUser(Integer.parseInt(idUtente)));
		StateResult result;
		
		result = controllerV.getRoom(new idFasciaOraria(Integer.parseInt(idFasciaOraria)), room);
		
		
		if(result != StateResult.VALID) {
			return result;
		}
		
		result = controllerP.getTokenByUtente(pagam);
		if(result != StateResult.VALID) {
			return result;
		}
		
		nomeRoom[0]  = room.getNomeRoom();
		System.out.println("getRoom: "+result.toString()+""+room.getNomeRoom());
		tokenUtente[0] = pagam.getToken();
		
		return result;
	}
	
	
	


	public StateResult verifyFasciaOrariaIsInProgress(FasciaOraria fascia) {
		
		System.out.println("verifyFasciaOrariaIsInProgress");
		SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
		sdformat.setTimeZone(TimeZone.getTimeZone("Europe/Rome"));
		Date date = new Date();
		System.out.println("verifyFasciaOrariaIsInProgress1");
		Date d1;
		Date d2;
		try {
			d1 = sdformat.parse(sdformat.format(date));
			
			d2 = sdformat.parse(sdformat.format(fascia.getDataLezione()));		
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return StateResult.NOVALID;
		}
		System.out.println("comparo le seguenti date: "+sdformat.format(d1)+", "+ sdformat.format(d2));
		//sdformat.format(fascia.getDataLezione());
		//sdformat.format(date);
		
		if (d1.compareTo(d2)==0) {
			//le date sono uguali
			String orarioFine = SimpleDateFormat.getTimeInstance(SimpleDateFormat.MEDIUM, Locale.UK).format(fascia.getOrarioFine());
			String orarioInizio = SimpleDateFormat.getTimeInstance(SimpleDateFormat.MEDIUM, Locale.UK).format(fascia.getOrarioInizio());
			LocalTime now = LocalTime.now(ZoneId.of("Europe/Rome"));
			System.out.println("orario Inizio "+now+"  "+orarioInizio+now.isAfter(LocalTime.parse( orarioInizio))+"\n OrarioFIne"+orarioFine+now.isBefore(LocalTime.parse( orarioFine) ));
			
			if(now.isAfter(LocalTime.parse( orarioInizio)) && now.isBefore(LocalTime.parse( orarioFine ))) {
				return StateResult.VALID;
			}
			
		}
		return StateResult.NOVALID;
		
	}

	
	public String genNomeRoom(String idFasciaOraria, String idDocente) {
		// TODO Auto-generated method stub
		
		return idFasciaOraria;
	}
	
	
	
	
	public StateResult deleteVideoRoom(String idFasciaOraria, String idDocente){
		FasciaOraria fascia = new FasciaOraria();
		fascia.setId(new utilities.idFasciaOraria(Integer.parseInt(idFasciaOraria)));
		ImplLezione implLezione = new ImplLezione();
		ImplVideoRoom implVideoRoom = new ImplVideoRoom();
		
		if (implLezione.verifyDocenteHasFasciaOraria(idDocente, fascia)==StateResult.VALID) {
			if(implVideoRoom.deleteVideoRoom(fascia.getId())==StateResult.REMOVED) {
				return StateResult.REMOVED;
				
			}else {
				return StateResult.NOREMOVED;
			}
			
		}else {
			return StateResult.NOREMOVED;
		}
	}
		
	public StateResult avviaVideoRoom(String idFasciaOraria, String idDocente,String[] nomeRoom, String[] tokenDocente, Vector <String> tokens){
		FasciaOraria fascia = new FasciaOraria();
		fascia.setId(new utilities.idFasciaOraria(Integer.parseInt(idFasciaOraria)));
		ImplLezione implLezione = new ImplLezione();
		if (implLezione.verifyDocenteHasFasciaOraria(idDocente, fascia)==StateResult.VALID && verifyFasciaOrariaIsInProgress(fascia) == StateResult.VALID) {
			nomeRoom[0] = genNomeRoom(idFasciaOraria, idDocente);
			if (startVideoRoom(idFasciaOraria,nomeRoom[0], tokenDocente, tokens)==StateResult.CREATED) {
				return StateResult.CREATED;
			
			}
		}
		
		return StateResult.NOCHANGES;
	}
	

	
	
	
}
