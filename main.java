import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.SocketException;
import java.net.ServerSocket;
import java.net.Socket;

class JavaClient{
	public static void main(String args[]){

        JavaTCP jtcp=new JavaTCP();
        jtcp.tcp_text(jtcp.tcp_getServerInformation(""));


	}
}

class Java_myBaseSystem{
    public String textInfo(String message)
    {
        //改行コードの取得
        String crlf = System.getProperty("line.separator");

        try {
            System.out.println(message+crlf);
            BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
            String input_data=new String(in.readLine());
            return input_data;
            
        } catch (Exception e) {
            return e.toString();
        }
    }
}


class JavaTCP{
    
    public String tcp_text(String serverInformation)
    {
        try{
            //文字列を分割（csv方式）
            String splitstr[]=serverInformation.split(",",0);

            String host=splitstr[0];
            int sendport=Integer.parseInt(splitstr[1]);
            String data=splitstr[2];
            int receport=Integer.parseInt(splitstr[3]);

            System.out.println(host+"/"+sendport+"/"+data+"/"+receport);

            //指定サーバにデータを送る
			Socket s = new Socket(host, sendport);
			PrintWriter pw = new PrintWriter(s.getOutputStream());
			pw.println(data);
			pw.flush();
			s.shutdownOutput();
            s.close();
            
            //サーバからの応答を返り値に指定する
			ServerSocket o_s=new ServerSocket(receport);
			Socket osocket=o_s.accept();
            BufferedReader br =new BufferedReader(new InputStreamReader(osocket.getInputStream()));
			String odata = br.readLine();
			br.close();
            o_s.close();
            return odata;


		}
		catch(Exception e){
			return e.toString();
		}
    }
    
    public String tcp_getServerInformation(String sendData)
    {
        Java_myBaseSystem myBase=new Java_myBaseSystem();

        try {
            
            //TCPの情報を尋ねる
            String host=myBase.textInfo("input host address");
            String sendport=myBase.textInfo("input host port");
            String data;
            if(sendData==""){data=myBase.textInfo("input sendData");}
            else{data=sendData;}
            String receport=myBase.textInfo("input receport");

            //CSV形式で文字列を生成
            String ServerInformation=output_csv(host, sendport, data, receport);

            return ServerInformation;
            
        } catch (Exception e) {
            return e.toString();
        }
   
    }

    //CSV形式で文字列を生成
    public String output_csv(String host,String sendport,String data,String receport)
    {
        String csvStr;
        csvStr=host+","+sendport+","+data+","+receport;
        return csvStr;
    }

}