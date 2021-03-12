package com.lowes.util;

public enum WSMessageType {

	/*
	 * Indica un error del servicio web, por ejemplo: Error de conectividad, parametros obligatorios, etc.
	 * */
	PLATFORM_ERROR, 
	
	/*
	 * Indica que el llamado se realizo correctamente al servicio web, el mensaje que retorna el servicio web
	 * no implica un mensaje satisfactorio o errones, solo sirve para conocer que el servicio web fue consumido.
	 * */
	WS_CONNECTION 
	
}
