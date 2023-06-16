import com.fazecast.jSerialComm.*;

public class Javino {

    private SerialPort serialPort = null;
    private String finalMessage = null;

    byte[] preamble = new byte[4];

    byte[] sizeMessage = new byte[2];
    private boolean last;


    public Javino(String portDescriptor){
        this.serialPort = SerialPort.getCommPort(portDescriptor);
        this.serialPort.setBaudRate(9600);
        this.serialPort.openPort();
    }

    //public void openPort(){this.serialPort.openPort();}
    //public void closePort(){this.serialPort.closePort();}

    public boolean availablemsg() {
            if (serialPort.readBytes(preamble,4) > 0){
                if (((preamble[0] & preamble[1] & preamble[2]) == 102) & (preamble[3] == 101 )){
                    if (serialPort.readBytes(sizeMessage,2) > 0){
                        int sizeOfMsg = Integer.parseUnsignedInt(new String(sizeMessage), 16);
                        byte[] byteMSG = new byte[sizeOfMsg];
                        if(serialPort.readBytes(byteMSG,sizeOfMsg) == sizeOfMsg){
                            this.finalMessage = new String(byteMSG);
                            return true;
                        }
                    }
                }
            }
        System.out.print("\r" + (last ? ">" : " ")); last = !last;
        return false;
    }

    public String getmsg(){
        return this.finalMessage;
    }

    public void sendmsg(String strMSG){
        String out = "fffe"+String.format("%02X", strMSG.length())+strMSG;
        byte[] messageBytes = out.getBytes();
        this.serialPort.writeBytes(messageBytes, messageBytes.length);
    }
}
