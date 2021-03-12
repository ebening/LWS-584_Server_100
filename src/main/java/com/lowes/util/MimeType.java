/**
 * 
 */
package com.lowes.util;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
/**
 * Clase que tienen todos los mime type y obtiene el tipo de acuerdo
 * a la extensión del archivo o path
 * 
 * @author columba b
 * @version 1.0
 *
 */
public class MimeType {
	private Collection<Type> types;
	private Collection<Type> typesCM;
	 
	public MimeType() {
		this.setTypes();
		this.setTypesCM();
	}

	public void setTypes(){
		this.types  = new ArrayList<Type>();
		types.add(new Type("application/word", 	  			new String[]{"docx","doc", "dot", "rtf"}));
		types.add(new Type("application/octet-stream",		new String[]{"bin","dms","lha","lzh","exe","class"}));
		types.add(new Type("application/pdf", 				new String[]{"pdf"}));
		types.add(new Type("application/rtf", 				new String[]{"rft"}));
		types.add(new Type("application/lotus-1-2-3", 		new String[]{"123","wk4","wk3","wk1", "wks", "wg1"}));
		types.add(new Type("application/lotus-freelance",	new String[]{"prz","pre"}));
		types.add(new Type("application/lotus-wordpro", 	new String[]{"lwp","sam","mwp","smm"}));
		types.add(new Type("application/vnd.ms-excel", 		new String[]{"xlsx","xls", "xlt", "xlm", "xld", "xla", "xlc", "xlw", "xll"}));
		types.add(new Type("application/vnd.ms-powerpoint", new String[]{"ppt", "pot", "ppa", "pps", "pwz"}));
		types.add(new Type("application/vnd.visio", 		new String[]{"vsd"}));
		types.add(new Type("audio/basic", 					new String[]{"au", "snd", "ulw"}));
		types.add(new Type("audio/mpeg", 					new String[]{"mpeg", "mpg", "m1s", "m1a", "mp2", "mp3", "mpm", "mpa", "mpga"}));
		types.add(new Type("audio/x-aiff", 					new String[]{"aif", "aiff", "aifc"}));
		types.add(new Type("audio/x-midi", 					new String[]{"midi", "mid", "smf", "kar"}));
		types.add(new Type("audio/x-wav", 					new String[]{"wav"}));
		types.add(new Type("image/bmp", 					new String[]{"bmp", "dib"}));
		types.add(new Type("image/gif", 					new String[]{"gif"}));
		types.add(new Type("image/jpeg", 					new String[]{"jpeg", "jpg", "jpe", "jfif", "pjpeg", "pjp"}));
		types.add(new Type("image/png", 					new String[]{"png"}));
		types.add(new Type("image/tiff", 					new String[]{"tiff","tif"}));
		types.add(new Type("text/csv",						new String[]{"csv"}));
		types.add(new Type("text/html", 					new String[]{"html", "htm", "shtml", "plg"}));
		types.add(new Type("text/plain", 					new String[]{"txt", "text"}));
		types.add(new Type("text/xml", 						new String[]{"xml", "dtd"}));
		types.add(new Type("video/vmpeg", 					new String[]{"mpeg","mpg","mpe","m1s","m1v","m1a","m75","m15","mp2"}));
		types.add(new Type("video/quicktime", 				new String[]{"mov","qt"}));
		
	 }
	
	public void setTypesCM(){
		this.typesCM  = new ArrayList<Type>();
		typesCM.add(new Type("application/msword", 	  			new String[]{"docx","doc", "dot", "rtf"}));
		typesCM.add(new Type("application/octet-stream",		new String[]{"bin","dms","lha","lzh","exe","class"}));
		typesCM.add(new Type("application/pdf", 				new String[]{"pdf"}));
		typesCM.add(new Type("application/rtf", 				new String[]{"rft"}));
		typesCM.add(new Type("application/lotus-1-2-3", 		new String[]{"123","wk4","wk3","wk1", "wks", "wg1"}));
		typesCM.add(new Type("application/lotus-freelance",		new String[]{"prz","pre"}));
		typesCM.add(new Type("application/lotus-wordpro", 		new String[]{"lwp","sam","mwp","smm"}));
		typesCM.add(new Type("application/vnd.ms-excel", 		new String[]{"xlsx","xls", "xlt", "xlm", "xld", "xla", "xlc", "xlw", "xll"}));
		typesCM.add(new Type("application/vnd.ms-powerpoint",	new String[]{"ppt", "pot", "ppa", "pps", "pwz"}));
		typesCM.add(new Type("application/vnd.visio", 			new String[]{"vsd"}));
		typesCM.add(new Type("audio/basic", 					new String[]{"au", "snd", "ulw"}));
		typesCM.add(new Type("audio/mpeg", 						new String[]{"mpeg", "mpg", "m1s", "m1a", "mp2", "mp3", "mpm", "mpa", "mpga"}));
		typesCM.add(new Type("audio/x-aiff", 					new String[]{"aif", "aiff", "aifc"}));
		typesCM.add(new Type("audio/x-midi", 					new String[]{"midi", "mid", "smf", "kar"}));
		typesCM.add(new Type("audio/x-wav", 					new String[]{"wav"}));
		typesCM.add(new Type("image/bmp", 						new String[]{"bmp", "dib"}));
		typesCM.add(new Type("image/gif", 						new String[]{"gif"}));
		typesCM.add(new Type("image/jpeg", 						new String[]{"jpeg", "jpg", "jpe", "jfif", "pjpeg", "pjp"}));
		typesCM.add(new Type("image/png", 						new String[]{"png"}));
		typesCM.add(new Type("image/tiff", 						new String[]{"tiff","tif"}));
		typesCM.add(new Type("text/csv",						new String[]{"csv"}));
		typesCM.add(new Type("text/html", 						new String[]{"html", "htm", "shtml", "plg"}));
		typesCM.add(new Type("text/plain", 						new String[]{"txt", "text"}));
		typesCM.add(new Type("text/xml", 						new String[]{"xml", "dtd"}));
		typesCM.add(new Type("video/vmpeg", 					new String[]{"mpeg","mpg","mpe","m1s","m1v","m1a","m75","m15","mp2"}));
		typesCM.add(new Type("video/quicktime", 				new String[]{"mov","qt"}));
		
	 }
	
	/**
	 * @param ext - Extencion del documento seleccionado.
	 * @return El tipo Mime para la extencion ingresada.
	 */
	public String getMimeTypeByExt(String ext){
		if(ext != null){
			Iterator<Type> i = this.types.iterator();
			 while(i.hasNext()){
				Type type = i.next();
				if(type.comparaExtension(ext)){
					return type.getMime();
				}
			 }
			 
		}
		 return null;
	}
	
	/**
	 * @param url - Url del documento ingresado.
	 * @return El tipo Mime del documento ingresado para.
	 */
	public String getMimeTypeByUrl(String url){
		String[] a = url.split("\\.");
		String ext = null;
		if(a.length > 0) ext = a[a.length-1];
		return this.getMimeTypeByExt(ext);	
	}
	
	/**
	 * @param ext - Extencion del documento seleccionado.
	 * @return El tipo Mime para la extencion 
	 * ingresada en Content Manager.
	 */
	public String getCMMimeTypeByExt(String ext){
		if(ext != null){
			Iterator<Type> i = this.typesCM.iterator();
			 while(i.hasNext()){
				Type type = i.next();
				if(type.comparaExtension(ext)){
					return type.getMime();
				}
			 }
			 
		}
		 return null;
	}
	
	/**
	 * @param url - Url del documento ingresado.
	 * @return El tipo Mime del documento 
	 * ingresado para Content Manager.
	 */
	public String getCMMimeTypeByUrl(String url){
		String[] a = url.split("\\.");
		String ext = null;
		if(a.length > 0) ext = a[a.length-1];
		return this.getCMMimeTypeByExt(ext);	
	}
	 
	/**
	 * Clase que tiene las caracteriscticas del mime type.
	 * @author columbab
	 * @version 1.0
	 *
	 */
	private class Type{
		private Collection<String> extensiones;
		private String mime = null;
		
		public Type(String mime, String[] strings) {
			for(int i = 0; i < strings.length; i++){
				this.addExtension(strings[i]);
			}
			this.mime = mime;
		}
		
		public boolean comparaExtension(String ext){
			if(this.extensiones != null){
				
				@SuppressWarnings("rawtypes")
				Iterator i = this.extensiones.iterator();
				while(i.hasNext()){
					String extension = (String)i.next();
					if(extension.equalsIgnoreCase(ext)){
						return true;
					}
				}
			}
			return false;
		}
		
		public void addExtension(String ext){
			if(this.extensiones == null){
				this.extensiones = new ArrayList<String>();
			}
			this.extensiones.add(ext);
		}
		
		@SuppressWarnings("unused")
		public Collection<String> getExtensiones(){
			return this.extensiones;
		}
		
		@SuppressWarnings("unused")
		public void setMime(String mime){
			this.mime = mime;
		}
		
		public String getMime(){
			return this.mime;
		}
	}	
}
