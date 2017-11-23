import java.io.*;
import java.net.*;

class JavaClient{
	public static void main(String args[]){

        JavaTCP jtcp=new JavaTCP();
        //Java_myBaseSystem jmyBase=new Java_myBaseSystem();
        System.out.println
        (
            jtcp.tcp_text(jtcp.loadProfile_tcpInformation("profile.txt",""))
        );
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
            
        }catch (Exception e) {
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
            int port=Integer.parseInt(splitstr[1]);
            String data=splitstr[2];
            System.out.println(host+"/"+port+"/"+data);

            //InputStreamReader is = new InputStreamReader(data);
            //BufferedReader br = new BufferedReader(is);
            

            //指定サーバにデータを送る
			Socket s = new Socket(host, port);
            OutputStream os = s.getOutputStream();
            
            InputStream sok_is = s.getInputStream();
			InputStreamReader sok_isr = new InputStreamReader(sok_is);
			BufferedReader sok_br = new BufferedReader(sok_isr);
            
            //data = br.readLine();	//キー1行入力
            os.write(data.getBytes());//送信
            
            String receive = sok_br.readLine();//受信データ取得
            //System.out.println("受信『" + receive + "』");
    


            s.shutdownOutput();
            s.close();
            return receive;


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
            String data;
            if(sendData==""){data=myBase.textInfo("input sendData");}
            else{data=sendData;}

            //CSV形式で文字列を生成
            String ServerInformation=output_csv(host,sendport,data);

            return ServerInformation;
            
        } catch (Exception e) {
            return e.toString();
        }
   
    }

    //CSV形式で文字列を生成
    public String output_csv(String host,String sendport,String data)
    {
        String csvStr;
        csvStr=host+","+sendport+","+data;
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
    private boolean checkBeforeReadfile(File file)
    {
        if (file.exists()){if (file.isFile() && file.canRead()){return true;}}return false;
    }
}