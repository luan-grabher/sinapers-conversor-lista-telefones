

import View.Home;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Conversor_Lista_Telefonica {


    public static void main(String[] args) {
        JFrame home = new Home();
        home.setVisible(true);
        
        ImageIcon img = new ImageIcon("./icone.png");

        home.setIconImage(img.getImage());
    }
    
}
