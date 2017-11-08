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
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;

class JavaClient{
	public static void main(String args[]){

        JavaTCP jtcp=new JavaTCP();
        Java_myBaseSystem jmyBase=new Java_myBaseSystem();

        

        for(int i=0;i<10;i++)
        {
            String str=String.valueOf(i);
            System.out.println(
                jtcp.tcp_text(jtcp.loadProfile_tcpInformation("profile.txt",str))
                
                        );
        }
        

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

    Java_myBaseSystem myBase=new Java_myBaseSystem();
    
    public String tcp_text(String serverInformation)
    {
        try{
            //文字列を分割（csv方式）
            String splitstr[]=serverInformation.split(",",0);

            String host=splitstr[0];
            int sendport=Integer.parseInt(splitstr[1]);
            int receport=Integer.parseInt(splitstr[2]);
            String data=splitstr[3];

            System.out.println(host+"/"+sendport+"/"+receport+"/"+data);

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

        try {
            
            //TCPの情報を尋ねる
            String host=myBase.textInfo("input host address");
            String sendport=myBase.textInfo("input host port");
            String receport=myBase.textInfo("input receport");
            String data;
            if(sendData==""){data=myBase.textInfo("input sendData");}
            else{data=sendData;}

            //CSV形式で文字列を生成
            String ServerInformation=output_csv(host,sendport,receport,data);

            return ServerInformation;
            
        } catch (Exception e) {
            return e.toString();
        }
   
    }

    //CSV形式で文字列を生成
    public String output_csv(String host,String sendport,String receport,String data)
    {
        String csvStr;
        csvStr=host+","+sendport+","+receport+","+data;
        return csvStr;
    }


    //ファイルからTCP情報を読み取る
    public String loadProfile_tcpInformation(String filepass,String sendData)
    {

        String tcpInformation="";
        
        try{
            File file = new File(filepass);
            String str;
            if (checkBeforeReadfile(file)){
              BufferedReader br = new BufferedReader(new FileReader(file));
              while((str = br.readLine()) != null){
                System.out.println(str);
                tcpInformation=tcpInformation+str+",";
              }
              br.close();

              String data;
              if(sendData==""){data=myBase.textInfo("input sendData");}
              else{data=sendData;}
              tcpInformation=tcpInformation+data;

            }else{
              System.out.println("ファイルが見つからないか開けません");
            }
          }catch(FileNotFoundException e){
            System.out.println(e);
          }catch(IOException e){
            System.out.println(e);
          }


        return tcpInformation;
    }

    //ファイルチェッカー
    private boolean checkBeforeReadfile(File file){
        if (file.exists()){if (file.isFile() && file.canRead()){return true;}}return false;}
}