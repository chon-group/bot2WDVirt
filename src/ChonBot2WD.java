import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ChonBot2WD extends JFrame{
    private JPanel panelMain;
    private JComboBox cBoxAlert;
    private JComboBox cBoxBreakLight;
    private JComboBox cBoxLight;
    private JComboBox cBoxMotorSpeed;
    private JComboBox cBoxMotorStatus;
    private JComboBox cBoxBuzzer;
    private JSlider jSliderDistance;
    private JSlider jSliderLuminosity;
    private JComboBox cBoxUSB;
    private JLabel jlInfo;
    Javino javino = null;

//    public ChonBot2WD() {
//        //Update JScroll
//        jtextComm.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                jtextComm.setCaretPosition(jtextComm.getDocument().getLength());
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//
//            }
//        });
//    }

    public String getStatus(){
        String out = "flashLight("+cBoxAlert.getSelectedItem()+");"+
                "light("+cBoxLight.getSelectedItem()+");"+
                "breakL("+cBoxBreakLight.getSelectedItem()+");"+
                "buzzer("+cBoxBuzzer.getSelectedItem()+");"+
                "luminosity("+jSliderLuminosity.getValue()+");"+
                "distance("+jSliderDistance.getValue()+");"+
                "motor("+cBoxMotorStatus.getSelectedItem()+");"+
                "speed("+cBoxMotorSpeed.getSelectedItem()+");";
        serialMonitor("<-- " + out);
        return out;
    }

    private void serialMonitor(String strSerial){
        //jtextComm.append("\n"+strSerial);
        //jlInfo.setText(strSerial);
        System.out.println(strSerial);
    }
    public void loop(){
            if(javino.availablemsg()){
                String strMsg = javino.getmsg();
                serialMonitor("--> "+strMsg);
                if(strMsg.equals("getPercepts"))javino.sendmsg(getStatus());
                else if(strMsg.equals("buzzerOnH"))buzzer("high");
                else if(strMsg.equals("buzzerOn"))buzzer("on");
                else if(strMsg.equals("buzzerOnL"))buzzer("low");
                else if(strMsg.equals("buzzerOff"))buzzer("off");
                else if(strMsg.equals("lightOnH"))light("high");
                else if(strMsg.equals("lightOn"))light("on");
                else if(strMsg.equals("lightOnL"))light("low");
                else if(strMsg.equals("lightOff"))light("off");
                else if(strMsg.equals("breakLOn"))breakL("on");
                else if(strMsg.equals("breakLOff"))breakL("off");
                else if(strMsg.equals("stop"))stopRightNow();
                else if(strMsg.equals("goLeft"))turnLeft();
                else if(strMsg.equals("goRight"))turnRight();
                else if(strMsg.equals("goAhead"))goAhead();
                else if(strMsg.equals("goBack"))goBack();
                else if(strMsg.equals("speedH"))setMotorSpeed("high");
                else if(strMsg.equals("speedM"))setMotorSpeed("default");
                else if(strMsg.equals("speedL"))setMotorSpeed("low");
                else if(strMsg.equals("alertOn")) flashlight("alert","on");
                else if(strMsg.equals("flashROn")) flashlight("right","on");
                else if(strMsg.equals("flashLOn")) flashlight("left","on");
                else if(strMsg.equals("flashLightOff")) flashlight("all","off");
            }
    }


    private void flashlight(String light, String status) {
        if(status.equals("off"))cBoxAlert.setSelectedItem("flashLightOff");
        else if (light.equals("alert")) cBoxAlert.setSelectedItem("alertOn");
        else if (light.equals("right")) cBoxAlert.setSelectedItem("flashROn");
        else if (light.equals("left"))  cBoxAlert.setSelectedItem("flashLOn");
    }

    private void setMotorSpeed(String strOpt){
        if(strOpt.equals("default"))    cBoxMotorSpeed.setSelectedItem("speedM");
        else if(strOpt.equals("high"))  cBoxMotorSpeed.setSelectedItem("speedH");
        else if(strOpt.equals("low"))   cBoxMotorSpeed.setSelectedItem("speedL");
    }

    private void goBack() {
        cBoxMotorStatus.setSelectedItem("goBack");
    }

    private void goAhead() {
        cBoxMotorStatus.setSelectedItem("goAhead");
    }

    private void turnRight() {
        cBoxMotorStatus.setSelectedItem("goRight");
    }

    private void turnLeft() {
        cBoxMotorStatus.setSelectedItem("goLeft");
    }

    private void stopRightNow() {
        cBoxMotorStatus.setSelectedItem("stop");
    }

    public void powerOn(String usbPort){
        setContentPane(panelMain);
        setTitle("ChonBot Virtual 2WD");
        setResizable(false);
        setSize(1000,450);
        resources();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        javino = new Javino(usbPort);
        setup(usbPort);
        while (true){
            loop();
//            try{
//                Thread.sleep(100);
//            }catch(Exception ex){
//
//            }

        }

    }

    private void setup(String strSerialPort){
        cBoxUSB.addItem(strSerialPort);
        cBoxUSB.setSelectedItem(strSerialPort);
        cBoxMotorStatus.setSelectedItem("stop");
        cBoxMotorSpeed.setSelectedItem("speedM");
        cBoxLight.setSelectedItem("lightOff");
        cBoxBuzzer.setSelectedItem("buzzerOff");
        cBoxAlert.setSelectedItem("flashLightOff");
        cBoxBreakLight.setSelectedItem("breakLOff");
    }

    private void resources(){
        cBoxMotorStatus.addItem("goAhead");
        cBoxMotorStatus.addItem("goLeft");
        cBoxMotorStatus.addItem("goRight");
        cBoxMotorStatus.addItem("goBack");
        cBoxMotorStatus.addItem("stop");

        cBoxMotorSpeed.addItem("speedH");
        cBoxMotorSpeed.addItem("speedM");
        cBoxMotorSpeed.addItem("speedL");

        cBoxLight.addItem("lightOn");
        cBoxLight.addItem("lightOnH");
        cBoxLight.addItem("lightOnL");
        cBoxLight.addItem("lightOff");

        cBoxBuzzer.addItem("buzzerOn");
        cBoxBuzzer.addItem("buzzerOnH");
        cBoxBuzzer.addItem("buzzerOnL");
        cBoxBuzzer.addItem("buzzerOff");

        cBoxAlert.addItem("alertOn");
        cBoxAlert.addItem("flashROn");
        cBoxAlert.addItem("flashLOn");
        cBoxAlert.addItem("flashLightOff");

        cBoxBreakLight.addItem("breakLOn");
        cBoxBreakLight.addItem("breakLOff");
    }

    private void breakL(String strOpt){
        if(strOpt.equals("on"))         cBoxBreakLight.setSelectedItem("breakLOn");
        else                            cBoxBreakLight.setSelectedItem("breakLOff");
    }
    private void buzzer(String strOpt){
        if(strOpt.equals("on"))         cBoxBuzzer.setSelectedItem("buzzerOn");
        else if(strOpt.equals("high"))  cBoxBuzzer.setSelectedItem("buzzerOnH");
        else if(strOpt.equals("low"))   cBoxBuzzer.setSelectedItem("buzzerOnL");
        else                            cBoxBuzzer.setSelectedItem("buzzerOff");
    }

    private void light(String strOpt){
        if(strOpt.equals("on"))         cBoxLight.setSelectedItem("lightOn");
        else if(strOpt.equals("high"))  cBoxLight.setSelectedItem("lightOnH");
        else if(strOpt.equals("low"))   cBoxLight.setSelectedItem("lightOnL");
        else                            cBoxLight.setSelectedItem("lightOff");
    }
    
}
