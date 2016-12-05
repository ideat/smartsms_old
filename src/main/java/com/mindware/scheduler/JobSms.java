package com.mindware.scheduler;

import com.mindware.domain.Mensaje;
import com.mindware.services.MensajeService;
import com.mindware.util.SendSms;
import com.mindware.util.SendSms.InboundNotification;

import java.util.ArrayList;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.smslib.Service;

@DisallowConcurrentExecution
public class JobSms implements Job  {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("CUERPO JOB");
		String[] status = new String[2];
		List<Mensaje> mensajes = new ArrayList<>();
//		mensajes = listaMensajes();
		mensajes = listaMensajesNoEnviados();
		
		try {
			SendSms sendSms = new SendSms("modem.com6", "COM6", 9600, "Huawei", "E303");
			
			try {
				
				Service.getInstance().startService();
				sendSms.borrarMensajes();
				System.out.println("Numero mensajes: " + mensajes.size());
				for(Mensaje mensaje: mensajes) {
//					Mensaje sendMensaje = new Mensaje();
					//sendMensaje = sendSms.sendMessage(mensaje.getMensaje(), mensaje.getCelular(), mensaje.getMensajeId(), mensaje.getNumeroIntentos());
//					if (mensaje.getNumeroIntentos()< 3)
						sendSms.sendMessage(mensaje.getMensaje(), mensaje.getCelular(), mensaje.getMensajeId(), mensaje.getNumeroIntentos());
//					Thread.sleep(1000);
//					sendSms.readSms();
//					status = sendSms.readSms();
//					sendMensaje.setStatus(status[0]);
//					sendMensaje.setStatusDetalle(status[1]);
//					if (status[0]==null) {
//						System.out.println("Status: "+ status[0]);
//						System.out.println("Status detalle: "+ status[1]);
//					}
// 					sendSms.actualizarMensaje(sendMensaje);
					
				}
			} finally {
				Service.getInstance().stopService();
				
				sendSms.removeGateway();
				sendSms = null;
				System.out.println("Mensajes Enviados");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	

	
	public List<Mensaje> listaMensajes() {
		List<Mensaje> listaMensajesNoEnviados = new ArrayList<Mensaje>();
		
		MensajeService mensajeService = new MensajeService();
		
		listaMensajesNoEnviados = mensajeService.findMensajeNoEnviado("F");
		return listaMensajesNoEnviados;
		
	}
	
	public List<Mensaje> listaMensajesNoEnviados() {
		List<Mensaje> listaMensajesNoEnviados = new ArrayList<Mensaje>();
		
		MensajeService mensajeService = new MensajeService();
		
		listaMensajesNoEnviados = mensajeService.findMensajeNoEntregados("ABORTED");
		return listaMensajesNoEnviados;
		
	}
	
	

}
