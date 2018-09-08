package custom;

import burp.IHttpRequestResponse;
import com.sun.jna.Library;
import com.sun.jna.Native;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

public class GUI extends javax.swing.JPanel {

    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        jPanel1.setVisible(false);
    }

    //获取当前程序目录
    public static String getPath() {
        try {
            File directory = new File("");//参数为空 
            String courseFile = directory.getCanonicalPath();
            return courseFile;
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return null;
    }

    //通用识别的dll调用
    public interface cnn extends Library {
        cnn INSTANCE = (cnn) Native.loadLibrary(getPath() + "\\discriminate\\cnn\\cnn.dll", cnn.class);//载入dll
        public boolean LoadCnnFromFile(String FilePath); //载入字库
        public String GetImageFromFile(String imgPath); //识别图片
    }

    //完美验证码的dll调用
    public interface wm extends Library {
        wm INSTANCE = (wm) Native.loadLibrary(getPath() + "\\discriminate\\wm\\wm.dll", wm.class);//载入dll     
        public boolean LoadWmFromFile(String FilePath, String Password); //加载识别库      
        public boolean GetImageFromFile(String FilePath, byte[] Vcode);  //识别验证码    
    }

    //打开完美验证码字库路径
    public void getFP() {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            txtFontPath.setText(file.getAbsolutePath());
        }
        fontName = txtFontPath.getText().trim();
    }

    //将验证码图片写入文件，并返回路径
    public String getImage() {
        String httpservice = imgHttpService.getText();
        String httpRaws = imgRequestRaws.getText();
        requestHelper x = new requestHelper();
        x.httpservice = httpservice;
        x.raws = httpRaws;
        x.parser();
        try {
            byte[] bytes = x.dorequest();
            String imgpath = x.writeImageToDisk(bytes);
            return imgpath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imgHttpService = new javax.swing.JTextField();
        recognitionMethod = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jblImgCode = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        imgRequestRaws = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCode = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtFontPath = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtFontPass = new javax.swing.JTextField();

        recognitionMethod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "通用字库识别", "完美验证码识别" }));
        recognitionMethod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                recognitionMethodItemStateChanged(evt);
            }
        });

        jButton1.setText("识别测试");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jblImgCode.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        imgRequestRaws.setColumns(20);
        imgRequestRaws.setRows(5);
        imgRequestRaws.setAutoscrolls(false);
        jScrollPane2.setViewportView(imgRequestRaws);

        jLabel3.setText("识别方式：");

        jLabel5.setText("识别结果:");

        txtCode.setText(" ");
        txtCode.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setText("识别库：");

        txtFontPath.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFontPathFocusGained(evt);
            }
        });

        jLabel4.setText("识别库密码：");

        txtFontPass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFontPassFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFontPath, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(txtFontPass))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtFontPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtFontPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(recognitionMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jblImgCode, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(383, 383, 383))
                    .addComponent(imgHttpService)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(imgHttpService, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jblImgCode, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCode)
                            .addComponent(jLabel5)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(recognitionMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addGap(30, 30, 30)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(123, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void recognitionMethodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_recognitionMethodItemStateChanged
        // 识别方式变换，字库路径，密码面板显示
        if (recognitionMethod.getSelectedIndex() == 0) {
            jPanel1.setVisible(false);
        } else {
            jPanel1.setVisible(true);
        }
    }//GEN-LAST:event_recognitionMethodItemStateChanged

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // 识别按钮
        if (recognitionMethod.getSelectedIndex() == 0) {//判断识别方式
            jPanel1.setVisible(false);
            try {
                String imgpath = getImage();
                Image image = ImageIO.read(new File(imgpath));
                ImageIcon icon = new ImageIcon(image);
                jblImgCode.setIcon(icon);
                cnn.INSTANCE.LoadCnnFromFile(getPath() + "\\discriminate\\cnn\\Caffe.model");
                String code = cnn.INSTANCE.GetImageFromFile(imgpath);
                System.out.println(code);
                txtCode.setText(code);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            jPanel1.setVisible(true);
            if ("".equals(txtFontPath.getText().trim()) || "".equals(txtFontPass.getText().trim())) {
                getFP();
            } else {
                try {
                    String imgpath = getImage();
                    Image image = ImageIO.read(new File(imgpath));
                    ImageIcon icon = new ImageIcon(image);
                    jblImgCode.setIcon(icon);
                    byte[] code = new byte[10];
                    if (wm.INSTANCE.LoadWmFromFile(fontName, fontPass)) {
                        wm.INSTANCE.GetImageFromFile(imgpath, code);
                        String c = new String(code);
                        txtCode.setText(c);
                    } else {
                        System.out.println(fontName);
                        System.out.println(fontPass);
                        System.out.println("wrong pass!");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void txtFontPassFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFontPassFocusLost
        //输入完密码后，获取字库密码
        fontPass = txtFontPass.getText().trim();
        System.out.println(fontPass);
    }//GEN-LAST:event_txtFontPassFocusLost

    private void txtFontPathFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFontPathFocusGained
        //选择字库路径
        if ("".equals(txtFontPath.getText().trim())) {
            getFP();
        }
    }//GEN-LAST:event_txtFontPathFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField imgHttpService;
    public javax.swing.JTextArea imgRequestRaws;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel jblImgCode;
    public javax.swing.JComboBox<String> recognitionMethod;
    private javax.swing.JLabel txtCode;
    private javax.swing.JTextField txtFontPass;
    private javax.swing.JTextField txtFontPath;
    // End of variables declaration//GEN-END:variables
    public IHttpRequestResponse MessageInfo;
    public String currentPath;
    public String fontName;
    public String fontPass;
}
