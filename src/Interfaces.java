import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Caret;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interfaces extends JFrame {
    public Interfaces(){

        //-------------------------------creacion de la ventana principal-----------------------------------------------
        Toolkit medidas = Toolkit.getDefaultToolkit();
        Dimension tPantalla =medidas.getScreenSize();
        int alto = tPantalla.height;
        int ancho = tPantalla.width;
        setBounds(ancho/4,alto/4,ancho/2,alto/2);
        setTitle("Procesador de Texto");

        //-----------------------------------barra barra_menu---------------------------------------------------------
        JMenuBar barra_menu = new JMenuBar();

        menuArchivo = new JMenu("Archivos");
        menuTamagno = new JMenu("Tamaño");
        menuEditar = new JMenu("Editar");



        archivosAcciones();
        barra_menu.add(menuArchivo);





        editarAcciones();
        barra_menu.add(menuEditar);


        tamagnoFuente();
        barra_menu.add(menuTamagno);

        setJMenuBar(barra_menu);




    //---------------------------------------------------------------------------------------------------------
        PanelLayout lamina = new PanelLayout();
        add(lamina);
        setVisible(true);




    }


    //-------------------------metodos de construccion de items---------------------------------------------------------
    private void editarAcciones(){
        menuEditar.add(new JMenuItem("Copiar")).addActionListener(new DefaultEditorKit.CopyAction());
        menuEditar.add(new JMenuItem("Cortar")).addActionListener(new DefaultEditorKit.CutAction());
        menuEditar.add(new JMenuItem("Pegar")).addActionListener(new DefaultEditorKit.PasteAction());
        menuEditar.add(new JMenuItem("Seleccionar todo")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textoArea.selectAll();

            }
        });

    }

    private void  archivosAcciones(){
        menuArchivo.add(new JMenuItem("Nuevo")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               textoArea.setText("");

            }
        });

        menuArchivo.add(new JMenuItem("Abrir")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ruta="";
                ruta=obtenerRuta(true);
                GestionFicheros texto = new GestionFicheros();
                texto.lectura(textoArea, ruta);


            }
        });
        menuArchivo.add(new JMenuItem("Guardar")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ruta="", textoNuevo="";
                ruta=obtenerRuta(false);
                textoNuevo=textoArea.getText();
                GestionFicheros texto = new GestionFicheros();
                texto.escritura(textoNuevo,ruta);


                texto.escritura(textoNuevo,ruta);
            }
        });
    }


    private  void tamagnoFuente(){

        for (int i=10; i<40;i+=2){

            menuTamagno.add(new JMenuItem("" + i)).addActionListener(new StyledEditorKit.FontSizeAction("size",i));

        }

    }
    //------------------------------------otros metodos-----------------------------------------------------------------
    private String obtenerRuta(boolean o){
        String ruta="";

        JFileChooser seleccionArchivo = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto","txt");

        seleccionArchivo.setFileFilter(filter);

        if(o){
            if(seleccionArchivo.showOpenDialog(menuArchivo)==JFileChooser.APPROVE_OPTION){
                ruta=seleccionArchivo.getSelectedFile().getAbsolutePath();
            }
        }else {
            if(seleccionArchivo.showSaveDialog(menuArchivo)==JFileChooser.APPROVE_OPTION){
                ruta=seleccionArchivo.getSelectedFile().getAbsolutePath();
            }
        }



        return ruta;
    }







    private  class PanelLayout extends JPanel{
        public PanelLayout(){

            setLayout(new BorderLayout());

            //----------------------------------Construccion barra de herramientas-------------------------------------------------
            JToolBar herrasmientas = new JToolBar();

            JButton negritas=new JButton(new ImageIcon("src/icons/letra-b.png"));
            herrasmientas.add(negritas);
            negritas.addActionListener(new StyledEditorKit.BoldAction());

            JButton btnCursiva= new JButton(new ImageIcon("src/icons/fuente-cursiva.png"));
            herrasmientas.add(btnCursiva);
            btnCursiva.addActionListener(new StyledEditorKit.ItalicAction());

            JButton btnSubrayar = new JButton(new ImageIcon("src/icons/subrayar.png"));
            herrasmientas.add(btnSubrayar);
            btnSubrayar.addActionListener(new StyledEditorKit.UnderlineAction());
            herrasmientas.addSeparator();
            JButton btnizquierda =new JButton(new ImageIcon("src/icons/alinear-a-la-izquierda.png"));
            herrasmientas.add(btnizquierda);
            btnizquierda.addActionListener(new StyledEditorKit.AlignmentAction("left",0));

            JButton btnCentro=new JButton(new ImageIcon("src/icons/alinear-al-centro.png"));
            herrasmientas.add(btnCentro);
            btnCentro.addActionListener(new StyledEditorKit.AlignmentAction("centro",1));

            JButton btnDerecha=new JButton(new ImageIcon("src/icons/alinear-a-la-derecha.png"));
            herrasmientas.add(btnDerecha);
            btnDerecha.addActionListener(new StyledEditorKit.AlignmentAction("Derecha",2));

            JButton btnJustificado=new JButton(new ImageIcon("src/icons/Justificado.png"));
            herrasmientas.add(btnJustificado);
            btnJustificado.addActionListener(new StyledEditorKit.AlignmentAction("Justificado",3));

            herrasmientas.addSeparator();

            comboMenu = new JComboBox(fuentes_instaladas());
            comboMenu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String fuente=(String) comboMenu.getSelectedItem();
                    Action fontAcction =new StyledEditorKit.FontFamilyAction("fuente",fuente);
                    fontAcction.actionPerformed(e);
                }
            });
            herrasmientas.add(comboMenu);

            herrasmientas.addSeparator();

            JButton btnColor = new JButton(new ImageIcon("src/icons/color.png"));
            btnColor.setBackground(Color.BLACK);

            btnColor.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Color j= JColorChooser.showDialog(btnColor,"sahd",Color.BLACK);
                    if(j== null)j= Color.BLACK;
                    Action color = new StyledEditorKit.ForegroundAction("color",j);
                    color.actionPerformed(e);
                    btnColor.setBackground(j);

                }
            });

            herrasmientas.add(btnColor);

            JButton btnMayusculas = new JButton(new ImageIcon("src/icons/boton-de-interfaz-en-mayusculas.png"));

            btnMayusculas.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String texto = textoArea.getSelectedText().toUpperCase();
                    textoArea.replaceSelection(texto);

                }
            });
            JButton btnMinusculas = new JButton(new ImageIcon("src/icons/simbolo-de-interfaz-en-minusculas.png"));
            btnMinusculas.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String texto = textoArea.getSelectedText().toLowerCase();
                    textoArea.replaceSelection(texto);

                }
            });

            herrasmientas.add(btnMayusculas);
            herrasmientas.add(btnMinusculas);


            add(herrasmientas,BorderLayout.NORTH);
            //---------------------------------------Area de texto---------------------------------------------------------
            textoArea = new JTextPane();
            add(textoArea,BorderLayout.CENTER);


        }


        //-------------------------------otros metodos-----------------------------------------------------------------
        private String[] fuentes_instaladas(){

            GraphicsEnvironment miEntorno = GraphicsEnvironment.getLocalGraphicsEnvironment();

            String[] nFuentes = miEntorno.getAvailableFontFamilyNames();

            return nFuentes;
        }


        private JComboBox comboMenu;

    }

    private JTextPane textoArea;
    private JMenu menuArchivo, menuTamagno, menuEditar;
}


