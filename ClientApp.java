import java.io.*;
import java.net.*;

/*
* File Name : ClientApp.java
*  ㅇ Browser 에서 http protocol로 WebServer 에 Request 시 
*  ㅇ WebServer 에서  http protocal 이용 어떻게  response 하는지 
*  ㅇ 확인을 위해 client(brower)의 역할을 수행하는 ClientApp
*/
public class ClientApp{

	///main method
	public static void main(String[] args) throws Exception{
		
		// 127.0.0.1 IP 로 80port 즉 W/S  접속 Socket 인스턴스생성
		Socket s = new Socket("127.0.0.1",80);
		
		// W/S로 data 전송 및 받기 위해
		// socket 인스턴스로 부터  Inputstream/OutputStream을 생성, return  받음

		//1. InputStream생성
		InputStream is = s.getInputStream();
		//2. OutputStream생성
		OutputStream os = s.getOutputStream();
		PrintWriter pw = new PrintWriter(os,true);

		//  W/S 로 data 전송 
		// 이전 예제의 "Header 의 시작라인" 같은 형식의 data 전송
		pw.println("GET /aaa.html HTTP/1.1");
		pw.println();

		System.out.println("\n*****************************************************");
		System.out.println("**** ip 127.0.0.1   WebServer로              ****");
		System.out.println("**** \"GET /aaa.html http/1.1\"를 전송함 ****");
		System.out.println("*****************************************************\n");

		// W/S 에서 전송된 Data 를 출력
		System.out.println("\nnnnn*****************************************************");
		System.out.println("**** WebServer에서 보내온 내용은 아래와 같습니다.****");
		System.out.println("*****************************************************\n");

		int i = 0 ;
		while( (i = is.read()) !=-1){
			System.out.print((char)i);
		}
		System.out.println("\n end-----------------------------------------------------");

		//Stream,Socket를 close
		os.close();
		is.close();
		s.close();

	}//end of main

}//end of class