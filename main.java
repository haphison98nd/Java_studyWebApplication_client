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

        ;

        //JavaTCP jtcp=new JavaTCP();
        //String data[];


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
    
    public String tcp_getServeerInformation(String sendData)
    {
        try {
            //改行コードの取得
            String crlf = System.getProperty("line.separator");

            System.out.println("input host address"+crlf);
            BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
            String host=new String(in.readLine());


            System.out.println("input host port"+crlf);
            in=new BufferedReader(new InputStreamReader(System.in));
            String sendport=new String(in.readLine());

            String data;
            if(sendData==null)
            {
                System.out.println("input sendData"+crlf);
                in=new BufferedReader(new InputStreamReader(System.in));
                data=new String(in.readLine());
            }
            else{
                data=sendData;
            }


            System.out.println("input receport"+crlf);
            in=new BufferedReader(new InputStreamReader(System.in));
            String receport=new String(in.readLine());

            //CSV形式で文字列を生成
            String ServerInformation=host+","
                                    +sendport+","
                                    +data+","
                                    +receport;

            return ServerInformation;
            
        } catch (Exception e) {
            return e.toString();
        }
   
    }

}

