package com.pb.td.po.dto;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class QueueServerRequest {
	private String serverName;
	private String queueServer;
	
	
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getQueueServer() {
		return queueServer;
	}
	public void setQueueServer(String queueServer) {
		this.queueServer = queueServer;
	}
	

}
