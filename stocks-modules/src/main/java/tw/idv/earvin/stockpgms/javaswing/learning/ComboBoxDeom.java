package tw.idv.earvin.stockpgms.javaswing.learning;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;

public class ComboBoxDeom extends JFrame {
  JComboBox combo = new JComboBox();

  public ComboBoxDeom() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    combo.addItem("A");
    combo.addItem("H");
    combo.addItem("P");
    combo.setEditable(true);
    System.out.println("#items=" + combo.getItemCount());

    combo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println("Selected index=" + combo.getSelectedIndex()
            + " Selected item=" + combo.getSelectedItem());
      }
    });

    getContentPane().add(combo);
    pack();
    setVisible(true);
  }

  public static void main(String arg[]) {
    new ComboBoxDeom();
  }
}