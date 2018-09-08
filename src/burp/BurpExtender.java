package burp;

import com.sun.jna.Library;
import com.sun.jna.Native;
import custom.GUI;
import static custom.GUI.getPath;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;


public class BurpExtender implements IBurpExtender, ITab, IContextMenuFactory, IIntruderPayloadGeneratorFactory, IIntruderPayloadGenerator {

    private static IBurpExtenderCallbacks callbacks;
    private IExtensionHelpers helpers;//一些辅助方法
    public PrintWriter stdout;//输出
    private final String extenderName = "reCAPTCHA by Releasel0ck"; //插件名称
    private final String blogURL = "http://www.releasel0ck.top";//haha,打一个自己的印记
    private GUI GUI; //图形界面
    public IHttpRequestResponse imgMessageInfo;//用于检索和更新有关http消息的详细信息。
    public boolean flag = false;//字库载入标记

    //在加载扩展名时调用此方法。它注册的iburpextendercallbacks接口的实例，提供的方法，可以通过扩展来实现各种动作。
    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        //载入插件输出
        stdout = new PrintWriter(callbacks.getStdout(), true);
        stdout.println(extenderName);
        stdout.println(blogURL);
        this.callbacks = callbacks;
        helpers = callbacks.getHelpers();
        callbacks.setExtensionName(extenderName);//设置插件名称
        callbacks.registerContextMenuFactory(this);//自定义上下文菜单项注册工厂
        callbacks.registerIntruderPayloadGeneratorFactory(this);//效载荷注册工厂
        addMenuTab();
    }

    /*--------------------------------------------------以下是各种burp必须的方法--------------------------------------------------------------*/
    public void addMenuTab() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUI = new GUI();
                callbacks.addSuiteTab(BurpExtender.this);
            }
        });
    }

     //ITab必须实现的两个方法
    @Override
    public String getTabCaption() {
        return extenderName;//返回名称
    }

    @Override
    public Component getUiComponent() {
        return GUI;//返回图形界面
    }

    //实现菜单
    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation invocation) {
        IHttpRequestResponse[] messages = invocation.getSelectedMessages();
        List<JMenuItem> list = new ArrayList<JMenuItem>();
        if ((messages != null) && (messages.length == 1)) {
            imgMessageInfo = messages[0];
            JMenuItem menuItem = new JMenuItem("Send to reCAPTCHA");
            menuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        GUI.MessageInfo = imgMessageInfo;
                        GUI.imgRequestRaws.setText(new String(imgMessageInfo.getRequest()));
                        IHttpService httpservice = imgMessageInfo.getHttpService();
                        String host = httpservice.getHost();
                        int port = httpservice.getPort();
                        String protocol = httpservice.getProtocol();
                        String shortUrl = protocol + "://" + host + ":" + port;
                        GUI.imgHttpService.setText(shortUrl);
                    } catch (Exception e1) {
                        BurpExtender.this.callbacks.printError(e1.getMessage());
                    }
                }
            });
            list.add(menuItem);
        }
        return list;
    }

    //IIntruderPayloadGeneratorFactory 所需实现的2个函数
    @Override
    public String getGeneratorName() {
        return extenderName;
    }

    @Override
    public IIntruderPayloadGenerator createNewInstance(IIntruderAttack attack) {
        return this;
    }

    //IIntruderPayloadGenerator 所需实现的三个函数
    @Override
    public boolean hasMorePayloads() {
        return true;
    }

    //返回识别结果
    @Override
    public byte[] getNextPayload(byte[] baseValue) {
        if (imgMessageInfo != null) {
            try {
                String imgpath = GUI.getImage();
                if (GUI.recognitionMethod.getSelectedIndex() == 0) {//判断识别方式
                    if (flag) {//是否已经载入过字库
                        cnn.INSTANCE.LoadCnnFromFile(GUI.getPath() + "\\discriminate\\cnn\\Caffe.model");
                        flag = true;
                        String code = cnn.INSTANCE.GetImageFromFile(imgpath);
                        return code.getBytes();//返回验证码结果
                    } else {
                        String code = cnn.INSTANCE.GetImageFromFile(imgpath);
                        return code.getBytes();
                    }
                } else {
                    byte[] code = new byte[10];
                    if (flag) {
                        if (wm.INSTANCE.LoadWmFromFile(GUI.fontName, GUI.fontPass)) {
                            flag = true;
                            wm.INSTANCE.GetImageFromFile(imgpath, code);
                            return code;
                        } else {
                            stdout.println("wrong pass!");
                        }

                    } else {
                        wm.INSTANCE.GetImageFromFile(imgpath, code);      
                        String tmpCode= new String(code);
                        byte[] newCode=tmpCode.trim().getBytes();
                        return newCode;
                    }
                }
                return null;
            } catch (Exception e) {
                return e.getMessage().getBytes();
            }
        } else {
            stdout.println("Failed try!!! please send image request to reCAPTCHA first!");
            return null;
        }
    }

    @Override
    public void reset() {
    }

    /*--------------------------------------------------以是各种burp必须的方法--------------------------------------------------------------*/
    /*--------------------------------------------------以下是自定义的方法-------------------------------------------------------------------*/
    public interface cnn extends Library {
        cnn INSTANCE = (cnn) Native.loadLibrary(getPath() + "\\discriminate\\cnn\\cnn.dll", cnn.class);
        public boolean LoadCnnFromFile(String FilePath); //载入字库
        public String GetImageFromFile(String imgPath); //识别图片
    }

    public interface wm extends Library {
        wm INSTANCE = (wm) Native.loadLibrary(getPath() + "\\discriminate\\wm\\wm.dll", wm.class);//载入dll     
        public boolean LoadWmFromFile(String FilePath, String Password); //加载识别库      
        public boolean GetImageFromFile(String FilePath, byte[] Vcode);  //识别验证码    
    }
    /*--------------------------------------------------以上是自定义的方法-------------------------------------------------------------------*/
}
