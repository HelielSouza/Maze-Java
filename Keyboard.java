import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Keyboard {
    private static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
    
    //métodos

    //String
    public static String getString(){
        String ret = null;

        try {
            ret = keyboard.readLine();
        } catch (Exception e) {
           System.out.println(e);
        }
        return ret; 
    }

    //Byte
    public static Byte getByte() throws Exception{
        byte ret = (byte) 0;

        try {
            ret = Byte.parseByte(keyboard.readLine());

        } catch (IOException erro) {
            System.out.println(erro);
        }
        catch(NumberFormatException erro){
            throw new Exception("Byte inválido");
        }
        return ret;
    }

    //Short
    public static short getShort() throws Exception{
        short ret = (short) 0;
        try {
            ret = Short.parseShort(keyboard.readLine());
        } catch (IOException e) {
            System.out.println(e);        
        }
        catch (NumberFormatException erro){
            throw new Exception("Short inválido");
        }
        return ret;
    }
    
    //Int
    public static int getInt() throws Exception{  
        int ret = 0;

        try {
            ret = Integer.parseInt(keyboard.readLine());
        } catch (IOException e) {
            System.out.println(e);
        }
        catch(NumberFormatException erro){
            throw new Exception("int invalido");
        }
        return ret;
    }

    //Long
    public static long getLong() throws Exception{
        long ret = 0L;

        try {
            ret = Long.parseLong(keyboard.readLine());
        } catch (IOException e ) {
            System.out.println(e);
        }
        catch(NumberFormatException erro){
            throw new Exception("Long inválido");
        }
        return ret;
    }

    //Float
    public static float getFloat() throws Exception{
        float ret = 0.0f;

        try {
            ret = Float.parseFloat(keyboard.readLine());
        } catch (IOException e) {
            System.out.println(e);
        }
        catch(NumberFormatException erro){
            throw new Exception("Float inválido");
        }
        return ret;
    }

    //Double
    public static double getDouble() throws Exception{
        double ret = 0.0;
         
        try {
            ret = Double.parseDouble(keyboard.readLine());

        } catch (IOException e) {
           System.out.println(e);
        }
        catch(NumberFormatException erro){
            throw new Exception("Double inválido");
        }
        return ret;
    }

    //Boolean 
    public static boolean getBoolean() throws Exception{
        boolean ret = false;

        try {
            String str = keyboard.readLine();

            if (str == null){
                throw new Exception("Boolean inválido");        
            }
            
            if (!str.equals("true") && !str.equals("false"));{
                throw new Exception("Boolean inválido");
            }

              // ret = Boolean.parseBoolean(str);  

        } catch (IOException e) {
            System.out.println(e);
        }
        return ret;
    }

    //Char
    public static char getChar() throws Exception{
        char ret = ' ';
        try {
            String str = keyboard.readLine();

            if (str.length() != 1)
                throw new Exception("Char invalido");

                ret = str.charAt(0);
        } catch (IOException e) {
            System.out.println(e);
        }
        return ret;
    }
}
