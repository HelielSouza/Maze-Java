import java.io.*;

public class File {

    private BufferedReader file;

    public File(String fileName){
        try {
            file = new BufferedReader(new FileReader(fileName));
        } catch (Exception e) {
            System.err.println("Arquivo invalido");
        }
    }

    public String getAString(){
        String ret = null;

        try{
            ret = file.readLine();
        }
        catch (IOException error){
            System.out.println(error);
        }
        return ret;
    }

    public Byte getAByte() throws Exception{
        byte ret = (byte) 0;

        try {
            ret = Byte.parseByte(file.readLine());

        } catch (IOException error) {
            System.out.println(error);
        }
        catch(NumberFormatException error){
            throw new Exception("Byte inválido");
        }
        return ret;
    }

    public short getAShort() throws Exception{
        short ret = (short) 0;

        try {
            ret = Short.parseShort(file.readLine());
        } catch (IOException e) {
            System.out.println(e);        
        }
        catch (NumberFormatException error){
            throw new Exception("Short inválido");
        }
        return ret;
    }

    public int getAnInt() throws Exception{  
        int ret = 0;

        try {
            ret = Integer.parseInt(file.readLine());
        } catch (IOException e) {
            System.out.println(e);
        }
        catch(NumberFormatException error){
            throw new Exception("Int invalido");
        }
        return ret;
    }

    public long getALong() throws Exception{
        long ret = 0L;

        try {
            ret = Long.parseLong(file.readLine());
        } catch (IOException e ) {
            System.out.println(e);
        }
        catch(NumberFormatException error){
            throw new Exception("Long inválido");
        }
        return ret;
    }

    public float getAFloat() throws Exception{
        float ret = 0.0f;

        try {
            ret = Float.parseFloat(file.readLine());
        } catch (IOException e) {
            System.out.println(e);
        }
        catch(NumberFormatException error){
            throw new Exception("Float inválido");
        }
        return ret;
    }

    public double getADouble() throws Exception{
        double ret = 0.0;
         
        try {
            ret = Double.parseDouble(file.readLine());

        } catch (IOException e) {
           System.out.println(e);
        }
        catch(NumberFormatException error){
            throw new Exception("Double inválido");
        }
        return ret;
    }

    public boolean getABoolean() throws Exception{
        boolean ret = false;

        try {
           String str = file.readLine();

            if (str == null){
                throw new Exception("Boolean inválido");        
            }
            
            if (!str.equals("true") && !str.equals("false"));{
                throw new Exception("Boolean inválido");
            }

               //ret = Boolean.parseBoolean(str);  

        } catch (IOException e) {
            System.out.println(e);
        }
        return ret;
    }

    public char getAChar() throws Exception{
        char ret = ' ';
        try {
            String str = file.readLine();

            if (str.length() != 1)
                throw new Exception("Char invalido");

                ret = str.charAt(0);
                
        } catch (IOException e) {
            System.out.println(e);
        }
        return ret;
    }
}

