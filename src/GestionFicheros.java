
import javax.swing.*;
import java.io.*;

public class GestionFicheros {


    public void escritura(String texto, String ruta){
        if(!ruta.isEmpty()){
            try {

                FileWriter entradaNueva = new FileWriter(ruta, false);
                BufferedWriter miBuffer = new BufferedWriter(entradaNueva);

                miBuffer.write(texto);


                miBuffer.close();
                entradaNueva.close();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }


    }


    public void lectura(JTextPane areaTexto, String ruta){

        FileInputStream entrada =null;

        if(!ruta.isEmpty()){
            try {

                entrada = new FileInputStream(ruta);

                BufferedInputStream mibuffer = new BufferedInputStream(entrada);

                int cont=mibuffer.available();
                byte datosEntrada[] = new byte[cont];
                boolean final_ar = false;
                int index=0;

                while (!final_ar){
                    int bytes = mibuffer.read();
                    if(bytes==-1){
                        final_ar=true;
                    }else{
                        datosEntrada[index]=(byte)bytes;
                        index++;
                    }
                }
                String texto = new String(datosEntrada);
                areaTexto.setText(texto);
                mibuffer.close();
                entrada.close();

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

}
