package com.mindware.util;


import com.mindware.domain.Mensaje;
import com.mindware.services.MensajeService;

import org.smslib.*;
import org.smslib.AGateway.GatewayStatuses;
import org.smslib.InboundMessage.MessageClasses;
import org.smslib.Message.MessageTypes;
import org.smslib.modem.SerialModemGateway;
import org.smslib.StatusReportMessage.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by freddy on 06-11-16.
 */
public class SendSms {
	private SerialModemGateway gateway;
	//private Mensaje updateMensaje;
	private OutboundNotification outboundNotification;
	private InboundNotification inboundNotification;
	
	public SendSms (String puertoModem, String puerto, int baud, String modem, String modelo) throws Exception{
		 outboundNotification = new OutboundNotification();
		 inboundNotification = new InboundNotification();
		
        gateway = new SerialModemGateway(puertoModem, puerto, baud,modem, modelo);
        gateway.setInbound(true);
        gateway.setOutbound(true);
    //    gateway.setSimPin("111");
        Service.getInstance().setOutboundMessageNotification(outboundNotification);
        Service.getInstance().setInboundMessageNotification(inboundNotification);
        Service.getInstance().addGateway(gateway);
 //       Service.getInstance().startService();
		
	}
	
	@SuppressWarnings("null")
	public void readSms() throws TimeoutException, GatewayException, IOException, InterruptedException {
		String[] status = new String[2];
		
		
		ArrayList<InboundMessage> msgRead;
//		InboundNotification inboundNotification = new InboundNotification();
//		GatewayStatusNotification statusNotification = new GatewayStatusNotification();
		
		Service.getInstance().setInboundMessageNotification(inboundNotification);
//		Service.getInstance().setGatewayStatusNotification(statusNotification);
		
		msgRead = new ArrayList<InboundMessage>();
		if (Service.getInstance().readMessages(msgRead, MessageClasses.ALL) > 0) {
			for (InboundMessage msg : msgRead) {
				if (msg.getType().toString() == "STATUSREPORT") {	
		           // System.out.println(msg); 
//					status[0] = ((StatusReportMessage)msg).getStatus().toString();
//		            status[1] = msg.getText();
					
					
		           // Service.getInstance().deleteMessage(msg);
		            		            
				}
				else {
//					System.out.println("MENSAJE TIPO:" + msg.getType().toString() );
//					System.out.println("Mensaje:" + msg.getText());
//					System.out.println("Numero:" + msg.getOriginator());
//					System.out.println(msg);
					Service.getInstance().deleteMessage(msg);
				}
			}
		}
		//return status;
	}
	
	public void removeGateway(){
		try {
			Service.getInstance().removeGateway(gateway);
		} catch (GatewayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessage(String mensaje, String celular, int mensajeId, int numeroIntentos) throws TimeoutException, GatewayException, IOException, InterruptedException  {
		String[] status = null;
		String idMensaje;
		String statusMensaje;
		
		Date fecha = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		
		String hora = sdf.format(fecha);

		OutboundMessage msg = new OutboundMessage(celular,mensaje);
//		InboundNotification inboundNotification = new InboundNotification();
//		Service.getInstance().setInboundMessageNotification(inboundNotification);
		
		msg.setStatusReport(true);
	
		Service.getInstance().sendMessage(msg);
        idMensaje = msg.getId().toString();
		statusMensaje = msg.getMessageStatus().toString();

		Mensaje updateMensaje = new Mensaje();
		updateMensaje.setMensajeId(mensajeId);
		if (statusMensaje.equals("SENT")) {
			updateMensaje.setEnviado("T");
		}else {
			updateMensaje.setEnviado("F");
		}
		updateMensaje.setFechaEnvio(fecha);
		updateMensaje.setHoraEnvio(hora);
		updateMensaje.setNumeroIntentos(numeroIntentos+1);
		actualizarMensaje(updateMensaje);
		System.out.println("PRIMER UPDATE");
		ArrayList<InboundMessage> msgRead = new ArrayList<InboundMessage>();
		System.out.println("MENSAJE ENVIADO" + msg.getRefNo());
        System.out.println(msg);
       
        Thread.sleep(1000);
        int nro = Service.getInstance().readMessages(msgRead, MessageClasses.ALL);
        System.out.println("Nro recibidos:" + nro);
        System.out.println(msgRead);
        if (nro > 0) {
	        for (InboundMessage msg1 : msgRead) {
				if (msg1.getType().toString() == "STATUSREPORT") {	
		            System.out.println("MENSAJE RECIBDO: " + ((StatusReportMessage)msg1).getRefNo()); 
		            updateMensaje.setStatus(((StatusReportMessage)msg1).getStatus().toString());
					updateMensaje.setStatusDetalle(msg1.getText() +  "ID:" + updateMensaje.getMensajeId() + "I-" + updateMensaje.getNumeroIntentos() + "REF:" + msg.getRefNo());
					actualizarMensaje(updateMensaje);
					System.out.println("SEGUNDO UPDATE");
					
	//				status[0] = ((StatusReportMessage)msg).getStatus().toString();
	//	            status[1] = msg.getText();
					
					
		            Service.getInstance().deleteMessage(msg1);
		            		            
				}
				else {
	//				System.out.println("MENSAJE TIPO:" + msg.getType().toString() );
	//				System.out.println("Mensaje:" + msg.getText());
	//				System.out.println("Numero:" + msg.getOriginator());
	//				System.out.println(msg);
					Service.getInstance().deleteMessage(msg1);
				}
			}
        }else if (statusMensaje.equals("SENT")) {
        	updateMensaje.setStatus("DELIVERED");
			updateMensaje.setStatusDetalle("Entregado/ no llego confirmacion del operador");
			actualizarMensaje(updateMensaje);
        	
        }
        	
        
//		if (Service.getInstance().readMessages(msgRead, MessageClasses.ALL) > 0) {
//			
//			for (InboundMessage msg1 : msgRead) {
//				if (msg1.getType().toString() == "STATUSREPORT") {	
//		            System.out.println("MENSAJE RECIBDO: " + ((StatusReportMessage)msg1).getRefNo()); 
//		            updateMensaje.setStatus(((StatusReportMessage)msg1).getStatus().toString());
//					updateMensaje.setStatusDetalle(msg1.getText() +  "ID:" + updateMensaje.getMensajeId() + "I-" + updateMensaje.getNumeroIntentos() + "REF:" + msg.getRefNo());
//					actualizarMensaje(updateMensaje);
//					System.out.println("SEGUNDO UPDATE");
//					
////					status[0] = ((StatusReportMessage)msg).getStatus().toString();
////		            status[1] = msg.getText();
//					
//					
//		            Service.getInstance().deleteMessage(msg1);
//		            		            
//				}
//				else {
////					System.out.println("MENSAJE TIPO:" + msg.getType().toString() );
////					System.out.println("Mensaje:" + msg.getText());
////					System.out.println("Numero:" + msg.getOriginator());
////					System.out.println(msg);
//					Service.getInstance().deleteMessage(msg1);
//				}
//			}
//			
//		}
	//	updateMensaje = null;
		
	//	return updateMensaje;

	}

	public void borrarMensajes() {
		ArrayList<InboundMessage> msgRead = new ArrayList<InboundMessage>();
		try {
			if (Service.getInstance().readMessages(msgRead, MessageClasses.ALL) > 0) {
				for (InboundMessage msg1 : msgRead) {
					Service.getInstance().deleteMessage(msg1);
				}
			}
		} catch (TimeoutException | GatewayException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void sendMessages(List<Mensaje> mensajes) throws Exception {

		List<OutboundMessage> listaMensajes = new ArrayList<>();
		java.util.Date fecha = new Date();
		String hora = OffsetTime.now().toString();
		List<Mensaje> updateListaMensajes = new ArrayList<>();
		
		for (Mensaje mensaje : mensajes) {
			Mensaje updateMensaje = new Mensaje();
			OutboundMessage msg = new OutboundMessage(mensaje.getCelular(),
					mensaje.getMensaje());
			listaMensajes.add(msg);
			
			updateMensaje.setMensajeId(mensaje.getMensajeId());
			updateMensaje.setEnviado("T");
			updateMensaje.setFechaEnvio(fecha);
			updateMensaje.setHoraEnvio(hora);
			updateMensaje.setNumeroIntentos( mensaje.getNumeroIntentos()+1);
			
			updateListaMensajes.add(updateMensaje);
			//TODO actualizar el mensaje con su estado, fecha y hora de envio y numero de intentos
		
		}

		Service.getInstance().sendMessages(listaMensajes,gateway.getGatewayId());
		
		
		actualizarMensajes(updateListaMensajes);
	
	}

	public void actualizarMensajes(List<Mensaje> listaMensajes) {
		
		MensajeService mensajeService = new MensajeService();
		mensajeService.updateListaMensajes(listaMensajes);

	}
	
	public void actualizarMensaje(Mensaje mensaje) {
		MensajeService mensajeService = new MensajeService();
		mensajeService.updateMensaje(mensaje);

	}

	public class OutboundNotification implements IOutboundMessageNotification {
	    public void process(AGateway gateway, OutboundMessage msg) {
	        System.out.println("##########################################################");
	    	System.out.println("Outbound handler called from Gateway: "
	                + gateway.getGatewayId());
	        System.out.println(msg);
	    }
	}
	
	 public class GatewayStatusNotification implements 
	    IGatewayStatusNotification { 
//	        public void process(String gatewayId, GatewayStatuses oldStatus, 
//	                GatewayStatuses newStatus) { 
//	            System.out.println(">>> Gateway Status change for " + gatewayId 
//	                    + ", OLD: " + oldStatus + " -> NEW: " + newStatus); 
//	        }

			@Override
			public void process(AGateway gateway, GatewayStatuses oldStatus, GatewayStatuses newStatus) {
				System.out.println(">>> Gateway Status change for " + gateway.getGatewayId() 
	                    + ", OLD: " + oldStatus + " -> NEW: " + newStatus); 
				
			} 
	    } 
	
	 public class InboundNotification implements IInboundMessageNotification { 

			@Override
			public void process(AGateway gateway, MessageTypes msgType, InboundMessage msg) {
				 if (msgType == MessageTypes.INBOUND) 
		                System.out.println(">>> 1. New Inbound message detected from Gateway: " 
		                        + gateway.getGatewayId()); 
		            else if (msgType == MessageTypes.STATUSREPORT) { 
		                System.out.println(">>> 1. New Inbound Status Report message detected from Gateway: " 
		                        + gateway.getGatewayId() + " >> " + ((StatusReportMessage)msg).getStatus().toString());
		               
							
						
		            }
		            System.out.println(msg); 
		            
		            try { 
		                // Uncomment following line if you wish to delete the message 
		                // upon arrival. 
		                // ReadMessages.this.srv.deleteMessage(msg); 
		            	System.out.println("Borra mensajes: " + msg.getText());
		            //	gateway.deleteMessage(msg);
		            	//Service.getInstance().deleteMessage(msg);
		            } catch (Throwable e) { 
		               // LogUtils.errorf(this, e, "Oops!!! Something gone bad...");
		            	System.out.println(e);
		            } 
				
			} 
	    }
	 
	 
}
