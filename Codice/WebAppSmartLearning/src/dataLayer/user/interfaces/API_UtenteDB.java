package dataLayer.user.interfaces;


import dataLayer.user.entities.UtenteDB;
import dataLayer.user.entities.result.ResultUtente;
import dataLayer.utilities.StateResult;
import dataLayer.utilities.idLesson;
import dataLayer.utilities.idUser;

public interface API_UtenteDB {
	/**
	 *Questa funzione permette di validare l'id di un utente
	 *
	 *@param id
	 *@return StateResult Rappresenta lo stato dell'operazione:
	 *- NOVALID
	 *- VALID
	 *- DBPROBLEM
	 */
	StateResult validateUser(idUser id); 
	/**
	 * Questa funzione permette di creare un utente
	 * 
	 * @param utente I/O struttura che contiene le informazioni da memorizzare per il nuovo utente.
	 * Essa verrà aggiornata con l'id dell'utente.
	 * @param password la password da memorizzare per il nuovo utente
	 * @return StateResult Rappresenta lo stato del risultato:
	 * - NOCHANGES
	 * - CREATED
	 * - DBPROBLEM
	 */
	StateResult createUser(UtenteDB utente, String password);
	/**
	 * Questa funzione permette di ottenere la struttura dati UtenteDB con le informazione 
	 * riguardo l'utente identificato dal suo id
	 * 
	 * @param id identificativo dell'utente da selezionare
	 * @param utente struttura che verrà aggiornata con le informazioni prelevate dalla base di dati perisistente
	 * @return StateResult struttura dati contenente il risultato della selezione. 
	 * Tale struttura contiene uno stato:
	 * - NOVALID 
	 * - VALID
	 * - DBPROBLEM
	 * e l'oggetto UtenteDB
	 */
	StateResult retrieveUser(idUser id, UtenteDB utente);
	
	/**
	 * Questa funzione permette di ottenere la password relativa  a un utente
	 * 
	 * @param id identificativo dell'utente da selezionare
	 * @param password password da validare
	 * @return StateResult Restituisce lo stato della validazione:
	 * -  VALID
	 * - NOVALID
	 * - DBPROBLEM
	 */
	StateResult verifyPassword(idUser id, String password);
	
	/**
	 * Questa funzione permette di controllare i dati di login per un utente
	 * 
	 * @param email email dell'utente che effettua il login
	 * @param password password da validare
	 * @return StateResult Restituisce lo stato della validazione:
	 * -  VALID
	 * - NOVALID
	 * - DBPROBLEM
	 */
	StateResult verifyLogin(String email, String password);
	
	
	/* QUESTE SONO LE FUNZIONI RIFERITE AL DOCENTE */
	/**
	 *Questa funzione permette di creare un docente  
	 *
	 *@param utente
	 *@return StateResult Rappresenta lo stato dell'operazione:
	 *
	 *- CREATED
	 *- NOCHANGES
	 *- DBPROBLEM
	 */
	StateResult createDocente(UtenteDB utente);
	
	/**
	 *Questa funzione permette di ottenere le informazioni del docente in base all'id
	 *
	 *@param id 
	 *@param utente
	 *@return StateResult Rappresenta lo stato dell'operazione:
	 *
	 *- VALID
	 *- NOVALID
	 *- DEFAULT
	 *- DBPROBLEM
	 */
	StateResult getDocentebyLesson(idLesson id, UtenteDB utente);
	
	/**
	 *Questa funzione permette di aggiornare il campo paypal di un profilo docente
	 *
	 *@param id 
	 *@param contPaypal stringa aggiornata
	 *@return StateResult Rappresenta lo stato dell'operazione:
	 *
	 *- UPDATED
	 *- NOUPDATED
	 *- DBPROBLEM
	 */
	StateResult updateContoPaypal(idUser id, String contPaypal);
}