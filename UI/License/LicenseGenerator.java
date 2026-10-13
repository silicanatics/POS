import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Base64;

class LicenseGenerator {

    static String getWindowsMotherBoardSerialNumber() throws IOException {

        String result = null;
        Process p = Runtime.getRuntime().exec("wmic baseboard get serialnumber");
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = input.readLine()) != null) {
            result += line;
        }
        if (result.equalsIgnoreCase(" ")) {
            System.out.println("Result is empty");
        }
        // } else
        // {
        // motherboard.setText(result);
        // }
        input.close();

        return result.substring(result.indexOf("SerialNumber") + "SerialNumber".length()).trim();

    }

    

    public static void main(String[] args) throws IOException {

        
        String serialNumber = getWindowsMotherBoardSerialNumber();
        Base64.Encoder encoder = Base64.getEncoder(); 
        if(args.length == 0){
            args = new String[3];
            args[0] = "11"; 
            args[1] = "admin"; 
            args[2] = "admin123";          
        }
        LocalDate date = LocalDate.now().plusDays(Integer.parseInt(args[0]));  
        String infoToEncrypt = serialNumber + ":" + date;
        String encodedInfo = encoder.encodeToString(infoToEncrypt.getBytes());
        System.out.println(encodedInfo);  

        Base64.Decoder decoder = Base64.getDecoder();
        String dStr = new String(decoder.decode(encodedInfo));
        PersistLicense persistLicense = new PersistLicense(encodedInfo, args[1], args[2]);
        System.out.println(persistLicense.updateDatabase());
        System.out.println(dStr);


    }
}