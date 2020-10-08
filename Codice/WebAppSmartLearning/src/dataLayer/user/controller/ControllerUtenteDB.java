package dataLayer.user.controller;

import java.io.File;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

import dataLayer.connectorManager.DBConnectionManager;
import dataLayer.lezione.controller.ControllerLezioneDB;
import dataLayer.lezione.entities.FasciaOraria;
import dataLayer.lezione.entities.LezioneDB;
import dataLayer.user.entities.UtenteDB;
import dataLayer.user.entities.result.ResultUtente;
import dataLayer.user.interfaces.API_UtenteDB;
import dataLayer.utilities.StateResult;
import dataLayer.utilities.idLesson;
import dataLayer.utilities.idTopic;
import dataLayer.utilities.idUser;

public class ControllerUtenteDB implements API_UtenteDB{

	


	
	public StateResult validateUser(idUser id) {
		// TODO Auto-generated method stub
		String [] fieldsToSelect = {"*"};
		Hashtable<String,String> conditionsFildsToValues = new Hashtable<String, String>();
		conditionsFildsToValues.put("idUtente", id.toString());
		
		ResultSet result;
		try {
			result = DBConnectionManager.SelectEntryDB("Utente", fieldsToSelect, conditionsFildsToValues);
			int numOfRows = 0;
			
			while (result.next()) {
				numOfRows++;
				
			}
			switch(numOfRows) {
		      case 1:
		    	  return StateResult.VALID;
		      default:
		    	  return StateResult.NOVALID; 
			}
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return StateResult.DBPROBLEM;
		}
	}
	
	
	public StateResult validateDocente(idUser id) {
		   // TODO Auto-generated method stub
		   String [] fieldsToSelect = {"*"};
		   Hashtable<String,String> conditionsFildsToValues = new Hashtable<String, String>();
		   conditionsFildsToValues.put("idUtente", id.toString());
		   conditionsFildsToValues.put("Docente", "1");
		   
		   ResultSet result;
		   try {
		    result = DBConnectionManager.SelectEntryDB("Utente", fieldsToSelect, conditionsFildsToValues);
		    int numOfRows = 0;
		    
		    while (result.next()) {
		     numOfRows++;
		     
		    }
		    switch(numOfRows) {
		         case 1:
		          return StateResult.VALID;
		         default:
		          return StateResult.NOVALID; 
		    }
		    
		   
		   } catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		    return StateResult.DBPROBLEM;
		   }
		   
		   
		   
		  }
	


	@Override
	public StateResult createUser(UtenteDB utente, String password) {
		Hashtable<String,String> u = new Hashtable<String, String>();
	 	u.put("Nome", utente.getNome());
	 	u.put("Cognome", utente.getCognome());
	 	u.put("Password", password);
	 	u.put("Email", utente.getEmail());
	 	u.put("Docente", "0");
		
		try {
			Integer r = DBConnectionManager.createNewEntryDB("Utente", u,true);
			
			if (r>0) {
				utente.setId(new idUser(r));
				return StateResult.CREATED; 
			}else {
				return StateResult.NOCHANGES;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return StateResult.DBPROBLEM;
		}
		
		
	}

	@Override

	
	public StateResult retrieveUser(idUser id,UtenteDB utente ) {
		
		
		// TODO Auto-generated method stub
		String [] fieldsToSelect = {"*"};
		Hashtable<String,String> conditionsFildsToValues = new Hashtable<String, String>();
		conditionsFildsToValues.put("idUtente", id.toString());
				
		ResultSet result;
		try {
			result = DBConnectionManager.SelectEntryDB("Utente", fieldsToSelect, conditionsFildsToValues);
			
			
			int numOfRows = 0;
					
			while (result.next()) {
					numOfRows++;
					if (numOfRows==1) {
						utente.setCognome(result.getString("Cognome"));
						utente.setEmail(result.getString("Email"));
						utente.setId(new idUser(result.getInt("idUtente")));
						utente.setNome(result.getString("Nome"));
						
					}
			}
			
			if(numOfRows ==1) {
				return StateResult.VALID;
			}else {
				return StateResult.NOVALID;
			}
			
					
				
		} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return StateResult.DBPROBLEM;
			    	
					
		}
	}

	
	public StateResult verifyPassword(idUser id, String password) {
		// TODO Auto-generated method stub
				String [] fieldsToSelect = {"Password"};
				Hashtable<String,String> conditionsFildsToValues = new Hashtable<String, String>();
				conditionsFildsToValues.put("idUtente", id.toString());
				
				ResultSet result;
				try {
					result = DBConnectionManager.SelectEntryDB("Utente", fieldsToSelect, conditionsFildsToValues);
					int numOfRows = 0;
					
					while (result.next()) {
						numOfRows++;
						if(numOfRows == 1) {
							
							if(result.getString("Password").compareTo(password)==0) {
								return StateResult.VALID;
							}
						}
					}
					return StateResult.NOVALID;
					
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return StateResult.DBPROBLEM;
				}
	}
	
	public StateResult verifyLogin(UtenteDB utente, String password) {
		// TODO Auto-generated method stub
				String [] fieldsToSelect = {"Password", "idUtente"};
				Hashtable<String,String> conditionsFildsToValues = new Hashtable<String, String>();
				conditionsFildsToValues.put("Email", utente.getEmail());
				
				ResultSet result;
				try {
					result = DBConnectionManager.SelectEntryDB("Utente", fieldsToSelect, conditionsFildsToValues);
					int numOfRows = 0;
					
					result.last();
					
					if(result.getRow() == 0) {
						return StateResult.NOUSER;
					}
					
					result.beforeFirst();
					
					while (result.next()) {
						numOfRows++;
						if(numOfRows == 1) {
							
							if(result.getString("Password").compareTo(password)==0) {
								utente.setId(new idUser(result.getInt("idUtente")));
								return StateResult.VALID;
							}
						}
					}
					
					return StateResult.NOVALID;
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return StateResult.DBPROBLEM;
				}
	}
	
	public StateResult getDocentebyLesson(idLesson id, UtenteDB utente) {
		// TODO Auto-generated method stub

				String [] fieldsToSelect = {"*"};
				Hashtable<String,String> conditionsFildsToValues = new Hashtable<String, String>();
				conditionsFildsToValues.put("idLezione", id.toString());
						
				ResultSet result;
				try {
					result = DBConnectionManager.SelectEntryInSelectDB("Utente", fieldsToSelect, "idUtente", "Lezione", "Utente_idUtente", conditionsFildsToValues);
					
					
					int numOfRows = 0;
							
					while (result.next()) {
							numOfRows++;
							if (numOfRows==1) {
								File file = new File(result.getString("Curriculum"));
								utente.setCognome(result.getString("Cognome"));
								utente.setEmail(result.getString("Email"));
								utente.setId(new idUser(result.getInt("idUtente")));
								utente.setNome(result.getString("Nome"));
								utente.setContoPaypal(result.getString("ContoPayPal"));
								utente.setCurriculum(file);
								utente.setMediaScoreLezioni(result.getInt("MediaScoreLezioni"));
								
							}
					}
					
					switch(numOfRows) {
						    case 0:
						    	return StateResult.NOVALID;
						    case 1:
						    	return StateResult.VALID;
						    default:
						    	return StateResult.DEFAULT;
					}		
				} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return StateResult.DBPROBLEM;
					    	
							
				}
	}

	@Override
	public StateResult updateContoPaypal(idUser id, String contPaypal) {
		// TODO Auto-generated method stub

		Hashtable<String,String> conditionsFildsToValues = new Hashtable<String, String>();
		conditionsFildsToValues.put("idUtente", id.toString());
		
		Hashtable<String,String> fildsToUpdate = new Hashtable<String, String>();
		fildsToUpdate.put("ContoPayPal", contPaypal);
	 	
				
		try {
			Integer r = DBConnectionManager.UpdateEntryDB("Utente", conditionsFildsToValues, fildsToUpdate, false);
			
			
			if (r == 1) {
				return StateResult.UPDATED; 
			}else {
				return StateResult.NOUPDATED;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return StateResult.DBPROBLEM;
		}
		
	}
	
	@Override
	public StateResult createDocente(UtenteDB utente) {
		// TODO Auto-generated method stub
		String FilepathAsString = utente.getCurriculum().getAbsolutePath();
		
		Hashtable<String,String> conditionsFildsToValues = new Hashtable<String, String>();
		conditionsFildsToValues.put("idUtente", utente.getId().toString());
		
		Hashtable<String,String> fildsToUpdate = new Hashtable<String, String>();
		fildsToUpdate.put("ContoPayPal", utente.getContoPaypal());
		fildsToUpdate.put("Curriculum", FilepathAsString);
		fildsToUpdate.put("MediaScoreLezioni", "" + utente.getMediaScoreLezioni() + "");
		fildsToUpdate.put("Docente","1");
		
		
		try {
			Integer r = DBConnectionManager.UpdateEntryDB("Utente", conditionsFildsToValues, fildsToUpdate, false);
			
			
			if (r == 1) {
				return StateResult.CREATED; 
			}else {
				return StateResult.NOCHANGES;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return StateResult.DBPROBLEM;
		}
	}
	
	
	public StateResult getLessonsByCognome(String cognome, Vector<LezioneDB> lezioni) {
		  // TODO Auto-generated method stub
			
		    String [] fieldsToSelect = {"idUtente"};
		    Hashtable<String,String> conditionsFildsToValues = new Hashtable<String, String>();
		    conditionsFildsToValues.put("Cognome", cognome);
		    ControllerLezioneDB controllerlez = new ControllerLezioneDB();
		    
		      
		    ResultSet result;
		    try {
		    	
		    	result = DBConnectionManager.SelectEntryDB("utente", fieldsToSelect, conditionsFildsToValues);
		    	if(result.next()) {
		    	int idUtente = result.getInt("idUtente");
		    	
		    	controllerlez.getLessonsByUser(new idUser(idUtente), lezioni);
		    	
		    	int i = 0;
		    	while(i < lezioni.size()) {
		    		
		    		if(lezioni.get(i).getSlots().size() == 0) {
		    			lezioni.remove(i);
		    		}else{
		    			i++;
		    		}
		    	}
		    	
			     if(lezioni.size()>0) {
			      return StateResult.VALID;
			     }else {
			      return StateResult.NOVALID;
			     }
			    }else {
			    	return StateResult.NOVALID;
			    }
		    } catch (Exception e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		     return StateResult.DBPROBLEM;
		    }
		 }
	

}



