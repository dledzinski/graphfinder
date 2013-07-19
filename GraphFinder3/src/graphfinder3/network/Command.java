/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.network;

/**
 * Instrukcja do wykonania
 *
 * @author damian
 */
public enum Command {
	// dane do logowania

	PING,
	PONG,
	/**
	 * Blad ogolny, parametr: String - tresc bledu
	 */
	ERROR,
	// obsluga uzytkownika
	/**
	 * Rozkaz zalogownia, Klient, Worker -> Serwer, parametr: LoginData
	 */
	LOGIN,
	/**
	 * Informacja zwrotna o poprawnosci logowania, Server -> Klient, Worker
	 * parametr: String - nadana nazwa
	 */
	LOGIN_OK,
	/**
	 * Informacja o bledzie logowania, Server -> Klient, Worker, parametr String
	 * komunikat
	 */
	LOGIN_ERROR,
	/**
	 * Dane z monitoringu, Klient, Worker -> Serwer
	 */
	MONITORING_DATA,
	// obsluga GUI
	/**
	 * Rejestracja sluchania informacji o klientach, Klient -> Serwer
	 */
	CLIETN_INFO_REGISTER,
	/**
	 * Wyrejestrowanie sluchania informacji o klientach, Klient -> Serwer
	 */
	CLIETN_INFO_UNREGISTER,
	/**
	 * Usuwanie informacji o nieaktywnych klientach, Klient -> Serwer
	 */
	CLIETN_INFO_DELETE_UNACTIVES,
	/**
	 * Informacje o klientach, Serwer -> Klient, parametr: Set ClientInfo
	 */
	CLIENT_INFOS,
	/**
	 * Rejestracja sluchania informacji o rozkazach, Klient -> Serwer
	 */
	ORDER_INFO_REGISTER,
	/**
	 * Wyrejestrowanie sluchania informacji o rozkazach, Klient -> Serwer
	 */
	ORDER_INFO_UNREGISTER,
	/**
	 * Informacje o rozkazach, Serwer -> Klient, parametr: Set OrderInfo
	 */
	ORDER_INFOS,
	/**
	 * Nowy rozkaz, Klient -> Serwer, parametr Order
	 */
	NEW_ORDER,
	/**
	 * Usun rozkaz, Klient -> Serwer, parametr String - nazwa
	 */
	DELETE_ORDER,
	/**
	 * Ustaw priorytet, Klient -> Serwer, parametr OrderPriority
	 */
	ORDER_SET_PRIORITY,
	/**
	 * Prosba o przyslanie szczegulow rozkazu, Klient -> Serwer, parametr String
	 * - nazwa rozkazu
	 */
	ORDER_GET_DETAILS,
	/**
	 * Szczegoly rozkazu, Serwer -> Klient, parametr: OrderDetail
	 */
	ORDER_DETAILS,
	// obsluga workera
	/**
	 * Zapytanie o rozkazy, Worker -> Server
	 */
	ORDERS_REQUEST,
	/**
	 * Nowe rozkazy, Serwer -> Worker, parametr: Set Order
	 */
	ORDERS,
	/**
	 * Rozkazy anulowane, Serwer -> Worker,
	 */
	ORDERS_CANCELLED,
	/**
	 * Blad wykonywania rozkazu, Worker -> Server, parametr: String - tresc
	 * bledu
	 */
	ORDER_ERROR,
	/**
	 * Wyniki wykonywania rozkazow, Worker -> Server, parametr: Set OrderResult
	 */
	ORDER_RESULTS,
	/**
	 * Rejestracja sluchania informacji o zdarzeniach workera, Worker -> Serwer
	 */
	WORKER_INFO_REGISTER,
	/**
	 * Wyrejestrowanie sluchania informacji o zdarzeniach workera, Worker ->
	 * Serwer
	 */
	WORKER_INFO_UNREGISTER;
}
