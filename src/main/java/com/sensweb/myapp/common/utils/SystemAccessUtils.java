package com.sensweb.myapp.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("systemAeccess")
public class SystemAccessUtils {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String SYSTEM_OS = System.getProperty("os.name");
	private Process process = null;
	
	public SystemAccessUtils() {
		 logger.info("OS : {}", SYSTEM_OS);
	}
	
	public void runExec(String[] args) throws RuntimeException{
		try {
			 this.process = Runtime.getRuntime().exec(args);
			 logger.info("Process 실행");
		}catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void waitFor() throws InterruptedException {
		// 자식 프로세스 종료시 까지 대기
		logger.info("OS : {}", "== WAIT ==");
		if(this.process!=null)
		this.process.waitFor();
	}
	
	public void destroy() {
		// 자식 프로세스 종료 
		logger.info("OS : {}", "== DESTORY ==");
		if(this.process!=null)
		this.process.destroy();
	}
	
	public String runExecAndGetString(String[] args) {
		Process p = null;
		StringBuilder sb = null; 
		String resultStr = "";
		try {
			 p = Runtime.getRuntime().exec(args);
			 logger.info("Process 실행");
			 
			 BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(),"EUC-KR"));
             sb = new StringBuilder();
             String line = null;
             while ((line = br.readLine()) != null) {
                 sb.append(line).append("\n");
             }
             resultStr = sb.toString();
             sb = null;
            
		}catch (Exception e) {
			throw new RuntimeException();
		}finally {
			// 자식 프로세스 종료시 까지 대기
			logger.info("OS : {}", "== WAIT ==");
			try {
				 p.waitFor();
			}catch(InterruptedException e) {
				 e.printStackTrace();
			}
			// 자식 프로세스 종료 
			logger.info("OS : {}", "== DESTORY ==");
            p.destroy();
		}
		return resultStr;
	}
}
