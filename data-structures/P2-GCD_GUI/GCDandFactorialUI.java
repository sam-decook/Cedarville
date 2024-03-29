package com.mycompany.project2;

/**
 *
 * @author samdecook
 */
public class GCDandFactorialUI extends javax.swing.JFrame {

    /**
     * Creates new form GCDandFactorialUI
     */
    public GCDandFactorialUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        GCDDialog = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        GCDFirstNum = new javax.swing.JTextField();
        GCDSecondNum = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        GCDTextField = new javax.swing.JTextField();
        exitGCDButton = new javax.swing.JButton();
        computeGCDButton = new javax.swing.JButton();
        FactorialDialog = new javax.swing.JDialog();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        factorialNumber = new javax.swing.JTextField();
        exitFactorialButton = new javax.swing.JButton();
        computeFactorialButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        factorialTextField = new javax.swing.JTextField();
        errorDialog = new javax.swing.JDialog();
        jLabel8 = new javax.swing.JLabel();
        errorLabel = new javax.swing.JLabel();
        errorDialogExitButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        computeMenu = new javax.swing.JMenu();
        computeFactorialMenuItem = new javax.swing.JMenuItem();
        computeGCDMenuItem = new javax.swing.JMenuItem();

        GCDDialog.setMinimumSize(new java.awt.Dimension(283, 220));
        GCDDialog.setPreferredSize(new java.awt.Dimension(283, 220));

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GCD");

        jLabel2.setText("First Number: ");
        jLabel2.setToolTipText("");

        jLabel3.setText("Second Number:");

        jLabel4.setText("GCD:");

        exitGCDButton.setText("Exit");
        exitGCDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitGCDButtonActionPerformed(evt);
            }
        });

        computeGCDButton.setText("Compute");
        computeGCDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                computeGCDButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout GCDDialogLayout = new javax.swing.GroupLayout(GCDDialog.getContentPane());
        GCDDialog.getContentPane().setLayout(GCDDialogLayout);
        GCDDialogLayout.setHorizontalGroup(
            GCDDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GCDDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(computeGCDButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                .addComponent(exitGCDButton)
                .addContainerGap())
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(GCDDialogLayout.createSequentialGroup()
                .addGroup(GCDDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GCDDialogLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel4))
                    .addGroup(GCDDialogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2))
                    .addGroup(GCDDialogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(GCDDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GCDFirstNum)
                    .addComponent(GCDSecondNum)
                    .addComponent(GCDTextField)))
        );
        GCDDialogLayout.setVerticalGroup(
            GCDDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GCDDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(GCDDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(GCDFirstNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(GCDDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(GCDSecondNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(GCDDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(GCDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(GCDDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exitGCDButton)
                    .addComponent(computeGCDButton))
                .addGap(11, 11, 11))
        );

        FactorialDialog.setMinimumSize(new java.awt.Dimension(250, 200));
        FactorialDialog.setPreferredSize(new java.awt.Dimension(250, 200));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Factorial");

        jLabel6.setText("Enter Number");

        exitFactorialButton.setText("Exit");
        exitFactorialButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitFactorialButtonActionPerformed(evt);
            }
        });

        computeFactorialButton.setText("Compute");
        computeFactorialButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                computeFactorialButtonActionPerformed(evt);
            }
        });

        jLabel7.setText("Factorial");

        javax.swing.GroupLayout FactorialDialogLayout = new javax.swing.GroupLayout(FactorialDialog.getContentPane());
        FactorialDialog.getContentPane().setLayout(FactorialDialogLayout);
        FactorialDialogLayout.setHorizontalGroup(
            FactorialDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FactorialDialogLayout.createSequentialGroup()
                .addGroup(FactorialDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FactorialDialogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(computeFactorialButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                        .addComponent(exitFactorialButton))
                    .addGroup(FactorialDialogLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(FactorialDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(FactorialDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(factorialTextField)
                            .addComponent(factorialNumber))))
                .addContainerGap())
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        FactorialDialogLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {computeFactorialButton, exitFactorialButton});

        FactorialDialogLayout.setVerticalGroup(
            FactorialDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FactorialDialogLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(FactorialDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(factorialNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(FactorialDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(factorialTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(FactorialDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exitFactorialButton)
                    .addComponent(computeFactorialButton))
                .addContainerGap())
        );

        errorDialog.setMinimumSize(new java.awt.Dimension(352, 180));
        errorDialog.setPreferredSize(new java.awt.Dimension(353, 180));

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(200, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("ERROR");

        errorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        errorDialogExitButton.setText("Exit");
        errorDialogExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                errorDialogExitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout errorDialogLayout = new javax.swing.GroupLayout(errorDialog.getContentPane());
        errorDialog.getContentPane().setLayout(errorDialogLayout);
        errorDialogLayout.setHorizontalGroup(
            errorDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(errorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(errorDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(errorDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, errorDialogLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(errorDialogExitButton)))
                .addContainerGap())
        );
        errorDialogLayout.setVerticalGroup(
            errorDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(errorDialogLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(errorLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(errorDialogExitButton)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        fileMenu.setText("File");

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        jMenuBar1.add(fileMenu);

        computeMenu.setText("Compute");

        computeFactorialMenuItem.setText("Factorial");
        computeFactorialMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                factorialMenuItemActionPerformed(evt);
            }
        });
        computeMenu.add(computeFactorialMenuItem);

        computeGCDMenuItem.setText("GCD");
        computeGCDMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GCDMenuItemActionPerformed(evt);
            }
        });
        computeMenu.add(computeGCDMenuItem);

        jMenuBar1.add(computeMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 487, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 299, Short.MAX_VALUE)
        );

        pack();
}// </editor-fold>                        


private void GCDMenuItemActionPerformed (java.awt.event.ActionEvent evt) {                                            
GCDDialog.setVisible(true);
}                                           

private void exitMenuItemActionPerformed (java.awt.event.ActionEvent evt) {                                             
System.exit(0);
}                                            

private void factorialMenuItemActionPerformed (java.awt.event.ActionEvent evt) {                                                  
FactorialDialog.setVisible(true);
}                                                 

private void exitGCDButtonActionPerformed (java.awt.event.ActionEvent evt) {                                              
GCDDialog.setVisible(false);
}                                             

private void exitFactorialButtonActionPerformed (java.awt.event.ActionEvent evt) {                                                    
FactorialDialog.setVisible(false);
}                                                   

private void computeGCDButtonActionPerformed (java.awt.event.ActionEvent evt) {                                                 
try {
int first = Integer.parseInt(GCDFirstNum.getText());
            int second = Integer.parseInt(GCDSecondNum.getText());
int gcd = GCD.computeGCD(first, second);
GCDTextField.setText(Integer.toString(gcd));
            
} catch (NumberFormatException e) {
errorDialog.setVisible(true);
errorLabel.setText("Can only compute integers");
}
}                                                

private void computeFactorialButtonActionPerformed (java.awt.event.ActionEvent evt) {                                                       
try {
int x = Integer.parseInt(factorialNumber.getText());
double factorial;
factorial = Factorial.computeFactorial(x);
factorialTextField.setText(Integer.toString((int) factorial));
            
} catch (NegativeNumberException e) {
errorDialog.setVisible(true);
errorLabel.setText(e.getMessage());
} catch (NumberFormatException e) {
errorDialog.setVisible(true);
errorLabel.setText("Can only compute integers");
            
}
}                                                      

private void errorDialogExitButtonActionPerformed (java.awt.event.ActionEvent evt) {                                                      
errorDialog.setVisible(false);
}                                                     

/**
 * @param args the command line arguments
 */
public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GCDandFactorialUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GCDandFactorialUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GCDandFactorialUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GCDandFactorialUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GCDandFactorialUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JDialog FactorialDialog;
    private javax.swing.JDialog GCDDialog;
    private javax.swing.JTextField GCDFirstNum;
    private javax.swing.JTextField GCDSecondNum;
    private javax.swing.JTextField GCDTextField;
    private javax.swing.JButton computeFactorialButton;
    private javax.swing.JMenuItem computeFactorialMenuItem;
    private javax.swing.JButton computeGCDButton;
    private javax.swing.JMenuItem computeGCDMenuItem;
    private javax.swing.JMenu computeMenu;
    private javax.swing.JDialog errorDialog;
    private javax.swing.JButton errorDialogExitButton;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JButton exitFactorialButton;
    private javax.swing.JButton exitGCDButton;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JTextField factorialNumber;
    private javax.swing.JTextField factorialTextField;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration                   
} 
